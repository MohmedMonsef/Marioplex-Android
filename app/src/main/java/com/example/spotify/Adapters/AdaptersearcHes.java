package com.example.spotify.Adapters;

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

import com.example.spotify.Fragments.NEW_RELEASE_FRAHMENT.newReleaseFragment;
import com.example.spotify.R;
import com.example.spotify.SpotifyClasses.SearchClasses.search;
import com.example.spotify.SpotifyClasses.SearchClasses.aclass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AdaptersearcHes extends RecyclerView.Adapter<AdaptersearcHes.MyviewHolder> {

    private Context context;
    private ArrayList<aclass> list1;
    public AdaptersearcHes(Context context, search LIST) {
        this.context = context;
        list1=new ArrayList<aclass>();
        for (int i=0;i<LIST.getArtists().getItems().size();i++)
        {

            list1.add(new aclass(LIST.getArtists().getItems().get(i).getType(),
                    LIST.getArtists().getItems().get(i).getName(),
                    LIST.getArtists().getItems().get(i).getImages().get(0).getUrl()));

        }

        for (int i=0;i<LIST.getTracks().getItems().size();i++)
        {

            list1.add(new aclass(LIST.getTracks().getItems().get(i).getType(),
                    LIST.getTracks().getItems().get(i).getName(),
                    LIST.getTracks().getItems().get(i).getAlbum().getImages().get(0).getUrl()));

        }

    }

    @NonNull
    @Override
    public AdaptersearcHes.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchitemartist, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptersearcHes.MyviewHolder holder, int position) {
        holder.ArtistName.setText(list1.get(position).getName());
        holder.ArtistType.setText(list1.get(position).getType());
        Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();
        Picasso.get().load(list1.get(position).getImage()).into(holder.ArtistImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: on item view");

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new newReleaseFragment();
                activity.getFragmentManager().beginTransaction().replace(R.id.frame_fragment, myFragment).addToBackStack(null).commit();

            }
        });


    }


    @Override
    public int getItemCount() {
        if (list1!= null) {
            return list1.size();
        }
        return 1;

    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView ArtistName, ArtistType;
        ImageView ArtistImage;

        public MyviewHolder(View itemView) {
            super(itemView);
            ArtistName = (TextView) itemView.findViewById(R.id.nameOfSearch);
            ArtistType = (TextView) itemView.findViewById(R.id.typeOfSearch);
            ArtistImage = (ImageView)itemView.findViewById(R.id.imgSearchitem);

        }
    }
}