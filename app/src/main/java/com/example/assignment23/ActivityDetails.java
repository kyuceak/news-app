package com.example.assignment23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityDetails extends AppCompatActivity {



    ImageView imgDetails;
    TextView txtDetailsTitle;
    TextView txtDetailsText;
    TextView txtDetailsDate;

    News news;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            news= (News) message.obj;

            txtDetailsTitle.setText(news.getTitle());
            txtDetailsDate.setText(news.getDate());
            txtDetailsText.setText(news.getText());

            NewsRepository repo = new NewsRepository();
            repo.downloadImage(((NewsApp)getApplication()).srv,imgHandler,news.getImage());



            return true;
        }
    });

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            Bitmap img = (Bitmap) message.obj;
            imgDetails.setImageBitmap(img);

            return true;
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int id = getIntent().getIntExtra("id",1);


        imgDetails = findViewById(R.id.imgDetails);
        txtDetailsTitle = findViewById(R.id.txtDetailsTitle);
        txtDetailsText = findViewById(R.id.txtDetailsText);
        txtDetailsDate = findViewById(R.id.txtDetailsDate);

        NewsRepository repo = new NewsRepository();
        repo.GetNewsById(((NewsApp)getApplication()).srv,dataHandler,id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()== R.id.menu_item){
            Intent i = new Intent(this,CommentsActivity.class);
            i.putExtra("newsidcom",news.getId());
            startActivity(i);
        }

        if(item.getItemId()==android.R.id.home){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }



        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}