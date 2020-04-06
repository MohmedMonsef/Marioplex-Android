package com.example.spotify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.R;
import com.example.spotify.SpotifyClasses.CategoryModel.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.MyviewHolder> {
    private Context context;
    private List<Item> Categorylist;

    public AdapterCategory(Context context, List<Item> Categorylist) {
        this.context = context;
        this.Categorylist = Categorylist;
    }

    public void setMovieList(List<Item> Categorylist) {
        this.Categorylist = Categorylist;
        notifyDataSetChanged();
    }

    @Override
    public AdapterCategory.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categoryitem,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterCategory.MyviewHolder holder, int position) {

        Item item=Categorylist.get(position);
        holder.ImageName.setText(item.getName());
        Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();
        Picasso.get().load(item.getIcons().get(0).getUrl()).into(holder.image);

    }

    @Override
    public int getItemCount()
    {
        if(Categorylist != null)
        {
            return Categorylist.size();
        }
        return 0;

    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView ImageName;
        ImageView image;

        public MyviewHolder(View itemView) {
            super(itemView);
            ImageName = (TextView)itemView.findViewById(R.id.categroy_textview);
            image = (ImageView)itemView.findViewById(R.id.categroy_imageview);
        }
    }
}
