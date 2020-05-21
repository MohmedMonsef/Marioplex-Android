package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Playlist_library_fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.pojo.ImageInfo;
import com.example.spotify.pojo.UploadImageResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upload_Image extends AppCompatActivity {
    private ImageView chosen_image;
    private Button upload_button;
    private Button cancel_upload_button;
    private Button pick_from_gallery;
    private String filePath = "";
    private String SourceID = "";
    private String From ="";
    private String belongsTo = "playlist";
    private static final int GALLERY_REQUEST_CODE = 100;
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);
        getViews();

        SourceID = getIntent().getStringExtra("SourceID");
        From = getIntent().getStringExtra("from");
        if(From!=null&&From.equals("user")){belongsTo = "user";}

        /**
         * listener for the click on the cancel button to go back to the previous page
         */
        cancel_upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        /**
         * listener for the upload button to send the request to upload the image
         */
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filePath.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "choose a photo from the gallery", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImageRequest();
                }
            }
        });

        /**
         * listener for the clock on the pick from gallery button to send intent to the gallery
         */
        pick_from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

    }

    /**
     * sends a request to upload the image to the server
     */
    void uploadImageRequest() {

        //Create a file object using file path
        File file = new File(filePath);
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), fileReqBody);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        Call<UploadImageResponse> call = endPointAPI.updateImage(SourceID, belongsTo, imageHeight, imageWidth, part, user.getToken());
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Some thing went wrong while uploading the image .try again", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if(belongsTo.equals("user")){
                        List<ImageInfo> img =new ArrayList<>();
                        ImageInfo newImage = new ImageInfo();
                        newImage.setID(response.body().getImageID());
                        img.add(newImage);
                        user.setImages(img);
                    }
                    Toast.makeText(getApplicationContext(), "Image is uploaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some thing went wrong while uploading the image .check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);

    }

    /**
     * this method is called when the user picks an image from the gallery
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    filePath = imgDecodableString;
                    // Set the Image in ImageView after decoding the String
                    chosen_image.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    break;

            }
    }

    /**
     * sends an intent to the gallery so the user can pick an image
     */
    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    /**
     * gets all the views I will use in the fragment
     */

    void getViews() {
        chosen_image = findViewById(R.id.chosen_image);
        upload_button = findViewById(R.id.upload_button);
        cancel_upload_button = findViewById(R.id.cancel_upload_button);
        pick_from_gallery = findViewById(R.id.pick_from_gallery);
    }
}
