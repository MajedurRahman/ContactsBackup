package com.mmr.meza.contactsbackup.Fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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

        View view =inflater.inflate(R.layout.fragment_backups, container, false);
        final FrameLayout frameLayout = view.findViewById(R.id.frame);

        view.findViewById(R.id.buttonOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(frameLayout , "majedur " , Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });


        return view;
    }

}
