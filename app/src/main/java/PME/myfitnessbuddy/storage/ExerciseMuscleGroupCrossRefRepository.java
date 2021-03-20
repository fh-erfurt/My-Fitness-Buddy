package PME.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;

import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.storage.Dao.ExerciseMuscleGroupCrossRefDao;

/**
 * Uses exerciseMuscleGroupCrossRefDao and manages the access to it
 * */

public class ExerciseMuscleGroupCrossRefRepository {

    private ExerciseMuscleGroupCrossRefDao exerciseMuscleGroupCrossRefDao;

    private static ExerciseMuscleGroupCrossRefRepository INSTANCE;

    public static ExerciseMuscleGroupCrossRefRepository getRepository(Application application )
    {
        if( INSTANCE == null ) {
            synchronized ( ExerciseMuscleGroupCrossRefRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new ExerciseMuscleGroupCrossRefRepository( application );
                }
            }
        }

        return INSTANCE;
    }

    public ExerciseMuscleGroupCrossRefRepository(Context context) {
        MyFitnessBuddyDatabase db = MyFitnessBuddyDatabase.getDatabase( context );
        this.exerciseMuscleGroupCrossRefDao = db.exerciseMuscleGroupCrossRefDao();
    }

    public void update(ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef) {
        exerciseMuscleGroupCrossRef.setVersion((int) System.currentTimeMillis());
        exerciseMuscleGroupCrossRef.setVersion( exerciseMuscleGroupCrossRef.getVersion() + 1 );

        MyFitnessBuddyDatabase.execute( () -> exerciseMuscleGroupCrossRefDao.update( exerciseMuscleGroupCrossRef) );
    }

    public void insert(ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef) {
        exerciseMuscleGroupCrossRef.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef.setModified( exerciseMuscleGroupCrossRef.getCreated() );
        exerciseMuscleGroupCrossRef.setVersion( 1 );

        MyFitnessBuddyDatabase.execute( () -> exerciseMuscleGroupCrossRefDao.insertExerciseCrossRef( exerciseMuscleGroupCrossRef ) );
    }

}
