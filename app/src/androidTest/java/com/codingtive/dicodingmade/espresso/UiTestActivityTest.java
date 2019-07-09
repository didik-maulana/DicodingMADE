package com.codingtive.dicodingmade.espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.codingtive.dicodingmade.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class UiTestActivityTest {
    @Rule
    public ActivityTestRule<UiTestActivity> activityRule = new ActivityTestRule<>(UiTestActivity.class);

    @Test
    public void ensureTextChangeWork() {
        onView(withId(R.id.edt_title)).perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(R.id.btn_change)).perform(click());
        onView(withId(R.id.tv_title)).check(matches(withText("Lalala")));
    }

    @Test
    public void ensureMoveActivityWithData() {
        onView(withId(R.id.edt_title)).perform(typeText("Become GDE at 2020"), closeSoftKeyboard());
        onView(withId(R.id.btn_move)).perform(click());
        onView(withId(R.id.tv_detail_title)).check(matches(withText("Become GDE at 2020")));
    }
}
