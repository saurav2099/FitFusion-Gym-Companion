package com.example.fitfusion.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitfusion.R;
import com.example.fitfusion.dataModel.WorkoutCardModel;

import java.util.List;

// MyAdapter.java
public class WorkoutCardAdapter extends RecyclerView.Adapter<WorkoutCardAdapter.MyViewHolder> {

    static List<WorkoutCardModel> dataList;

    public WorkoutCardAdapter(List<WorkoutCardModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workoutcard, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WorkoutCardModel data = dataList.get(position);
        // Bind data to the ViewHolder
        holder.imageView.setImageResource(data.getImageResourceId());
        holder.time.setText(data.getTime());
        holder.workout.setText(data.getWorkout());
        holder.unit.setText(data.getUnit());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView tickImage;
        TextView time;
        TextView workout;
        TextView unit;
        Boolean completed=false;
        MyViewHolder(View itemView) {
            super(itemView);
            unit= itemView.findViewById(R.id.textView29);
            tickImage = itemView.findViewById(R.id.tick);
            imageView = itemView.findViewById(R.id.imageView9);
            time = itemView.findViewById(R.id.textView30);
            workout = itemView.findViewById(R.id.textView26);
            tickImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    WorkoutCardModel data = dataList.get(getAdapterPosition());
                    if(completed) {
                        tickImage.setImageResource(R.drawable.tick_pending);
                    }
                    else {
                        tickImage.setImageResource(R.drawable.tick_completed);
                    }

                    completed=!completed;
                    data.setCompleted(completed);



                }
            });
        }
    }

}
