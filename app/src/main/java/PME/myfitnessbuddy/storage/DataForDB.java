package PME.myfitnessbuddy.storage;

import com.myfitnessbuddy.R;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;
import PME.myfitnessbuddy.model.training.Category;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.storage.Dao.ExerciseCrossRefDao;
import PME.myfitnessbuddy.storage.Dao.ExerciseDao;
import PME.myfitnessbuddy.storage.Dao.MuscleGroupDao;
import PME.myfitnessbuddy.storage.Dao.PersonDao;
import PME.myfitnessbuddy.storage.Dao.TrainingDao;
import PME.myfitnessbuddy.storage.Dao.TrainingExerciseCrossRefDao;

public class DataForDB {

     // method for insert example data in DB
   public void generateDBData (PersonDao daoPerson, TrainingDao daoTraining,ExerciseDao daoExercise,MuscleGroupDao daoMuscleGroup,ExerciseCrossRefDao exerciseCrossRefDao,TrainingExerciseCrossRefDao trainingExerciseCrossRefDao){

        // Insert Test Person -> later via first start in App
        Person person1 = new Person("Arni","30.07.47",1,188.0,115.5);
        person1.setCreated( System.currentTimeMillis() );
        person1.setModified( person1.getCreated() );
        person1.setVersion( 1 );
        daoPerson.insertPerson(person1);

        // muscle group standards
        MuscleGroup muscleGroup1 = new MuscleGroup("Schultern");
        muscleGroup1.setCreated( System.currentTimeMillis() );
        muscleGroup1.setProfileImageUrlByString( "Schultern" );
        muscleGroup1.setModified( muscleGroup1.getCreated() );
        muscleGroup1.setVersion( 1 );
        daoMuscleGroup.insertMuscleGroup(muscleGroup1);

        MuscleGroup muscleGroup2 = new MuscleGroup("Beine");
        muscleGroup2.setCreated( System.currentTimeMillis() );
        muscleGroup2.setProfileImageUrlByString( "Beine" );
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

        MuscleGroup muscleGroup6 = new MuscleGroup("Trizeps");
        muscleGroup6.setCreated( System.currentTimeMillis() );
        muscleGroup6.setProfileImageUrlByString( "Trizeps" );
        muscleGroup6.setModified( muscleGroup5.getCreated() );
        muscleGroup6.setVersion( 1 );
        daoMuscleGroup.insertMuscleGroup(muscleGroup6);

        MuscleGroup muscleGroup7 = new MuscleGroup("Bauch");
        muscleGroup7.setCreated( System.currentTimeMillis() );
        muscleGroup7.setProfileImageUrlByString( "Bauch" );
        muscleGroup7.setModified( muscleGroup7.getCreated() );
        muscleGroup7.setVersion( 1 );
        daoMuscleGroup.insertMuscleGroup(muscleGroup7);

        MuscleGroup muscleGroup8 = new MuscleGroup("Wade");
        muscleGroup8.setCreated( System.currentTimeMillis() );
        muscleGroup8.setProfileImageUrlByString( "Wade" );
        muscleGroup8.setModified( muscleGroup8.getCreated() );
        muscleGroup8.setVersion( 1 );
        daoMuscleGroup.insertMuscleGroup(muscleGroup8);

        // example exercises
        Exercise exercise1 = new Exercise("Bankdrücken","Lege dich flach auf den Rücken und bilde ein leichtes Hohlkreuz. Fasse die Stange etwas mehr als Schulterbreit und drücke sie aus der Halterung. Lass die Stange langsam nach unten bis etwa 5cm über den Brustwarzen und hebe die Stange wieder an.", R.drawable.bench_press);
        exercise1.setModified( exercise1.getCreated() );
        exercise1.setVersion( 1 );
        daoExercise.insertExercise(exercise1);

        Exercise exercise2 = new Exercise("Latziehen","Fasse die Stange außen an und ziehe sie bis in deinen Nacken, achte beim hochgehen der Stange, dass deine Arme und Schultern nicht voll ausgestreckt sind",R.drawable.pullups);
        exercise2.setModified( exercise2.getCreated() );
        exercise2.setVersion( 1 );
        daoExercise.insertExercise(exercise2);

        Exercise exercise3 = new Exercise("Kniebeuge","Lege die Stange in deinen Nacken. Gehe nun soweit wie möglich in die Knie in dem du dein Gesäß raus drückst",R.drawable.squats);
        exercise3.setModified( exercise3.getCreated() );
        exercise3.setVersion( 1 );
        daoExercise.insertExercise(exercise3);

        Exercise exercise4 = new Exercise("Kreuzheben","Gehe in die Knie und umfasse die Stange etwa schulterbreit. Stehe nun mit der Stange auf, in dem du dein Gesäß anspannst und deinen Rückem gerade hälst.",R.drawable.deadlift);
        exercise4.setModified( exercise4.getCreated() );
        exercise4.setVersion( 1 );
        daoExercise.insertExercise(exercise4);

        Exercise exercise5 = new Exercise("Bizepscurls im Stehen","Führe die Hantel von einer ausgetrecken Position bis zu deiner Schulter.Achte darauf, das deine Finger nach oben zeigen und du nicht wippst",R.drawable.curls_with_dumbell);
        exercise5.setModified( exercise5.getCreated() );
        exercise5.setVersion( 1 );
        daoExercise.insertExercise(exercise5);

        Exercise exercise6 = new Exercise("Planks","Unterarmstütz mit durchgestrecktem Rücken",R.drawable.plank);
        exercise6.setModified( exercise6.getCreated() );
        exercise6.setVersion( 1 );
        daoExercise.insertExercise(exercise6);

        Exercise exercise7 = new Exercise("Aufwärmen","10min joggen oder Fahrrad fahren",R.drawable.cardio);
        exercise7.setModified( exercise7.getCreated() );
        exercise7.setVersion( 1 );
        daoExercise.insertExercise(exercise7);

        Exercise exercise8 = new Exercise("Schulterdrücken Kurzhantel","Halte beide Kurzhanteln neben deinem Kopf bei ca 90Grad Armbeuge. Die Handflächen sollte dabei nach vorne zeigen. Drücke die Hanten nach oben.Gehe langsam in die Ausgangsposition zurück",R.drawable.weightlifting);
        exercise8.setModified( exercise8.getCreated() );
        exercise8.setVersion( 1 );
        daoExercise.insertExercise(exercise8);

        Exercise exercise9 = new Exercise("Beinpresse","Stelle deine Füße etwa schulterbreit auf die Platte. Löse die Halterung und lass deine Beine langsam zurück. Drücke die Platte nun nach oben ohne die Beine ganz durch zustrecken",R.drawable.run);
        exercise9.setModified( exercise9.getCreated() );
        exercise9.setVersion( 1 );
        daoExercise.insertExercise(exercise9);

        Exercise exercise10 = new Exercise("Rudern","Ziehe den Griff bis zum Bauch und achte darauf, dass auch die Schultern nach hinten gehen.Lass den Rücken dabei gerade",R.drawable.run);
        exercise10.setModified( exercise10.getCreated() );
        exercise10.setVersion( 1 );
        daoExercise.insertExercise(exercise10);

        // Exercise Musclegroup Cross Ref
        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef1 = new ExerciseMuscleGroupCrossRef(1, 3);
        exerciseMuscleGroupCrossRef1.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef1.setModified( exerciseMuscleGroupCrossRef1.getCreated() );
        exerciseMuscleGroupCrossRef1.setVersion( 1 );
        exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef1);

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef2 = new ExerciseMuscleGroupCrossRef(2, 4);
        exerciseMuscleGroupCrossRef2.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef2.setModified( exerciseMuscleGroupCrossRef2.getCreated() );
        exerciseMuscleGroupCrossRef2.setVersion( 1 );
        exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef2);

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef3 = new ExerciseMuscleGroupCrossRef(3, 2);
        exerciseMuscleGroupCrossRef3.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef3.setModified( exerciseMuscleGroupCrossRef3.getCreated() );
        exerciseMuscleGroupCrossRef3.setVersion( 1 );
        exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef3);

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef4 = new ExerciseMuscleGroupCrossRef(4, 4);
        exerciseMuscleGroupCrossRef4.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef4.setModified( exerciseMuscleGroupCrossRef4.getCreated() );
        exerciseMuscleGroupCrossRef4.setVersion( 1 );
        exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef4);

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef5 = new ExerciseMuscleGroupCrossRef(5, 5);
        exerciseMuscleGroupCrossRef5.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef5.setModified( exerciseMuscleGroupCrossRef5.getCreated() );
        exerciseMuscleGroupCrossRef5.setVersion( 1 );
        exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef5);

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef6 = new ExerciseMuscleGroupCrossRef(6, 2);
        exerciseMuscleGroupCrossRef6.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef6.setModified( exerciseMuscleGroupCrossRef6.getCreated() );
        exerciseMuscleGroupCrossRef6.setVersion( 1 );
        exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef6);

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef7 = new ExerciseMuscleGroupCrossRef(7, 2);
        exerciseMuscleGroupCrossRef7.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef7.setModified( exerciseMuscleGroupCrossRef7.getCreated() );
        exerciseMuscleGroupCrossRef7.setVersion( 1 );
        exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef7);

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef8 = new ExerciseMuscleGroupCrossRef(8, 1);
        exerciseMuscleGroupCrossRef8.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef8.setModified( exerciseMuscleGroupCrossRef8.getCreated() );
        exerciseMuscleGroupCrossRef8.setVersion( 1 );
        exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef8);

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef9 = new ExerciseMuscleGroupCrossRef(9, 2);
        exerciseMuscleGroupCrossRef9.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef9.setModified( exerciseMuscleGroupCrossRef9.getCreated() );
        exerciseMuscleGroupCrossRef9.setVersion( 1 );
        exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef9);

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef10 = new ExerciseMuscleGroupCrossRef(10, 4);
        exerciseMuscleGroupCrossRef10.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef10.setModified( exerciseMuscleGroupCrossRef10.getCreated() );
        exerciseMuscleGroupCrossRef10.setVersion( 1 );
        exerciseCrossRefDao.insertExerciseCrossRef(exerciseMuscleGroupCrossRef10);

        //example Training
        Training back = new Training("Brust", "push");
        back.setCreated( System.currentTimeMillis() );
        back.setModified( back.getCreated() );
        back.setVersion( 1 );
        daoTraining.insert(back);

        Training chest = new Training("Rücken", "pull");
        chest.setCreated( System.currentTimeMillis() );
        chest.setModified( chest.getCreated() );
        chest.setVersion( 1 );
        daoTraining.insert(chest);

        Training legs = new Training("Beine", "beine");
        legs.setCreated( System.currentTimeMillis() );
        legs.setModified( legs.getCreated() );
        legs.setVersion( 1 );
        daoTraining.insert(legs);

        // Training Exercise Cross Ref
        TrainingExerciseCrossRef trainingExerciseCrossRef1 = new TrainingExerciseCrossRef(1, 1);
        trainingExerciseCrossRef1.setCreated( System.currentTimeMillis() );
        trainingExerciseCrossRef1.setModified( exerciseMuscleGroupCrossRef1.getCreated() );
        trainingExerciseCrossRef1.setVersion( 1 );
        trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef1);

        TrainingExerciseCrossRef trainingExerciseCrossRef2 = new TrainingExerciseCrossRef(1, 8);
        trainingExerciseCrossRef2.setCreated( System.currentTimeMillis() );
        trainingExerciseCrossRef2.setModified( trainingExerciseCrossRef2.getCreated() );
        trainingExerciseCrossRef2.setVersion( 1 );
        trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef2);

        TrainingExerciseCrossRef trainingExerciseCrossRef3 = new TrainingExerciseCrossRef(1, 6);
        trainingExerciseCrossRef3.setCreated( System.currentTimeMillis() );
        trainingExerciseCrossRef3.setModified( trainingExerciseCrossRef3.getCreated() );
        trainingExerciseCrossRef3.setVersion( 1 );
        trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef3);

        TrainingExerciseCrossRef trainingExerciseCrossRef4 = new TrainingExerciseCrossRef(2, 2);
        trainingExerciseCrossRef4.setCreated( System.currentTimeMillis() );
        trainingExerciseCrossRef4.setModified( trainingExerciseCrossRef4.getCreated() );
        trainingExerciseCrossRef4.setVersion( 1 );
        trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef4);

        TrainingExerciseCrossRef trainingExerciseCrossRef5 = new TrainingExerciseCrossRef(2, 4);
        trainingExerciseCrossRef5.setCreated( System.currentTimeMillis() );
        trainingExerciseCrossRef5.setModified( trainingExerciseCrossRef5.getCreated() );
        trainingExerciseCrossRef5.setVersion( 1 );
        trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef5);

        TrainingExerciseCrossRef trainingExerciseCrossRef6 = new TrainingExerciseCrossRef(2, 5);
        trainingExerciseCrossRef6.setCreated( System.currentTimeMillis() );
        trainingExerciseCrossRef6.setModified( trainingExerciseCrossRef6.getCreated() );
        trainingExerciseCrossRef6.setVersion( 1 );
        trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef6);

        TrainingExerciseCrossRef trainingExerciseCrossRef7 = new TrainingExerciseCrossRef(2, 10);
        trainingExerciseCrossRef7.setCreated( System.currentTimeMillis() );
        trainingExerciseCrossRef7.setModified( trainingExerciseCrossRef7.getCreated() );
        trainingExerciseCrossRef7.setVersion( 1 );
        trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef7);

        TrainingExerciseCrossRef trainingExerciseCrossRef8 = new TrainingExerciseCrossRef(3, 7);
        trainingExerciseCrossRef8.setCreated( System.currentTimeMillis() );
        trainingExerciseCrossRef8.setModified( trainingExerciseCrossRef8.getCreated() );
        trainingExerciseCrossRef8.setVersion( 1 );
        trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef8);

        TrainingExerciseCrossRef trainingExerciseCrossRef9 = new TrainingExerciseCrossRef(3, 9);
        trainingExerciseCrossRef9.setCreated( System.currentTimeMillis() );
        trainingExerciseCrossRef9.setModified( trainingExerciseCrossRef9.getCreated() );
        trainingExerciseCrossRef9.setVersion( 1 );
        trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef9);

        TrainingExerciseCrossRef trainingExerciseCrossRef10 = new TrainingExerciseCrossRef(3, 3);
        trainingExerciseCrossRef10.setCreated( System.currentTimeMillis() );
        trainingExerciseCrossRef10.setModified( trainingExerciseCrossRef10.getCreated() );
        trainingExerciseCrossRef10.setVersion( 1 );
        trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef(trainingExerciseCrossRef10);

    };


}
