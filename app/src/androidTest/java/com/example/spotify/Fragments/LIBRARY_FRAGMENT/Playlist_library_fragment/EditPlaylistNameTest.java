package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Playlist_library_fragment;

import android.view.View;

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
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
public class EditPlaylistNameTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void checkActivity() {
        OpenEditPlaylistNameActivity();
        onView(withId(R.id.cancel_edit_playlist_name)).check(matches(isDisplayed()));
        onView(withId(R.id.playlist_new_name_edit_text)).check(matches(isDisplayed()));
        check_cancel_button();
        empty_input_is_not_accepeted_test();
        check_changing_playlist_name();


    }

    private void check_cancel_button() {
        onView(withId(R.id.cancel_edit_playlist_name)).check(matches(isDisplayed()));
        onView(withId(R.id.cancel_edit_playlist_name)).perform(click());
        onView(withId(R.id.library_playlist_name)).check(matches(withText("Create playlist")));
        openAgain();
        onView(withId(R.id.playlist_new_name_edit_text)).check(matches(isDisplayed()));
    }

    public void empty_input_is_not_accepeted_test() {
        onView(withId(R.id.playlist_new_name_edit_text)).perform(clearText(), pressImeActionButton());
        onView(withId(R.id.playlist_new_name_edit_text)).check(matches(isDisplayed()));
    }

    public void check_changing_playlist_name(){
        onView(withId(R.id.playlist_new_name_edit_text)).perform(clearText(), typeText("playlist1") , pressImeActionButton());
        onView(withId(R.id.library_playlist_name)).check(matches(withText("Create playlist")));
    }


    private void openAgain() {
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

        onView(withId(R.id.settings_edit_name)).perform(click());
    }

    /**
     * this functions assumes thar the first playlist in the library is a created playlist
     */
    private void OpenEditPlaylistNameActivity() {
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

        onView(withId(R.id.settings_edit_name)).perform(click());

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