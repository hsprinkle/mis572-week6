package com.example.rmu.week6;

import com.example.rmu.week6.model.Fruit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends ActionBarActivity {

    private static final String KEY = "key";

    private TextView textview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview1 = (TextView) findViewById(R.id.textView1);

        if(savedInstanceState != null && savedInstanceState.containsKey(KEY)){

            String json = savedInstanceState.getString(KEY);
            textview1.setText(json);

            //Advises user that data has already been downloaded
            Toast.makeText(this, "This JSON was cached", Toast.LENGTH_LONG).show();

        }

            else {

                //Warn users that you are going to perform a long running operation
                Toast.makeText(this, "Performing download, screen will update shortly", Toast.LENGTH_LONG).show();

                new DownloadWebpageTask().execute("https://data.ct.gov/resource/hma6-9xbg.json?category=Fruit&item=Peaches");
            }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY, textview1.getText().toString());
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(urls[0]);
                HttpResponse response = client.execute(request);

                StringBuilder content = new StringBuilder();
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while (null != (line = rd.readLine())) {
                    content.append(line);
                }
                return content.toString();

            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //simply put the long request into the textview for now.
            textview1.setText(result);

            Gson gson = new Gson();

            //TODO - instead of using the raw JSON, we want to deserialize
            Type lectureCollectionType = new TypeToken<Collection<Fruit>>(){}.getType();

            Collection<Fruit> fruitCollection = gson.fromJson(result, lectureCollectionType);

            String zip = fruitCollection.iterator().next().getZipcode();

            textview1.setText("First ZIP in Fruit Collection:\t" + zip
                    + "\nNumber of Objects in Fruit Collection:\t" + fruitCollection.size());
        }
    }
}
