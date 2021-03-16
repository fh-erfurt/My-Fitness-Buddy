package PME.myfitnessbuddy.model.training;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Training {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trainingId")
    private int trainingId;

    @NonNull
    @ColumnInfo(name = "designation")
    private String designation;

    @NonNull
    @ColumnInfo(name = "category")
    private String category;

    @NonNull
    @ColumnInfo(name = "created")
    private long created;

    @NonNull
    @ColumnInfo(name = "modified")
    private long modified;

    @NonNull
    @ColumnInfo(name = "version")
    private int version = 0;

    public Training(@NonNull String designation, @NonNull String category) {
        this.designation = designation;
        this.category = category;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    @NonNull
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(@NonNull String designation) {
        this.designation = designation;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
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
