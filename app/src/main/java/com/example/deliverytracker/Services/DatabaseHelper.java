package com.example.deliverytracker.Services;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.deliverytracker.models.Event;
import com.example.deliverytracker.models.LastPoint;
import com.example.deliverytracker.models.TrackData;
import java.util.List;
import java.util.ArrayList;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tracking.db";
    private static final int DATABASE_VERSION = 3;

    // Таблица для хранения данных
    private static final String TABLE_DATA = "data";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TRACKING_NUMBER = "tracking_number";
    private static final String COLUMN_DELIVERY_SERVICE = "delivery_service";

    // Таблица для хранения событий
    private static final String TABLE_EVENTS = "events";
    private static final String COLUMN_EVENT_ID = "id";
    private static final String COLUMN_EVENT_DATE = "event_date";
    private static final String COLUMN_SERVICE_NAME = "service_name";
    private static final String COLUMN_OPERATION_TYPE = "operation_type";
    private static final String COLUMN_OPERATION_DATE = "operation_date";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_AWAITING = "awaiting";
    private static final String COLUMN_USER_LABEL = "user_label"; // новое поле
    private static final String COLUMN_DATA_ID = "data_id"; // внешний ключ на data

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDataTable = "CREATE TABLE " + TABLE_DATA + " (" +
                COLUMN_TRACKING_NUMBER + " TEXT, " +
                COLUMN_DELIVERY_SERVICE + " TEXT, " +
                COLUMN_OPERATION_TYPE + " TEXT, " +
                COLUMN_OPERATION_DATE + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_AWAITING + " TEXT, " +
                COLUMN_USER_LABEL + " TEXT)"; // добавлено!


        String createEventsTable = "CREATE TABLE " + TABLE_EVENTS + " (" +
                COLUMN_EVENT_ID + "  TEXT, " +
                COLUMN_EVENT_DATE + " TEXT, " +
                COLUMN_SERVICE_NAME + " TEXT, " +
                COLUMN_OPERATION_TYPE + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_DATA_ID + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_DATA_ID + ") REFERENCES " + TABLE_DATA + "(" + COLUMN_TRACKING_NUMBER + ") ON DELETE CASCADE)";

        db.execSQL(createDataTable);
        db.execSQL(createEventsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }


    public boolean deleteRecordById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_DATA, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }
    public boolean deleteAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_DATA, null, null);
        db.close();
        return rowsDeleted > 0;
    }
    // Сохранение данных в таблицу data
    public long insertData(TrackData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRACKING_NUMBER, data.trackCode);
        values.put(COLUMN_DELIVERY_SERVICE, data.lastPoint.serviceName);
        values.put(COLUMN_OPERATION_TYPE, data.lastPoint.operationAttribute);
        values.put(COLUMN_LOCATION, data.lastPoint.operationPlaceName);
        values.put(COLUMN_OPERATION_DATE, data.lastPoint.operationDateTime);
        values.put(COLUMN_AWAITING, data.awaitingStatus);
        values.put(COLUMN_USER_LABEL, data.userLabel);
        long id = db.insert(TABLE_DATA, null, values);
        db.close();
        return id;
    }
    public int updateData(TrackData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRACKING_NUMBER, data.trackCode);
        values.put(COLUMN_DELIVERY_SERVICE, data.lastPoint.serviceName);
        values.put(COLUMN_OPERATION_TYPE, data.lastPoint.operationAttribute);
        values.put(COLUMN_OPERATION_DATE, data.lastPoint.operationDateTime);
        values.put(COLUMN_LOCATION, data.lastPoint.operationPlaceName);
        values.put(COLUMN_USER_LABEL, data.userLabel);

        // Обновление строки по id
        int rowsUpdated = db.update(
                TABLE_DATA,
                values,
                COLUMN_TRACKING_NUMBER + " = ?",
                new String[]{(data.trackCode)}
        );

        db.close();
        return rowsUpdated; // Возвращает количество обновленных строк
    }
    public void deleteEvents(String dataId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, COLUMN_DATA_ID + "=?", new String[]{String.valueOf(dataId)});
        db.close(); // Закрываем базу данных после завершения операции
    }
    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_DATE, event.operationDateTime);
        values.put(COLUMN_SERVICE_NAME, event.serviceName);
        values.put(COLUMN_OPERATION_TYPE, event.operationAttribute);
        values.put(COLUMN_LOCATION, event.operationPlaceNameTranslated);

        // Обновление строки по id
        int rowsUpdated = db.update(
                TABLE_EVENTS,
                values,
                COLUMN_DATA_ID + "=? and "+COLUMN_EVENT_ID+"=?",
                new String[]{event.DataId,}
        );

        db.close();
        return rowsUpdated; // Возвращает количество обновленных строк
    }

    // Сохранение данных в таблицу events
    public void insertEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_ID, event.id);
        values.put(COLUMN_EVENT_DATE, event.operationDateTime);
        values.put(COLUMN_SERVICE_NAME, event.serviceName);
        values.put(COLUMN_OPERATION_TYPE, event.operationAttribute);
        values.put(COLUMN_LOCATION, event.operationPlaceNameTranslated);

        values.put(COLUMN_DATA_ID, event.DataId);
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    public void updateUserLabel(String trackCode, String userLabel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_LABEL, userLabel);
        db.update(TABLE_DATA, values, COLUMN_TRACKING_NUMBER + "=?", new String[]{trackCode});
        db.close();
    }


    public void deleteTrackByCode(String trackCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Удаляем сначала связанные события
        db.delete(TABLE_EVENTS, COLUMN_DATA_ID + "=?", new String[]{trackCode});
        // Потом сам трек
        db.delete(TABLE_DATA, COLUMN_TRACKING_NUMBER + "=?", new String[]{trackCode});
        db.close();
    }



    // Получение всех событий

    public List<TrackData> getAllDataWithEvents() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<TrackData> dataList = new ArrayList<>();

        String query = "SELECT  d.tracking_number, d.delivery_service, d.operation_type as last_operation_type, d.operation_date as last_operation_date, d.location as last_location, d.awaiting, d.user_label," +
                "e.id AS event_id, e.event_date, e.service_name, e.operation_type, e.location " +
                "FROM data d " +
                "LEFT JOIN events e ON d.tracking_number = e.data_id";

        Cursor cursor = db.rawQuery(query, null);

        String lastDataId = "";
        TrackData currentData = null;

        LastPoint lastpont=null;
        if (cursor.moveToFirst()) {
            do {
                String dataId = cursor.getString(cursor.getColumnIndexOrThrow("tracking_number"));
                lastpont= new LastPoint(
                        cursor.getString(cursor.getColumnIndexOrThrow("last_operation_type")),
                        cursor.getString(cursor.getColumnIndexOrThrow("last_location")),
                        cursor.getString(cursor.getColumnIndexOrThrow("last_operation_date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("delivery_service"))
                );
                if (currentData == null || !dataId.equals(lastDataId)) {
                    currentData = new TrackData(
                            cursor.getString(cursor.getColumnIndexOrThrow("tracking_number")),
                            cursor.getString(cursor.getColumnIndexOrThrow("awaiting")),
                            lastpont
                    );
                    currentData.events=new ArrayList<>();
                    currentData.userLabel = cursor.getString(cursor.getColumnIndexOrThrow("user_label"));

                    dataList.add(currentData);
                    lastDataId = dataId;
                }


                if (!cursor.isNull(cursor.getColumnIndexOrThrow("event_id"))) {
                    Event event = new Event(
                            cursor.getString(cursor.getColumnIndexOrThrow("event_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("operation_type")),
                            cursor.getString(cursor.getColumnIndexOrThrow("location")),
                            cursor.getString(cursor.getColumnIndexOrThrow("event_date")),
                            cursor.getString(cursor.getColumnIndexOrThrow("service_name")),
                            currentData.trackCode
                    );

                    currentData.events.add(event);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }
}
