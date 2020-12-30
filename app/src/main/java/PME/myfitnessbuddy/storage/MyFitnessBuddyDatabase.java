package PME.myfitnessbuddy.storage;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;
import PME.myfitnessbuddy.model.training.Category;


import PME.myfitnessbuddy.model.training.CategoryConverter;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.storage.Dao.ExerciseCrossRefDao;
import PME.myfitnessbuddy.storage.Dao.ExerciseDao;
import PME.myfitnessbuddy.storage.Dao.MuscleGroupDao;
import PME.myfitnessbuddy.storage.Dao.PersonDao;
import PME.myfitnessbuddy.storage.Dao.TrainingDao;
import PME.myfitnessbuddy.storage.Dao.TrainingExerciseCrossRefDao;

import com.github.javafaker.Faker;
import com.myfitnessbuddy.R;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database( entities = {Person.class, Training.class, MuscleGroup.class, Exercise.class, ExerciseMuscleGroupCrossRef.class, TrainingExerciseCrossRef.class}, version = 31     )
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
        Used to add some initial data
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

               ExerciseCrossRefDao exerciseCrossRefDao = INSTANCE.exerciseMuscleGroupCrossRefDao();
               exerciseCrossRefDao.deleteAll();

               TrainingExerciseCrossRefDao trainingExerciseCrossRefDao = INSTANCE.trainingExerciseCrossRefDao();
               trainingExerciseCrossRefDao.deleteAll();

               MuscleGroupDao daoMuscleGroup = INSTANCE.muscleGroupDao();
               daoMuscleGroup.deleteAll();



                Faker faker = Faker.instance();
                for (int i = 0; i < 40; i++)
                {



/*
                    MuscleGroup muscleGroup = new MuscleGroup("Brust");
                    muscleGroup.setCreated( System.currentTimeMillis() );
                    muscleGroup.setProfileImageUrlByString( "test" );
                    muscleGroup.setModified( muscleGroup.getCreated() );
                    muscleGroup.setVersion( 1 );


                    Exercise exercise = new Exercise(faker.pokemon().name(),faker.pokemon().location());
                    exercise.setCreated( System.currentTimeMillis() );
                    //exercise.setProfileImageUrl( faker.avatar().image() );
                    exercise.setModified( training.getCreated() );
                    exercise.setVersion( 1 );

                    long exerciseId = daoExercise.insertExercise(exercise);
                    long muscleGroupId = daoMuscleGroup.insertMuscleGroup(muscleGroup);

                    ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef = new ExerciseMuscleGroupCrossRef(exerciseId, muscleGroupId);
                    exerciseMuscleGroupCrossRef.setCreated( System.currentTimeMillis() );
                    //exercise.setProfileImageUrl( faker.avatar().image() );
                    exerciseMuscleGroupCrossRef.setModified( training.getCreated() );
                    exerciseMuscleGroupCrossRef.setVersion( 1 );
                    exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef);
*/




                    Log.i(LOG_TAG_DB, "Inserted 10 values to DB");
                }

                // Insert Test Person -> later via first start in App
                Person person1 = new Person("Marco","19.06.88",1,185.0,75.5);
                person1.setCreated( System.currentTimeMillis() );
                person1.setModified( person1.getCreated() );
                person1.setVersion( 1 );
                daoPerson.insertPerson(person1);


                // muscle group standards









                MuscleGroup muscleGroup2 = new MuscleGroup("Beine");
                muscleGroup2.setCreated( System.currentTimeMillis() );
                muscleGroup2.setProfileImageUrlByString( muscleGroup2.getDesignation() );
                muscleGroup2.setModified( muscleGroup2.getCreated() );
                muscleGroup2.setVersion( 1 );
                daoMuscleGroup.insertMuscleGroup(muscleGroup2);

                MuscleGroup muscleGroup3 = new MuscleGroup("Brust");
                muscleGroup3.setCreated( System.currentTimeMillis() );
                muscleGroup3.setProfileImageUrlByString( "Brust" );
                muscleGroup3.setModified( muscleGroup3.getCreated() );
                muscleGroup3.setVersion( 1 );
                daoMuscleGroup.insertMuscleGroup(muscleGroup3);

                MuscleGroup muscleGroup4 = new MuscleGroup("Rücken");
                muscleGroup4.setCreated( System.currentTimeMillis() );
                muscleGroup4.setProfileImageUrlByString( "Rücken" );
                muscleGroup4.setModified( muscleGroup4.getCreated() );
                muscleGroup4.setVersion( 1 );
                daoMuscleGroup.insertMuscleGroup(muscleGroup4);

                MuscleGroup muscleGroup5 = new MuscleGroup("Bizeps");
                muscleGroup5.setCreated( System.currentTimeMillis() );
                muscleGroup5.setProfileImageUrlByString( "Bizeps" );
                muscleGroup5.setModified( muscleGroup5.getCreated() );
                muscleGroup5.setVersion( 1 );
                daoMuscleGroup.insertMuscleGroup(muscleGroup5);

                // example exercises
                Exercise exercise1 = new Exercise("Bankdrücken","Lege dich flach auf den Rücken und bilde ein leichtes Hohlkreuz. Fasse die Stange etwas mehr als Schulterbreit und drücke sie aus der Halterung. Lass die Stange langsam nach unten bis etwa 5cm über den Brustwarzen und hebe die Stange wieder an.", R.drawable.biceps);
                exercise1.setModified( exercise1.getCreated() );
                exercise1.setVersion( 1 );
                daoExercise.insertExercise(exercise1);

                Exercise exercise2 = new Exercise("Latziehen","Fasse die Stange außen an und ziehe sie bis in deinen Nacken, achte beim hochgehen der Stange, dass deine Arme und Schultern nicht voll ausgestreckt sind",R.drawable.biceps);
                exercise2.setModified( exercise2.getCreated() );
                exercise2.setVersion( 1 );
                daoExercise.insertExercise(exercise2);

                Exercise exercise3 = new Exercise("Kniebeuge","Lege die Stange in deinen Nacken. Gehe nun soweit wie möglich in die Knie in dem du dein Gesäß raus drückst",R.drawable.biceps);
                exercise3.setModified( exercise3.getCreated() );
                exercise3.setVersion( 1 );
                daoExercise.insertExercise(exercise3);

                Exercise exercise4 = new Exercise("Kreuzheben","Gehe in die Knie und umfasse die Stange etwa schulterbreit. Stehe nun mit der Stange auf, in dem du dein Gesäß anspannst und deinen Rückem gerade hälst.",R.drawable.biceps);
                exercise4.setModified( exercise4.getCreated() );
                exercise4.setVersion( 1 );
                daoExercise.insertExercise(exercise4);

                Exercise exercise5 = new Exercise("Bizepscurls im Stehen","Führe die Hantel von einer ausgetrecken Position bis zu deiner Schulter.Achte darauf, das deine Finger nach oben zeigen und du nicht wippst",R.drawable.biceps);
                exercise5.setModified( exercise5.getCreated() );
                exercise5.setVersion( 1 );
                daoExercise.insertExercise(exercise5);

                Exercise exercise6 = new Exercise("Dips","Stütze dich mit durch gestreckten Armen auf die Stangen. Gehe nun nach unten bis du mit der Elle ca 90grad erreichst und drücke dich wieder hoch",R.drawable.biceps);
                exercise6.setModified( exercise6.getCreated() );
                exercise6.setVersion( 1 );
                daoExercise.insertExercise(exercise6);

                Exercise exercise7 = new Exercise("Aufwärmen","10min joggen oder Fahrrad fahren",R.drawable.biceps);
                exercise7.setModified( exercise7.getCreated() );
                exercise7.setVersion( 1 );
                daoExercise.insertExercise(exercise7);

                Exercise exercise8 = new Exercise("Schulterdrücken Kurzhantel","Halte beide Kurzhanteln neben deinem Kopf bei ca 90Grad Armbeuge. Die Handflächen sollte dabei nach vorne zeigen. Drücke die Hanten nach oben.Gehe langsam in die Ausgangsposition zurück",R.drawable.biceps);
                exercise8.setModified( exercise8.getCreated() );
                exercise8.setVersion( 1 );
                daoExercise.insertExercise(exercise8);

                Exercise exercise9 = new Exercise("Beinpresse","Stelle deine Füße etwa schulterbreit auf die Platte. Löse die Halterung und lass deine Beine langsam zurück. Drücke die Platte nun nach oben ohne die Beine ganz durch zustrecken",R.drawable.biceps);
                exercise9.setModified( exercise9.getCreated() );
                exercise9.setVersion( 1 );
                daoExercise.insertExercise(exercise9);

                Exercise exercise10 = new Exercise("Rudern","Ziehe den Griff bis zum Bauch und achte darauf, dass auch die Schultern nach hinten gehen.Lass den Rücken dabei gerade",R.drawable.biceps);
                exercise10.setModified( exercise10.getCreated() );
                exercise10.setVersion( 1 );
                daoExercise.insertExercise(exercise10);

                // crossRef exercise - muscleGroup
                ExerciseWithMuscleGroup exerciseMG1 = new ExerciseWithMuscleGroup();



                //exercise
                Exercise exercise11 = new Exercise(faker.pokemon().name(),faker.pokemon().location(), R.drawable.biceps);
                exercise11.setCreated( System.currentTimeMillis() );
                exercise11.setProfileImageUrlByString("Beine");
                exercise11.setModified( exercise1.getCreated() );
                exercise11.setVersion( 1 );

                long exerciseId = daoExercise.insertExercise(exercise11);

                ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef = new ExerciseMuscleGroupCrossRef(exerciseId, 1);
                exerciseMuscleGroupCrossRef.setCreated( System.currentTimeMillis() );
                //exercise.setProfileImageUrl( faker.avatar().image() );
                exerciseMuscleGroupCrossRef.setModified( exerciseMuscleGroupCrossRef.getCreated() );
                exerciseMuscleGroupCrossRef.setVersion( 1 );
                exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef);


                //exercise12
                Exercise exercise12 = new Exercise(faker.pokemon().name(),faker.pokemon().location(),R.drawable.biceps);
                exercise12.setCreated( System.currentTimeMillis() );
                exercise12.setProfileImageUrlByString("Rücken");
                exercise12.setModified( exercise2.getCreated() );
                exercise12.setVersion( 1 );

                long exerciseId2 = daoExercise.insertExercise(exercise12);

                ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef2 = new ExerciseMuscleGroupCrossRef(exerciseId2, 2);
                exerciseMuscleGroupCrossRef2.setCreated( System.currentTimeMillis() );
                //exercise.setProfileImageUrl( faker.avatar().image() );
                exerciseMuscleGroupCrossRef2.setModified( exerciseMuscleGroupCrossRef2.getCreated() );
                exerciseMuscleGroupCrossRef2.setVersion( 1 );
                exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef2);

                ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef3 = new ExerciseMuscleGroupCrossRef(exerciseId2, 3);
                exerciseMuscleGroupCrossRef3.setCreated( System.currentTimeMillis() );
                //exercise.setProfileImageUrl( faker.avatar().image() );
                exerciseMuscleGroupCrossRef3.setModified( exerciseMuscleGroupCrossRef3.getCreated() );
                exerciseMuscleGroupCrossRef3.setVersion( 1 );
                exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef3);

                //Training
                Training training = new Training(faker.team().sport(), Category.category1);
                training.setCreated( System.currentTimeMillis() );
                training.setProfileImageUrl( faker.avatar().image() );
                training.setModified( training.getCreated() );
                training.setVersion( 1 );
                long trainingId = daoTraining.insert(training);

                TrainingExerciseCrossRef trainingExerciseCrossRef = new TrainingExerciseCrossRef(trainingId, exerciseId);
                trainingExerciseCrossRef.setCreated( System.currentTimeMillis() );
                //exercise.setProfileImageUrl( faker.avatar().image() );
                trainingExerciseCrossRef.setModified( exerciseMuscleGroupCrossRef2.getCreated() );
                trainingExerciseCrossRef.setVersion( 1 );
                trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef);

                TrainingExerciseCrossRef trainingExerciseCrossRef2 = new TrainingExerciseCrossRef(trainingId, exerciseId2);
                trainingExerciseCrossRef2.setCreated( System.currentTimeMillis() );
                //exercise.setProfileImageUrl( faker.avatar().image() );
                trainingExerciseCrossRef2.setModified( exerciseMuscleGroupCrossRef2.getCreated() );
                trainingExerciseCrossRef2.setVersion( 1 );
                trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef2);


                Log.i(LOG_TAG_DB, "Inserted 10 values to DB");
            });
        }
    };

}
