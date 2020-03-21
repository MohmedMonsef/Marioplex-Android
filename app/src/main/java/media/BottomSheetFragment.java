package media;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.spotifycorrected.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import pojo.Artist_;
import pojo.Image;
import pojo.Track;

public class BottomSheetFragment extends Fragment {

    ImageView bottom_image_id;
    TextView song_artist_name;
    private TrackInfo track;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.bottom_sheet_fragment,container,false);
        bottom_image_id = root.findViewById(R.id.bottom_image_id);
        song_artist_name = root.findViewById(R.id.song_artist_name);
        track = TrackInfo.getInstance();
        track.setName("Hager");
        song_artist_name.setText(track.getName());


        bottom_image_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MediaPlayerActivity.class);
                startActivity(intent);
            }
        });

        track.getTrack().observe(this, new Observer<Track>() {
            @Override
            public void onChanged(Track track) {
                UpdateUI();
            }
        });

        return root;
    }

    void UpdateUI(){

        List<Artist_> artists = track.getTrack().getValue().getArtists();
        String artistsNames = "";
        for(Artist_ artist_ : artists)
        {
            artistsNames+=artist_.getName() +" ";
        }
        song_artist_name.setText(track.getTrack().getValue().getName()+" . "+artistsNames);


        List<Image> images= track.getTrack().getValue().getAlbum().getImages();
        String Imageurl = images.get(0).getUrl();
        //new DownLoadImageTask(song_image).execute(Imageurl);
        new DownLoadImageTask(bottom_image_id).execute(Imageurl);
    }

    //LOADS AN IMAGE FROM A URL IN THE BACKGROUND
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

}
