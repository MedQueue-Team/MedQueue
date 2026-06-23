package com.example.mediqueue.ui.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SystemLog {
    public enum Type {
        INFO,
        WARNING,
        ERROR,
        SUCCESS
    }

    private final String timestamp;
    private final Type type;
    private final String message;

    public SystemLog(Type type, String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        this.timestamp = sdf.format(new Date());
        this.type = type;
        this.message = message;
    }

    public String getTimestamp() { return timestamp; }
    public Type getType() { return type; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + type.name() + ": " + message;
    }
}
