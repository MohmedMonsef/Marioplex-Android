package com.example.spotify.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.BackClasses.Backclasses.albumTrack.AlbumTracks;
import com.example.spotify.BackClasses.Backclasses.albumTrack.Track;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.media.TrackInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

public class adapterAlbumTracks extends RecyclerView.Adapter<adapterAlbumTracks.MyviewHolder> {
    private Context context;
    private List<Track> Track;
    private String artistName;
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();

    public adapterAlbumTracks(Context context, AlbumTracks AlbumTracks,String ArtistName)
    {
        this.context = context;
        this.Track = AlbumTracks.getTracks();
        this.artistName=ArtistName;
    }

    public void setMovieList(AlbumTracks AlbumTracks)
    {
        this.Track = AlbumTracks.getTracks();
        notifyDataSetChanged();
    }

    /**
     * @param parent --> the view that has the recyclerview of this class
     * @param viewType-->
     * @return new object of the view holder
     */

    @Override
    public adapterAlbumTracks.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.album_preview_listview_item,parent,false);
        return new MyviewHolder(view);
    }

    /**
     *
     * @param holder -->that has the view item (layout)
     * @param position -->index of the item in the list
     * set on item of recyclerview with its data
     */
    @Override
    public void onBindViewHolder(final adapterAlbumTracks.MyviewHolder holder, final int position)
    {


        final Track item=Track.get(position);
        holder.preview_song_artist_album.setText(artistName);
        holder.preview_song_name_album.setText(item.getName());
        Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();
        /// check if the image not null
        if(item.getImages().size()!=0)
        {
            // load the image
            Picasso.get().load(item.getImages().get(0).toString()).into(holder.preview_song_image_album);
        }
        /**
         * a click listener handel the item view
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             * go to the next fragment from the selected item view
             */
            @Override
            public void onClick(View v)
            {

               //ToDO on click the tracks will play
            }
        });
holder.preview_like_album.setOnClickListener(new View.OnClickListener() {
    /**
     *
     * @param v
     * go to the next fragment from the selected item view
     */
    @Override
    public void onClick(final View v)
    {

        //ToDO on click the tracks will like track
    }
});
holder.preview_settings_image_album.setOnClickListener(new View.OnClickListener() {
    /**
     *
     * @param v
     * go to the next fragment from the selected item view
     */
    @Override
    public void onClick(View v)
    {

        //ToDO on click the tracks setting track
    }
});
    }

    /**
     *
     * @return --> Categorylist size
     */
    @Override
    public int getItemCount()
    {
        if(Track != null)
        {
            return Track.size();
        }
        return 0;

    }

    /**
     * a class that handel the parsing of the recyclerview data
     */
    public class MyviewHolder extends RecyclerView.ViewHolder
    {
        TextView preview_song_name_album,preview_song_artist_album;
        ImageView preview_song_image_album,preview_settings_image_album,preview_like_album;

        public MyviewHolder(View itemView) {
            super(itemView);
            preview_song_name_album = (TextView)itemView.findViewById(R.id.preview_song_name_album);
            preview_song_artist_album = (TextView)itemView.findViewById(R.id.preview_song_artist_album);

            preview_song_image_album = (ImageView)itemView.findViewById(R.id.preview_song_image_album);
            preview_settings_image_album = (ImageView)itemView.findViewById(R.id.preview_settings_image_album);
            preview_like_album = (ImageView)itemView.findViewById(R.id.preview_like_album);

        }
    }

}
