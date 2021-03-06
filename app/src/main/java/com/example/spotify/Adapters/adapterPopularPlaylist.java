package com.example.spotify.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.BackClasses.Backclasses.backpopularplaylist.Playlist;
import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistFragment;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterPopularPlaylist extends RecyclerView.Adapter<adapterPopularPlaylist.MyviewHolder>
{
    private Context context;
    private List<Playlist> PopularPlaylistList;
    /**
     * @param context
     * @param PopularPlaylistList
     * set the adapterPopularPlaylist with list
     */
    public adapterPopularPlaylist(Context context, List<Playlist> PopularPlaylistList)
    {
        this.context = context;
        this.PopularPlaylistList = PopularPlaylistList;
    }

    public void setMovieList(List<Playlist> movieList)
    {
        this.PopularPlaylistList = movieList;
        notifyDataSetChanged();
    }
    /**
     * @param parent --> the view that has the recyclerview of this class
     * @param viewType-->
     * @return new object of the view holder
     */
    @Override
    public adapterPopularPlaylist.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType)
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
    public void onBindViewHolder(adapterPopularPlaylist.MyviewHolder holder, int position) {

        final Playlist item=PopularPlaylistList.get(position);
        holder.ImageName.setText(item.getName());
        /// check if the image not null
        String imageID = "12D";
        if(item.getImages()!= null & item.getImages().size()!=0)
        {
            imageID = item.getImages().get(0).getID();
        }
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=playlist";
        Picasso.get().load(Imageurl).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

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

    /**
     *
     * @return --> PopularPlaylistList size
     */
    @Override
    public int getItemCount()
    {
        if(PopularPlaylistList != null){
            return PopularPlaylistList.size();
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
