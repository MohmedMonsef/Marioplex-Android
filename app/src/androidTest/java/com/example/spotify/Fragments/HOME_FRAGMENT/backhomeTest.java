package com.example.spotify.Fragments.HOME_FRAGMENT;

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

public class backhomeTest<introact>
{
    MainActivity introact;
    @Rule
    public ActivityTestRule<MainActivity> mact=new ActivityTestRule<>(MainActivity.class);


    @Test
    public void buttombar()
    {
        onView(withId(R.id.navigation_search)).perform(click());
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.text_search)).check(matches(isDisplayed()));
        /////////////////////////
        try {
            sleep(70);
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
        ////////////////////////
        try {
            sleep(70);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation_premium)).perform(click());
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.text_premium)).check(matches(isDisplayed()));
        /////////////////////////
        try {
            sleep(70);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation_home)).perform(click());
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.Popular_new_releases)).check(matches(isDisplayed()));
        try {
            sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void HOME()
    {

        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        onView(withId(R.id.recycle)).check((ViewAssertion) allOf(isAssignableFrom(ScrollView.class)));
        // onView(withId(R.id.recycle)).perform(RecyclerView.a)
//onView(ViewMatchers.withId(R.id.recycle)).perform(RecyclerViewActions.scrollToHolder((Matcher<RecyclerView.ViewHolder>) matches(isDisplayed())));
        onView(ViewMatchers.withId(R.id.recycle)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.album_name_middle)).check(matches(isDisplayed()));
        try {
            sleep(70);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation_home)).perform(click());
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
///////////////////////////////////////////////
        try
        {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
////////////////////////////////////////////////
        onView(ViewMatchers.withId(R.id.recyclealbum)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.album_name_middle)).check(matches(isDisplayed()));
        try {
            sleep(70);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation_home)).perform(click());
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
//////////////////////******************************///////////////////////////////
        try
        {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
////////////////////////////////////////////////
        onView(withId(R.id.recycleplaylist)).perform(ViewActions.scrollTo());
        onView(withId(R.id.recycleplaylist)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.playlist_name_middle)).check(matches(isDisplayed()));
        try {
            sleep(70);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //onView(withId(R.id.shuffle_play_button)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_home)).perform(click());
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
///////////////////////////////////////////////////////////////////////
        try
        {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
////////////////////////////////////////////////
        onView(withId(R.id.recycleartist)).perform(ViewActions.scrollTo());
        try
        {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.recycleartist)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
        //onView(withId(R.id.navigation_home)).perform(click());
        //onView(withId(R.id.navigation)).check(matches(isDisplayed()));

        //  onView(withId(R.id.navigation)).check(matches(isDisplayed()));
        ///////////////////////////////////////////////
        //onView(withId(R.id.action_settings)).perform(click());
        //onView(withId(R.id.profile_picture)).check(matches(isDisplayed()));
        /////////////////////////////////////////////////////////////
        //onView(withId(R.id.recycle)).perform(ViewActions.scrollTo());
        //onView(withId(R.id.recycle)).perform(RecyclerViewActions.actionOnItemAtPosition(7, click()));
        //onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
//////////////////////////////////////
        //onView(withId(R.id.navigation_home)).perform(click());
        //onView(withId(R.id.navigation)).check(matches(isDisplayed()));
///////////////////////////////////////////////////////////////////////
        // onView(withText("Launch")).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
        //Espresso.onView(ViewMatchers.withId(R.id.recyclealbum)).perform(ViewActions.swipeUp());
        //////////////////////////////////////////////
        //onView(withId(R.id.recyclealbum)).perform(ViewActions.scrollTo());
        //onView(withId(R.id.recyclealbum)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
        //////////////////////////////////////
        /*onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
///////////////////////////////////////////////////////////////////////
        onView(withId(R.id.recycleartist)).perform(ViewActions.scrollTo());
        onView(withId(R.id.recycleartist)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
        //////////////////////////////////////
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
///////////////////////////////////////////////////////////////////////
        onView(withId(R.id.recycleplaylist)).perform(ViewActions.scrollTo());
        onView(withId(R.id.recycleplaylist)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
        ////////////////////////////////////////////////////////////////////
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
        //onView(ViewMatchers.withId(R.id.recycle)).check(matches(isDisplayed()));
        //isAssignableFrom(HorizontalScrollView.class));*/
    }
}