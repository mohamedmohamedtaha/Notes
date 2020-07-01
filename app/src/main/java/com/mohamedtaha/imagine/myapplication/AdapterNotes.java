package com.mohamedtaha.imagine.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.ViewHolder> {
    private Context context;
    private List<NoteInfo> noteInfoList;
    private LayoutInflater layoutInflater;

    public AdapterNotes(Context context, List<NoteInfo> noteInfoList) {
        this.context = context;
        this.noteInfoList = noteInfoList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_note_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteInfo noteInfo = noteInfoList.get(position);
        holder.titleCourse_textView.setText(noteInfo.getCourse().getTitle());
        holder.text_textView.setText(noteInfo.getTitle());
        holder.current_position = position;

    }

    @Override
    public int getItemCount() {
        return noteInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleCourse_textView;
        private TextView text_textView;
        private int current_position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleCourse_textView = (TextView) itemView.findViewById(R.id.TextCourse);
            text_textView = (TextView) itemView.findViewById(R.id.TextTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent  intent = new Intent(context,ContentNoteActivity.class);
                    intent.putExtra(ContentNoteActivity.NOTE_POSITION,current_position);
                    context.startActivity(intent);
                }
            });
        }
    }
}
