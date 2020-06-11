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

import com.example.spotify.Fragments.ALBUM_FRAGMENT.album;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.pojo.ImageInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterSavedAlbum extends RecyclerView.Adapter<adapterSavedAlbum.MyviewHolder> {
    private Context context;
    private List<com.example.spotify.BackClasses.Backclasses.SavedAlbums.SavedAlbum> SavedAlbum;

    /**
     * @param context
     * @param SavedAlbum
     * set the adapterSavedAlbum with list
     */
    public adapterSavedAlbum(Context context, List<com.example.spotify.BackClasses.Backclasses.SavedAlbums.SavedAlbum> SavedAlbum) {
        this.context = context;
        this.SavedAlbum = SavedAlbum;
    }

    public void setMovieList(List<com.example.spotify.BackClasses.Backclasses.SavedAlbums.SavedAlbum> SavedAlbum)
    {
        this.SavedAlbum = SavedAlbum;
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
    public adapterSavedAlbum.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.album_library_item,parent,false);
        return new MyviewHolder(view);
    }

    /**
     *
     * @param holder -->that has the view item (layout)
     * @param position -->index of the item in the list
     * set on item of recyclerview with its data
     */
    @Override
    public void onBindViewHolder(adapterSavedAlbum.MyviewHolder holder, int position)
    {
        final com.example.spotify.BackClasses.Backclasses.SavedAlbums.SavedAlbum item=SavedAlbum.get(position);
        holder.ImageName.setText(item.getName());
        holder.artistName.setText(item.getArtistName());
        /// check if the image not null
        if(item.getImages()!= null & item.getImages().size()!=0)
        {
            // load the image
            Picasso.get().load(item.getImages().get(0).toString()).into(holder.image);
        }

        List<ImageInfo> images= item.getImages();
        String imageID ="12d";
        if(images != null && images.size()!=0) {
            imageID = images.get(0).getID();
        }
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=album";
        Picasso.get().load(Imageurl).into(holder.image);

        /**
         * a click listener handel the item view
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Bundle bundle = new Bundle();
                bundle.putString("albumID" , item.getId());
                bundle.putInt("backID",1);
                bundle.putString("artistName" , item.getArtistName());
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
     *
     * @return --> SavedAlbum size
     */
    @Override
    public int getItemCount() {
        if(SavedAlbum != null){
            return SavedAlbum.size();
        }
        return 0;

    }

    /**
     * a class that handel the parsing of the recyclerview data
     */
    public class MyviewHolder extends RecyclerView.ViewHolder
    {
        TextView ImageName,artistName;
        ImageView image;

        public MyviewHolder(View itemView)
        {
            super(itemView);
            ImageName = (TextView)itemView.findViewById(R.id.saved_album_name);
            image = (ImageView)itemView.findViewById(R.id.album_saved_image);
            artistName = (TextView)itemView.findViewById(R.id.library_artist_name);

        }
    }
}

