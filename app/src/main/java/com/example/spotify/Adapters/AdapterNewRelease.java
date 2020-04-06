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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Fragments.NEW_RELEASE_FRAHMENT.newReleaseFragment;
import com.example.spotify.R;
import com.example.spotify.SpotifyClasses.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;


public class AdapterNewRelease extends RecyclerView.Adapter<AdapterNewRelease.MyviewHolder> {
    private  Context context;
    private List<Item> NewReleaseList;

    public AdapterNewRelease(Context context, List<Item> NewReleaseList) {
        this.context = context;
        this.NewReleaseList = NewReleaseList;
    }

    public void setMovieList(List<Item> newreleasesList) {
        this.NewReleaseList = newreleasesList;
        notifyDataSetChanged();
    }

    @Override
    public AdapterNewRelease.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterNewRelease.MyviewHolder holder, int position) {

        Item item=NewReleaseList.get(position);
        holder.ImageName.setText(item.getName());
        Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();
        Picasso.get().load(item.getImages().get(0).getUrl()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.i(TAG, "onClick: on item");

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new newReleaseFragment();
                activity.getFragmentManager().beginTransaction().replace(R.id.frame_fragment, myFragment).addToBackStack(null).commit();

            }
        });



    }

    @Override
    public int getItemCount() {
        if(NewReleaseList != null){
            return NewReleaseList.size();
        }
        return 0;

    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView ImageName;
        ImageView image;

        public MyviewHolder(View itemView) {
            super(itemView);
            ImageName = (TextView)itemView.findViewById(R.id.NameOfImage);
            image = (ImageView)itemView.findViewById(R.id.entity_image);
        }
    }
}
