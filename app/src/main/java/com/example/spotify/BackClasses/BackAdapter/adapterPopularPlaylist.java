package com.example.spotify.BackClasses.BackAdapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.BackClasses.Backclasses.backpopularplaylist.Playlist;
import com.example.spotify.Fragments.NEW_RELEASE_FRAHMENT.newReleaseFragment;
import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistFragment;
import com.example.spotify.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

public class adapterPopularPlaylist extends RecyclerView.Adapter<adapterPopularPlaylist.MyviewHolder> {
    private Context context;
    private List<Playlist> PopularPlaylistList;

    public adapterPopularPlaylist(Context context, List<Playlist> PopularPlaylistList) {
        this.context = context;
        this.PopularPlaylistList = PopularPlaylistList;
    }

    public void setMovieList(List<Playlist> movieList) {
        this.PopularPlaylistList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public adapterPopularPlaylist.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(adapterPopularPlaylist.MyviewHolder holder, int position) {

        final Playlist item=PopularPlaylistList.get(position);
        holder.ImageName.setText(item.getName());
        Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();
        if(item.getImages()!= null & item.getImages().size()!=0) {
            Picasso.get().load(item.getImages().get(0).toString()).into(holder.image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.i(TAG, "onClick: jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");

//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                Fragment myFragment = new newReleaseFragment();
//                activity.getFragmentManager().beginTransaction().replace(R.id.frame_fragment, myFragment).addToBackStack(null).commit();
                Bundle bundle = new Bundle();
                bundle.putString("playlistID" , item.getId());
                bundle.putString("ownerName" , item.getOwner().getName());

                androidx.fragment.app.Fragment f = new PlaylistFragment();
                f.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_fragment, f)
                        .addToBackStack(null).commit();

            }
        });



    }

    @Override
    public int getItemCount() {
        if(PopularPlaylistList != null){
            return PopularPlaylistList.size();
        }
        return 0;

    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView ImageName;
        ImageView image;

        public MyviewHolder(View itemView) {
            super(itemView);
            ImageName = (TextView)itemView.findViewById(R.id.NameOfImage);
            image = (ImageView)itemView.findViewById(R.id.entity_image);
        }
    }
}
