package com.mmr.meza.contactsbackup.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.PhoneNumber;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mmr.meza.contactsbackup.R;

import java.util.ArrayList;
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

        return new AdapterViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {

        holder.nameTextView.setText(contactList.get(position).getDisplayName());
        List<PhoneNumber> phoneNumberList = contactList.get(position).getPhoneNumbers();
        holder.numberTextView.setText(phoneNumberList.get(0).getNumber() + "");


        holder.onItemClick(contactList.get(position));


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        Context context;
        TextView numberTextView;
        RelativeLayout relativeLayout;
        Button callButton;

        public AdapterViewHolder(View itemView, Context c) {
            super(itemView);
            context = c;
            nameTextView = (TextView) itemView.findViewById(R.id.contactName);
            numberTextView = (TextView) itemView.findViewById(R.id.contactNumber);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.itemRelativeLayout);
            callButton = (Button) itemView.findViewById(R.id.call_button_item);


        }


        public void onItemClick(final Contact contact) {

            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    permissionCheck(contact.getPhoneNumbers().get(0).getNumber());
                }
            });


        }


        public void permissionCheck(final String number) {
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {


                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                    context.startActivity(intent);

                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    // permission denied

                }
            };


            TedPermission.with(context)
                    .setPermissionListener(permissionlistener)
                    .setDeniedTitle("Permission denied")
                    .setDeniedMessage(
                            R.string.denied_message)
                    .setGotoSettingButtonText("Go to Settings")
                    .setPermissions(Manifest.permission.CALL_PHONE)
                    .check();


        }
    }
}
