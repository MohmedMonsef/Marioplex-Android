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

import com.example.spotify.BackClasses.Backclasses.backnewrelease.Album;
import com.example.spotify.Fragments.ALBUM_FRAGMENT.album;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterNewreleases extends RecyclerView.Adapter<adapterNewreleases.MyviewHolder> {
    private Context context;
    private List<Album> NewReleaseList;

    /**
     * @param context
     * @param NewReleaseList set the adapterPopularArtist with list
     */
    public adapterNewreleases(Context context, List<Album> NewReleaseList) {
        this.context = context;
        this.NewReleaseList = NewReleaseList;
    }

    public void setMovieList(List<Album> movieList) {
        this.NewReleaseList = movieList;
        notifyDataSetChanged();
    }

    /**
     * @param parent      --> the view that has the recyclerview of this class
     * @param viewType-->
     * @return new object of the view holder
     */
    @Override
    public adapterNewreleases.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false);
        return new MyviewHolder(view);
    }

    /**
     * @param holder   -->that has the view item (layout)
     * @param position -->index of the item in the list
     *                 set on item of recyclerview with its data
     */
    @Override
    public void onBindViewHolder(final adapterNewreleases.MyviewHolder holder, int position) {

        final Album item = NewReleaseList.get(position);
        holder.ImageName.setText(item.getName());
        /// check if the image not null
        String imageID = "12D";
        if (item.getImages() != null & item.getImages().size() != 0) {
            imageID = item.getImages().get(0).getID();
        }
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=album";
        Picasso.get().load(Imageurl).into(holder.image);
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
            public void onClick(View v) {


                        Bundle bundle = new Bundle();
                        bundle.putString("albumID" , item.getId());
                        //bundle.putString("artistName" , item.getArtist().getName());
                        androidx.fragment.app.Fragment f = new album();
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
     * @return --> NewReleaseList size
     */
    @Override
    public int getItemCount() {
        if (NewReleaseList != null) {
            return NewReleaseList.size();
        }
        return 0;
    }

    /**
     * a class that handel the parsing of the recyclerview data
     */
    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView ImageName;
        ImageView image;

        public MyviewHolder(View itemView) {
            super(itemView);
            ImageName = (TextView) itemView.findViewById(R.id.NameOfImage);
            image = (ImageView) itemView.findViewById(R.id.entity_image);
        }
    }
}