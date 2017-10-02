package com.mmr.meza.contactsbackup.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.PhoneNumber;
import com.mmr.meza.contactsbackup.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Backups extends Fragment {


    public Backups() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backups, container, false);
        initComponent(view);
        return view;
    }

    private void initComponent(final View parentView) {
        //Save to Sd Card Button Initialization
        parentView.findViewById(R.id.backupSdCardButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View localView) {

                new BackgroundTask(getActivity(), parentView).execute();

            }
        });

        parentView.findViewById(R.id.ShareEmailButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View localView) {
                try {
                    BackgroundTask bTask = new BackgroundTask(getActivity(), parentView);
                    bTask.execute();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + ""));
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.mail_text));
                    intent.putExtra(Intent.EXTRA_STREAM, bTask.getVcfPath());
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    //TODO smth
                }
            }
        });

        parentView.findViewById(R.id.restoreFromLocalButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View localView) {


                File vcfFile = new File(Environment.getExternalStorageDirectory() + getString(R.string.contact_folder_name) + getString(R.string.vcf_file_name));

                Intent intent = new Intent(); //this will import vcf in contact list
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(vcfFile), "text/x-vcard");
                startActivity(Intent.createChooser(intent, getString(R.string.promote_choose)));
            }
        });
    }

    public class BackgroundTask extends AsyncTask<Void, Void, String> {

        private static final String VCF_DIRECTORY = "/Contacts Backup/";
        String fileName = "ContactsBackup.vcf";
        Context context;
        FileWriter fw = null;
        File vcfFile;
        File vdfdirectory;
        View view;

        BackgroundTask(Context c, View v) {
            context = c;
            view = v;
        }

        @Override
        protected void onPreExecute() {

            initializeData();
        }

        private void initializeData() {
            //  super.onPreExecute();
            try {
                // File vcfFile = new File(this.getExternalFilesDir(null), "generated.vcf");
                vdfdirectory = new File(
                        Environment.getExternalStorageDirectory() + VCF_DIRECTORY);
                // have the object build the directory structure, if needed.
                if (!vdfdirectory.exists()) {
                    vdfdirectory.mkdirs();
                }
                vcfFile = new File(vdfdirectory, fileName);
                fw = new FileWriter(new File(Environment.getExternalStorageDirectory() + "/" + "ContactsBackup.vcf"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Uri getVcfPath() {

            Uri path = Uri.fromFile(vcfFile);
            return path;
        }

        @Override
        protected String doInBackground(Void... voids) {
            int i = 0;
            StringBuilder VcfFile = new StringBuilder();

            for (Contact c : ContactsList.contactsList) {
                List<PhoneNumber> phoneNumberList = c.getPhoneNumbers();
                /**
                 * write data to file
                 * with Display name and phone number
                 */
                fileWrite(c.getDisplayName(), phoneNumberList.get(0).getNumber());
            }
            return VcfFile.toString() + "   " + i;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Contact", s);
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            RelativeLayout relativeLayout = view.findViewById(R.id.relativeBackup);
            Snackbar snackbar = Snackbar.make(relativeLayout, "Backup Complete", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        void fileWrite(String name, String phone) {

            try {
                fw.write("BEGIN:VCARD\r\n");
                fw.write("VERSION:3.0\r\n");
                fw.write("FN:" + "" + name + "\r\n");
                fw.write("TEL;TYPE=WORK,VOICE:" + "" + phone + "\r\n");
                //  fw.write("EMAIL;TYPE=PREF,Email:" + "" + email + "\r\n");
                fw.write("END:VCARD\r\n");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
