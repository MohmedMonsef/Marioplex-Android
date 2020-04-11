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
public class BottomSheetFragmentTest {

    @Rule
    public ActivityTestRule<IntroActivity> mact=new ActivityTestRule<>(IntroActivity.class);

    @Test
    public void aviews_displayed_test() {

        greeterSaysHello();

        onView(withId(R.id.bottom_image_id)).check(matches(isDisplayed()));
        onView(withId(R.id.song_artist_name)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_favorite)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_play_pause)).check(matches(isDisplayed()));



    }

     @Test
    public void play_pause_test() {

        greeterSaysHello();

        onView(withId(R.id.bottom_play_pause)).check(matches(isDisplayed()));

        onView(withId(R.id.bottom_play_pause)).perform(click());
         try {
             sleep(2000);
         }
         catch (InterruptedException e)
         {
             e.printStackTrace();
         }
         onView(withId(R.id.bottom_play_pause)).perform(click());


    }

     @Test
     public void like_unlike_test() {
        greeterSaysHello();

        onView(withId(R.id.bottom_favorite)).check(matches(isDisplayed()));

        onView(withId(R.id.bottom_favorite)).perform(click());
        try {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.bottom_favorite)).perform(click());
         try {
             sleep(10000);
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