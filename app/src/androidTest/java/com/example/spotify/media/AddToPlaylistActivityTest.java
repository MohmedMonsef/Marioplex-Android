package com.example.spotify.media;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.spotify.R;
import com.example.spotify.login.IntroActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
public class AddToPlaylistActivityTest {

//    @Rule
//    public ActivityTestRule<AddToPlaylistActivity> mact=new ActivityTestRule<AddToPlaylistActivity>(AddToPlaylistActivity.class);
//    private AddToPlaylistActivity addToPlaylistActivity = null;

    @Rule
    public ActivityTestRule<IntroActivity> mact=new ActivityTestRule<>(IntroActivity.class);


    @Test
    public void views_displayed_test() {

        greeterSaysHello();

        onView(withId(R.id.bottom_image_id)).perform(click());
        onView(withId(R.id.song_settings_button)).perform(click());
        onView(withId(R.id.settings_add_to_playlist)).perform(click());

        onView(withId(R.id.back_button_to_mediaplayer)).check(matches(isDisplayed()));
        onView(withId(R.id.new_playlist_button)).check(matches(isDisplayed()));
        onView(withId(R.id.new_playlist_button)).check(matches(isDisplayed()));
        onView(withId(R.id.new_playlist_button)).perform(click());
        onView(withId(R.id.playlist_name_edit_text)).check(matches(isDisplayed()));


    }

    @Test
    public void list_view_test() {

        greeterSaysHello();
        onView(withId(R.id.bottom_image_id)).perform(click());
        onView(withId(R.id.song_settings_button)).perform(click());
        onView(withId(R.id.settings_add_to_playlist)).perform(click());
        try {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        onView(withId(R.id.playlists_list_view)).check(matches(isDisplayed()));

        onView(withId(R.id.back_button_to_mediaplayer)).perform(click());
        onView(withId(R.id.play)).check(matches(isDisplayed()));


    }




    public void greeterSaysHello()
    {

        //startActivity(new Intent(introact.getBaseContext(), IntroActivity.class));
        onView(withId(R.id.introya)).perform(click());
        onView(withId(R.id.email)).perform(typeText("hager7@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("hager"));
        onView(withId(R.id.loginButton)).perform(click());
        try {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
    }

}
