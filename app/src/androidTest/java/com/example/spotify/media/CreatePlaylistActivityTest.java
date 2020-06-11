package com.example.spotify.media;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Fragments.LIBRARY_FRAGMENT.libraryFragment;
import com.example.spotify.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CreatePlaylistActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule=new ActivityTestRule<>(MainActivity.class);


    @Test
    public void views_displayed_test() {

        OpenPlaylistFragment();
        onView(withId(R.id.library_create_playliste_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.library_create_playliste_layout)).perform(click());

        onView(withId(R.id.playlist_name_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.cancel_create_playlist)).check(matches(isDisplayed()));

        empty_input_is_not_accepeted_test();
        cancel_test();
    }

    public void empty_input_is_not_accepeted_test() {

        onView(withId(R.id.playlist_name_edit_text)).perform(clearText(), pressImeActionButton());
        onView(withId(R.id.playlist_name_edit_text)).check(matches(isDisplayed()));

    }


    public void cancel_test() {

        onView(withId(R.id.cancel_create_playlist)).perform(click());
        onView(withId(R.id.library_playlist_name)).check(matches(withText("Create playlist")));

    }

    private void OpenPlaylistFragment() {
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                libraryFragment voiceFragment = startPlaylistFragment();
            }
        });
    }


    private libraryFragment startPlaylistFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        libraryFragment playlistFrag = new libraryFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, playlistFrag).commit();

        return playlistFrag;
    }

}