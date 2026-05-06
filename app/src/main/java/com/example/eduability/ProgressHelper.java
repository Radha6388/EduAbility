package com.example.eduability;

import android.content.Context;
import android.content.SharedPreferences;

public class ProgressHelper {

    private static final String PREF_NAME = "child_progress";

    public static final String KEY_ALPHABET = "alphabet";
    public static final String KEY_NUMBERS = "numbers";
    public static final String KEY_WORDS = "words";
    public static final String KEY_SHAPES = "shapes";
    public static final String KEY_QUIZ = "quiz";
    public static final String KEY_EMOTION = "emotion";
    public static final String KEY_FIND_LETTER = "find_letter";

    public static void saveProgress(Context context, String key, int value) {
        if (value < 0) value = 0;
        if (value > 100) value = 100;

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(key, value).apply();
    }

    public static int getProgress(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    public static void increaseProgress(Context context, String key, int addValue) {
        int current = getProgress(context, key);
        int updated = current + addValue;

        if (updated > 100) updated = 100;

        saveProgress(context, key, updated);
    }

    public static void resetProgress(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}