package com.example.assignment23;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsRepository {



    public void GetAllNewsCategories(ExecutorService srv, Handler uiHandler){




        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getallnewscategories");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject responseJson = new JSONObject(buffer.toString());
                JSONArray arr = responseJson.getJSONArray("items");

                List<Categories> categories = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);
                    Categories cat = new Categories(current.getInt("id"), current.getString("name"));
                    categories.add(cat);
                }


                Message msg = new Message();
                msg.obj = categories;
                uiHandler.sendMessage(msg);

                conn.disconnect();







            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });


    }

    public void GetNewByCategoryId(ExecutorService srv,Handler uiHandler, int id){

        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject responseJson = new JSONObject(buffer.toString());
                JSONArray arr = responseJson.getJSONArray("items");

                List<News> newsList = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);
                    News newsItem = new News(current.getInt("id"), current.getString("title"),
                            current.getString("text"), current.getString("date"), current.getString("image"),
                            current.getString("categoryName"));
                    newsList.add(newsItem);
                }


                Message msg = new Message();
                msg.obj = newsList;
                uiHandler.sendMessage(msg);

                conn.disconnect();










            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

    }

    public void downloadImage(ExecutorService srv, Handler uiHandler, String path){

        srv.execute(()->{
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                Bitmap bitmap =  BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    public void GetNewsById(ExecutorService srv, Handler uiHandler, int id){

        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject responseJson = new JSONObject(buffer.toString());
                JSONArray arr = responseJson.getJSONArray("items");




                    JSONObject current = arr.getJSONObject(0);
                    News newsItem = new News(current.getInt("id"), current.getString("title"),
                            current.getString("text"), current.getString("date"), current.getString("image"),
                            current.getString("categoryName"));




                Message msg = new Message();
                msg.obj = newsItem;
                uiHandler.sendMessage(msg);

                conn.disconnect();










            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

    }

    public void GetCommentById(ExecutorService srv, Handler uiHandler, int id){

        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject responseJson = new JSONObject(buffer.toString());
                JSONArray arr = responseJson.getJSONArray("items");

                List<Comment> comList = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);
                    Comment commentItem = new Comment(current.getInt("id"),current.getInt("news_id"),
                            current.getString("text"),current.getString("name") );
                    comList.add(commentItem);
                }


                Message msg = new Message();
                msg.obj = comList;
                uiHandler.sendMessage(msg);

                conn.disconnect();











            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

    }

    public void postComment(ExecutorService srv,Handler uiHandler, String name, String comment,int news_id)  {


        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");

                JSONObject outputData = new JSONObject();

                outputData.put("name",name);
                outputData.put("text",comment);
                outputData.put("news_id",String.valueOf(news_id));


                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());

                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while((line= reader.readLine()) != null){
                    buffer.append(line);

                }
                conn.disconnect();
                JSONObject retVal = new JSONObject(buffer.toString());

                int retValint = retVal.getInt("serviceMessageCode");

                Message msg = new Message();
                msg.obj = retValint;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });




    }


}