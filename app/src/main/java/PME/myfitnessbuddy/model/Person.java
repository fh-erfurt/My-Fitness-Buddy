package PME.myfitnessbuddy.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Person {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "nickname")
    private String nickname;

    @NonNull
    @ColumnInfo(name = "birthday")
    private String birthday;

    @NonNull
    @ColumnInfo(name = "gender")
    private int gender;

    @NonNull
    @ColumnInfo(name = "height")
    private double height;

    @NonNull
    @ColumnInfo(name = "weight")
    private double weight;


    @NonNull
    @ColumnInfo(name = "created")
    private long created;

    @NonNull
    @ColumnInfo(name = "modified")
    private long modified;

    @NonNull
    @ColumnInfo(name = "version")
    private int version = 0;

    public Person(@NonNull String nickname, @NonNull String birthday, @NonNull int gender,
                  @NonNull double height, @NonNull double  weight) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNickname() {
        return nickname;
    }

    public void setNickname(@NonNull String nickname) {
        this.nickname = nickname;
    }

    @NonNull
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(@NonNull String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double  getWeight() {
        return weight;
    }

    public void setWeight(double  weight) {
        this.weight = weight;
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
}
