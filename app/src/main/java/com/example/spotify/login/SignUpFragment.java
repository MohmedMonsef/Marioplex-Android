package com.example.spotify.login;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.util.TimeUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.R;
import com.example.spotify.login.apiClasses.SignUpData;
import com.example.spotify.login.apiClasses.updateProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SignUpFragment extends Fragment {

    private static Retrofit mRetrofit;
    private static EndPointAPI mEndPointAPI;
    private static float displayWidth = IntroActivity.getDisplayWidth();
    private int currentForm = 0;

    String email, password, username, gender;
    Calendar birthday;

    /*public SignUpFragment() {
        // Required empty public constructor
    }*/

    SignUpFragment(Retrofit retrofit, EndPointAPI EndPointAPI) {
        mRetrofit = retrofit;
        mEndPointAPI = EndPointAPI;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        rootView.findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        final TextView maleButton = (TextView) rootView.findViewById(R.id.male_button);
        final TextView femaleButton = (TextView) rootView.findViewById(R.id.female_button);
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleButton.setBackgroundColor(Color.GRAY);
                maleButton.setTextColor(Color.WHITE);
                femaleButton.setBackgroundColor(Color.WHITE);
                femaleButton.setTextColor(Color.GRAY);
                gender = "Male";
            }
        });

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleButton.setBackgroundColor(Color.WHITE);
                maleButton.setTextColor(Color.GRAY);
                femaleButton.setBackgroundColor(Color.GRAY);
                femaleButton.setTextColor(Color.WHITE);
                gender = "Female";
            }
        });


        final View confirmEmailButton = rootView.findViewById(R.id.confirm_email_button);
        final View confirmPasswordButton = rootView.findViewById(R.id.confirm_password_button);
        final View confirmDateButton = rootView.findViewById(R.id.confirm_date_button);
        confirmEmailButton.setEnabled(false);
        confirmPasswordButton.setEnabled(false);

        ((EditText)rootView.findViewById(R.id.sign_up_email)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(validateEmail(s.toString())){
                    confirmEmailButton.setEnabled(true);
                    email = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText)rootView.findViewById(R.id.sign_up_password)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(validatePassword(s.toString())){
                    confirmPasswordButton.setEnabled(true);
                    password = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        View.OnClickListener onNextClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextForm();
            }
        };

        confirmEmailButton.setOnClickListener(onNextClickListener);
        confirmPasswordButton.setOnClickListener(onNextClickListener);

        // Date form related
        final DatePicker datePicker = rootView.findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,-18);
        datePicker.setMaxDate(calendar.getTimeInMillis());
        calendar.add(Calendar.YEAR,-102);
        datePicker.setMinDate(calendar.getTimeInMillis());

        confirmDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthday = Calendar.getInstance();
                birthday.set(datePicker.getYear() + 1900,datePicker.getMonth(),datePicker.getDayOfMonth());
                nextForm();
            }
        });





        positionViews(rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getContext(), "fragment destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    // display next form
    private void nextForm() {
        if (currentForm == 4)
            return;
        currentForm++;
        slideForms();
    }

    // display previous form
    private void previousForm() {
        if (currentForm == 0)
            return;
        currentForm--;
        slideForms();
    }

    private void slideForms(){
        getView().findViewById(R.id.email_form).animate().translationX(0 - displayWidth * currentForm);
        getView().findViewById(R.id.password_form).animate().translationX(displayWidth * 1 - displayWidth * currentForm);
        getView().findViewById(R.id.birthday_form).animate().translationX(displayWidth * 2 - displayWidth * currentForm);
        getView().findViewById(R.id.gender_form).animate().translationX(displayWidth * 3 - displayWidth * currentForm);
        getView().findViewById(R.id.name_form).animate().translationX(displayWidth * 4 - displayWidth * currentForm);
    }

    private void positionViews(View rootView) {
        rootView.findViewById(R.id.email_form).setTranslationX(0);
        rootView.findViewById(R.id.password_form).setTranslationX(displayWidth * 1);
        rootView.findViewById(R.id.birthday_form).setTranslationX(displayWidth * 2);
        rootView.findViewById(R.id.gender_form).setTranslationX(displayWidth * 3);
        rootView.findViewById(R.id.name_form).setTranslationX(displayWidth * 4);
    }

    /**
     * Validate sign up form data and send it to backend server
     * Displays a Toast message indicating sign up request result
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void signUp() {

        try {
            username = ((EditText) getView().findViewById(R.id.sign_up_name)).getText().toString();
            //gender = ((EditText) getView().findViewById(R.id.sign_up_gender)).getText().toString();
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "fill in the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        String country = "Egypt";

        // form validation



        //String[] days = birthday.split("\\\\");
        if (gender == null) {
            Toast.makeText(getContext(), "Choose your gender", Toast.LENGTH_SHORT).show();
            return;
        }

        SignUpData signUpData = new SignUpData(username, password, country, email, gender, birthday);

        mEndPointAPI.signUp(signUpData).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.v("Intro Activity", response.body().toString());
                    Toast.makeText(getContext(), "Sucess " + response.code(), Toast.LENGTH_SHORT).show();

                } else {
                    Log.i("Intro Activity", response.errorBody().toString());
                    Toast.makeText(getContext(), "Failed " + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Intro Activity", t.getMessage());
                Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
            }
        });

    }

    boolean validateEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean validatePassword(String password){
        return password.length() >= 8;
    }



    boolean handleOnBackPressed() {
        if (currentForm == 0) {
            return false;
        } else {
            previousForm();
            return true;
        }
    }

}
