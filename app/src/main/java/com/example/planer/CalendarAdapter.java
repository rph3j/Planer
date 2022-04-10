package com.example.planer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysofMonth;
    private final OnItemListener onItemListener;

    CalendarAdapter(ArrayList<String> daysofMonth, OnItemListener onItemListener)
    {
        this.daysofMonth = daysofMonth;
        this.onItemListener = onItemListener;
    }


    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        holder.dayofMonth.setText(daysofMonth.get(position));
    }

    @Override
    public int getItemCount()
    {
        return daysofMonth.size();
    }

    public interface OnItemListener
    {
        void OnItemClick(int position, String dayText);
    }
}
