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

import com.example.spotify.BackClasses.Backclasses.backcategoryplaylist.CategoryPlaylist;
import com.example.spotify.BackClasses.Backclasses.backcategoryplaylist.Playlist;
import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistFragment;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterCategoryPlaylists extends RecyclerView.Adapter<adapterCategoryPlaylists.MyviewHolder> {
    private Context context;
    private List<Playlist> Categoryplaylist;
    /**
     * @param context
     * @param Categoryplaylist
     * set the adapterCategoryPlaylists with list
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
        View view = LayoutInflater.from(context).inflate(R.layout.playlistcategory,parent,false);
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

        holder.playlisName1.setText(Categoryplaylist.get(position).getName());
        holder.OwnerName1.setText(Categoryplaylist.get(position).getOwnerName());
        /// check if the image not null
        String imageID = "12D";
        if(Categoryplaylist.get(position).getImages()!= null & Categoryplaylist.get(position).getImages().size()!=0)
        {
            imageID = Categoryplaylist.get(position).getImages().get(0).getID();
        }
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=playlist";
        Picasso.get().load(Imageurl).into(holder.playlistimage1);
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
                bundle.putString("from" , "searchfrag1");

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
     * @return --> Categoryplaylist size
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


        TextView playlisName1;
        TextView OwnerName1;
        ImageView playlistimage1;

        public MyviewHolder(View itemView)
        {
            super(itemView);
            playlisName1 = (TextView)itemView.findViewById(R.id.NameOfPlaylist1);
            OwnerName1 = (TextView)itemView.findViewById(R.id.NameOfOwner1);
            playlistimage1 = (ImageView)itemView.findViewById(R.id.playlist_image1);
        }
    }
        //////////////******************************///
       /* TextView ImageName;
        ImageView image;

        public MyviewHolder(View itemView)
        {
            super(itemView);
            ImageName = (TextView)itemView.findViewById(R.id.NameOfImage);
            image = (ImageView)itemView.findViewById(R.id.entity_image);
        }
    }*/
}