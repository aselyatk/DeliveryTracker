package com.example.deliverytracker.Services;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliverytracker.EventDetailsActivity;
import com.example.deliverytracker.R;
import com.example.deliverytracker.models.Event;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private List<Event> eventList;

    public EventsAdapter( List<Event> eventList) {
        this.eventList = eventList;

    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.dateTextView.setText("Дата прибытия \t"+event.operationDateTime);
        holder.serviceNameTextView.setText("Название службы \t"+event.serviceName);
        holder.operationTypeTextView.setText("Статус \t"+event.operationAttribute);
        holder.locationTextView.setText("Местонахождения \t"+event.operationPlaceNameTranslated);
        if (position == eventList.size()) {
            // Последний элемент: стрелка скрыта
            holder.arrowIcon.setVisibility(View.INVISIBLE);
        } else {
            // Остальные элементы: стрелка видна
            holder.arrowIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView serviceNameTextView;
        TextView operationTypeTextView;
        TextView locationTextView;

        ImageView arrowIcon;

        public EventViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.eventDate);
            serviceNameTextView = itemView.findViewById(R.id.serviceName);
            operationTypeTextView = itemView.findViewById(R.id.operationType);
            locationTextView = itemView.findViewById(R.id.location);
            arrowIcon = itemView.findViewById(R.id.arrowIcon);
        }
    }
}
