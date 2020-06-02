package com.example.spotify.Fragments.ALBUM_FRAGMENT;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.R;
import com.example.spotify.login.IntroActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isLeftOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)

public class albumTest<introact> {
    MainActivity introact;
@Rule
public ActivityTestRule<MainActivity> mact=new ActivityTestRule<>(MainActivity.class);

@Test
public void AlbumItem()
{

    AlbumPopular();
    onView(withId(R.id.back_arrow_album)).check(isLeftOf(withId(R.id.album_name_album_fragment)));
    onView(withId(R.id.album_name_album_fragment)).check(isLeftOf(withId(R.id.like_album)));
    onView(withId(R.id.like_album)).check(isLeftOf(withId(R.id.album_settings_button)));
    onView(withId(R.id.album_image_album_fragment)).check(isAbove(withId(R.id.album_name_middle)));
    onView(withId(R.id.album_name_middle)).check(isAbove(withId(R.id.album_owner)));
    onView(withId(R.id.album_owner)).check(isAbove(withId(R.id.shuffle_play_button_album)));
    onView(withId(R.id.shuffle_play_button_album)).check(isAbove(withId(R.id.preview_text_album)));
}

@Test
public void AlbumLike()
{

    AlbumNewRelease();
    onView(withId(R.id.like_album)).perform(click());
    try {
        sleep(5000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    onView(withId(R.id.like_album)).perform(click());
    try {
        sleep(5000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
public void AlbumPopular()
{
    try {
        sleep(35000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    onView(ViewMatchers.withId(R.id.recyclealbum)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
    try {
        sleep(5000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    onView(withId(R.id.album_name_middle)).check(matches(isDisplayed()));



}


    public void AlbumNewRelease()
    {
        try {
            sleep(35000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(ViewMatchers.withId(R.id.recycle)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.album_name_middle)).check(matches(isDisplayed()));

    }
}



