package com.example.myfitnessbuddy.model.Training;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Training {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "training_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "designation")
    private String designation;

    @NonNull
    @ColumnInfo(name = "category")
    private Category category;

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
    @ColumnInfo(name = "profile_image_url")
    private String profileImageUrl;


    public Training(@NonNull String designation, @NonNull Category category) {
        this.designation = designation;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(@NonNull String designation) {
        this.designation = designation;
    }

    @NonNull
    public Category getCategory() {
        return category;
    }

    public void setCategory(@NonNull Category category) {
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



    @NonNull
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(@NonNull String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
