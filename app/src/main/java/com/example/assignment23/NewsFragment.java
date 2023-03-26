package com.example.assignment23;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;


public class NewsFragment extends Fragment {
    RecyclerView recView;
    ProgressBar prg;

    public static final String title = "title";
    public static final String idcat = "idcat";

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<News> data = (List<News>)message.obj;
            NewsAdapter adp = new NewsAdapter(getActivity(),data);

            recView.setAdapter(adp);
            adp.notifyDataSetChanged();





            prg.setVisibility(View.INVISIBLE);
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_news, container, false);



        recView = v.findViewById(R.id.recView);
        prg = v.findViewById(R.id.progBar);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));




        NewsRepository repo = new NewsRepository();

        repo.GetNewByCategoryId(((NewsApp)getActivity().getApplication()).srv,dataHandler, getArguments().getInt("idcat"));

        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}