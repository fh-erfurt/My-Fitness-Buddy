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

    private long repositoryTrainingsLogId;

    private TrainingsLogDao trainingsLogDao;

    private static TrainingsLogRepository INSTANCE;

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

}
