package com.example.fragspotify.BackClasses.BackAdapter;

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

import com.example.fragspotify.BackClasses.Backclasses.backsearch.Search;
import com.example.fragspotify.Fragments.NEW_RELEASE_FRAHMENT.newReleaseFragment;
import com.example.fragspotify.R;
import com.example.fragspotify.SpotifyClasses.SearchClasses.aclass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class adapterSearch extends RecyclerView.Adapter<adapterSearch.MyviewHolder> {
    //private  int TYPE_ARTIST = 2;
//private  int TYPE_TRACKS = 1;
    private Context context;
    //private search LIST;
//    private List<Item> ArtistList;
    private ArrayList<aclass> list1;
//private List<Item> ArtistList;

    public adapterSearch(Context context, Search LIST) {
        this.context = context;
        // this.ArtistList = ArtistList;
        list1=new ArrayList<aclass>();
        for (int i=0;i<LIST.getArtist().size();i++)
        {

            list1.add(new aclass(LIST.getArtist().get(i).getType(),
                    LIST.getArtist().get(i).getName(),
                    LIST.getArtist().get(i).getImages().get(0).toString()));

        }

        for (int i=0;i<LIST.getTrack().size();i++)
        {

            list1.add(new aclass(LIST.getTrack().get(i).getType(),
                    LIST.getTrack().get(i).getName(),
                    LIST.getTrack().get(i).getImages().get(0).toString()));

        }

    }

    @NonNull
    @Override
    public adapterSearch.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchitemartist, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterSearch.MyviewHolder holder, int position) {
        holder.ArtistName.setText(list1.get(position).getName());
        holder.ArtistType.setText(list1.get(position).getType());
        Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();
        Picasso.get().load(list1.get(position).getImage()).into(holder.ArtistImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: pppppppppppppppppppppp");

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