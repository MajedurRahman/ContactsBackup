package com.mmr.meza.contactsbackup.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.PhoneNumber;
import com.mmr.meza.contactsbackup.R;

import java.util.List;

/**
 * Created by Majedur Rahman on 9/29/2017.
 */

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.AdapterViewHolder> {


    Context context;
    List<Contact> contactList;
    LayoutInflater layoutInflater;


    public RecyclerViewCustomAdapter(Context c, List<Contact> contactses) {

        context = c;
        contactList = contactses;
        layoutInflater = LayoutInflater.from(context);


    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.contacts_item, parent, false);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {

        holder.nameTextView.setText(contactList.get(position).getDisplayName());
        List<PhoneNumber> phoneNumberList = contactList.get(position).getPhoneNumbers();
        holder.numberTextView.setText(phoneNumberList.get(0).getNumber() + "");
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView numberTextView;

        public AdapterViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.contactName);
            numberTextView = itemView.findViewById(R.id.contactNumber);
        }
    }
}
