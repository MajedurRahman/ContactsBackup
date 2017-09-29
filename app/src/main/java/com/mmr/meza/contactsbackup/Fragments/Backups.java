package com.mmr.meza.contactsbackup.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmr.meza.contactsbackup.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Backups extends Fragment {


    public Backups() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_backups, container, false);
    }

}
