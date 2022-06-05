package com.example.planer;

import static com.example.planer.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DayCalender extends AppCompatActivity {

    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;
    private boolean menuVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_calender);
        initWidgets();

        ImageButton ND = findViewById(R.id.nextDay);
        ND.setOnClickListener(view -> nextDayAction());

        ImageButton PD = findViewById(R.id.previousDay);
        PD.setOnClickListener(view -> previousDayAction());

        Button W = findViewById(R.id.weekly);
        W.setOnClickListener(view -> weeklyAction());

        Button M = findViewById(R.id.month);
        M.setOnClickListener(view -> monthAction());
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        setDayView();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        finish();
    }

    private void initWidgets()
    {
        monthDayText = findViewById(R.id.monthDayText);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        hourListView = findViewById(R.id.hourListView);
    }

    private void setDayView()
    {
        monthDayText.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        setHourAdapter();
    }

    private void setHourAdapter()
    {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);
    }

    private ArrayList<HourEvent> hourEventList()
    {
        ArrayList<HourEvent> list = new ArrayList<>();

        for(int hour = 0; hour < 24; hour++)
        {
            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<Event> events = Event.eventsForDateAndTime(selectedDate, time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }

        return list;
    }

    public void previousDayAction()
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDayAction()
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        setDayView();
    }
    public void weeklyAction()
    {
        startActivity(new Intent(this, WeekViewActivity.class));
    }
    private void monthAction() {startActivity(new Intent(this, MainActivity.class)); }
    public void newEventAction()
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    public void makeVisability(View view)
    {
        if(menuVisible) {
            findViewById(R.id.menu).setVisibility(View.INVISIBLE);
            menuVisible = false;
        }
        else {
            findViewById(R.id.menu).setVisibility(View.VISIBLE);
            menuVisible = true;
        }
    }
    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }
}