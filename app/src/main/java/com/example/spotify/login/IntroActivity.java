package com.example.spotify.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.animation.TimeAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Toast;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Fragments.ALBUM_FRAGMENT.album;
import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistFragment;
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

import static android.content.Intent.ACTION_VIEW;

public class IntroActivity extends AppCompatActivity {

    //final private static int ANIMATION_DURATION = 200;

    private static Retrofit retrofit = null;
    private static EndPointAPI endPointAPI = null;
    private static LoginFragment loginFragment = null;
    private static SignUpFragment signUpFragment = null;


    //private static String token = null;
    private static float displayWidth;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        createNotificationChannel();

        retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();

        endPointAPI = com.example.spotify.Interfaces.Retrofit.getInstance().getEndPointAPI();

        calculateDisplayWidth();


        loadToken();

        Intent intent = getIntent();
        String action = intent.getAction();
        final Uri data = intent.getData();
        if (action == ACTION_VIEW) {
            if (data.toString().contains("/login/reset_password")) {
                setContentView(R.layout.activity_intro);
                String url = data.toString();
                String token = url.split("token=")[1];
                user.setToken(token);
                saveToken();
                forgotPasswordFragment fragment = new forgotPasswordFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("currentView", 3);
                fragment.setArguments(bundle);
                showFragment(fragment);
            } else if ((user.getToken() != null) && (data.toString().contains("playlist") || data.toString().contains("album"))) {
                user.fetchUserData();
                user.getUserDataReadyFlag().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        Intent mainActivityIntent = new Intent(IntroActivity.this, MainActivity.class);
                        mainActivityIntent.setData(data);
                        mainActivityIntent.setAction(ACTION_VIEW);
                        startActivity(mainActivityIntent);
                        finish();
                    }
                });
            }
        } else if (user.getToken() != null) {
            user.fetchUserData();
            user.getUserDataReadyFlag().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean s) {
                    if (s) {
                        startActivity(new Intent(IntroActivity.this, MainActivity.class));
                        finish();
                    }
                }
            });
        } else {
            setContentView(R.layout.activity_intro);
            findViewById(R.id.intro_fragment).setTranslationX(displayWidth);

            findViewById(R.id.login_facebook_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginByFacebook();
                }
            });
        }

    }

    /**
     * Fetch user data from facebook and use to login into the app
     */
    private void loginByFacebook() {
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logOut();
        loginManager.logIn(IntroActivity.this, Arrays.asList("email", "user_gender", "user_birthday"));
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
                Toast.makeText(IntroActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(IntroActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Send user's facebook data to the database and launche the main app activity
     *
     * @param data User data received from facebook api
     */
    private void saveAndLaunchMainActivity(FacebookLoginData data) {
        endPointAPI.facebookLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    user.setToken(response.body().getToken());
                    Toast.makeText(IntroActivity.this, "Sucess " + response.code(), Toast.LENGTH_SHORT).show();
                    saveToken();
                    user.fetchUserData();
                    user.getUserDataReadyFlag().observe(IntroActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            startActivity(new Intent(IntroActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(IntroActivity.this, "Failed to login " + response.message(), Toast.LENGTH_SHORT).show();
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

    /**
     * Display login screen
     *
     * @param view The calling View
     */
    public void showLoginFragment(View view) {
        loginFragment = new LoginFragment(retrofit, endPointAPI);
        showFragment(loginFragment);
    }


    /**
     * Display Sign up screen
     *
     * @param view The calling View
     */
    public void showSignUpFragment(View view) {
        signUpFragment = new SignUpFragment(retrofit, endPointAPI);
        showFragment(signUpFragment);
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        if (loginFragment != null) {
            hideFragment(loginFragment);
            loginFragment = null;
        } else if (signUpFragment != null) {
            if (!signUpFragment.handleOnBackPressed()) {
                hideFragment(signUpFragment);
                signUpFragment = null;
            }
        } else {
            super.onBackPressed();
        }
    }


    /**
     * Display passed fragment
     *
     * @param fragment loginFragment or SignUpFragment
     */
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.intro_fragment, fragment).commit();
        findViewById(R.id.intro).animate().translationX(-displayWidth);
        findViewById(R.id.intro_fragment).animate().translationX(0);
    }

    /**
     * Hide passed fragment
     *
     * @param fragment loginFragment or SignUpFragment
     */
    public void hideFragment(final Fragment fragment) {
        findViewById(R.id.intro).animate().translationX(0);
        findViewById(R.id.intro_fragment).animate().translationX(displayWidth).withEndAction(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });
    }

    public void saveToken() {
        String token = user.getToken();
        if (token != null) {
            getSharedPreferences("token", Context.MODE_PRIVATE).edit().putString("token", token).apply();
        } else {
            //user.getTokenAsLiveData().observe(this,);
        }
    }

    boolean loadToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        if (token == null) {
            return false;
        } else {
            user.setToken(token);
            return true;
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "spotify";
            //String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("spotify_channel", name, importance);
            //channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Read the device display width
     */
    public void calculateDisplayWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
    }

    public static float getDisplayWidth() {
        return displayWidth;
    }

}