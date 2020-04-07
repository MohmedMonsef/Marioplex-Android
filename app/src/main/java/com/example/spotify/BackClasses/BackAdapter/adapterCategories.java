package com.example.spotify.BackClasses.BackAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.BackClasses.Backclasses.backcategory.Category_;
import com.example.spotify.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterCategories extends RecyclerView.Adapter<adapterCategories.MyviewHolder> {
    private Context context;
    private List<Category_> Categorylist;

    public adapterCategories(Context context, List<Category_> Categorylist) {
        this.context = context;
        this.Categorylist = Categorylist;
    }

    public void setMovieList(List<Category_> Categorylist) {
        this.Categorylist = Categorylist;
        notifyDataSetChanged();
    }

    @Override
    public adapterCategories.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categoryitem,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(adapterCategories.MyviewHolder holder, int position) {

        Category_ item=Categorylist.get(position);
        holder.ImageName.setText(item.getName());
        Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();
        if(item.getImages().size()!=0) {
            Picasso.get().load(item.getImages().get(0).toString()).into(holder.image);
        }

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
