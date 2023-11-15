package com.example.levanbinhdinh_asyntask_json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<contact> arrayList;
    adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<contact>();
        new Xuly().execute("https://api.npoint.io/0f172fd90f552a081af8");
    }
    private class Xuly extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();

            try {
                URL url = new URL(strings[0]);

                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }

                bufferedReader.close();

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String name = "";
            String desc = "";
            String img = "";
            try {
                JSONObject object = new JSONObject(s);

                JSONArray arrayfood = object.getJSONArray("foods");

                for(int i = 0; i < arrayfood.length(); i++){
                    JSONObject foods = arrayfood.getJSONObject(i);
                    name = foods.getString("name");
                    desc = foods.getString("description");
                    img = foods.getString("img");
                    contact food = new contact(name,desc,img);
                    arrayList.add(food);
                }
                adapter = new adapter(MainActivity.this,arrayList,R.layout.news_line);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }


}