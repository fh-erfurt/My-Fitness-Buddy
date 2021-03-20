package PME.myfitnessbuddy.model.muscleGroup;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.myfitnessbuddy.R;

/**
 * Entity class MuscleGroup stores all information to the muscleGroup object
 *
 * */

@Entity
public class MuscleGroup {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "muscleGroupId")
    private long muscleGroupId;

    @NonNull
    @ColumnInfo(name = "designation")
    private String designation;

    @NonNull
    public int getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(@NonNull int muscleGroup) {
        this.profileImageId = muscleGroup;
    }

    public void setProfileImageUrlByString(String muscleGroup) {
        this.profileImageId = checkImgAndGetId(muscleGroup);
    }

    @NonNull
    @ColumnInfo(name = "profile_image_id")
    private int profileImageId;

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

    /**
     * assigns images to the muscleGroups
     */

    private int checkImgAndGetId(String profileImageName) {

        int id;

        switch (profileImageName) {
            case "Bizeps":
                id = R.drawable.biceps;
                break;
            case "Rücken":
                id = R.drawable.upper_back;
                break;
            case "Brust":
                id = R.drawable.chest;
                break;
            case "Beine":
                id = R.drawable.quadriceps;
                break;
            case "Trizeps":
                id = R.drawable.triceps;
                break;
            case "Wade":
                id = R.drawable.calves;
                break;
            case "Schultern":
                id = R.drawable.shoulders;
                break;
            case "Bauch":
                id = R.drawable.abs;
                break;
            default:
                id = R.drawable.info;
                break;
        }

        return id;

    }

}
