package PME.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import PME.myfitnessbuddy.model.TrainingsLog;
import PME.myfitnessbuddy.model.exercise.ExerciseWithTrainingsLog;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.model.training.TrainingWithExercise;
import PME.myfitnessbuddy.storage.Dao.TrainingsLogDao;

public class TrainingsLogRepository {

    public static final String LOG_TAG = "TrainingsLogRepository";

    public long getRepositoryTrainingsLogId() {
        return repositoryTrainingsLogId;
    }

    private long repositoryTrainingsLogId;

    private TrainingsLogDao trainingsLogDao;

    private static TrainingsLogRepository INSTANCE;

    private LiveData<List<TrainingsLog>> allTrainingsLogs;


    public static TrainingsLogRepository getRepository( Application application )
    {
        if( INSTANCE == null ) {
            synchronized ( TrainingsLogRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new TrainingsLogRepository( application );
                }
            }
        }

        return INSTANCE;
    }

    public TrainingsLogRepository(Context context) {
        MyFitnessBuddyDatabase db = MyFitnessBuddyDatabase.getDatabase( context );
        this.trainingsLogDao = db.trainingsLogDao();
    }

    public List<TrainingsLog> getTrainingsLogs()
    {
        return this.query( () -> this.trainingsLogDao.getTrainingsLogs() );
    }

    public LiveData<List<ExerciseWithTrainingsLog>> getTrainingsLogLiveData()
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( this.trainingsLogDao::getExerciseWithTrainingsLog);
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<TrainingsLog> query( Callable<List<TrainingsLog>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void update(TrainingsLog trainingsLog) {
        trainingsLog.setModified( System.currentTimeMillis() );
        trainingsLog.setVersion( trainingsLog.getVersion() + 1 );

        MyFitnessBuddyDatabase.execute( () -> trainingsLogDao.update( trainingsLog) );
    }

    public void insert(TrainingsLog trainingsLog) {
        trainingsLog.setCreated( System.currentTimeMillis() );
        trainingsLog.setModified( trainingsLog.getCreated() );
        trainingsLog.setVersion( 1 );

        MyFitnessBuddyDatabase.execute( () -> this.repositoryTrainingsLogId = trainingsLogDao.insertTrainingsLog( trainingsLog ) );
    }


    public long insertAndWait( TrainingsLog trainingsLog ) {
        trainingsLog.setCreated( System.currentTimeMillis() );
        trainingsLog.setModified( trainingsLog.getCreated() );
        trainingsLog.setVersion( 1 );

        try {
            return MyFitnessBuddyDatabase.executeWithReturn( () -> trainingsLogDao.insertTrainingsLog( trainingsLog ) );
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return -1;
    }


    private <T> LiveData<T> queryLiveData( Callable<LiveData<T>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Well, is this a reasonable default return value?
        return new MutableLiveData<>();
    }

}
