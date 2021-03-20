package PME.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;

import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.storage.Dao.MuscleGroupDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class MuscleGroupRepository {

    private MuscleGroupDao muscleGroupDao;

    private static MuscleGroupRepository INSTANCE;

    long muscleGroupRepositoryId;

    public static MuscleGroupRepository getRepository( Application application )
    {
        if( INSTANCE == null ) {
            synchronized ( MuscleGroupRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new MuscleGroupRepository( application );
                }
            }
        }

        return INSTANCE;
    }

    public MuscleGroupRepository(Context context) {
        MyFitnessBuddyDatabase db = MyFitnessBuddyDatabase.getDatabase( context );
        this.muscleGroupDao = db.muscleGroupDao();
    }

    public List<MuscleGroup> getMuscleGroupsForDesignation(String search )
    {
        return this.query( () -> this.muscleGroupDao.getMuscleGroupForDesignation( search ) );
    }

    private List<MuscleGroup> query( Callable<List<MuscleGroup>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void update(MuscleGroup muscleGroup) {
        muscleGroup.setModified( System.currentTimeMillis() );
        muscleGroup.setVersion( muscleGroup.getVersion() + 1 );

        MyFitnessBuddyDatabase.execute( () -> muscleGroupDao.update( muscleGroup) );
    }

    public void insert(MuscleGroup muscleGroup) {
        muscleGroup.setCreated( System.currentTimeMillis() );
        muscleGroup.setModified( muscleGroup.getCreated() );
        muscleGroup.setVersion( 1 );

        MyFitnessBuddyDatabase.execute( () -> this.muscleGroupRepositoryId = muscleGroupDao.insertMuscleGroup( muscleGroup ) );
    }

}
