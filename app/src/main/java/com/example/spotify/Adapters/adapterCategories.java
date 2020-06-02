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

import com.example.spotify.BackClasses.Backclasses.backcategory.Category_;
import com.example.spotify.Fragments.CATEGORY_PLAYLISTS.categoryplaylist;
import com.example.spotify.Interfaces.Retrofit;
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
    public void onBindViewHolder(adapterCategories.MyviewHolder holder, final int position)
    {


        Category_ item=Categorylist.get(position);
        holder.ImageName.setText(item.getName());
        /// check if the image not null
        String imageID = "12D";
        if(item.getImages().size()!=0)
        {
            imageID = item.getImages().get(0).getID();
        }
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=playlist";
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
            public void onClick(View v)
            {

                Bundle bundle = new Bundle();
                bundle.putString("CategoryID" ,Categorylist.get(position).getId());
                bundle.putString("CategoryName" , Categorylist.get(position).getName());
                androidx.fragment.app.Fragment f = new categoryplaylist();
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
