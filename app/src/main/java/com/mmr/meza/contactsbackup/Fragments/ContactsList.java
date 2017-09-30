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
import android.widget.Toast;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.github.tamir7.contacts.Query;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mmr.meza.contactsbackup.Activity.MainActivity;
import com.mmr.meza.contactsbackup.Adapter.RecyclerViewCustomAdapter;
import com.mmr.meza.contactsbackup.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsList extends Fragment {


    public ContactsList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        checkePermission(view);
        return view;
    }

    private void checkePermission(final View view){

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                initRecyclerView(view);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // permission denied
                RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                Snackbar snackbar = Snackbar.make(recyclerView , "Permission Denied " , Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        };

        TedPermission.with(getActivity())
                .setPermissionListener(permissionlistener)
                .setDeniedTitle("Permission denied")
                .setDeniedMessage(
                        "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("Go to Settings")
                .setPermissions(Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }

    private void initRecyclerView(View view) {


        RecyclerView rv = view.findViewById(R.id.recyclerViewContactsList);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());

        RecyclerViewCustomAdapter adapter = new RecyclerViewCustomAdapter(getActivity(),getAllContactsFromPhone());
        rv.setAdapter(adapter);
        rv.setLayoutManager(layout);

    }

    private List<Contact> getAllContactsFromPhone() {

        List<Contact> contactsList;
        Query q = Contacts.getQuery();
        q.hasPhoneNumber();
        contactsList = q.find();
        return contactsList;
    }

}
