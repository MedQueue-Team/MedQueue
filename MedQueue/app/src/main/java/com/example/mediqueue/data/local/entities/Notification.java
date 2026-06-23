package com.example.mediqueue.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import androidx.room.TypeConverter;

@Entity(tableName = "notifications")
public class Notification {
    public enum Type { QUEUE, SYNC, SUMMARY, EVENT, SECURITY }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String message;
    private Type type;
    private long timestamp;
    private boolean isRead;
    private boolean isUrgent;
    private String actionText;

    @Ignore
    public Notification(String title, String message, Type type, long timestamp, boolean isRead) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public Notification(String title, String message, Type type, long timestamp, boolean isRead, boolean isUrgent, String actionText) {
        this(title, message, type, timestamp, isRead);
        this.isUrgent = isUrgent;
        this.actionText = actionText;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { this.isRead = read; }
    public boolean isUrgent() { return isUrgent; }
    public void setUrgent(boolean urgent) { isUrgent = urgent; }
    public String getActionText() { return actionText; }
    public void setActionText(String actionText) { this.actionText = actionText; }

    public static class Converters {
        @TypeConverter
        public static String fromType(Type type) { return type.name(); }
        @TypeConverter
        public static Type toType(String value) { return Type.valueOf(value); }
    }
}
