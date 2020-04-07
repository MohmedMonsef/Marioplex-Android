package com.example.spotify.BackClasses.BackAdapter;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.BackClasses.Backclasses.backpopularalbum.Album;
import com.example.spotify.Fragments.NEW_RELEASE_FRAHMENT.newReleaseFragment;
import com.example.spotify.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

public class adapterPopularAlbum extends RecyclerView.Adapter<adapterPopularAlbum.MyviewHolder> {
    private Context context;
    private List<Album> PopularAlbumList;

    public adapterPopularAlbum(Context context, List<Album> PopularAlbumList) {
        this.context = context;
        this.PopularAlbumList = PopularAlbumList;
    }

    public void setMovieList(List<Album> movieList) {
        this.PopularAlbumList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public adapterPopularAlbum.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler,parent,false);
        return new MyviewHolder(view);
    }


    @Override
    public void onBindViewHolder(adapterPopularAlbum.MyviewHolder holder, int position) {

        Album item=PopularAlbumList.get(position);
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

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new newReleaseFragment();
                activity.getFragmentManager().beginTransaction().replace(R.id.frame_fragment, myFragment).addToBackStack(null).commit();

            }
        });



    }

    @Override
    public int getItemCount() {
        if(PopularAlbumList != null){
            return PopularAlbumList.size();
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
