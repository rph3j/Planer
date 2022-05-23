package com.example.planer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.planer.CalendarUtils.daysInMonthArray;
import static com.example.planer.CalendarUtils.daysInWeekArray;
import static com.example.planer.CalendarUtils.monthYearFromDate;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private boolean menuVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();

        ImageButton NW = findViewById(R.id.nextWeek);
        NW.setOnClickListener(view -> nextWeekAction());

        ImageButton PW = findViewById(R.id.previousWeek);
        PW.setOnClickListener(view -> previousWeekAction());

        Button ADD = findViewById(R.id.addEvent);
        ADD.setOnClickListener(view -> newEventAction());

        Button D = findViewById(R.id.day);
        D.setOnClickListener(view -> dailyAction());

        Button M = findViewById(R.id.month);
        M.setOnClickListener(view -> monthAction());
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }


    public void previousWeekAction()
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction()
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction()
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        finish();
    }

    public void dailyAction()
    {
        startActivity(new Intent(this, DayCalender.class));
    }
    public void monthAction()
    {
        startActivity(new Intent(this, MainActivity.class));
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
}