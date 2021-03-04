package PME.myfitnessbuddy.storage;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import PME.myfitnessbuddy.model.TrainingsLog;
import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;


import PME.myfitnessbuddy.model.training.CategoryConverter;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.storage.Dao.ExerciseCrossRefDao;
import PME.myfitnessbuddy.storage.Dao.ExerciseDao;
import PME.myfitnessbuddy.storage.Dao.MuscleGroupDao;
import PME.myfitnessbuddy.storage.Dao.PersonDao;
import PME.myfitnessbuddy.storage.Dao.TrainingDao;
import PME.myfitnessbuddy.storage.Dao.TrainingExerciseCrossRefDao;
import PME.myfitnessbuddy.storage.Dao.TrainingsLogDao;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database( entities = {Person.class, Training.class, MuscleGroup.class, Exercise.class, ExerciseMuscleGroupCrossRef.class, TrainingExerciseCrossRef.class, TrainingsLog.class}, version = 7    )
@TypeConverters({CategoryConverter.class})
public abstract class MyFitnessBuddyDatabase extends RoomDatabase {

    private static final String LOG_TAG_DB = "MyFitnessBuddyDB";

    /*
        Contact DAO reference, will be filled by Android
     */

    public abstract PersonDao personDao();
    public abstract TrainingDao trainingDao();
  //  public abstract MuscleGroupDao muscleGroupDao();
    public abstract ExerciseDao exerciseDao();

    public abstract MuscleGroupDao muscleGroupDao();

    public abstract ExerciseCrossRefDao exerciseMuscleGroupCrossRefDao();

    public abstract TrainingExerciseCrossRefDao trainingExerciseCrossRefDao();

    public abstract TrainingsLogDao trainingsLogDao();




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
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        Used to add some initial DataForDB
     */
    private static RoomDatabase.Callback createCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Log.i( LOG_TAG_DB, "onCreate() called" );

            execute(() -> {
                PersonDao daoPerson = INSTANCE.personDao();
                daoPerson.deleteAll();

                TrainingDao daoTraining = INSTANCE.trainingDao();
                daoTraining.deleteAll();

                ExerciseDao daoExercise = INSTANCE.exerciseDao();
                daoExercise.deleteAll();

                MuscleGroupDao daoMuscleGroup = INSTANCE.muscleGroupDao();
                daoMuscleGroup.deleteAll();

                ExerciseCrossRefDao exerciseCrossRefDao = INSTANCE.exerciseMuscleGroupCrossRefDao();
                exerciseCrossRefDao.deleteAll();

                TrainingExerciseCrossRefDao trainingExerciseCrossRefDao = INSTANCE.trainingExerciseCrossRefDao();
                trainingExerciseCrossRefDao.deleteAll();

                TrainingsLogDao trainingsLogDao = INSTANCE.trainingsLogDao();
                trainingsLogDao.deleteAll();

                //Insert example data in the database
                DataForDB DBDataForDB = new DataForDB();
                DBDataForDB.generateDBData(daoPerson,daoTraining,daoExercise,daoMuscleGroup,exerciseCrossRefDao,trainingExerciseCrossRefDao, trainingsLogDao);

            });
        }
    };

}
