package com.example.assignment23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager2 viewPager2;
    static List<Categories> data = new ArrayList<>();
    static List<Categories> unordered_data = new ArrayList<>();


    Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            unordered_data = (List<Categories>)message.obj;


            Collections.sort(unordered_data, (o1, o2) -> String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId())));

            data = (List<Categories>)message.obj;

             tabLayout = findViewById(R.id.tabLayout);
             viewPager2 = findViewById(R.id.pager);

            AdapterFragment adapterFragment = new AdapterFragment(getSupportFragmentManager(),getLifecycle());
            viewPager2.setAdapter(adapterFragment);
            adapterFragment.notifyDataSetChanged();



            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {


                tab.setText(data.get(position).getName() );


            }).attach();


            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hdpi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NewsRepository repo = new NewsRepository();
        repo.GetAllNewsCategories(((NewsApp) getApplication()).srv,handle);



    }
}