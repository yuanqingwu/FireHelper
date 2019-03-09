package com.wyq.firehelper.architecture.archcomponents;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {

    @NonNull
    private NetStatus netStatus;
    @Nullable
    private T data;
    @Nullable
    private String message;

    private Resource(@NonNull NetStatus netStatus, @Nullable T data, @Nullable String message) {
        this.netStatus = netStatus;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(NetStatus.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String message, @Nullable T data) {
        return new Resource<>(NetStatus.ERROR, data, message);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(NetStatus.LOADING, data, null);
    }

}
