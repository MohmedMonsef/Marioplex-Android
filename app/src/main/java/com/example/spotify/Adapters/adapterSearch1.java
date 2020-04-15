package com.example.spotify.Adapters;

import android.content.Context;
import android.os.Bundle;
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
import com.example.spotify.BackClasses.Backclasses.backsearch.Search;
import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistFragment;
import com.example.spotify.R;
import com.example.spotify.BackClasses.Backclasses.aclass;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import static android.content.ContentValues.TAG;
public class adapterSearch1 extends RecyclerView.Adapter<adapterSearch1.MyviewHolder> {

    private Context context;
    private ArrayList<aclass> list1;
    /**
     * @param context
     * @param LIST
     * set the adapterSearch with list
     */
    public adapterSearch1(Context context, Search LIST)
    {
        this.context = context;
        list1=new ArrayList<aclass>();

        if(LIST.getPlaylist()!=null)
        {
            //fill the playlist list
            for (int i = 0; i < LIST.getPlaylist().size(); i++)
            {
                String image = "";
                if (LIST.getPlaylist().get(i).getImages() != null && LIST.getPlaylist().get(i).getImages().size() != 0)
                {
                    image = LIST.getPlaylist().get(i).getImages().get(0).toString();
                }
                list1.add(new aclass(LIST.getPlaylist().get(i).getType(),
                        LIST.getPlaylist().get(i).getName(),
                        image,
                        LIST.getPlaylist().get(i).getId()));
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
    public adapterSearch1.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.playlistitem, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterSearch1.MyviewHolder holder, final int position)
    {
        holder.itemName.setText(list1.get(position).getName());
        Toast.makeText(context.getApplicationContext(),"Image Loading",Toast.LENGTH_SHORT).show();

        if(list1.get(position).getImage() !="")
        {
            Picasso.get().load(list1.get(position).getImage()).into(holder.itemImage);
        }

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
            itemName = (TextView) itemView.findViewById(R.id.NameOfImageplay);
            itemImage = (ImageView)itemView.findViewById(R.id.play_image);

        }
    }
}