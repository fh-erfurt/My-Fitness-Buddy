package PME.myfitnessbuddy.model.exercise;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import PME.myfitnessbuddy.model.MuscleGroup;

@Entity
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exerciseId")
    private long exerciseId;

    /*
    public long getMuscleGroupId() {
        return muscleGroupId;
    }

    public void setMuscleGroupId(long muscleGroupId) {
        this.muscleGroupId = muscleGroupId;
    }

    @NonNull
    @ColumnInfo(name = "muscleGroupId")
    private long muscleGroupId;


     */
    @NonNull
    @ColumnInfo(name = "designation")
    private String designation;

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    public void setMuscleGroups(List<MuscleGroup> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    @Ignore
    private List<MuscleGroup> muscleGroups = new ArrayList<>();


    @NonNull
    @ColumnInfo(name = "created")
    private long created;

    @NonNull
    @ColumnInfo(name = "modified")
    private long modified;

    @NonNull
    @ColumnInfo(name = "version")
    private int version = 0;

    public Exercise(@NonNull String designation, @NonNull String description) {
        this.designation = designation;
        this.description = description;
        //this.muscleGroup = muscleGroup;
    }


    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    @NonNull
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(@NonNull String designation) {
        this.designation = designation;
    }

 /*   @NonNull
    public MuscleGroup getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(@NonNull MuscleGroup muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
*/
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

    /*
    public List<MuscleGroup> getMuscleGroups() {
        return muscleGroups;
    }


     */






}
