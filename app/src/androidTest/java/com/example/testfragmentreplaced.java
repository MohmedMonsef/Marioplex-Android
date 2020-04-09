package com.example;

import android.app.Activity;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.spotify.Fragments.HOME_FRAGMENT.backhome;
import com.example.spotify.Fragments.SEARCH_FRAGMENT.searchfragment;
import com.example.spotify.Fragments.LIBRARY_FRAGMENT.libraryFragment;
import com.example.spotify.Fragments.PREMIUM_FRAGMENT.premiumFragment;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.R;
import com.example.spotify.login.IntroActivity;
import com.example.spotify.media.MediaPlayerService;
import com.example.spotify.media.TrackInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.PendingIntent.getActivity;
import static androidx.core.content.ContextCompat.startActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.addGlobalAssertion;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isAbove;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.test.rule.ActivityTestRule;

import com.example.spotify.Fragments.HOME_FRAGMENT.backhome;
import com.example.spotify.Fragments.SEARCH_FRAGMENT.searchfragment;
import com.example.spotify.Fragments.LIBRARY_FRAGMENT.libraryFragment;
import com.example.spotify.Fragments.PREMIUM_FRAGMENT.premiumFragment;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.R;
import com.example.spotify.media.MediaPlayerService;
import com.example.spotify.media.TrackInfo;

import kotlin.jvm.JvmField;


@RunWith(AndroidJUnit4.class)

public class testfragmentreplaced<introact>
{
IntroActivity introact;


@Rule
public ActivityTestRule<IntroActivity> mact=new ActivityTestRule<>(IntroActivity.class);

@Test
public void textUI()
{
 onView(withId(R.id.login_facebook_button)).check(isAbove(withId(R.id.introya)));
}
@Test
public void buttombar()
{
    greeterSaysHello();
    onView(withId(R.id.navigation_search)).perform(click());
    onView(withId(R.id.text_search)).check(matches(isDisplayed()));


}


@Test
    public void greeterSaysHello() {


        //startActivity(new Intent(introact.getBaseContext(), IntroActivity.class));

        onView(withId(R.id.introya)).perform(click());
    onView(withId(R.id.email)).perform(typeText("lenaa@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("lenaa"));
        onView(withId(R.id.loginButton)).perform(click());
    try {
        sleep(70000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
onView(withId(R.id.navigation)).check(matches(isDisplayed()));
//onView(withText("Hello Steve!")).check(matches(isDisplayed()));
    }
/*@Test
    public void basicLifecycle() throws Throwable {
      //  final FragmentManager fm = mActivity.getSupportFragmentManager();
         backhome strictFragment;
         //= new backhome();
        // Add fragment; StrictFragment will throw if it detects any violation
        // in standard lifecycle method ordering or expected preconditions.
        //fm.beginTransaction().replace(R.id.frame_fragment,strictFragment).commit();
        //executePendingTransactions(fm);
         strictFragment= (backhome) mActivity.loadFragment( new backhome());
//        assertFalse("fragment is detached", strictFragment.isDetached());
  //      assertTrue("fragment is not resumed", strictFragment.isResumed());
        // Test removal as well; StrictFragment will throw here too.
        //fm.beginTransaction().remove(strictFragment).commit();
        //executePendingTransactions(fm);
        assertTrue("fragment is added", strictFragment.isInLayout());
    //    assertFalse("fragment is resumed", strictFragment.isResumed());
        // This one is perhaps counterintuitive; "detached" means specifically detached
        // but still managed by a FragmentManager. The .remove call above
        // should not enter this state.
        assertFalse("fragment is detached", strictFragment.isDetached());
    }


    private void executePendingTransactions(final FragmentManager fm) throws Throwable {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fm.executePendingTransactions();
            }
        });
    }
/*
//ButtonNavigationView   buNewFragment;
@Test public void func1() {
    with(launchFragment < backhome > ()) {
        onFragment {
            fragment ->
                    assertThat(fragment.dialog).isNotNull()
            assertThat(fragment.requireDialog().isShowing).isTrue()
            fragment.dismiss()
            fragment.requireFragmentManager().executePendingTransactions()
            assertThat(fragment.dialog).isNull()
        }
    }
}
*/
   /* @Override
    protected void setUp() throws Exception {
        super.setUp();
        //setActivityInitialTouchMode(false);
        mActivity = new MainActivity();
        buNewFragment =(ButtonNavigationView) mActivity.findViewById(R.id.navigation);
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                buNewFragment .performClick();
            }
        });
        sleep(500);//simple Thread.sleep() method
    }

    @UiThreadTest
    public void testNewFragmentScrollView() {
        assertTrue(mActivity.getFragmentType().equals(FragmentTypes.FRAGMENT_NEW));
      // mActivity.findViewById();
    }

*/

}
