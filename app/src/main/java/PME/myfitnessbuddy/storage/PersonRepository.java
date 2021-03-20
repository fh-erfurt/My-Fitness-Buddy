package PME.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;


import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.storage.Dao.PersonDao;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class PersonRepository {

    private PersonDao personDao;

    private static PersonRepository INSTANCE;

    public static PersonRepository getRepository( Application application )
    {
        if( INSTANCE == null ) {
            synchronized ( PersonRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new PersonRepository( application );
                }
            }
        }

        return INSTANCE;
    }

    public PersonRepository(Context context) {
        MyFitnessBuddyDatabase db = MyFitnessBuddyDatabase.getDatabase( context );
        this.personDao = db.personDao();
    }


    public Person getLastPerson()
    {
        return this.query( () -> this.personDao.getLastEntry().get(0) );
    }

    public Person getPersonFromNickname(String name)
    {
        return this.query( () -> this.personDao.getPersonForNickname(name).get(0));
    }


    private Person query( Callable<Person> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(Person person) {
        person.setModified( System.currentTimeMillis() );
        person.setVersion( person.getVersion() + 1 );

        MyFitnessBuddyDatabase.execute( () -> personDao.update( person) );
    }

    public void insert(Person person) {
        person.setCreated( System.currentTimeMillis() );
        person.setModified( person.getCreated() );
        person.setVersion( 1 );

        MyFitnessBuddyDatabase.execute( () -> personDao.insert( person ) );
    }


}
