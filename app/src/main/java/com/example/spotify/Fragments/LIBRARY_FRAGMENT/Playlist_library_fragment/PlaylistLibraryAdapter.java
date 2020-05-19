package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Playlist_library_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistFragment;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.pojo.BasicPlaylist;
import com.example.spotify.pojo.ImageInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistLibraryAdapter extends RecyclerView.Adapter<PlaylistLibraryAdapter.MyviewHolder>  {

    private Context context;
    private List<BasicPlaylist> playlists;

    public PlaylistLibraryAdapter(Context context, List<BasicPlaylist> p){
        this.context = context;
        playlists = p;

    }

    @NonNull
    @Override
    public PlaylistLibraryAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.playlist_list_view_layout, parent, false);
        return new PlaylistLibraryAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistLibraryAdapter.MyviewHolder holder, final int position) {
        if(!(playlists.get(position).getType().equals("created"))){
            holder.settings_library.setEnabled(false);
            holder.settings_library.setImageResource(R.drawable.more_options_notenabled);
        }
        else{
            holder.settings_library.setEnabled(true);
            holder.settings_library.setImageResource(R.drawable.more_options);
        }
        holder.user_playlist_name.setText(playlists.get(position).getName());
        if(!playlists.get(position).getOwner().isEmpty()){
            holder.playlist_user_name.setText("by " + playlists.get(position).getOwner());
        }
        else{
            holder.playlist_user_name.setText("by Spotify");
        }
        List<ImageInfo> images= playlists.get(position).getImages();
        String imageID ="12d";
        if(images != null && images.size()!=0) {
            imageID = images.get(0).getID();
        }
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=playlist";
        Picasso.get().load(Imageurl).into(holder.user_playlist_image);
        holder.playlist_list_view_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("playlistID" , playlists.get(position).getId());
                bundle.putString("ownerName" , playlists.get(position).getOwner());
                bundle.putString("from" , "Playlist_library");
                androidx.fragment.app.Fragment f = new PlaylistFragment();
                f.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_fragment, f)
                        .addToBackStack(null).commit();
            }
        });

        holder.settings_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ImageInfo> images= playlists.get(position).getImages();
                String imageID ="12d";
                if(images != null && images.size()!=0) {
                    imageID = images.get(0).getID();
                }

                Intent intent = new Intent(context, Playlist_Library_Settings.class);
                intent.putExtra("SourceID" , playlists.get(position).getId());
                intent.putExtra("playlistName" , playlists.get(position).getName());
                intent.putExtra("ImageID" , imageID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView user_playlist_name, playlist_user_name;
        ImageView user_playlist_image;
        LinearLayout playlist_list_view_layout;
        ImageView settings_library;

        public MyviewHolder(View itemView) {
            super(itemView);
            user_playlist_name = (TextView) itemView.findViewById(R.id.user_playlist_name);
            playlist_user_name = (TextView) itemView.findViewById(R.id.playlist_user_name);
            user_playlist_image = (ImageView)itemView.findViewById(R.id.user_playlist_image);
            playlist_list_view_layout = (LinearLayout)itemView.findViewById(R.id.playlist_list_view_layout);
            settings_library = itemView.findViewById(R.id.settings_library);

        }
    }
}
