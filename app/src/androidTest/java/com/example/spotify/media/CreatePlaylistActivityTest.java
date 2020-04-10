package com.example.spotify.media;

import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.spotify.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class CreatePlaylistActivityTest {

    @Test
    public void views_displayed_test() {
        ActivityScenario a = ActivityScenario.launch(CreatePlaylistActivity.class);
        onView(withId(R.id.playlist_name_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.cancel_create_playlist)).check(matches(isDisplayed()));
        //onView(withId(R.id.create_text)).check(matches(isDisplayed()));

        //onView(withId(R.id.create_text)).check(matches(withText("Give your playlist a name")));


    }
    @Test
    public void empty_input_is_not_accepeted_test() {
        ActivityScenario a = ActivityScenario.launch(CreatePlaylistActivity.class);
        onView(withId(R.id.playlist_name_edit_text)).perform(clearText(), pressImeActionButton());
        onView(withId(R.id.playlist_name_edit_text)).check(matches(isDisplayed()));

    }



}