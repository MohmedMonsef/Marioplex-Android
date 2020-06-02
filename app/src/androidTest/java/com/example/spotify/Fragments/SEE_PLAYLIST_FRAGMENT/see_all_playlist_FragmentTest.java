package com.example.spotify.Fragments.SEE_PLAYLIST_FRAGMENT;
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

public class see_all_playlist_FragmentTest<introact>
{
    MainActivity introact;
    @Rule
    public ActivityTestRule<MainActivity> mact=new ActivityTestRule<>(MainActivity.class);

    @Test
    public void SEARCHPlaylist()
    {

        try {
            sleep(70);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation_search)).perform(click());
        onView(withId(R.id.text_search)).check(matches(isDisplayed()));
        try {
            sleep(70);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.searchImg)).perform(click());
        onView(withId(R.id.textartist)).check(isAbove(withId(R.id.texttrack)));
        onView(withId(R.id.texttrack)).check(isAbove(withId(R.id.textplaylist)));
        onView(withId(R.id.textplaylist)).check(isAbove(withId(R.id.textalbums)));
        onView(withId(R.id.searchbarlist)).check(isAbove(withId(R.id.texta)));
        try {
            sleep(70);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.searchbarlist)).perform(typeText("Q"));
        try {
            sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.searchbarlist)).perform(typeText("u"));
        try {
            sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.searchbarlist)).perform(typeText("r"));
        try {
            sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.searchbarlist)).perform(typeText("a"));
        try {
            sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.searchbarlist)).perform(typeText("n"));
        try {
            sleep(35000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(ViewMatchers.withId(R.id.recycleSearch)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        try {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.playlist_name_middle)).check(matches(isDisplayed()));
        //onView(withId(R.id.textalbums)).perform(click());



        // onView(withId(R.id.textalbums)).perform(click());

    }

    @Test
    public void SEARCH_SEE_Playlist()
    {

        try {
            sleep(70);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation_search)).perform(click());
        onView(withId(R.id.text_search)).check(matches(isDisplayed()));
        try {
            sleep(70);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.searchImg)).perform(click());
        onView(withId(R.id.textartist)).check(isAbove(withId(R.id.texttrack)));
        onView(withId(R.id.texttrack)).check(isAbove(withId(R.id.textplaylist)));
        onView(withId(R.id.textplaylist)).check(isAbove(withId(R.id.textalbums)));
        onView(withId(R.id.searchbarlist)).check(isAbove(withId(R.id.texta)));
        try {
            sleep(70);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.searchbarlist)).perform(typeText("Quran"));
        onView(withId(R.id.textplaylist)).perform(click());
        try {
            sleep(35000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.recycleSearchSeeAllPlaylist)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.recycleSearchSeeAllPlaylist)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        try {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.playlist_name_middle)).check(matches(isDisplayed()));

    }

}