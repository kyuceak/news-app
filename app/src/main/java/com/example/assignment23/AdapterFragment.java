package com.example.assignment23;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterFragment extends FragmentStateAdapter {


    public AdapterFragment(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override



    public Fragment createFragment(int position) {
        Fragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(NewsFragment.title,"Tab"+(position+1));
        args.putInt(NewsFragment.idcat, position+1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
