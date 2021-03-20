package PME.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;

import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;
import PME.myfitnessbuddy.storage.Dao.TrainingExerciseCrossRefDao;

public class TrainingExerciseCrossRefRepository {

    private TrainingExerciseCrossRefDao trainingExerciseCrossRefDao;

    private static TrainingExerciseCrossRefRepository INSTANCE;

    public static TrainingExerciseCrossRefRepository getRepository(Application application )
    {
        if( INSTANCE == null ) {
            synchronized ( TrainingExerciseCrossRefRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new TrainingExerciseCrossRefRepository( application );
                }
            }
        }

        return INSTANCE;
    }

    public TrainingExerciseCrossRefRepository(Context context) {
        MyFitnessBuddyDatabase db = MyFitnessBuddyDatabase.getDatabase( context );
        this.trainingExerciseCrossRefDao = db.trainingExerciseCrossRefDao();
    }

    public void deleteByTrainingId( long id )
    {
        MyFitnessBuddyDatabase.execute( () -> trainingExerciseCrossRefDao.deleteByTrainingsId( id ) );
    }

    public void deleteByTrainingIdAndExerciseId( long trainingId, long exerciseId )
    {
        MyFitnessBuddyDatabase.execute( () -> trainingExerciseCrossRefDao.deleteByTrainingsIdAndExerciseId( trainingId, exerciseId) );
    }

    public void update(TrainingExerciseCrossRef trainingExerciseCrossRef) {
        trainingExerciseCrossRef.setVersion((int) System.currentTimeMillis());
        trainingExerciseCrossRef.setVersion( trainingExerciseCrossRef.getVersion() + 1 );

        MyFitnessBuddyDatabase.execute( () -> trainingExerciseCrossRefDao.update( trainingExerciseCrossRef) );
    }

    public void insert(TrainingExerciseCrossRef trainingExerciseCrossRef) {
        trainingExerciseCrossRef.setCreated( System.currentTimeMillis() );
        trainingExerciseCrossRef.setModified( trainingExerciseCrossRef.getCreated() );
        trainingExerciseCrossRef.setVersion( 1 );

        MyFitnessBuddyDatabase.execute( () -> trainingExerciseCrossRefDao.insertTrainingExerciseCrossRef( trainingExerciseCrossRef ) );
    }

}
