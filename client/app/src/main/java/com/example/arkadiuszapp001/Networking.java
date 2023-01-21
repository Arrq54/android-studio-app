package com.example.arkadiuszapp001;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Networking {

    public boolean checkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfoWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (netInfoWifi != null && netInfoWifi.isConnectedOrConnecting())
            return true;
        else
            return false;
    }
}
