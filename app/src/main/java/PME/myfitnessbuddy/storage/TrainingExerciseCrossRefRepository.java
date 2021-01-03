package PME.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;
import PME.myfitnessbuddy.storage.Dao.TrainingExerciseCrossRefDao;

public class TrainingExerciseCrossRefRepository {

    public static final String LOG_TAG = "TrainingExerciseCrossRefRepository";

    private TrainingExerciseCrossRefDao trainingExerciseCrossRefDao;

    private static TrainingExerciseCrossRefRepository INSTANCE;

    private LiveData<List<TrainingExerciseCrossRef>> allTrainingExerciseCrossRefs;


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
    /*
        public List<ExerciseMuscleGroupCrossRef> getExercisesWithMuscleGroupCrossRef()
        {
            return this.query( () -> this.exerciseCrossRefDao.getExerciseLiveData() );
        }

    */
    public LiveData<List<TrainingExerciseCrossRef>> getTrainingLiveData()
    {
        if( this.allTrainingExerciseCrossRefs == null ) {
            this.allTrainingExerciseCrossRefs = this.queryLiveData(this.trainingExerciseCrossRefDao::getTrainingLiveData);
        }

        return this.allTrainingExerciseCrossRefs;
    }





    private List<TrainingExerciseCrossRef> query( Callable<List<TrainingExerciseCrossRef>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
    /*
        public Exercise getLastExercise() {
            try {
                return MyFitnessBuddyDatabase.executeWithReturn( this.exerciseDao::getLastEntry );
            }
            catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            // Well, is this a reasonable default return value?
            return new Exercise("");
        }
    */
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

    /*
        public long insertAndWait( Exercise exercise ) {
            try {
                return MyFitnessBuddyDatabase.executeWithReturn( () -> exerciseDao.insertExerciseWithMuscleGroups( exercise ) );
            }
            catch (ExecutionException | InterruptedException e)
            {
                e.printStackTrace();
            }

            return -1;
        }
        public LiveData<Exercise> getExerciseByIdAsLiveData(long exerciseId )
        {
            return this.queryLiveData(() -> this.exerciseDao.getExerciseById(exerciseId) );
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public LiveData<List<Exercise>> getExerciseLiveData()
        {
            if( this.allExerciseMuscleGroupCrossRefs == null )
                this.allExerciseMuscleGroupCrossRefs = Transformations.map(
                        this.queryLiveData(this.exerciseCrossRefDao::getExercisesWithMuscleGroups),
                        input -> input
                                .stream()
                                .map( ExerciseWithMuscleGroup::merge )
                                .collect(Collectors.toList())
                );


            return this.allExercises;
        }
    */
    private <T> LiveData<T> queryLiveData( Callable<LiveData<T>> query ) {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn(query);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Well, is this a reasonable default return value?
        return new MutableLiveData<>();
    }

}
