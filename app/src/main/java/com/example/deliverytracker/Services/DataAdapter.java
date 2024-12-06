package com.example.deliverytracker.Services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliverytracker.EventDetailsActivity;
import com.example.deliverytracker.R;
import com.example.deliverytracker.models.Event;
import com.example.deliverytracker.models.TrackData;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private List<TrackData> dataList;
    private Context context;
    public DataAdapter(Context context,List<TrackData> dataList) {
        this.dataList = dataList;
        this.context=context;
    }
    public void setdataList(List<TrackData> dataList){
        this.dataList = dataList;
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
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView trackingNumberTextView, deliveryServiceTextView, eventsTextView;
        Button btnDetails;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            trackingNumberTextView = itemView.findViewById(R.id.trackingNumberTextView);
            btnDetails=itemView.findViewById(R.id.btnDetails);
            eventsTextView = itemView.findViewById(R.id.eventsTextView);
        }
    }
}
