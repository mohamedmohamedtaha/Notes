package com.mohamedtaha.imagine.myapplication;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class ContentNoteActivityViewModel extends ViewModel {
    public static final String ORIGINAL_NOTE_COURSE_ID = "com.mohamedtaha.imagine.myapplication.ORIGINAL_NOTE_COURSE_ID";
    public static final String ORIGINAL_NOTE_TITLE = " com.mohamedtaha.imagine.myapplication.ORIGINAL_NOTE_TITLE";
    public static final String ORIGINAL_NOTE_TEXT = "com.mohamedtaha.imagine.myapplication.ORIGINAL_NOTE_TEXT";
    public boolean isNewCreated  = true;

    public String originalNoteCourseId;
    public String originalNoteText;
    public String originalNoteTitle;

    public void saveState(Bundle outState) {
        outState.putString(ORIGINAL_NOTE_COURSE_ID, originalNoteCourseId);
        outState.putString(ORIGINAL_NOTE_TITLE, originalNoteText);
        outState.putString(ORIGINAL_NOTE_TEXT, originalNoteTitle);

    }

    public void restoreState(Bundle outState) {
        originalNoteCourseId = outState.getString(ORIGINAL_NOTE_COURSE_ID);
        originalNoteText = outState.getString(ORIGINAL_NOTE_TITLE);
        originalNoteTitle = outState.getString(ORIGINAL_NOTE_TEXT);


    }
}
