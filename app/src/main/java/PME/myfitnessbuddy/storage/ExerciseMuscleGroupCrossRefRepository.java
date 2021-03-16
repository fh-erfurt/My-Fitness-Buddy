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

    public static final String LOG_TAG = "ExerciseCrossRefRepository";

    private ExerciseCrossRefDao exerciseCrossRefDao;

    private static ExerciseMuscleGroupCrossRefRepository INSTANCE;

    private LiveData<List<ExerciseMuscleGroupCrossRef>> allExerciseMuscleGroupCrossRefs;


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

    public LiveData<List<ExerciseMuscleGroupCrossRef>> getExerciseLiveData()
    {
        if( this.allExerciseMuscleGroupCrossRefs == null ) {
            this.allExerciseMuscleGroupCrossRefs = this.queryLiveData(this.exerciseCrossRefDao::getExerciseLiveData);
        }

        return this.allExerciseMuscleGroupCrossRefs;
    }

    private List<ExerciseMuscleGroupCrossRef> query( Callable<List<ExerciseMuscleGroupCrossRef>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
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

    private <T> LiveData<T> queryLiveData( Callable<LiveData<T>> query ) {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn(query);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new MutableLiveData<>();
    }
}
