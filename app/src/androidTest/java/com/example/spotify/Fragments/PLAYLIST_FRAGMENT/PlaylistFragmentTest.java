package com.example.spotify.Fragments.PLAYLIST_FRAGMENT;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
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
public class PlaylistFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mact=new ActivityTestRule<>(MainActivity.class);

    @Test
    public void views_displayed_test() {

        delay(35000);

        onView(withId(R.id.recycleplaylist)).perform(ViewActions.scrollTo());
        onView(withId(R.id.recycleplaylist))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        delay(5000);
        testLikeandUnlike();

        onView(withId(R.id.shuffle_play_button)).check(matches(isDisplayed()));
        onView(withId(R.id.shuffle_play_button)).perform(click());

        delay(10000);
        onView(withId(R.id.bottom_image_id)).check(matches(isDisplayed()));

        onView(withId(R.id.back_arrow_playlist)).perform(click());



    }

    private void testLikeandUnlike(){

        onView(withId(R.id.like_playlist)).check(matches(isDisplayed()));
        onView(withId(R.id.like_playlist)).perform(click());
        delay(10000);
        onView(withId(R.id.like_playlist)).check(matches(isDisplayed()));
        onView(withId(R.id.like_playlist)).perform(click());
        delay(5000);
    }
    private void delay(int milliseconds) {
        try {
            sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}