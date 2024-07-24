package com.example.fitfusion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fitfusion.R;
import com.example.fitfusion.dataModel.ActivityHistoryDataModel;

import java.util.List;

public class ActivityAdapter extends ArrayAdapter<ActivityHistoryDataModel> {

    private final Context context;
    private final int resource;

    public ActivityAdapter(@NonNull Context context, int resource, @NonNull List<ActivityHistoryDataModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        // Get the data item for this position
        ActivityHistoryDataModel currentItem = getItem(position);

        // Find and set values in the complex UI elements
        TextView dateText = listItemView.findViewById(R.id.date);
        TextView caloriesText = listItemView.findViewById(R.id.calories);
        TextView stepsText = listItemView.findViewById(R.id.steps);
        TextView descriptionText = listItemView.findViewById(R.id.details);

        dateText.setText(currentItem != null ? currentItem.getDate() : "");
        caloriesText.setText(currentItem != null ? currentItem.getCalories() : "");
        stepsText.setText(currentItem != null ? currentItem.getSteps() : "");
        descriptionText.setText(currentItem != null ? currentItem.getDetails() : "");

        // Add more code to set other UI elements as needed

        return listItemView;
    }
}
