package com.example.srivi.midterm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.example.srivi.midterm.R.layout.app_item;

/*
Sri Vishnu Yandrapati
 */

public class MainActivity extends AppCompatActivity {

    TextView tvGenre;
    Button btnFilter;
    String link = "https://rss.itunes.apple.com/api/v1/us/ios-apps/top-grossing/all/50/explicit.json";
    ProgressBar progressBar;
    ListView listView;
    static String APP_KEY = "APP";
    HashMap<String,Integer> hm = new HashMap<>(  );
    CharSequence[] items;
    ArrayList<String> allgenres = new ArrayList<>(  );
    String filterGenre="";
    ArrayList<App> filterApps = new ArrayList<>(  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        setTitle( "Apps" );
        tvGenre = findViewById( R.id.tvgenre );
        btnFilter = findViewById( R.id.btnFilter );
        progressBar = findViewById( R.id.progressBar );
        listView = findViewById( R.id.listView );

        if(isConnected()) {
            new GetDataAsync(MainActivity.this).execute( link );
        } else {
            Toast.makeText( this, "No Internet", Toast.LENGTH_SHORT ).show();
        }

    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected() && (networkInfo.getType()==ConnectivityManager.TYPE_WIFI || networkInfo.getType()==ConnectivityManager.TYPE_MOBILE))
            return true;
        return false;

    }
    public void handleData(final ArrayList<App> apps) {
        int n = apps.size();
        progressBar.setVisibility( View.INVISIBLE );
        if(n == 0) return;

        for(int i=0;i<n;i++) {
            ArrayList<String> genres = apps.get( i ).genres;
            for(int j=0;j<genres.size();j++) {
                if(!hm.containsKey( genres.get( j ) ))
                    hm.put(genres.get( j ), 1);
            }
        }
        for (Map.Entry<String,Integer> entry : hm.entrySet())
            allgenres.add( entry.getKey());
            allgenres.add( "All" );
        Collections.sort( allgenres );
        items = new CharSequence[allgenres.size()];
        for(int ite=0;ite<allgenres.size();ite++)
            items[ite] = allgenres.get( ite );

        AppAdapter appAdapter = new AppAdapter(this, app_item, apps);
        listView.setAdapter( appAdapter );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent( MainActivity.this, Details.class );
                intent.putExtra( APP_KEY, apps.get( i ) );
                startActivity( intent );
            }
        } );

        AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );
        builder.setTitle( "Choose Category" );
        builder.setItems( items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                filterGenre = (String) items[i];
                tvGenre.setText( items[i] );
                if(filterGenre == "All") {
                    AppAdapter appAdapter = new AppAdapter(MainActivity.this, app_item, apps);
                    listView.setAdapter( appAdapter );
                    listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent( MainActivity.this, Details.class );
                            intent.putExtra( APP_KEY, apps.get( i ) );
                            startActivity( intent );
                        }
                    } );
                }
                else {
                    filterApps = new ArrayList<>(  );
                    for(int k=0;k<apps.size();k++) {
                        App app = apps.get( k );
                        if(contains(app, filterGenre)) {
                            filterApps.add( app );
                        }
                    }
                    AppAdapter appAdapter1 = new AppAdapter( MainActivity.this, app_item, filterApps );
                    listView.setAdapter( appAdapter1 );
                    listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent( MainActivity.this, Details.class );
                            intent.putExtra( APP_KEY, filterApps.get( i ) );
                            startActivity( intent );
                        }
                    } );
                }


            }
        } );
        final AlertDialog alertDialog = builder.create();
        btnFilter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        } );

    }
    public boolean contains(App app, String filterGenre) {
        ArrayList<String> temp = app.genres;
        for(int i=0;i<temp.size();i++) {
            if(filterGenre.equals( temp.get(i) ))
                return true;
        }
        return false;
    }
}
