package com.example.spotify.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.BackClasses.Backclasses.backcategoryplaylist.CategoryPlaylist;
import com.example.spotify.BackClasses.Backclasses.backcategoryplaylist.Playlist;
import com.example.spotify.Fragments.NEW_RELEASE_FRAHMENT.newReleaseFragment;
import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistFragment;
import com.example.spotify.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterCategoryPlaylists extends RecyclerView.Adapter<adapterCategoryPlaylists.MyviewHolder> {
    private Context context;
    private List<Playlist> Categoryplaylist;
    /**
     * @param context
     * @param Categoryplaylist
     * set the adapterPopularArtist with list
     */
    public adapterCategoryPlaylists(Context context, CategoryPlaylist Categoryplaylist)
    {
        this.context = context;
        this.Categoryplaylist = Categoryplaylist.getPlaylists();
    }

    public void setMovieList(CategoryPlaylist movieList)
    {
        this.Categoryplaylist = movieList.getPlaylists();
        notifyDataSetChanged();
    }

    /**
     * @param parent --> the view that has the recyclerview of this class
     * @param viewType-->
     * @return new object of the view holder
     */
    @Override
    public adapterCategoryPlaylists.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler,parent,false);
        return new MyviewHolder(view);
    }

    /**
     *
     * @param holder -->that has the view item (layout)
     * @param position -->index of the item in the list
     * set on item of recyclerview with its data
     */
    @Override
    public void onBindViewHolder(adapterCategoryPlaylists.MyviewHolder holder, final int position)
    {

        holder.ImageName.setText(Categoryplaylist.get(position).getName());
        /// check if the image not null
        if(Categoryplaylist.get(position).getImages()!= null & Categoryplaylist.get(position).getImages().size()!=0)
        {
            // load the image
            Picasso.get().load(Categoryplaylist.get(position).getImages().get(0).toString()).into(holder.image);
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

                Bundle bundle = new Bundle();
                bundle.putString("playlistID" , Categoryplaylist.get(position).getId());
                //bundle.putString("ownerName" , list1.getOwner().getName());

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

    /**
     *
     * @return --> NewReleaseList size
     */
    @Override
    public int getItemCount()
    {
        if(Categoryplaylist != null)
        {
            return Categoryplaylist.size();
        }
        return 0;
    }

    /**
     * a class that handel the parsing of the recyclerview data
     */
    public class MyviewHolder extends RecyclerView.ViewHolder
    {
        TextView ImageName;
        ImageView image;

        public MyviewHolder(View itemView)
        {
            super(itemView);
            ImageName = (TextView)itemView.findViewById(R.id.NameOfImage);
            image = (ImageView)itemView.findViewById(R.id.entity_image);
        }
    }
}