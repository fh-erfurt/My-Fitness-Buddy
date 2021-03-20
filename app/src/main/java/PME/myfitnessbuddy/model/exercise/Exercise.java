package PME.myfitnessbuddy.model.exercise;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.myfitnessbuddy.R;

import java.util.ArrayList;
import java.util.List;

import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;

/**
 * Entity class Exercise stores all information to the exercise object
 *
 * */


@Entity
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exerciseId")
    private long exerciseId;

    @Ignore
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @NonNull
    public int getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(@NonNull int muscleGroup) {
        this.profileImageId = muscleGroup;
    }

    @NonNull
    @ColumnInfo(name = "profile_image_id")
    private int profileImageId;

    @NonNull
    @ColumnInfo(name = "designation")
    private String designation;

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

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

    public Exercise(@NonNull String designation, @NonNull String description, int profileImageId) {
        this.designation = designation;
        this.description = description;
        this.profileImageId = profileImageId;
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
     * assigns images to the exercises
     */

    public int checkImgAndGetId(String profileImageName) {

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
                id = R.drawable.legs;
                break;
            default:
                id = R.drawable.info;
                break;
        }
        return id;
    }

}
