package com.example.deliverytracker;



import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliverytracker.Services.EventsAdapter;
import com.example.deliverytracker.models.Event;

import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity {

    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;
    private TextView itemWight;
    private TextView trackNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Event> eventList = (ArrayList<Event>) getIntent().getSerializableExtra("events");
        eventsAdapter = new EventsAdapter(eventList);
        eventsRecyclerView.setAdapter(eventsAdapter);
        String deliveryService = getIntent().getStringExtra("deliveryService");
        TextView deliveryServiceLabel = findViewById(R.id.deliveryServiceLabel);
        deliveryServiceLabel.setText("Служба доставки: " + deliveryService);
    }
}
