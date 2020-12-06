package com.example.myfitnessbuddy.model.Training;

import androidx.room.TypeConverter;

public class CategoryConverter {
    @TypeConverter
    public static int fromCategoryToInt(Category value) {
        return value.ordinal();
    }

    /**
     * Convert an integer to Health
     */
    @TypeConverter
    public static Category fromIntToCategory(int value) {
        return (Category.values()[value]);
    }
}
