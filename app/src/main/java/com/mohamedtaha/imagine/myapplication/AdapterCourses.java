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

public class AdapterCourses extends RecyclerView.Adapter<AdapterCourses.ViewHolder> {
    private Context context;
    private List<CourseInfo> courseInfos;
    private LayoutInflater layoutInflater;

    public AdapterCourses(Context context, List<CourseInfo> courseInfos) {
        this.context = context;
        this.courseInfos = courseInfos;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_course_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseInfo noteInfo = courseInfos.get(position);
        holder.titleCourse_textView.setText(noteInfo.getTitle());
        holder.current_position = position;

    }

    @Override
    public int getItemCount() {
        return courseInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleCourse_textView;
        private int current_position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleCourse_textView = (TextView) itemView.findViewById(R.id.TextCourse);
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
