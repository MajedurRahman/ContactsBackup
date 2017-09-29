package com.mmr.meza.contactsbackup.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mmr.meza.contactsbackup.Adapter.NavigationViewAdapter;
import com.mmr.meza.contactsbackup.Fragments.AccountInfo;
import com.mmr.meza.contactsbackup.Fragments.Backups;
import com.mmr.meza.contactsbackup.Fragments.ContactsList;
import com.mmr.meza.contactsbackup.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initComponent();

    }

    private void initActionListener() {


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.backups_menu:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.contacts_menu:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.accountInfo_menu:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                menuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override

            public void onPageScrollStateChanged(int state) {

            }
        });


        setUpPager(viewPager);
    }

    private void setUpPager(ViewPager viewPager) {

        NavigationViewAdapter adapter = new NavigationViewAdapter(getSupportFragmentManager());

        adapter.addFragment(new Backups());
        adapter.addFragment(new ContactsList());
        adapter.addFragment(new AccountInfo());
        viewPager.setAdapter(adapter);
    }


    public void initComponent() {


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        initActionListener();



    }
}
