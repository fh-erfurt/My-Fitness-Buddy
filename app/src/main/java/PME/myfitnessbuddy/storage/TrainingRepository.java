package PME.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.model.training.TrainingWithExercise;
import PME.myfitnessbuddy.storage.Dao.TrainingDao;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Uses trainingDao and manages the access to it
 * */

public class TrainingRepository {

    public long getRepositoryTrainingId() {
        return repositoryTrainingId;
    }

    private long repositoryTrainingId;

    private TrainingDao trainingDao;

    private static TrainingRepository INSTANCE;

    public static TrainingRepository getRepository( Application application )
    {
        if( INSTANCE == null ) {
            synchronized ( TrainingRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new TrainingRepository( application );
                }
            }
        }

        return INSTANCE;
    }

    public TrainingRepository(Context context) {
        MyFitnessBuddyDatabase db = MyFitnessBuddyDatabase.getDatabase( context );
        this.trainingDao = db.trainingDao();
    }

    public LiveData<List<TrainingWithExercise>> getTrainingsLiveData()
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( this.trainingDao::getTrainingWithExercises );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Training training) {
        training.setModified( System.currentTimeMillis() );
        training.setVersion( training.getVersion() + 1 );

        MyFitnessBuddyDatabase.execute( () -> trainingDao.update( training) );
    }

    public void insert(Training training) {
        training.setCreated( System.currentTimeMillis() );
        training.setModified( training.getCreated() );
        training.setVersion( 1 );

        MyFitnessBuddyDatabase.execute( () -> this.repositoryTrainingId = trainingDao.insert( training ) );
    }

    public void delete2( Training training )
    {
        MyFitnessBuddyDatabase.execute( () -> trainingDao.delete2( training ) );
    }

    public LiveData<Training> getTrainingByIdAsLiveData( long trainingId )
    {
        return this.queryLiveData(() -> this.trainingDao.getTrainingById(trainingId) );
    }

    private <T> LiveData<T> queryLiveData( Callable<LiveData<T>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new MutableLiveData<>();
    }
}
