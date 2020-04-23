package com.example.spotify.login;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.R;
import com.example.spotify.login.apiClasses.LoginResponse;
import com.example.spotify.login.apiClasses.SignUpData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SignUpFragment extends Fragment {

    final String FEMALE = "female";
    final String MALE = "male";

    private static Retrofit mRetrofit;
    private static EndPointAPI mEndPointAPI;
    private static float displayWidth = IntroActivity.getDisplayWidth();
    private int currentForm = 0;

    String email, password, username;
    String country = "Egypt";
    String gender;
    String birthdate;

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

        rootView.findViewById(R.id.create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });


        final View confirmEmailButton = rootView.findViewById(R.id.confirm_email_button);
        final View confirmPasswordButton = rootView.findViewById(R.id.confirm_password_button);
        final View confirmDateButton = rootView.findViewById(R.id.confirm_date_button);
        final View createButton = rootView.findViewById(R.id.create_button);
        confirmEmailButton.setEnabled(false);
        confirmPasswordButton.setEnabled(false);
        createButton.setEnabled(false);

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
                else{
                    confirmEmailButton.setEnabled(false);
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
                else{
                    confirmPasswordButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        confirmEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText)rootView.findViewById(R.id.sign_up_email)).clearFocus();
                ((EditText)rootView.findViewById(R.id.sign_up_password)).requestFocus();
                nextForm();
            }
        });

        confirmPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText)rootView.findViewById(R.id.sign_up_password)).clearFocus();
                ((DatePicker)rootView.findViewById(R.id.datePicker)).requestFocus();
                hideSoftKeyboard();
                nextForm();
            }
        });

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
                birthdate = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
                nextForm();
            }
        });

        // Gender form related
        rootView.findViewById(R.id.female_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = FEMALE;
                nextForm();
            }
        });

        rootView.findViewById(R.id.male_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = MALE;
                nextForm();
            }
        });

        //name form related
        ((EditText)rootView.findViewById(R.id.sign_up_name)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() > 0){
                    username = s.toString();
                    createButton.setEnabled(true);
                }
                else{
                    createButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });


        positionViews(rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        //Toast.makeText(getContext(), "fragment destroyed", Toast.LENGTH_SHORT).show();
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
    private void submit() {



        SignUpData signUpData = new SignUpData(username, password, country, email, gender, birthdate);

        mEndPointAPI.signUp(signUpData).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<com.example.spotify.login.apiClasses.LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("Intro Activity", response.body().toString());
                    Toast.makeText(getContext(), "Sucess " + response.code(), Toast.LENGTH_SHORT).show();
                    user.setToken(response.body().getToken());
                    user.fetchUserData();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                } else {
                    Log.i("Intro Activity", response.errorBody().toString());
                    Toast.makeText(getContext(), "Failed " + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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

    void hideSoftKeyboard(){
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
