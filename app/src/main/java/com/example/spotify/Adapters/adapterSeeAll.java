package com.example.spotify.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.BackClasses.Backclasses.aclass;
import com.example.spotify.BackClasses.Backclasses.backsearch.Search;
import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistFragment;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class adapterSeeAll extends RecyclerView.Adapter<adapterSeeAll.MyviewHolder> {

    private Context context;
    private ArrayList<aclass> list1;
    /**
     * @param context
     * @param LIST
     * set the adapterSearch with list
     */
    public adapterSeeAll(Context context, Search LIST)
    {
        this.context = context;
        list1=new ArrayList<aclass>();
        if(LIST.getArtist()!=null)
        {
            //fill the artist list
            for (int i = 0; i < LIST.getArtist().size(); i++)
            {
                String image = "";
                if (LIST.getArtist().get(i).getImages() != null && LIST.getArtist().get(i).getImages().size() != 0)
                {
                    image = LIST.getArtist().get(i).getImages().get(0).getID();
                }
                list1.add(new aclass(LIST.getArtist().get(i).getType(),
                        LIST.getArtist().get(i).getName(),
                        image,
                        LIST.getArtist().get(i).getId()));
            }
        }
        if(LIST.getTrack()!=null)
        {
            //fill the track list
            for (int i = 0; i < LIST.getTrack().size(); i++)
            {
                String image = "";
                if (LIST.getTrack().get(i).getImages() != null && LIST.getTrack().get(i).getImages().size() != 0)
                {
                    image = LIST.getTrack().get(i).getImages().get(0).getID();
                }
                list1.add(new aclass(LIST.getTrack().get(i).getType(),
                        LIST.getTrack().get(i).getName(),
                        image,
                        LIST.getTrack().get(i).getId()));
            }
        }
        if(LIST.getAlbum()!=null)
        {
            //fill the album list
            for (int i = 0; i < LIST.getAlbum().size(); i++)
            {
                String image = "";
                if (LIST.getAlbum().get(i).getImages() != null && LIST.getAlbum().get(i).getImages().size() != 0)
                {
                    image = LIST.getAlbum().get(i).getImages().get(0).getID();
                }
                list1.add(new aclass(LIST.getAlbum().get(i).getType(),
                        LIST.getAlbum().get(i).getName(),
                        image,
                        LIST.getAlbum().get(i).getId()));
            }
        }

    }

    /**
     * @param parent --> the view that has the recyclerview of this class
     * @param viewType-->
     * @return new object of the view holder
     */
    @NonNull
    @Override
    public adapterSeeAll.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.see_all_item, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterSeeAll.MyviewHolder holder, final int position)
    {
        holder.itemName.setText(list1.get(position).getName());

        String imageID = "12d";
        if(list1.get(position).getImage() !="")
        {
            imageID = list1.get(position).getImage();
        }
        String Imageurl ;
        if(list1.get(position).getType().equals("playlist")) {
            Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=playlist";
        }
        else if(list1.get(position).getType().equals("Track")){
            Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=track";
        }
        else if(list1.get(position).getType().equals("album")){
            Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=album";
        }
        else{
            Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=artist";
        }
        Picasso.get().load(Imageurl).into(holder.itemImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list1.get(position).getType().equals("playlist")){
                    Bundle bundle = new Bundle();
                    bundle.putString("playlistID" , list1.get(position).getid());
                    //bundle.putString("ownerName" , list1.getOwner().getName());

                    androidx.fragment.app.Fragment f = new PlaylistFragment();
                    f.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_fragment, f)
                            .addToBackStack(null).commit();
                }


            }
        });


    }

    /**
     *
     * @return --> search list1 size
     */
    @Override
    public int getItemCount()
    {
        if (list1!= null)
        {
            return list1.size();
        }
        return 1;

    }
    /**
     * a class that handel the parsing of the recyclerview data
     */
    public class MyviewHolder extends RecyclerView.ViewHolder
    {
        TextView itemName;
        ImageView itemImage;

        public MyviewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.nameOfSeeAll);
            itemImage = (ImageView)itemView.findViewById(R.id.imgSeeAllitem);

        }
    }
}