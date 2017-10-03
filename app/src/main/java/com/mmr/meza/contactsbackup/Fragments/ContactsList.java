package com.mmr.meza.contactsbackup.Fragments;


import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.github.tamir7.contacts.Query;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mmr.meza.contactsbackup.Adapter.RecyclerViewCustomAdapter;
import com.mmr.meza.contactsbackup.R;

import java.util.ArrayList;
import java.util.List;


public class ContactsList extends Fragment {


    public static List<Contact> contactsList;

    public ContactsList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);


        checkPermission(view);
        return view;
    }

    private void checkPermission(final View view) {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                initRecyclerView(view);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // permission denied
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
                Snackbar snackbar = Snackbar.make(recyclerView, R.string.denied_message, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        };

        TedPermission.with(getActivity())
                .setPermissionListener(permissionlistener)
                .setDeniedTitle(R.string.denied_permission)
                .setDeniedMessage(
                        R.string.denied_message)
                .setGotoSettingButtonText(R.string.go_to_settings)
                .setPermissions(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .check();

    }


    private void initRecyclerView(View view) {


        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerViewContactsList);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());

        RecyclerViewCustomAdapter adapter = new RecyclerViewCustomAdapter(getActivity(), getAllContactsFromPhone());
        rv.setAdapter(adapter);
        rv.setLayoutManager(layout);

    }

    public List<Contact> getAllContactsFromPhone() {

        Query q = Contacts.getQuery();
        q.hasPhoneNumber();
        contactsList = q.find();
        return contactsList;
    }


}
