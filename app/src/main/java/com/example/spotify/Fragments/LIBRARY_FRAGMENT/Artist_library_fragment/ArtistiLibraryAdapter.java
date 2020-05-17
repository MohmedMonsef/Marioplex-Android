package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Artist_library_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.pojo.BasicArtist;
import com.example.spotify.pojo.ImageInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistiLibraryAdapter extends RecyclerView.Adapter<ArtistiLibraryAdapter.MyviewHolder>{

    private Context context;
    private List<BasicArtist> Artists;

    public ArtistiLibraryAdapter(Context context, List<BasicArtist> A){
        this.context = context;
        Artists = A;

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.artist_recycle_view_layout, parent, false);
        return new ArtistiLibraryAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistiLibraryAdapter.MyviewHolder holder, final int position) {
        holder.artist_library_name.setText(Artists.get(position).getName());

        List<ImageInfo> images= Artists.get(position).getImages();
        String imageID ="12d";
        if(images != null && images.size()!=0) {
            imageID = images.get(0).getID();
        }
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=artist";
        Picasso.get().load(Imageurl).into(holder.artist_library_image);
        holder.artist_recycle_view_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("artistID" , playlists.get(position).getId());
//                bundle.putString("from" , "artist_library");
//                androidx.fragment.app.Fragment f = new artistfragment;
//                f.setArguments(bundle);
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                activity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frame_fragment, f)
//                        .addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Artists.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView artist_library_name;
        ImageView artist_library_image;
        LinearLayout artist_recycle_view_layout;

        public MyviewHolder(View itemView) {
            super(itemView);
            artist_library_name = (TextView) itemView.findViewById(R.id.artist_library_name);
            artist_library_image = (ImageView)itemView.findViewById(R.id.artist_library_image);
            artist_recycle_view_layout = (LinearLayout)itemView.findViewById(R.id.artist_recycle_view_layout);

        }
    }
}
