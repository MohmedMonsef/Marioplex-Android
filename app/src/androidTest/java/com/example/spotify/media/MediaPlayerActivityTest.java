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
public class MediaPlayerActivityTest {


    @Rule
    public ActivityTestRule<MainActivity> mact=new ActivityTestRule<>(MainActivity.class);

    @Test
    //@UiThreadTest
    public void views_displayed_test() {
        delay(10000);
        onView(withId(R.id.bottom_image_id)).perform(click());

        onView(withId(R.id.play)).check(matches(isDisplayed()));
        onView(withId(R.id.next)).check(matches(isDisplayed()));
        onView(withId(R.id.previous)).check(matches(isDisplayed()));

        onView(withId(R.id.arrow)).perform(click());
        onView(withId(R.id.bottom_image_id)).perform(click());

        onView(withId(R.id.play)).perform(click());
        delay(5000);
        onView(withId(R.id.play)).perform(click());

        like_unlike_test();
        settings_like_test();
        settings_sleep_timer_test();

    }


    public void like_unlike_test() {
        onView(withId(R.id.favorite)).check(matches(isDisplayed()));

        onView(withId(R.id.favorite)).perform(click());
        delay(10000);
        onView(withId(R.id.favorite)).perform(click());
        delay(10000);
    }


    public void settings_like_test() {

        onView(withId(R.id.song_settings_button)).check(matches(isDisplayed()));
        onView(withId(R.id.song_settings_button)).perform(click());
        onView(withId(R.id.favorite2)).check(matches(isDisplayed()));
        onView(withId(R.id.favorite2)).perform(click());
        delay(10000);
        onView(withId(R.id.favorite2)).check(matches(isDisplayed()));
        onView(withId(R.id.favorite2)).perform(click());
        delay(10000);

    }

    public void settings_sleep_timer_test() {


        onView(withId(R.id.timer_image)).check(matches(isDisplayed()));
        onView(withId(R.id.timer_image)).perform(click());

        delay(200);

        onView(withId(R.id.five)).check(matches(isDisplayed()));
        onView(withId(R.id.five)).perform(click());

        onView(withId(R.id.play)).check(matches(isDisplayed()));

        delay(200);
        onView(withId(R.id.song_settings_button)).check(matches(isDisplayed()));
        onView(withId(R.id.song_settings_button)).perform(click());

        delay(200);

        onView(withId(R.id.timer_image)).check(matches(isDisplayed()));
        onView(withId(R.id.timer_image)).perform(click());

        delay(200);

        onView(withId(R.id.turn_of_timer)).check(matches(isDisplayed()));
        onView(withId(R.id.turn_of_timer)).perform(click());

        delay(200);
    }

    private void delay(int milliseconds) {
        try {
            sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}