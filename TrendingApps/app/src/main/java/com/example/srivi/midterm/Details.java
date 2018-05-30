package com.example.srivi.midterm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    TextView name;
    ImageView imageView2;
    TextView artistName;
    TextView releaseDate;
    TextView genre;
    TextView copyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_details );
        setTitle( "App Details" );
        if(getIntent()!=null && getIntent().getExtras()!=null) {
            App app = (App) getIntent().getExtras().getSerializable( MainActivity.APP_KEY );
            name = findViewById( R.id.tvname );
            imageView2 = findViewById( R.id.imageView );
            artistName = findViewById( R.id.tvartistname );
            releaseDate = findViewById( R.id.tvreleasedate );
            genre = findViewById( R.id.tvgenre );
            copyright = findViewById( R.id.tvcopyright );
            name.setText( app.name );
            artistName.setText( app.artistName );
            releaseDate.setText( app.releaseDate );
            genre.setText( app.totoString() );
            copyright.setText( app.copyright );
            String link = app.artworkUrl100;
            ImageView imageView = findViewById( R.id.imageView2 );
            Picasso.get().load( link ).into(imageView);
        }
    }
}
