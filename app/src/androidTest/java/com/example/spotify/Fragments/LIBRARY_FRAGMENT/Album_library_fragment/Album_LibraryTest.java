package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Album_library_fragment;



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
public class Album_LibraryTest<introact>
{
    MainActivity introact;
    @Rule
    public ActivityTestRule<MainActivity> mact=new ActivityTestRule<>(MainActivity.class);


    @Test
    public void libraryAlbum()
    {


        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation_library)).perform(click());
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.playlist_library)).check(matches(isDisplayed()));
        onView(withId(R.id.artist_library)).check(matches(isDisplayed()));
        onView(withId(R.id.album_library)).check(matches(isDisplayed()));

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.album_library)).perform(click());

        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recyclerSavedAlbum)).check(matches(isDisplayed()));

        try {
            sleep(45000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(ViewMatchers.withId(R.id.recyclerSavedAlbum)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        try {
            sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.album_name_middle)).check(matches(isDisplayed()));



    }





}