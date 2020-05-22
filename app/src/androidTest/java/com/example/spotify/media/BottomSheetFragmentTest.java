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
public class BottomSheetFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mact=new ActivityTestRule<>(MainActivity.class);

    @Test
    public void aviews_displayed_test() {


        delay(10000);
        onView(withId(R.id.bottom_image_id)).check(matches(isDisplayed()));
        onView(withId(R.id.song_artist_name)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_favorite)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_play_pause)).check(matches(isDisplayed()));


        play_pause_test();

        like_unlike_test();

    }


    public void play_pause_test() {

        onView(withId(R.id.bottom_play_pause)).check(matches(isDisplayed()));

        onView(withId(R.id.bottom_play_pause)).perform(click());
         delay(5000);
         onView(withId(R.id.bottom_play_pause)).perform(click());
         delay(5000);

    }


     public void like_unlike_test() {


        onView(withId(R.id.bottom_favorite)).check(matches(isDisplayed()));

        onView(withId(R.id.bottom_favorite)).perform(click());
        delay(1000);
        onView(withId(R.id.bottom_favorite)).perform(click());
         delay(10000);
    }

    private void delay(int milliseconds) {
        try {
            sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}