package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Playlist_library_fragment;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Fragments.LIBRARY_FRAGMENT.libraryFragment;
import com.example.spotify.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
public class Playlist_Library_SettingsTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    private libraryFragment frag ;
    @Test
    public void testingAvtivityLaunch(){
        OpenPlaylistFragmentSettings();
        onView(withId(R.id.setting_playlist_image)).check(matches(isDisplayed()));
        onView(withId(R.id.playlist_name_settings)).check(matches(isDisplayed()));
        //go back
        onView(withId(R.id.settings_playlist_library_arrow)).check(matches(isDisplayed()));
        onView(withId(R.id.settings_playlist_library_arrow)).perform(click());
        delay(5000);
        //onView(withId(R.id.settings_library)).check(matches(isDisplayed()));
        testDelete();

    }


    public void testDelete(){
        //create playlist the delete it
        onView(withId(R.id.library_create_playliste_layout)).perform(click());
        onView(withId(R.id.playlist_name_edit_text)).perform(clearText(), typeText("test2")  , pressImeActionButton());
        delay(10000);
        OpenPlaylistFragment();
        delay(10000);
        RecyclerView recyclerView = activityRule.getActivity().findViewById(R.id.library_playlist_recycleview);
        int itemCount = recyclerView.getAdapter().getItemCount();

        //recyclerView.scrollToPosition(itemCount-1);
        onView(withText("test2")).perform(scrollTo());
        onView(withId(R.id.library_playlist_recycleview))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition(
                                itemCount-1,
                                new ViewAction() {
                                    @Override
                                    public Matcher<View> getConstraints() {
                                        return null;
                                    }

                                    @Override
                                    public String getDescription() {
                                        return "Click on specific button";
                                    }

                                    @Override
                                    public void perform(UiController uiController, View view) {
                                        View Image = view.findViewById(R.id.settings_library);
                                        // Maybe check for null
                                        Image.performClick();
                                    }
                                })
                );
        onView(withId(R.id.settings_delete)).perform(click());
        delay(6000);
        onView(withId(R.id.library_playlist_name)).check(matches(withText("Create playlist")));

    }

    private void OpenPlaylistFragment() {
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                 frag = startPlaylistFragment();
            }
        });
    }

    private void OpenPlaylistFragmentSettings() {
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                libraryFragment frag = startPlaylistFragment();
            }
        });

        delay(20000);
        onView(withId(R.id.library_playlist_recycleview))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition(
                                0,
                                new ViewAction() {
                                    @Override
                                    public Matcher<View> getConstraints() {
                                        return null;
                                    }

                                    @Override
                                    public String getDescription() {
                                        return "Click on specific button";
                                    }

                                    @Override
                                    public void perform(UiController uiController, View view) {
                                        View Image = view.findViewById(R.id.settings_library);
                                        // Maybe check for null
                                        Image.performClick();
                                    }
                                })
                );

    }

    private void delay(int milliseconds) {
        try {
            sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private libraryFragment startPlaylistFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        libraryFragment playlistFrag = new libraryFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, playlistFrag).commit();

        return playlistFrag;
    }
}