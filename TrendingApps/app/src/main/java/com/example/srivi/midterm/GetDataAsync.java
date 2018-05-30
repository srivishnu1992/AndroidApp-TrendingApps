package com.example.srivi.midterm;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by srivi on 12-03-2018.
 */

public class GetDataAsync extends AsyncTask<String, Void, ArrayList<App>> {

    ArrayList<App> apps = new ArrayList<>(  );
    HttpURLConnection httpURLConnection = null;
    StringBuilder json;
    BufferedReader bufferedReader;
    MainActivity mainActivity;

    public GetDataAsync(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    protected ArrayList<App> doInBackground(String... strings) {
        try {
            json = new StringBuilder(  );
            URL url = new URL( strings[0] );
            httpURLConnection = (HttpURLConnection) url.openConnection();
            String line = "";
            bufferedReader = new BufferedReader( new InputStreamReader( httpURLConnection.getInputStream() ));
            while ((line = bufferedReader.readLine())!=null) {
                json.append( line );
            }
            Log.d("Movie", json.toString());
            JSONObject root = new JSONObject( json.toString() );
            JSONObject feed = root.getJSONObject( "feed" );
            JSONArray results = feed.getJSONArray( "results" );
            for(int i=0;i<results.length();i++) {
                JSONObject jsonApp = results.getJSONObject(i);
                App app = new App();
                app.name = jsonApp.getString( "name" );
                app.artistName = jsonApp.getString( "artistName" );
                app.copyright = jsonApp.getString( "copyright" );
                app.releaseDate = jsonApp.getString( "releaseDate" );
                app.artworkUrl100 = jsonApp.getString( "artworkUrl100" );
                JSONArray genreArray = jsonApp.getJSONArray( "genres" );
                for(int j=0;j<genreArray.length();j++) {
                    app.genres.add(genreArray.getJSONObject( j ).getString( "name" ));
                }
                Collections.sort( app.genres );
                Log.d("App", app.toString());
                apps.add( app );
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return apps;
    }

    @Override
    protected void onPostExecute(ArrayList<App> apps) {
        mainActivity.handleData(apps);
    }

    public static interface IData {
        public void handleData(ArrayList<App> apps);
    }


}
