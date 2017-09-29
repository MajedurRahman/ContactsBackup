package com.mmr.meza.contactsbackup.Application;

import android.app.Application;

import com.github.tamir7.contacts.Contacts;

/**
 * Created by Majedur Rahman on 9/28/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Contacts.initialize(this);
    }

}
