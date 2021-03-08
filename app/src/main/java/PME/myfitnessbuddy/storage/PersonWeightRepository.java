package PME.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.PersonWeight;
import PME.myfitnessbuddy.storage.Dao.PersonDao;
import PME.myfitnessbuddy.storage.Dao.PersonWeightDao;

public class PersonWeightRepository {
    public static final String LOG_TAG = "PersonWeightRepository";

    private PersonWeightDao personWeightDao;

    private static PersonWeightRepository INSTANCE;

    public static PersonWeightRepository getRepository( Application application )
    {
        if( INSTANCE == null ) {
            synchronized ( PersonWeightRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new PersonWeightRepository( application );
                }
            }
        }

        return INSTANCE;
    }

    public PersonWeightRepository(Context context) {
        MyFitnessBuddyDatabase db = MyFitnessBuddyDatabase.getDatabase( context );
        this.personWeightDao = db.personWeightDao();
    }

    public List<PersonWeight> getPersonWeights()
    {
        return this.query( () -> this.personWeightDao.getPersonWeights() );
    }

    public List<PersonWeight> getLastPersonWeights()
    {
        return this.query( () -> this.personWeightDao.getLastPersonWeights() );
    }

    private List<PersonWeight> query( Callable<List<PersonWeight>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private <T> LiveData<T> queryLiveData(Callable<LiveData<T>> query )
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

    public PersonWeight getLastPersonWeight() {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( this.personWeightDao::getLastEntry );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Well, is this a reasonable default return value?
        return new PersonWeight(0);
    }

    public void update(PersonWeight personWeight) {
        personWeight.setModified( System.currentTimeMillis() );
        personWeight.setVersion( personWeight.getVersion() + 1 );

        MyFitnessBuddyDatabase.execute( () -> personWeightDao.update( personWeight) );
    }

    public void insert(PersonWeight personWeight) {
        personWeight.setCreated( System.currentTimeMillis() );
        personWeight.setModified( personWeight.getCreated() );
        personWeight.setVersion( 1 );

        MyFitnessBuddyDatabase.execute( () -> personWeightDao.insert( personWeight ) );
    }


    public long insertAndWait( PersonWeight personWeight ) {
        personWeight.setCreated( System.currentTimeMillis() );
        personWeight.setModified( personWeight.getCreated() );
        personWeight.setVersion( 1 );

        try {
            return MyFitnessBuddyDatabase.executeWithReturn( () -> personWeightDao.insertPersonWeight( personWeight ) );
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return -1;
    }
}
