package com.codingtive.dicodingmade.preload.service;

public interface LoadDataCallback {
    void onPreload();

    void onProgressUpdate(long progress);

    void onLoadSuccess();

    void onLoadFailed();

    void onLoadCancel();
}
