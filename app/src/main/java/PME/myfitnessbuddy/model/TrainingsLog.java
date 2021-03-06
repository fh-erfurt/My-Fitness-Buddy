package PME.myfitnessbuddy.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class TrainingsLog stores all information to the trainingsLog object
 *
 * */

@Entity
public class TrainingsLog implements Comparable<TrainingsLog> {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trainingsLogId")
    private long trainingsLogId;

    private long exerciseCreatorId;

    @NonNull
    @ColumnInfo(name = "created")
    private long created;

    @NonNull
    @ColumnInfo(name = "modified")
    private long modified;

    @NonNull
    @ColumnInfo(name = "version")
    private int version = 0;

    @NonNull
    @ColumnInfo(name = "repetitions")
    private String repetitions;

    @NonNull
    @ColumnInfo(name = "weight")
    private String weight;

    @NonNull
    @ColumnInfo(name = "alternativeText")
    private String alternativeText;

    @NonNull
    @ColumnInfo(name = "trainingId")
    private long trainingId;

    public TrainingsLog(long exerciseCreatorId, @NonNull String repetitions, @NonNull String weight, @NonNull String alternativeText) {
        this.exerciseCreatorId = exerciseCreatorId;
        this.repetitions = repetitions;
        this.weight = weight;
        this.alternativeText = alternativeText;
    }

    public long getTrainingsLogId() {
        return trainingsLogId;
    }

    public void setTrainingsLogId(long trainingsLogId) {
        this.trainingsLogId = trainingsLogId;
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

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    @NonNull
    public String getRepetitions() {
        return repetitions;
    }

    @NonNull
    public String getWeight() {
        return weight;
    }

    @NonNull
    public String getAlternativeText() {
        return alternativeText;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public long getExerciseCreatorId() {
        return exerciseCreatorId;
    }

    /**
     *
     * in a container, the log entries are sorted according to the creation date o
     */
    @Override
    public int compareTo(TrainingsLog o) {

        return Long.compare(this.created, o.created);

    }

}
