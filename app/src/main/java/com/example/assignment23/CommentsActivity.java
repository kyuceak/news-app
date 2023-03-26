package com.example.assignment23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    RecyclerView recComments;
    ProgressBar prg;
    int id;

    Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            List<Comment> data = (List<Comment>) message.obj;
            CommentsAdapter adp = new CommentsAdapter(CommentsActivity.this,data);
            recComments.setAdapter(adp);

            prg.setVisibility(View.INVISIBLE);


            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        prg = findViewById(R.id.progressBarc);
        id = getIntent().getIntExtra("newsidcom",1);

        recComments = findViewById(R.id.recComments);
        recComments.setLayoutManager(new LinearLayoutManager(this));

        NewsRepository repo = new NewsRepository();
        repo.GetCommentById(((NewsApp) getApplication()).srv,handle,id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()== R.id.menu_icon){
            Intent i = new Intent(this,PostComment.class);
            i.putExtra("newidcomm", id);

            startActivity(i);
        }



        if(item.getItemId()==android.R.id.home){
            Intent i = new Intent(this,ActivityDetails.class);
            i.putExtra("id",id);
            startActivity(i);
        }



        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuicon, menu);
        return true;
    }
}
