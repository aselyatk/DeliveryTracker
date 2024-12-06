package com.example.deliverytracker;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliverytracker.Services.DataAdapter;
import com.example.deliverytracker.Services.DatabaseHelper;
import com.example.deliverytracker.Services.NotificationHelper;
import com.example.deliverytracker.Services.TrackParser;
import com.example.deliverytracker.Services.TrackingApiService;
import com.example.deliverytracker.Services.TrackingRepository;
import com.example.deliverytracker.models.TrackData;
import com.example.deliverytracker.models.TrackResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class TrackInfoActivity extends AppCompatActivity
{
    private EditText packageNumberInput;
    private Button searchButton;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private TrackingApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_track_info);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        packageNumberInput = findViewById(R.id.packageNumberInput);
        searchButton = findViewById(R.id.searchButton);

        databaseHelper = new DatabaseHelper(this);
        TrackingRepository repository = new TrackingRepository();
        List<TrackData> dataList = databaseHelper.getAllDataWithEvents();
        List<TrackData>responseList=new ArrayList<TrackData>();
        if (!dataList.isEmpty()) {
            dataAdapter = new DataAdapter(TrackInfoActivity.this,dataList);
            recyclerView.setAdapter(dataAdapter);

        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем введенный номер посылки
                String packageNumber = packageNumberInput.getText().toString();

                // Логика для получения данных о посылке (это просто пример)
                if (!packageNumber.isEmpty()) {
                    repository.fetchTrackingData("RB287529899RU", new TrackingRepository.TrackingDataCallback() {
                        @Override
                        public void onSuccess(TrackResponse response) {
                            boolean rewrite=false;
                            if(response.data.awaitingStatus.equals("1")){
                                NotificationHelper.sendNotification(TrackInfoActivity.this, "Tracking Update", "Your package has arrived!");
                            }
                            if (dataList.isEmpty() && responseList.isEmpty()) {
                                databaseHelper.insertData(response.data);

                                response.data.events.forEach(e->{
                                    databaseHelper.insertEvent(e);
                                });

                                responseList.add(response.data);
                                dataAdapter = new DataAdapter(TrackInfoActivity.this,responseList);
                                recyclerView.setAdapter(dataAdapter);

                                System.out.println("NO Rewrite");


                            } else if(dataList.isEmpty()) {

                                for(TrackData data:responseList)
                                    if(data.trackCode.equals(response.data.trackCode)){
                                        data.lastPoint=response.data.lastPoint;
                                        data.events=response.data.events;
                                        databaseHelper.updateData(data);
                                        data.events.forEach(e->{
                                            databaseHelper.updateEvent(e);
                                        });
                                        rewrite=true;
                                    }
                                if(rewrite) {

                                    System.out.println("ReWrite");
                                    dataAdapter.setdataList(responseList);
                                    dataAdapter.notifyDataSetChanged();
                                }
                            }else {
                                for(TrackData data:dataList)
                                    if(data.trackCode.equals(response.data.trackCode)){
                                        data.lastPoint=response.data.lastPoint;
                                        data.events=response.data.events;
                                        databaseHelper.updateData(data);
                                        data.events.forEach(e->{
                                            databaseHelper.updateEvent(e);
                                        });
                                        rewrite=true;
                                    }
                                if(rewrite) {

                                    System.out.println("ReWrite");
                                    dataAdapter.setdataList(dataList);
                                    dataAdapter.notifyDataSetChanged();
                                }
                            }
                            Log.d("TrackInfo", "Tracking number: " + response.data.trackCode);
                        }

                        @Override
                        public void onError(Throwable t) {
                            Log.e("TrackInfo", "Error: " + t.getMessage());
                        }
                    });



                }

            }

        });
        dataList.forEach(data->{
            if(data.awaitingStatus.equals("1"))
            {
                NotificationHelper.sendNotification(this, "Tracking Update", "Your package has arrived!");
            }
        });


    }

}
