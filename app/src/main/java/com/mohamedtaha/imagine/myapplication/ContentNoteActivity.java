package com.mohamedtaha.imagine.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class ContentNoteActivity extends AppCompatActivity {
    public static final String NOTE_POSITION = "com.mohamedtaha.imagine.myapplication.note_position";
    public static final int POSITION_NOT_SET = -1;
    private NoteInfo noteInfo;
    private boolean isNewNote;
    private Spinner spinner;
    private EditText editTextTitle;
    private EditText editTextText;
    private int newNotePosition;
    private boolean isCanceleing = false;
    private ContentNoteActivityViewModel contentNoteActivityViewModel;
    ViewModelProvider viewModelProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_note);
        spinner = findViewById(R.id.SP_spinner);
        viewModelProvider = new ViewModelProvider(getViewModelStore(),
        ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        contentNoteActivityViewModel = viewModelProvider.get(ContentNoteActivityViewModel.class);

       if ( contentNoteActivityViewModel.isNewCreated && savedInstanceState != null)
           contentNoteActivityViewModel.restoreState(savedInstanceState);

       contentNoteActivityViewModel.isNewCreated = false;
        List<CourseInfo> courseInfos = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> adapterCourses = new ArrayAdapter<CourseInfo>(this, android.R.layout.simple_spinner_item,courseInfos);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCourses);
        readDisplayStateValues();
        saveOriginalNoteValues();
        editTextTitle = findViewById(R.id.ET_ShowTitle);
        editTextText = findViewById(R.id.ET_ShowText);
        if (!isNewNote)
            displayNote(spinner, editTextTitle, editTextText);
    }

    private void saveOriginalNoteValues() {
        if (isNewNote)
            return;
        contentNoteActivityViewModel.originalNoteCourseId = noteInfo.getCourse().getCourseId();
        contentNoteActivityViewModel.originalNoteTitle = noteInfo.getTitle();
        contentNoteActivityViewModel.originalNoteText = noteInfo.getText();
    }

    private void displayNote(Spinner spinner, EditText editTextTitle, EditText editTextText) {
        List<CourseInfo> courseInfos = DataManager.getInstance().getCourses();
        int courseIndex = courseInfos.indexOf(noteInfo.getCourse());
        spinner.setSelection(courseIndex);
        editTextTitle.setText(noteInfo.getTitle());
        editTextText.setText(noteInfo.getText());
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        //   noteInfo = intent.getParcelableExtra(NOTE_POSITION);
        int position = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
        isNewNote = position == POSITION_NOT_SET;
        if (!isNewNote) {
            noteInfo = DataManager.getInstance().getNotes().get(position);
        } else {
            createNewNote();
        }
    }

    private void createNewNote() {
        DataManager dataManager = DataManager.getInstance();
        newNotePosition = dataManager.createNewNote();
        noteInfo = dataManager.getNotes().get(newNotePosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isCanceleing) {
            if (isNewNote) {
                DataManager.getInstance().removeNote(newNotePosition);
            } else {
                storePreviousNoteValues();
            }
        } else {
            saveNote();
        }
    }

    private void storePreviousNoteValues() {
        CourseInfo courseInfo = DataManager.getInstance().getCourse(contentNoteActivityViewModel.originalNoteCourseId);
        noteInfo.setCourse(courseInfo);
        noteInfo.setTitle(contentNoteActivityViewModel.originalNoteTitle);
        noteInfo.setText(contentNoteActivityViewModel.originalNoteText);
    }

    private void saveNote() {
        noteInfo.setCourse((CourseInfo) spinner.getSelectedItem());
        noteInfo.setText(editTextText.getText().toString());
        noteInfo.setTitle(editTextTitle.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            sendEmail();
            return true;
        } else if (id == R.id.action_cancel) {
            isCanceleing = true;
            finish();
        }else if (id == R.id.action_next){
            moveNext();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_next);
        int lastNoteIndex = DataManager.getInstance().getNotes().size()-1;
        item.setEnabled(newNotePosition < lastNoteIndex);
        return super.onPrepareOptionsMenu(menu);
    }

    private void moveNext() {
        saveNote();
        ++ newNotePosition;
        noteInfo = DataManager.getInstance().getNotes().get(newNotePosition);
        saveOriginalNoteValues();
        displayNote(spinner,editTextTitle,editTextTitle);
        invalidateOptionsMenu();
    }

    private void sendEmail() {
        CourseInfo courseInfo = (CourseInfo) spinner.getSelectedItem();
        String subject = editTextTitle.getText().toString();
        String text = "Checkout what I learned in the Pluralsight course \n" +
                courseInfo.getTitle() + "\n" + editTextText.getText().toString();
        Intent intent = new Intent();
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null){
            contentNoteActivityViewModel.saveState(outState);
        }
    }
}










