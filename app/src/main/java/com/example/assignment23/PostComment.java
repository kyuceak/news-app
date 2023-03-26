package com.example.assignment23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PostComment extends AppCompatActivity {

    EditText editName;
    EditText editComment;
    Button postButton;
    ProgressBar prg;
    TextView error;
    int id;
    int status;




    Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            status = (int)message.obj;


            prg.setVisibility(View.INVISIBLE);



            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        error = findViewById(R.id.error);
        editName = findViewById(R.id.editName);
        editComment = findViewById(R.id.editComment);
        postButton = findViewById(R.id.postButton);
        prg = findViewById(R.id.progBarPost);
        id = getIntent().getIntExtra("newidcomm",1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = editName.getText().toString();

                String comment = editComment.getText().toString();

                if(name.equals("") || comment.equals("")) {
                    error.setVisibility(View.VISIBLE);
                }else{
                    error.setVisibility(View.INVISIBLE);
                    prg.setVisibility(View.VISIBLE);


                NewsRepository repo = new NewsRepository();
                repo.postComment(((NewsApp) getApplication()).srv,handle,name,comment,id);


                    Intent i = new Intent(PostComment.this,CommentsActivity.class);
                    i.putExtra("newsidcom",id);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(i);
                }

            }
        });



    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            Intent i = new Intent(PostComment.this,CommentsActivity.class);
            i.putExtra("newsidcom",id);

            startActivity(i);
        }






        return true;
    }

}