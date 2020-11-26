package com.example.myfitnessbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.myfitnessbuddy.model.Person;
import com.example.myfitnessbuddy.storage.KeyValueStore;
import com.example.myfitnessbuddy.storage.PersonRepository;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private static final String STORE_KEY_COUNTER = "COUNTER";
    public static final String LOG_TAG = "AppClass";

    private KeyValueStore store;

    @Override
    public void onCreate() {
        super.onCreate();

        testDatabase();
    }

    /*
        Simple DB Test - will be removed in next version
     */
    private void testDatabase() {

        // Create Repo instance - which in turn will init the Contact DB
        PersonRepository personRepository = new PersonRepository( this );

        // Query all contacts and log them
        List<Person> allPersons = personRepository.getPersons();
        Log.i(LOG_TAG, allPersons.toString() );

        // Do we have any contacts at all?
        // During first app run, 10 contacts will be created. However, this happens asynchronous so
        // we might end up here before the DB got filled. So, better check it.
        if( allPersons.size() > 0 ) {

            // Ok, lets get all contacts sorted by lastname
            allPersons = personRepository.getPersonsSortedByNickname();
            Log.i(LOG_TAG, allPersons.toString() );

            // Get the latest added contact, e.g. the one with the highest primary key value
            Person lastPerson = personRepository.getLastContact();
            Log.i(LOG_TAG, "" + lastPerson);

            // Change its lastname to something random
            lastPerson.setNickname("Nickname " + new Random().nextInt(1000));
            personRepository.update(lastPerson);

            // Wait for the async update operation to finish....
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Re-query the latest contact and check if the change was written successfully
            lastPerson = personRepository.getLastContact();
            Log.i(LOG_TAG, lastPerson.toString());

            // Get all contacts with lastname like 'Last*'
            List<Person> nameSearchResult = personRepository.getPersonsForNickname( "Nick%" );
            Log.i(LOG_TAG, nameSearchResult.toString() );
        }
    }

    /**
     * Create single store instance
     *
     * Currently, that method could be private because this class is the only client of the store.
     * However, maybe other parts of the application might like to use the key value store in the
     * future so lets just keep it public for now.
     * @return the key value store instance
     */
    public KeyValueStore getStore()
    {
        if( this.store == null )
            this.store = new KeyValueStore(this);

        return this.store;
    }

    /*
        Counter Handling
     */
    public int getCounter()
    {
        return this.getStore().getIntValue( STORE_KEY_COUNTER );
    }

    public void incCounter() {
        this.getStore().writeValue( STORE_KEY_COUNTER, this.getCounter() + 1 );
    }

    public void decCounter() {
        this.getStore().writeValue( STORE_KEY_COUNTER, this.getCounter() - 1 );
    }

}