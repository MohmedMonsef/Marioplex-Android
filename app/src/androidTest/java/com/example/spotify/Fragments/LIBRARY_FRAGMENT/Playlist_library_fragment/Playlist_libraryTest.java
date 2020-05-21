package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Playlist_library_fragment;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Fragments.LIBRARY_FRAGMENT.libraryFragment;
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
public class Playlist_libraryTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testingFragmentTransaction() {

        // Then use Espresso to test the Fragment
        OpenPlaylistFragment();
        //onView(withId(R.id.iv_record_image)).check(matches(isDisplayed()));
        onView(withId(R.id.library_playlist_image)).check(matches(isDisplayed()));
        delay(5000);
    }

    @Test
    public void testingCreatePlaylistButton(){
        OpenPlaylistFragment();
        onView(withId(R.id.library_create_playliste_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.library_create_playliste_layout)).perform(click());
        onView(withId(R.id.playlist_name_edit_text)).check(matches(isDisplayed()));
    }
    private void OpenPlaylistFragment() {
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                libraryFragment voiceFragment = startPlaylistFragment();
            }
        });
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