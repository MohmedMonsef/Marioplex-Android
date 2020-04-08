package com.example.spotify.Adapters;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    /**
     * @param context
     * @param PopularAlbumList
     * set the adapterPopularAlbum with list
     */
    public adapterPopularAlbum(Context context, List<Album> PopularAlbumList) {
        this.context = context;
        this.PopularAlbumList = PopularAlbumList;
    }

    public void setMovieList(List<Album> PopularAlbumList)
    {
        this.PopularAlbumList = PopularAlbumList;
        notifyDataSetChanged();
    }
    /**
     *
     * @param parent --> the view that has the recyclerview of this class
     * @param viewType-->
     *
     *
     * @return new object of the view holder
     */
    @Override
    public adapterPopularAlbum.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType)
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
    public void onBindViewHolder(adapterPopularAlbum.MyviewHolder holder, int position)
    {
        Album item=PopularAlbumList.get(position);
        holder.ImageName.setText(item.getName());
        Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();
        /// check if the image not null
        if(item.getImages()!= null & item.getImages().size()!=0)
        {
            // load the image
            Picasso.get().load(item.getImages().get(0).toString()).into(holder.image);
        }
        /**
         * a click listener handel the item view
         */
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            /**
             *
             * @param v
             * go to the next fragment from the selected item view
             */
            @Override
            public void onClick(View v)
            {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new newReleaseFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, myFragment).addToBackStack(null).commit();

            }
        });



    }

    /**
     *
     * @return --> PopularAlbumList size
     */
    @Override
    public int getItemCount() {
        if(PopularAlbumList != null){
            return PopularAlbumList.size();
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
