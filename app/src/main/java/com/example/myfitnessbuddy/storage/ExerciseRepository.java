package com.example.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfitnessbuddy.model.Training.Training;
import com.example.myfitnessbuddy.model.exercise.Exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class ExerciseRepository {

    public static final String LOG_TAG = "ExerciseRepository";

    private ExerciseDao exerciseDao;

    private static ExerciseRepository INSTANCE;

    private LiveData<List<Exercise>> allExercise;


    public static ExerciseRepository getRepository( Application application )
    {
        if( INSTANCE == null ) {
            synchronized ( ExerciseRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new ExerciseRepository( application );
                }
            }
        }

        return INSTANCE;
    }

    public ExerciseRepository(Context context) {
        MyFitnessBuddyDatabase db = MyFitnessBuddyDatabase.getDatabase( context );
        this.exerciseDao = db.exerciseDao();
    }


    public List<Exercise> getExercise()
    {
        return this.query( () -> this.exerciseDao.getExercise() );
    }

    public LiveData<List<Exercise>> getExerciseLiveData()
    {
        if( this.allExercise == null ) {
            this.allExercise = this.queryLiveData(this.exerciseDao::getExerciseLiveData);
        }

        return this.allExercise;
    }



    public List<Exercise> getExerciseForDesignation(String search )
    {
        return this.query( () -> this.exerciseDao.getExerciseForDesignation( search ) );
    }

    public List<Exercise> getExerciseSortedByDesignation()
    {
        return this.query( () -> this.exerciseDao.getExerciseSortedByDesignation() );
    }

    private List<Exercise> query( Callable<List<Exercise>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

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

    public void update(Exercise exercise) {
        exercise.setVersion((int) System.currentTimeMillis());
        exercise.setVersion( exercise.getVersion() + 1 );

        MyFitnessBuddyDatabase.execute( () -> exerciseDao.update( exercise) );
    }

    public void insert(Exercise exercise) {
        exercise.setCreated( System.currentTimeMillis() );
        exercise.setModified( exercise.getCreated() );
        exercise.setVersion( 1 );

        MyFitnessBuddyDatabase.execute( () -> exerciseDao.insert( exercise ) );
    }


    public long insertAndWait( Exercise exercise ) {
        exercise.setCreated( System.currentTimeMillis() );
        exercise.setModified( exercise.getCreated() );
        exercise.setVersion( 1 );

        try {
            return MyFitnessBuddyDatabase.executeWithReturn( () -> exerciseDao.insert( exercise ) );
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