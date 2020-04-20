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
public class MediaPlayerActivityTest {

//    @Rule
//    public ActivityTestRule<MediaPlayerActivity> mact=new ActivityTestRule<MediaPlayerActivity>(MediaPlayerActivity.class);
//    private MediaPlayerActivity mediaPlayerActivity = null;

    @Rule
    public ActivityTestRule<IntroActivity> mact=new ActivityTestRule<>(IntroActivity.class);

    @Test
    //@UiThreadTest
    public void views_displayed_test() {
        greeterSaysHello();
        onView(withId(R.id.bottom_image_id)).perform(click());

        onView(withId(R.id.play)).check(matches(isDisplayed()));
        onView(withId(R.id.next)).check(matches(isDisplayed()));
        onView(withId(R.id.previous)).check(matches(isDisplayed()));

        onView(withId(R.id.arrow)).perform(click());
        onView(withId(R.id.bottom_image_id)).perform(click());

        onView(withId(R.id.play)).perform(click());
        try {
            sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.play)).perform(click());

    }

    @Test
    public void like_unlike_test() {
        greeterSaysHello();
        onView(withId(R.id.bottom_image_id)).perform(click());

        onView(withId(R.id.favorite)).check(matches(isDisplayed()));

        onView(withId(R.id.favorite)).perform(click());
        try {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.favorite)).perform(click());
        try {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void settings_like_test() {
        greeterSaysHello();

        onView(withId(R.id.bottom_image_id)).perform(click());

        onView(withId(R.id.song_settings_button)).check(matches(isDisplayed()));
        onView(withId(R.id.song_settings_button)).perform(click());
        onView(withId(R.id.favorite2)).check(matches(isDisplayed()));
        onView(withId(R.id.favorite2)).perform(click());
        try {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.favorite2)).check(matches(isDisplayed()));
        onView(withId(R.id.favorite2)).perform(click());
        try {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }
    @Test
    public void settings_sleep_timer_test() {
        greeterSaysHello();

        onView(withId(R.id.bottom_image_id)).perform(click());

        onView(withId(R.id.song_settings_button)).check(matches(isDisplayed()));
        onView(withId(R.id.song_settings_button)).perform(click());

        try {
            sleep(200);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        onView(withId(R.id.timer_image)).check(matches(isDisplayed()));
        onView(withId(R.id.timer_image)).perform(click());

        try {
            sleep(200);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        onView(withId(R.id.five)).check(matches(isDisplayed()));
        onView(withId(R.id.five)).perform(click());

        onView(withId(R.id.play)).check(matches(isDisplayed()));

        try {
            sleep(200);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        onView(withId(R.id.song_settings_button)).check(matches(isDisplayed()));
        onView(withId(R.id.song_settings_button)).perform(click());

        try {
            sleep(200);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        onView(withId(R.id.timer_image)).check(matches(isDisplayed()));
        onView(withId(R.id.timer_image)).perform(click());

        try {
            sleep(200);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        onView(withId(R.id.turn_of_timer)).check(matches(isDisplayed()));
        onView(withId(R.id.turn_of_timer)).perform(click());

        try {
            sleep(200);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }


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