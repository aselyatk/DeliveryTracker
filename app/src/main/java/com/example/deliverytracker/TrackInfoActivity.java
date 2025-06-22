package com.example.deliverytracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;
import com.example.deliverytracker.models.Event;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.deliverytracker.Services.DataAdapter;
import com.example.deliverytracker.Services.DatabaseHelper;
import com.example.deliverytracker.Services.NotificationHelper;
import com.example.deliverytracker.Services.TrackingNumberValidator;
import com.example.deliverytracker.Services.TrackingRepository;
import com.example.deliverytracker.models.TrackData;
import com.example.deliverytracker.models.TrackResponse;

import java.util.ArrayList;
import java.util.List;

public class TrackInfoActivity extends AppCompatActivity {

    private EditText packageNumberInput;
    private Button searchButton;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_track_info);

        // Инициализация
        packageNumberInput = findViewById(R.id.packageNumberInput);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);
        TrackingRepository repository = new TrackingRepository();

        // Загружаем треки из базы
        List<TrackData> dataList = databaseHelper.getAllDataWithEvents();
        dataAdapter = new DataAdapter(TrackInfoActivity.this, dataList);
        recyclerView.setAdapter(dataAdapter);

        // Поиск по списку
        EditText searchInput = findViewById(R.id.searchInput);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (dataAdapter != null) {
                    dataAdapter.filter(s.toString());
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Диалоговое меню
        Button showDialogButton = findViewById(R.id.menuButton);
        showDialogButton.setOnClickListener(v -> showCustomDialog());

        // Поиск новой посылки по трек-номеру
        searchButton.setOnClickListener(v -> {
            String packageNumber = packageNumberInput.getText().toString().trim();
            if (!packageNumber.isEmpty() && TrackingNumberValidator.check(packageNumber)) {
                repository.fetchTrackingData(packageNumber, new TrackingRepository.TrackingDataCallback() {
                    @Override
                    public void onSuccess(TrackResponse response) {
                        if (!response.status.equals("ok")) {
                            Toast.makeText(TrackInfoActivity.this, response.message, Toast.LENGTH_LONG).show();
                            return;
                        }
                        // Проверяем есть ли уже такой трек
                        List<TrackData> updatedDataList = databaseHelper.getAllDataWithEvents();
                        boolean exists = false;
                        for (TrackData data : updatedDataList) {
                            if (response.status.equals("ok")) {
                                databaseHelper.updateData(response.data);
                                for (Event e : response.data.events) {
                                    databaseHelper.updateEvent(e);
                                }
                            }

                        }
                        if (!exists) {
                            databaseHelper.insertData(response.data);
                            for (Event e : response.data.events) {
                                databaseHelper.updateEvent(e);
                            }

                        }
                        // Обновляем список на экране
                        runOnUiThread(() -> {
                            dataAdapter.setdataList(databaseHelper.getAllDataWithEvents());
                        });

                        if (response.data.awaitingStatus.equals("1")) {
                            NotificationHelper.sendNotification(TrackInfoActivity.this, "Tracking Update", "Your package has arrived!");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("TrackInfo", "Error: " + t.getMessage());
                    }
                });
            } else {
                Toast.makeText(TrackInfoActivity.this, "Track number is invalid", Toast.LENGTH_LONG).show();
            }
        });

        // Авто-уведомление по ожидающим
        dataList.forEach(data -> {
            if (data.awaitingStatus.equals("1")) {
                NotificationHelper.sendNotification(this, "Tracking Update", "Your package has arrived!");
            }
        });

        // PULL-TO-REFRESH — свайп вниз
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            List<TrackData> currentList = databaseHelper.getAllDataWithEvents();
            if (currentList.isEmpty()) {
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
            TrackingRepository rep = new TrackingRepository();
            int[] counter = {0};
            for (TrackData track : currentList) {
                rep.fetchTrackingData(track.trackCode, new TrackingRepository.TrackingDataCallback() {
                    @Override
                    public void onSuccess(TrackResponse response) {
                        if (response.status.equals("ok")) {
                            databaseHelper.updateData(response.data);
                            for (Event e : response.data.events) {
                                databaseHelper.updateEvent(e);
                            }

                        }
                        counter[0]++;
                        if (counter[0] == currentList.size()) {
                            runOnUiThread(() -> {
                                dataAdapter.setdataList(databaseHelper.getAllDataWithEvents());
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(TrackInfoActivity.this, "Статусы обновлены!", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        counter[0]++;
                        if (counter[0] == currentList.size()) {
                            runOnUiThread(() -> {
                                dataAdapter.setdataList(databaseHelper.getAllDataWithEvents());
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(TrackInfoActivity.this, "Статусы обновлены (ошибки возможны)!", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                });
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        TextView dialogText = dialogView.findViewById(R.id.dialog_text);
        Button dialogLink = dialogView.findViewById(R.id.dialog_link);
        Button deleteLink = dialogView.findViewById(R.id.DeleteLink);

        dialogLink.setOnClickListener(v -> {
            Intent intent = new Intent(TrackInfoActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
        deleteLink.setOnClickListener(v -> {
            databaseHelper.deleteAllRecords();
            dataAdapter.setdataList(new ArrayList<>());
            dataAdapter.notifyDataSetChanged();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
