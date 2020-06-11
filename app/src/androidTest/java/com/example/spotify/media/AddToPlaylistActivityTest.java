package com.example.spotify.media;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
public class AddToPlaylistActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mact=new ActivityTestRule<>(MainActivity.class);


    @Test
    public void views_displayed_test() {


        delay(5000);
        onView(withId(R.id.bottom_image_id)).perform(click());
        onView(withId(R.id.song_settings_button)).perform(click());
        onView(withId(R.id.settings_add_to_playlist)).perform(click());

        onView(withId(R.id.back_button_to_mediaplayer)).check(matches(isDisplayed()));
        onView(withId(R.id.new_playlist_button)).check(matches(isDisplayed()));
        list_view_test();
        onView(withId(R.id.new_playlist_button)).perform(click());
        onView(withId(R.id.playlist_name_edit_text)).check(matches(isDisplayed()));



    }


    public void list_view_test() {

        delay(5000);

        onView(withId(R.id.playlists_list_view)).check(matches(isDisplayed()));

    }

    private void delay(int milliseconds) {
        try {
            sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
