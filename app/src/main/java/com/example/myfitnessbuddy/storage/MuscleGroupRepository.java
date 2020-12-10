package com.example.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfitnessbuddy.model.MuscleGroup;
import com.example.myfitnessbuddy.model.Training.Training;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class MuscleGroupRepository {
    public static final String LOG_TAG = "MuscleGroupRepository";

    private MuscleGroupDao muscleGroupDao;

    private static MuscleGroupRepository INSTANCE;

    private LiveData<List<MuscleGroup>> allMuscleGroups;


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

    public List<MuscleGroup> getMuscleGroups()
    {
        return this.query( () -> this.muscleGroupDao.getMuscleGroups() );
    }

    public LiveData<List<MuscleGroup>> getMuscleGroupsLiveData()
    {
        if( this.allMuscleGroups == null )
            this.allMuscleGroups = this.queryLiveData(this.muscleGroupDao::getMuscleGroupsLiveData);

        return this.allMuscleGroups;
    }




    private LiveData<List<MuscleGroup>> queryLiveData( Callable<LiveData<List<MuscleGroup>>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new MutableLiveData<>(Collections.emptyList());
    }

    public List<MuscleGroup> getMuscleGroupsForDesignation(String search )
    {
        return this.query( () -> this.muscleGroupDao.getMuscleGroupForDesignation( search ) );
    }

    public List<MuscleGroup> getMuscleGroupsSortedByDesignation()
    {
        return this.query( () -> this.muscleGroupDao.getMuscleGroupSortedByDesignation() );
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

    public MuscleGroup getLastMuscleGroup() {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( this.muscleGroupDao::getLastEntry );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Well, is this a reasonable default return value?
        return new MuscleGroup("");
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

        MyFitnessBuddyDatabase.execute( () -> muscleGroupDao.insert( muscleGroup ) );
    }


    public long insertAndWait( MuscleGroup muscleGroup ) {
        muscleGroup.setCreated( System.currentTimeMillis() );
        muscleGroup.setModified( muscleGroup.getCreated() );
        muscleGroup.setVersion( 1 );

        try {
            return MyFitnessBuddyDatabase.executeWithReturn( () -> muscleGroupDao.insert( muscleGroup ) );
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return -1;
    }
}
