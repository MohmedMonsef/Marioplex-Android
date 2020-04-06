package com.example.spotify.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.TimeAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Toast;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.R;
import com.example.spotify.login.apiClasses.FacebookLoginData;
import com.example.spotify.login.apiClasses.LoginResponse;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IntroActivity extends AppCompatActivity {

    //final private static int ANIMATION_DURATION = 200;

    private static Retrofit retrofit = null;
    private static EndPointAPI endPointAPI = null;
    private static LoginFragment loginFragment = null;
    private static SignUpFragment signUpFragment= null;
    //private static String token = null;
    private static float displayWidth;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();

        endPointAPI = com.example.spotify.Interfaces.Retrofit.getInstance().getEndPointAPI();

        calculateDisplayWidth();


        findViewById(R.id.intro_fragment).setTranslationX(displayWidth);

        findViewById(R.id.login_facebook_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginByFacebook();
            }
        });
    }

    private void loginByFacebook(){
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logOut();
        loginManager.logIn(IntroActivity.this, Arrays.asList("email","user_gender","user_birthday"));
        callbackManager = CallbackManager.Factory.create();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                try {
                                    FacebookLoginData data = new FacebookLoginData();
                                    data.setName(object.getString("name"));
                                    data.setEmail(object.getString("email"));
                                    data.setGender(object.getString("gender"));
                                    data.setBirthday(object.getString("birthday"));
                                    saveAndLaunchMainActivity(data);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(IntroActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(IntroActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });






    }

    public void saveAndLaunchMainActivity(FacebookLoginData data){
        endPointAPI.facebookLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    user.setToken(response.body().getToken());
                    Toast.makeText(IntroActivity.this, "Sucess " + response.code(), Toast.LENGTH_SHORT).show();
                    user.fetchUserData();
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(IntroActivity.this,"Failed to login " + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void showLoginFragment(View view) {
        loginFragment = new LoginFragment(retrofit,endPointAPI);
        showFragment(loginFragment);
    }


    public void showSignUpFragment(View view){
        signUpFragment = new SignUpFragment(retrofit,endPointAPI);
        showFragment(signUpFragment);
    }

    @Override
    public void onBackPressed() {
        if(loginFragment != null){
            hideFragment(loginFragment);
            loginFragment = null;
        }
        else if(signUpFragment != null){
            hideFragment(signUpFragment);
            signUpFragment = null;
        }
        else{
            super.onBackPressed();
        }
    }


    public void showFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.intro_fragment,fragment).commit();
        findViewById(R.id.intro).animate().translationX(-displayWidth);
        findViewById(R.id.intro_fragment).animate().translationX(0);
    }

    public void hideFragment(final Fragment fragment){
        findViewById(R.id.intro).animate().translationX(0);
        findViewById(R.id.intro_fragment).animate().translationX(displayWidth).withEndAction(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().remove(fragment);
            }
        });
    }

    public void calculateDisplayWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
    }
}