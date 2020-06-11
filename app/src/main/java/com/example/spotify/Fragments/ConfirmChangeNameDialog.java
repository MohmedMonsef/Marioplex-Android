package com.example.spotify.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Constants;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.apiClasses.nameUpdate;
import com.example.spotify.login.user;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmChangeNameDialog extends DialogFragment {
    String name;
    Context context;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        name = getArguments().getString("name");
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Enter password");
        final View view = inflater.inflate(R.layout.enter_password_dialog, null);
        builder.setView(view);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                EditText editText = view.findViewById(R.id.password);
                nameUpdate newName = new nameUpdate(name,editText.getText().toString());
                EndPointAPI api = Retrofit.getInstance().getEndPointAPI();
                api.updateProfile(user.getToken(),newName).enqueue(new Callback<ResponseBody>() {
                    //@RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(context,"Confirmation mail sent",Toast.LENGTH_SHORT).show();
                            Log.v("nameUpdate","success");
                        }
                        else{
                            Toast.makeText(context,"incorrect password",Toast.LENGTH_SHORT).show();
                        }
                    }

                    //@RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
            }
        });



        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }
}