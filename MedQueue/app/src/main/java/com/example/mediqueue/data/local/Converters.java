package com.example.mediqueue.data.local;

import androidx.room.TypeConverter;
import com.example.mediqueue.core.UserRole;

public class Converters {
    @TypeConverter
    public static String fromUserRole(UserRole role) {
        return role == null ? null : role.name();
    }

    @TypeConverter
    public static UserRole toUserRole(String role) {
        return role == null ? null : UserRole.valueOf(role);
    }
}
