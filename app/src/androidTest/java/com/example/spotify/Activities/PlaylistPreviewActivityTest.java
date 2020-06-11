package com.example.spotify.Activities;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.spotify.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
public class PlaylistPreviewActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mact=new ActivityTestRule<>(MainActivity.class);

    /**
     * to do these test the account that you will enter with must have created plyliast and followed playlist and must have a current song
     * the shuffle play button must be pushed at least once
     * the animation on the phone must be turned off
     * the server must be up and running at all time
     * the phone must be logged in before(the token must be cached)
     */
    @Test
    public void views_displayed_test() {

        //greeterSaysHello();
        delay(40000);

        onView(withId(R.id.recycleplaylist)).perform(ViewActions.scrollTo());
        onView(withId(R.id.recycleplaylist))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        delay(5000);
        onView(withId(R.id.preview_button)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.preview_button)).perform(scrollTo()).perform(click());


        onView(withId(R.id.back_arrow_from_preview)).check(matches(isDisplayed()));
        onView(withId(R.id.back_arrow_from_preview)).perform(click());

        onView(withId(R.id.artist_name_song_name_playlist)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.artist_name_song_name_playlist)).perform(scrollTo()).perform(click());


        onView(withId(R.id.back_arrow_from_preview)).check(matches(isDisplayed()));
        onView(withId(R.id.back_arrow_from_preview)).perform(click());


        onView(withId(R.id.preview_button)).perform(scrollTo()).check(matches(isDisplayed()));

    }

    private void delay(int milliseconds) {
        try {
            sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}