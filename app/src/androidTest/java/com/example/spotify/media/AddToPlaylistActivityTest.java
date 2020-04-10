package com.example.spotify.media;

import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.spotify.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class AddToPlaylistActivityTest {

    @Rule
    public ActivityTestRule<AddToPlaylistActivity> mact=new ActivityTestRule<AddToPlaylistActivity>(AddToPlaylistActivity.class);
    private AddToPlaylistActivity addToPlaylistActivity = null;

    @Test
    public void views_displayed_test() {
        //ActivityScenario a = ActivityScenario.launch(AddToPlaylistActivity.class);
        //mact.getActivity().getviews();

        addToPlaylistActivity.getviews();
        View v = addToPlaylistActivity.findViewById(R.id.new_playlist_button);
        assertNotNull(v);
        onView(withId(R.id.back_button_to_mediaplayer)).check(matches(isDisplayed()));
        onView(withId(R.id.new_playlist_button)).check(matches(isDisplayed()));


    }

    @Before
    public void setUp() throws Exception {
        addToPlaylistActivity = mact.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        addToPlaylistActivity = null;
    }

//    @Test
//    public void views_displayed1_test() {
//        //ActivityScenario a = ActivityScenario.launch(AddToPlaylistActivity.class);
//        mact.getActivity().getviews();
//        onView(withId(R.id.new_playlist_button)).perform(click());
//        onView(withId(R.id.playlist_name_edit_text)).check(matches(isDisplayed()));
//
//
//    }



}
