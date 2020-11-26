package com.example.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;

import com.example.myfitnessbuddy.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class PersonRepository {

    public static final String LOG_TAG = "PersonRepository";

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

    public List<Person> getPersons()
    {
        return this.query( () -> this.personDao.getPersons() );
    }

    public List<Person> getPersonsForNickname(String search )
    {
        return this.query( () -> this.personDao.getPersonForNickname( search ) );
    }

    public List<Person> getPersonsSortedByNickname()
    {
        return this.query( () -> this.personDao.getPersonSortedByNickname() );
    }

    private List<Person> query( Callable<List<Person>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public Person getLastContact() {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( this.personDao::getLastEntry );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Well, is this a reasonable default return value?
        return new Person("", "",0,0,0);
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


    public long insertAndWait( Person contact ) {
        contact.setCreated( System.currentTimeMillis() );
        contact.setModified( contact.getCreated() );
        contact.setVersion( 1 );

        try {
            return MyFitnessBuddyDatabase.executeWithReturn( () -> personDao.insert( contact ) );
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return -1;
    }

}
