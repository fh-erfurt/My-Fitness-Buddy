package PME.myfitnessbuddy.model.relationship;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * Entity class ExerciseMuscleGroupCrossRef stores all information to the exerciseMuscleGroupCrossRef object
 * is the realtion class between exercise and muscleGroup
 *
 * */

@Entity (primaryKeys = {"muscleGroupId", "exerciseId"})
public class ExerciseMuscleGroupCrossRef {

    public ExerciseMuscleGroupCrossRef(@NonNull long exerciseId, @NonNull long muscleGroupId) {
        this.exerciseId = exerciseId;
        this.muscleGroupId = muscleGroupId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }


    public long exerciseId;

    public long muscleGroupId;

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    @NonNull
    @ColumnInfo(name = "created")
    private long created;

    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }

    @NonNull
    @ColumnInfo(name = "modified")
    private long modified;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @NonNull
    @ColumnInfo(name = "version")
    private int version = 0;
}
