package com.example.spotify.Fragments.CATEGORY_PLAYLISTS;
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
public class categoryplaylistTest<introact>
{
    MainActivity introact;
    @Rule
    public ActivityTestRule<MainActivity> mact=new ActivityTestRule<>(MainActivity.class);
    @Test
    public void CATEGORY()
    {

        try {
            sleep(70);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation_search)).perform(click());
        onView(withId(R.id.text_search)).check(matches(isDisplayed()));
        onView(withId(R.id.text_search)).check(isAbove(withId(R.id.searchImg)));
        onView(withId(R.id.searchImg)).check(isAbove(withId(R.id.textcategor)));

        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(ViewMatchers.withId(R.id.recycleCategory)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.textcategorplaylist)).check(matches(isDisplayed()));
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.textcategorplaylist)).check(isAbove(withId(R.id.recycleCategoryPlaylist1)));
        onView(ViewMatchers.withId(R.id.recycleCategoryPlaylist1)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.playlist_name_middle)).check(matches(isDisplayed()));
        //playlist_name_playlist_fragment



        //onView(ViewMatchers.withId(R.id.recycleCategory)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        //onView(ViewMatchers.withId(R.id.recycleCategory)).perform(RecyclerViewActions.scrollToHolder((Matcher<RecyclerView.ViewHolder>) matches(isDisplayed())));

    }


}