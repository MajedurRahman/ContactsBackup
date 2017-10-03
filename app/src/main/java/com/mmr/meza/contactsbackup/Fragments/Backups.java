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

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.PhoneNumber;
import com.google.firebase.auth.FirebaseAuth;
import com.mmr.meza.contactsbackup.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Backups extends Fragment {


    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;


    public Backups() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backups, container, false);
        initComponent(view);
        auth = FirebaseAuth.getInstance();
        return view;
    }

    private void initAuth() {


        //need to check Auth to Login auth.getCurrentUser() != null
        if (auth.getCurrentUser() != null) {

            // already signed in


        } else {


            // not signed in
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(
                                    Arrays.asList(
                                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                                    ))
                            .build(),
                    RC_SIGN_IN);

        }
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


        parentView.findViewById(R.id.backupCloudButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initAuth();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {


                /**
                 *
                 *
                 *
                 *
                 *
                 */
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Log.e("Login", "Login canceled by User");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Log.e("Login", "No Internet Connection");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Log.e("Login", "Unknown Error");
                    return;
                }
            }

            Log.e("Login", "Unknown sign in response");
        }
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

            RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeBackup);
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
