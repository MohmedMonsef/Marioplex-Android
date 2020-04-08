package com.example.spotify.Adapters;

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
    /**
     * @param context
     * @param Categorylist
     * set the adapterCategories with list
     */
    public adapterCategories(Context context, List<Category_> Categorylist)
    {
        this.context = context;
        this.Categorylist = Categorylist;
    }

    public void setMovieList(List<Category_> Categorylist)
    {
        this.Categorylist = Categorylist;
        notifyDataSetChanged();
    }

    /**
     * @param parent --> the view that has the recyclerview of this class
     * @param viewType-->
     * @return new object of the view holder
     */

    @Override
    public adapterCategories.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.categoryitem,parent,false);
        return new MyviewHolder(view);
    }

    /**
     *
     * @param holder -->that has the view item (layout)
     * @param position -->index of the item in the list
     * set on item of recyclerview with its data
     */
    @Override
    public void onBindViewHolder(adapterCategories.MyviewHolder holder, int position)
    {


        Category_ item=Categorylist.get(position);
        holder.ImageName.setText(item.getName());
        Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();
        /// check if the image not null
        if(item.getImages().size()!=0)
        {
            // load the image
            Picasso.get().load(item.getImages().get(0).toString()).into(holder.image);
        }

    }

    /**
     *
     * @return --> Categorylist size
     */
    @Override
    public int getItemCount()
    {
        if(Categorylist != null)
        {
            return Categorylist.size();
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

        public MyviewHolder(View itemView) {
            super(itemView);
            ImageName = (TextView)itemView.findViewById(R.id.categroy_textview);
            image = (ImageView)itemView.findViewById(R.id.categroy_imageview);
        }
    }
}
