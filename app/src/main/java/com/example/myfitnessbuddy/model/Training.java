package com.example.myfitnessbuddy.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Training {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "designation")
    private String designation;

    @NonNull
    @ColumnInfo(name = "categorie")
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
}
