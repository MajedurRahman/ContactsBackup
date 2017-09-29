package com.mmr.meza.contactsbackup.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.github.tamir7.contacts.Query;
import com.mmr.meza.contactsbackup.Adapter.RecyclerViewCustomAdapter;
import com.mmr.meza.contactsbackup.R;

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

        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {


        RecyclerView rv = view.findViewById(R.id.recyclerViewContactsList);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());

        RecyclerViewCustomAdapter adapter = new RecyclerViewCustomAdapter(getActivity(),getAllContacsFromPhone());
        rv.setAdapter(adapter);
        rv.setLayoutManager(layout);

    }

    private List<Contact> getAllContacsFromPhone() {

        List<Contact> contactsList;
        Query q = Contacts.getQuery();
        q.hasPhoneNumber();
        contactsList = q.find();
        return contactsList;
    }

}
