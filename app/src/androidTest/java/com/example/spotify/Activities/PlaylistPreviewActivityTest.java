package com.example.spotify.Activities;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.spotify.R;
import com.example.spotify.login.IntroActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
public class PlaylistPreviewActivityTest {

    @Rule
    public ActivityTestRule<IntroActivity> mact=new ActivityTestRule<>(IntroActivity.class);

    @Test
    public void views_displayed_test() {

        greeterSaysHello();

        onView(withId(R.id.recycleplaylist)).perform(ViewActions.scrollTo());
        onView(withId(R.id.recycleplaylist))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.preview_button)).check(matches(isDisplayed()));
        onView(withId(R.id.preview_button)).perform(click());


        onView(withId(R.id.back_arrow_from_preview)).check(matches(isDisplayed()));
        onView(withId(R.id.back_arrow_from_preview)).perform(click());

        onView(withId(R.id.preview_button)).check(matches(isDisplayed()));




    }

    public void greeterSaysHello()
    {
        //startActivity(new Intent(introact.getBaseContext(), IntroActivity.class));
        onView(withId(R.id.introya)).perform(click());
        onView(withId(R.id.email)).perform(typeText("hager7@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("hager"));
        onView(withId(R.id.loginButton)).perform(click());
        try {
            sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
    }
}