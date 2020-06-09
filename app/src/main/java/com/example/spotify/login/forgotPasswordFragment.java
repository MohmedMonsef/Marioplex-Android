package com.example.spotify.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.apiClasses.Password;
import com.example.spotify.login.apiClasses.forgotPasswordEmail;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class forgotPasswordFragment extends Fragment {

    String email;
    Password password;

    public forgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        if(getArguments() != null) {
            if (getArguments().getInt("currentView") == 3) {
                rootView.findViewById(R.id.get_link_layout).setVisibility(View.GONE);
                rootView.findViewById(R.id.open_email_app).setVisibility(View.GONE);
                rootView.findViewById(R.id.enter_new_password_layout).setVisibility(View.VISIBLE);
                password = new Password();
            }
        }
        else{
            rootView.findViewById(R.id.get_link_layout).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.open_email_app).setVisibility(View.GONE);
            rootView.findViewById(R.id.enter_new_password_layout).setVisibility(View.GONE);
        }

        final View getlinkButton = rootView.findViewById(R.id.getlink_button);

        getlinkButton.setEnabled(false);

        ((EditText) rootView.findViewById(R.id.email)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = s.toString();
                getlinkButton.setEnabled(validateEmail(email));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getlinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordEmail data = new forgotPasswordEmail();
                data.setEmail(email);
                Retrofit.getInstance().getEndPointAPI().forgetPassword(data).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            //Toast.makeText(getContext(), "Email sent", Toast.LENGTH_SHORT).show();
                            rootView.findViewById(R.id.check_your_email_layout).setVisibility(View.VISIBLE);
                            rootView.findViewById(R.id.get_link_layout).setVisibility(View.GONE);

                        } else {
                            Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        rootView.findViewById(R.id.open_email_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                startActivity(intent);
            }
        });

        final View saveButton = rootView.findViewById(R.id.save_password_button);
        saveButton.setEnabled(false);
        ((EditText)rootView.findViewById(R.id.new_password)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length() >= 8){
                    saveButton.setEnabled(true);
                    password.setPassword(charSequence.toString());
                }
                else{
                    saveButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewPassword();
                Intent intent = new Intent(getActivity(), IntroActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return rootView;
    }

    private boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void saveNewPassword(){
        Retrofit.getInstance().getEndPointAPI().resetPasword(user.getToken(),password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"New password saved",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Error occurred",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),"Failed to connect",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
