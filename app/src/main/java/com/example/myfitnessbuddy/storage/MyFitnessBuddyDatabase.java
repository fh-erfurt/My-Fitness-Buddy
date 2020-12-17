package com.example.myfitnessbuddy.storage;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.example.myfitnessbuddy.model.MuscleGroup;
import com.example.myfitnessbuddy.model.Person;
import com.example.myfitnessbuddy.model.Training.Category;


import com.example.myfitnessbuddy.model.Training.CategoryConverter;
import com.example.myfitnessbuddy.model.Training.Training;
import com.example.myfitnessbuddy.model.exercise.Exercise;
import com.github.javafaker.Faker;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database( entities = {Person.class, Training.class, MuscleGroup.class,Exercise.class}, version = 8 )
@TypeConverters({CategoryConverter.class})
public abstract class MyFitnessBuddyDatabase extends RoomDatabase {

    private static final String LOG_TAG_DB = "MyFitnessBuddyDB";

    public abstract PersonDao personDao();
    public abstract TrainingDao trainingDao();
    public abstract MuscleGroupDao muscleGroupDao();
    public abstract ExerciseDao exerciseDao();


    /*
        Executor service to perform database operations asynchronous and independent from UI thread
     */
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool( NUMBER_OF_THREADS );

    /*
        Singleton Instance
     */
    private static volatile MyFitnessBuddyDatabase INSTANCE;

    /*
        Helper methods to ease external usage of ExecutorService
        e.g. perform async database operations
     */
    public static <T> T executeWithReturn(Callable<T> task)
            throws ExecutionException, InterruptedException
    {
        return databaseWriteExecutor.invokeAny( Collections.singletonList( task ) );
    }

    public static void execute( Runnable runnable )
    {
        databaseWriteExecutor.execute( runnable );
    }

    /*
        Singleton 'getInstance' method to create database instance thereby opening and, if not
        already done, init the database. Note the 'createCallback'.
     */
    static MyFitnessBuddyDatabase getDatabase(final Context context) {
        Log.i( LOG_TAG_DB, "getDatabase() called" );
        if (INSTANCE == null) {
            synchronized (MyFitnessBuddyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyFitnessBuddyDatabase.class, "myfitnessbuddy_db")
                            .addCallback(createCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /*
        Create DB Callback
        Used to add some initial data
     */
    private static final Callback createCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Log.i(LOG_TAG_DB, "onCreate() called");

            execute(() -> {
                PersonDao dao = INSTANCE.personDao();
                dao.deleteAll();

                TrainingDao daoTraining = INSTANCE.trainingDao();
                daoTraining.deleteAll();

                MuscleGroupDao daoMuscleGroup = INSTANCE.muscleGroupDao();
                daoMuscleGroup.deleteAll();


                Faker faker = Faker.instance();
                for (int i = 0; i < 25; i++) {
                    Training training = new Training(faker.team().sport(), Category.category1);

                    training.setCreated(System.currentTimeMillis());
                    training.setModified(training.getCreated());
                    training.setVersion(1);
                    daoTraining.insert(training);

                    Exercise exercise = new Exercise(faker.pokemon().name());

                    exercise.setCreated(System.currentTimeMillis());
                    exercise.setModified(training.getCreated());
                    exercise.setVersion(1);
                    daoTraining.insert(training);

                }
                Log.i(LOG_TAG_DB, "Inserted 10 values to DB");
            });
        }
    };

}
