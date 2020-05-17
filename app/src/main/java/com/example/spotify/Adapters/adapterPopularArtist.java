package com.example.spotify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.BackClasses.Backclasses.backpopularartist.Artist;
import com.example.spotify.Fragments.NEW_RELEASE_FRAHMENT.newReleaseFragment;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterPopularArtist extends RecyclerView.Adapter<adapterPopularArtist.MyviewHolder>
{
    private Context context;
    private List<Artist> PopularArtistList;
    /**
     * @param context
     * @param PopularArtistList
     * set the adapterPopularArtist with list
     */
    public adapterPopularArtist(Context context, List<Artist> PopularArtistList)
    {
        this.context = context;
        this.PopularArtistList = PopularArtistList;
    }

    public void setMovieList(List<Artist> movieList) {
        this.PopularArtistList = movieList;
        notifyDataSetChanged();
    }
    /**
     * @param parent --> the view that has the recyclerview of this class
     * @param viewType-->
     * @return new object of the view holder
     */
    @Override
    public adapterPopularArtist.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType)
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
    public void onBindViewHolder(adapterPopularArtist.MyviewHolder holder, int position)
    {

        Artist item=PopularArtistList.get(position);
        holder.ImageName.setText(item.getName());
      //  Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();
        /// check if the image not null
        String imageID = "12D";
        if(item.getImages()!= null & item.getImages().size()!=0)
        {
           imageID = item.getImages().get(0).getID();
        }
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=artist";
        Picasso.get().load(Imageurl).into(holder.image);
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
     * @return --> PopularArtistList size
     */
    @Override
    public int getItemCount()
    {
        if(PopularArtistList != null)
        {
            return PopularArtistList.size();
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
