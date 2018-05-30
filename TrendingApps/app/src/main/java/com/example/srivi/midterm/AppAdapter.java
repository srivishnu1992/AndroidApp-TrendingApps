package com.example.srivi.midterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by srivi on 12-03-2018.
 */

public class AppAdapter extends ArrayAdapter<App> {
    public AppAdapter(@NonNull Context context, int resource, @NonNull List<App> objects) {
        super( context, resource, objects );
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        App app = getItem( position );
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from( getContext() ).inflate( R.layout.app_item, parent, false );
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById( R.id.tvname );
            viewHolder.imageView = convertView.findViewById( R.id.imageView );
            viewHolder.artistName = convertView.findViewById( R.id.tvartistname );
            viewHolder.releaseDate = convertView.findViewById( R.id.tvreleasedate );
            viewHolder.genre = convertView.findViewById( R.id.tvgenre );
            convertView.setTag( viewHolder );
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText( app.name );
        viewHolder.artistName.setText( app.artistName );
        viewHolder.releaseDate.setText( app.releaseDate );
        viewHolder.genre.setText( app.totoString() );
        String link = app.artworkUrl100;
        ImageView imageView = convertView.findViewById( R.id.imageView );
        Picasso.get().load( link ).into(imageView);
        return convertView;
    }

    public static class ViewHolder {
        TextView name;
        ImageView imageView;
        TextView artistName;
        TextView releaseDate;
        TextView genre;
    }

}
