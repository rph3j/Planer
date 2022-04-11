package com.example.planer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameEt;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initwidgets();
        time = LocalTime.now();
        eventDateTV.setText("Data:" + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventDateTV.setText("Czas:" + CalendarUtils.formattedTime(time));
    }

    private void initwidgets()
    {
        eventNameEt = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view)
    {
        String eventName = eventNameEt.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate,time);
        Event.eventsList.add(newEvent);
        finish();
    }
}