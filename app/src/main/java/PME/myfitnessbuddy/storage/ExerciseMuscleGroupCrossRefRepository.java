package PME.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.storage.Dao.ExerciseCrossRefDao;

public class ExerciseMuscleGroupCrossRefRepository {

    private ExerciseCrossRefDao exerciseCrossRefDao;

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
        this.exerciseCrossRefDao = db.exerciseMuscleGroupCrossRefDao();
    }

    public void update(ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef) {
        exerciseMuscleGroupCrossRef.setVersion((int) System.currentTimeMillis());
        exerciseMuscleGroupCrossRef.setVersion( exerciseMuscleGroupCrossRef.getVersion() + 1 );

        MyFitnessBuddyDatabase.execute( () -> exerciseCrossRefDao.update( exerciseMuscleGroupCrossRef) );
    }

    public void insert(ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef) {
        exerciseMuscleGroupCrossRef.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef.setModified( exerciseMuscleGroupCrossRef.getCreated() );
        exerciseMuscleGroupCrossRef.setVersion( 1 );

        MyFitnessBuddyDatabase.execute( () -> exerciseCrossRefDao.insertExerciseCrossRef( exerciseMuscleGroupCrossRef ) );
    }

}
