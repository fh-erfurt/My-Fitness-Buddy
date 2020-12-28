package PME.myfitnessbuddy.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import PME.myfitnessbuddy.model.exercise.Exercise;


@Entity
public class MuscleGroup {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "muscleGroupId")
    private long muscleGroupId;

    /*
    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }



    @NonNull
    @ColumnInfo(name = "exerciseId")
    private long exerciseId;


     */
    @NonNull
    @ColumnInfo(name = "designation")
    private String designation;

    /*
    @Ignore
    private List<Exercise> exercises = new ArrayList<>();


     */
    @NonNull
    public int getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(@NonNull int profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @NonNull
    @ColumnInfo(name = "profile_image_url")
    private int profileImageUrl;


    @NonNull
    @ColumnInfo(name = "created")
    private long created;

    @NonNull
    @ColumnInfo(name = "modified")
    private long modified;

    @NonNull
    @ColumnInfo(name = "version")
    private int version = 0;



    public MuscleGroup(@NonNull String designation) {
        this.designation = designation;
    }

    public long getMuscleGroupId() {
        return muscleGroupId;
    }

    public void setMuscleGroupId(long muscleGroupId) {
        this.muscleGroupId = muscleGroupId;
    }

    @NonNull
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(@NonNull String designation) {
        this.designation = designation;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }




}
