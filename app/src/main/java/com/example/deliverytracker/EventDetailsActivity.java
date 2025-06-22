package com.example.deliverytracker;

import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.deliverytracker.Services.DatabaseHelper;

import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

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

        // ==== Вот этот блок — добавь сюда ====
        String trackCode = getIntent().getStringExtra("trackCode");
        Button btnRename = findViewById(R.id.btnRename);
        btnRename.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailsActivity.this);
            builder.setTitle("Новое название посылки");

            final EditText input = new EditText(EventDetailsActivity.this);
            input.setHint("Например: Заказ с OZON");
            builder.setView(input);

            builder.setPositiveButton("Сохранить", (dialog, which) -> {
                String label = input.getText().toString();
                if (!label.trim().isEmpty()) {
                    DatabaseHelper db = new DatabaseHelper(EventDetailsActivity.this);
                    db.updateUserLabel(trackCode, label);
                    Toast.makeText(EventDetailsActivity.this, "Название сохранено", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            });

            builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }
}

