package com.example.eduability;

import android.app.Activity;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.Button;

import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

    int images[] = {

            R.drawable.children_preparing_their_backpack_cuate,

            R.drawable.raising_hand_cuate,



    };



    String titles[] = {

            "Online School",

            "Smart Kids",

            "Education"

    };



    String descriptions[] = {

            "Creativity Education & E-Learning",

            "Smart learning for kids",

            "Learning made easy"

    };



    @NonNull

    @Override

    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.item_slide, parent, false);

        return new SlideViewHolder(view);

    }



    @Override

    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {

        holder.image.setImageResource(images[position]);

        holder.title.setText(titles[position]);

        holder.desc.setText(descriptions[position]);

    }



    @Override

    public int getItemCount() {

        return images.length;

    }



    static class SlideViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        TextView title, desc;



        SlideViewHolder(@NonNull View itemView) {

            super(itemView);

            image = itemView.findViewById(R.id.slideImage);

            title = itemView.findViewById(R.id.slideTitle);

            desc = itemView.findViewById(R.id.slideDesc);

        }

    }

}