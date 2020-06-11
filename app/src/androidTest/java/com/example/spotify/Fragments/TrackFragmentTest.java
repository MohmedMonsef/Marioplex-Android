///*
//package com.example.spotify.Fragments;
//
//import androidx.test.espresso.action.ViewActions;
//import androidx.test.espresso.contrib.RecyclerViewActions;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.rule.ActivityTestRule;
//
//import com.example.spotify.Activities.MainActivity;
//import com.example.spotify.Fragments.SEARCH_LIST_FRAGMENT.searchListfragment;
//import com.example.spotify.R;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.closeSoftKeyboard;
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.clearText;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static java.lang.Thread.sleep;
//
//@RunWith(AndroidJUnit4.class)
//public class TrackFragmentTest {
//
//    @Rule
//    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
//
//    @Test
//    public void testingFragmentTransaction() {
//        OpenTrackFragment();
//        onView(withId(R.id.track_name_middle)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
//        onView(withId(R.id.add_to_playlist_button)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
//        onView(withId(R.id.shuffle_play_button_track)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
//        onView(withId(R.id.artist_name_song_name_track)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
//        onView(withId(R.id.track_image_track_fragment)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
//        CheckButtons();
//    }
//
//
//    private void CheckButtons(){
//
//        onView(withId(R.id.like_track)).perform(click());
//        delay(4000);
//        onView(withId(R.id.like_track)).perform(click());
//        delay(1000);
//        onView(withId(R.id.add_to_playlist_button)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
//        onView(withId(R.id.add_to_playlist_button)).perform(ViewActions.scrollTo()).perform(click());
//        delay(1000);
//        onView(withId(R.id.back_button_to_mediaplayer)).check(matches(isDisplayed()));
//        onView(withId(R.id.back_button_to_mediaplayer)).perform(click());
//        delay(1000);
//        onView(withId(R.id.track_image_track_fragment)).check(matches(isDisplayed()));
//        onView(withId(R.id.shuffle_play_button_track)).perform(ViewActions.scrollTo()).perform(click());
//        delay(4000);
//        onView(withId(R.id.back_arrow_track)).check(matches(isDisplayed()));
//        onView(withId(R.id.back_arrow_track)).perform(click());
//        onView(withId(R.id.searchImg)).check(matches(isDisplayed()));
//
//    }
//
//    private void OpenTrackFragment() {
//        activityRule.getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                searchListfragment voiceFragment = startSearchFragment();
//            }
//        });
//        onView(withId(R.id.searchbarlist)).perform(clearText(), typeText("track28"));
//        closeSoftKeyboard();
//        delay(10000);
//
//        onView(withId(R.id.recycleSearch)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
//        delay(10000);
//    }
//
//    private void delay(int milliseconds) {
//        try {
//            sleep(milliseconds);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private searchListfragment startSearchFragment() {
//        MainActivity activity = (MainActivity) activityRule.getActivity();
//        searchListfragment searchFragment = new searchListfragment();
//        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, searchFragment).commit();
//
//        return searchFragment;
//    }
//}*/
