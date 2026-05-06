package com.example.eduability;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StoryAdapter extends ArrayAdapter<String> {

    public StoryAdapter(Context context, String[] titles) {
        super(context, 0, titles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_story, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.tvStoryTitle);
        tvTitle.setText(getItem(position));

        return convertView;
    }
}
