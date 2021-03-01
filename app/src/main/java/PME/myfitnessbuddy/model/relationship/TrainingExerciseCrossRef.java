package PME.myfitnessbuddy.model.relationship;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"trainingId", "exerciseId"})
public class TrainingExerciseCrossRef {

    public TrainingExerciseCrossRef(@NonNull long trainingId, @NonNull long exerciseId) {

        this.trainingId = trainingId;
        this.exerciseId = exerciseId;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public long trainingId;

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long exerciseId;

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
