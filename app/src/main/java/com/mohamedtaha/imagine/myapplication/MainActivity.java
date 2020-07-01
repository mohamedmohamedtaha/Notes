package com.mohamedtaha.imagine.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    private AdapterNotes adapterNotes;
   // private ArrayAdapter<NoteInfo> noteInfoArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialazationDisplayContent();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ContentNoteActivity.class));
//
//                String getData = textView.getText().toString().trim();
//                int originalValue = Integer.parseInt(getData);
//                int newValue = MyWorker.doubleValue(originalValue);
//                textView.setText(Integer.toString(newValue));
//
//                Snackbar.make(view, "The old value is : " + originalValue  + "\n" + "The new value is : " + newValue, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
       // noteInfoArrayAdapter.notifyDataSetChanged();
        adapterNotes.notifyDataSetChanged();
    }

    private void initialazationDisplayContent() {
//        final ListView listView = findViewById(R.id.LV_list);
//        List<NoteInfo> noteInfos = DataManager.getInstance().getNotes();
//        noteInfoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteInfos);
//        listView.setAdapter(noteInfoArrayAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, ContentNoteActivity.class);
////                NoteInfo noteInfo=(NoteInfo)listView.getItemAtPosition(position);
//                intent.putExtra(ContentNoteActivity.NOTE_POSITION,position);
//                startActivity(intent);
//            }
//        });
         final RecyclerView recyclerView = findViewById(R.id.LV_list);
         final LinearLayoutManager notesLinearLayoutManager = new LinearLayoutManager(this);
         recyclerView.setLayoutManager(notesLinearLayoutManager);
         List<NoteInfo> noteInfos = DataManager.getInstance().getNotes();
          adapterNotes = new AdapterNotes(this,noteInfos);
          recyclerView.setAdapter(adapterNotes);
    }}