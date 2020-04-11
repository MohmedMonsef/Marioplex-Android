package com.example;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
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
import static androidx.test.espresso.assertion.PositionAssertions.isAbove;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;


@RunWith(AndroidJUnit4.class)

public class testfragmentreplaced<introact>

{

IntroActivity introact;
@Rule
public ActivityTestRule<IntroActivity> mact=new ActivityTestRule<>(IntroActivity.class);

@Test
public void textIntroUI()
{
        onView(withId(R.id.login_facebook_button)).check(isAbove(withId(R.id.introya)));
        onView(withId(R.id.signup)).check(isAbove(withId(R.id.login_facebook_button)));
}

@Test
public void textLoginUI()
{

        textIntroUI();
        onView(withId(R.id.introya)).perform(click());

        onView(withId(R.id.emailtext)).check(isAbove(withId(R.id.email)));
        onView(withId(R.id.email)).check(isAbove(withId(R.id.passwordtext)));
        onView(withId(R.id.passwordtext)).check(isAbove(withId(R.id.password)));
        onView(withId(R.id.password)).check(isAbove(withId(R.id.loginButton)));

}

    public void greeterSaysHello()
    {


        //startActivity(new Intent(introact.getBaseContext(), IntroActivity.class));
        onView(withId(R.id.introya)).perform(click());
        onView(withId(R.id.email)).perform(typeText("lenaa@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("lenaa"));
        onView(withId(R.id.loginButton)).perform(click());
    try {
        sleep(15000);
        }
    catch (InterruptedException e)
        {
        e.printStackTrace();
        }
onView(withId(R.id.navigation)).check(matches(isDisplayed()));
//onView(withText("Hello Steve!")).check(matches(isDisplayed()));
    }

//    @Test
//    public void HOME()
//    {
//        greeterSaysHello();
//    //onView(withId(R.id.recycle)).check((ViewAssertion) allOf(isAssignableFrom(ScrollView.class)));
//   // onView(withId(R.id.recycle)).perform(RecyclerView.a)
//
//    //isAssignableFrom(HorizontalScrollView.class));
//    }


    @Test
    public void HOME()
    {
        greeterSaysHello();
//        onView(withId(R.id.recycle)).check((ViewAssertion) allOf(isAssignableFrom(ScrollView.class)));
        // onView(withId(R.id.recycle)).perform(RecyclerView.a)
//onView(ViewMatchers.withId(R.id.recycle)).perform(RecyclerViewActions.scrollToHolder((Matcher<RecyclerView.ViewHolder>) matches(isDisplayed())));
        onView(ViewMatchers.withId(R.id.recycle)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
///////////////////////////////////////////////
        try
        {
            sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
////////////////////////////////////////////////
        onView(ViewMatchers.withId(R.id.recyclealbum)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));
        onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
//////////////////////******************************///////////////////////////////
        try
        {
            sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
////////////////////////////////////////////////
        onView(withId(R.id.recycleplaylist)).perform(ViewActions.scrollTo());
        onView(withId(R.id.recycleplaylist)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.shuffle_play_button)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));

///////////////////////////////////////////////////////////////////////
        try
        {
            sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
////////////////////////////////////////////////
        onView(withId(R.id.recycleartist)).perform(ViewActions.scrollTo());
        onView(withId(R.id.recycleartist)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));

        //  onView(withId(R.id.navigation)).check(matches(isDisplayed()));
        ///////////////////////////////////////////////
        //onView(withId(R.id.action_settings)).perform(click());
        //onView(withId(R.id.profile_picture)).check(matches(isDisplayed()));
        /////////////////////////////////////////////////////////////
        //onView(withId(R.id.recycle)).perform(ViewActions.scrollTo());
        //onView(withId(R.id.recycle)).perform(RecyclerViewActions.actionOnItemAtPosition(7, click()));
        //onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
//////////////////////////////////////
        //onView(withId(R.id.navigation_home)).perform(click());
        //onView(withId(R.id.navigation)).check(matches(isDisplayed()));
///////////////////////////////////////////////////////////////////////
        // onView(withText("Launch")).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
        //Espresso.onView(ViewMatchers.withId(R.id.recyclealbum)).perform(ViewActions.swipeUp());
        //////////////////////////////////////////////
        //onView(withId(R.id.recyclealbum)).perform(ViewActions.scrollTo());
        //onView(withId(R.id.recyclealbum)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
        //////////////////////////////////////
        /*onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
///////////////////////////////////////////////////////////////////////
        onView(withId(R.id.recycleartist)).perform(ViewActions.scrollTo());
        onView(withId(R.id.recycleartist)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
        //////////////////////////////////////
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
///////////////////////////////////////////////////////////////////////
        onView(withId(R.id.recycleplaylist)).perform(ViewActions.scrollTo());
        onView(withId(R.id.recycleplaylist)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.text_newRelease)).check(matches(isDisplayed()));
        ////////////////////////////////////////////////////////////////////
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
        //onView(ViewMatchers.withId(R.id.recycle)).check(matches(isDisplayed()));
        //isAssignableFrom(HorizontalScrollView.class));*/
    }
    @Test
    public void CATEGORY()
    {
        greeterSaysHello();
        onView(withId(R.id.navigation_search)).perform(click());
        onView(withId(R.id.text_search)).check(matches(isDisplayed()));
        onView(withId(R.id.text_search)).check(isAbove(withId(R.id.searchImg)));
        onView(withId(R.id.searchImg)).check(isAbove(withId(R.id.textcategor)));
        //onView(ViewMatchers.withId(R.id.recycleCategory)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        //onView(ViewMatchers.withId(R.id.recycleCategory)).perform(RecyclerViewActions.scrollToHolder((Matcher<RecyclerView.ViewHolder>) matches(isDisplayed())));

    }
    @Test
    public void SEARCH()
    {greeterSaysHello();
        onView(withId(R.id.navigation_search)).perform(click());
        onView(withId(R.id.text_search)).check(matches(isDisplayed()));
        onView(withId(R.id.searchImg)).perform(click());
        onView(withId(R.id.textartist)).check(isAbove(withId(R.id.texttrack)));
        onView(withId(R.id.texttrack)).check(isAbove(withId(R.id.textplaylist)));
        onView(withId(R.id.textplaylist)).check(isAbove(withId(R.id.textalbums)));

        onView(withId(R.id.searchbarlist)).check(isAbove(withId(R.id.texta)));
        try {
            sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        onView(withId(R.id.searchbarlist)).perform(typeText("n"));
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
        onView(withId(R.id.searchbarlist)).perform(typeText("d"));
        try {
            sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        onView(withId(R.id.searchbarlist)).perform(typeText("a"));
        try {
            sleep(70000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }





    }



    @Test
    public void buttombar()
    {
        greeterSaysHello();
        onView(withId(R.id.navigation_search)).perform(click());
        onView(withId(R.id.text_search)).check(matches(isDisplayed()));
        /////////////////////////
        onView(withId(R.id.navigation_library)).perform(click());
        onView(withId(R.id.text_library)).check(matches(isDisplayed()));
        ////////////////////////
        onView(withId(R.id.navigation_premium)).perform(click());
        onView(withId(R.id.text_premium)).check(matches(isDisplayed()));
        /////////////////////////
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
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
