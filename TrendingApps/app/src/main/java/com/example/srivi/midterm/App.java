package com.example.srivi.midterm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by srivi on 12-03-2018.
 */

public class App implements Serializable{
    String name;
    String artistName;
    ArrayList<String> genres = new ArrayList<>(  );
    String releaseDate;
    String artworkUrl100;
    String copyright;

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", artistName='" + artistName + '\'' +
                ", genres=" + genres +
                ", releaseDate='" + releaseDate + '\'' +
                ", artworkUrl100='" + artworkUrl100 + '\'' +
                ", copyright='" + copyright + '\'' +
                '}';
    }

    public String totoString() {
        String genre = "";
        for(int i=0;i<genres.size();i++) {
            genre+=genres.get( i );
            if(i!=genres.size()-1)
                genre+=", ";
        }
        return genre;
    }

}
