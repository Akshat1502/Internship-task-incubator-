package com.example.api_data;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.api_data.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText City_name;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    Button button;
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        City_name= findViewById(R.id.City_name);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        textView5=findViewById(R.id.textView5);
        button=findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button){
            try {
                queryData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void queryData() throws IOException {
        URL url= NetworkUtils.buildUrl();
        new DataTask().execute(url);


    }
    @SuppressLint("StaticFieldLeak")
    public class DataTask extends AsyncTask<URL,Void,String>{


        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String data= null;
            try {
                data = NetworkUtils.getDatafromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            setcityData(s);
        }
        @SuppressLint("SetTextI18n")
        public void setcityData(String data){
            JSONObject myObject;
            try {
                myObject = new JSONObject(data);
                JSONArray citya = myObject.getJSONArray("data");
                for (int i=0; i<citya.length();i++) {
                    JSONObject cityo = citya.getJSONObject(i);
                    String cityn = cityo.get("State").toString();
                    Log.d("adApi",cityn);
                    Log.d("TextCityName","cityName");
                    if (cityn.equals(cityName)) {
                        String cityp = cityo.get("Population").toString();
                        textView2.setText(cityp);
                        break;
                    }
                    else
                    {
                        textView2.setText(cityName + "Not Found");

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }





}
