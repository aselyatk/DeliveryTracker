package com.example.deliverytracker.Services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliverytracker.EventDetailsActivity;
import com.example.deliverytracker.R;
import com.example.deliverytracker.models.Event;
import com.example.deliverytracker.models.TrackData;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private List<TrackData> dataList;
    private List<TrackData> dataListFull;
    private Context context;
    public DataAdapter(Context context,List<TrackData> dataList) {
        this.dataList = dataList;
        this.dataListFull = new ArrayList<>(dataList);
        this.context=context;
    }
    public void setdataList(List<TrackData> dataList){
        this.dataList = dataList;
        this.dataListFull = new ArrayList<>(dataList); // обновляем и полный!
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        TrackData data = dataList.get(position);

        holder.trackingNumberTextView.setText("Tracking: " + data.trackCode);


        // События
        StringBuilder eventsBuilder = new StringBuilder();

            eventsBuilder.append("Date: ").append(data.lastPoint.operationDateTime)
                    .append("\nService: ").append(data.lastPoint.serviceName)
                    .append("\nType: ").append(data.lastPoint.operationAttribute)
                    .append("\nLocation: ").append(data.lastPoint.operationPlaceName)
                    .append("\n\n");

        holder.eventsTextView.setText(eventsBuilder.toString());
        holder.btnDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventDetailsActivity.class);
            intent.putExtra("events", data.events); // Передаём объект события
            intent.putExtra("deliveryService", data.lastPoint.serviceName); // Передаём объект события
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            // Удаление из БД
            DatabaseHelper db = new DatabaseHelper(context);
            db.deleteTrackByCode(data.trackCode);
            // Удаление из списка адаптера
            dataList.remove(position);
            dataListFull.removeIf(item -> item.trackCode.equals(data.trackCode));
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataList.size());
            Toast.makeText(context, "Посылка удалена", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView trackingNumberTextView, deliveryServiceTextView, eventsTextView;
        Button btnDetails;
        ImageButton btnDelete;


        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            trackingNumberTextView = itemView.findViewById(R.id.trackingNumberTextView);
            btnDetails=itemView.findViewById(R.id.btnDetails);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            eventsTextView = itemView.findViewById(R.id.eventsTextView);
        }
    }
    // Фильтрация:
    public void filter(String text) {
        List<TrackData> filteredList = new ArrayList<>();
        if (text.isEmpty()) {
            filteredList.addAll(dataListFull);
        } else {
            String filterPattern = text.toLowerCase().trim();
            for (TrackData item : dataListFull) {
                if (item.trackCode.toLowerCase().contains(filterPattern)) {
                    filteredList.add(item);
                }
            }
        }
        dataList.clear();
        dataList.addAll(filteredList);
        notifyDataSetChanged();
    }

}
