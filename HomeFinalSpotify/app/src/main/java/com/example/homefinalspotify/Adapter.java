package com.example.homefinalspotify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.MyviewHolder> {
    private  Context context;
    private List<Item> NewReleaseList;

public Adapter(Context context, List<Item> NewReleaseList) {
        this.context = context;
        this.NewReleaseList = NewReleaseList;
        }

public void setMovieList(List<Item> movieList) {
        this.NewReleaseList = movieList;
        notifyDataSetChanged();
        }

@Override
public Adapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler,parent,false);
        return new MyviewHolder(view);
        }

@Override
public void onBindViewHolder(Adapter.MyviewHolder holder, int position) {

    Item item=NewReleaseList.get(position);


        holder.ImageName.setText(item.getName());
    Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();
    Picasso.get().load(item.getImages().get(0).getUrl()).into(holder.image);
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
