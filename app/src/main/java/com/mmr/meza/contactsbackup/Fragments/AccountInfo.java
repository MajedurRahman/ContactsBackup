package com.mmr.meza.contactsbackup.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmr.meza.contactsbackup.R;


public class AccountInfo extends Fragment {


    public AccountInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View parentView = inflater.inflate(R.layout.fragment_account_info, container, false);

        initComponent(parentView);
        return parentView;
    }


    public void initComponent(View parentView) {

        TextView contactsNumberTextView = (TextView) parentView.findViewById(R.id.currentContactsNumber);
        contactsNumberTextView.setText(ContactsList.contactsList.size() + "");
    }

}
