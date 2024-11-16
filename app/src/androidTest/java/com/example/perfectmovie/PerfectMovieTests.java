package com.example.perfectmovie;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

public class PerfectMovieTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void recyclerView_recyclerViewWithAuthors_isDisplayedReturn() {
        Espresso.onView(ViewMatchers.withId(R.id.recycleWait))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withId(R.id.actors)).perform(click());
        Espresso.onView(withId(R.id.recycleActors)).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerView_recyclerViewWithTrailers_isDisplayedReturn() {
        Espresso.onView(ViewMatchers.withId(R.id.recycleWait))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withId(R.id.gototr)).perform(click());
        Espresso.onView(withId(R.id.recycleVid)).check(matches(isDisplayed()));
    }

    @Test
    public void FirstFilmTrailer_FirstElementInRecyclerViewWithTrailers_isDisplayedReturn() {
        Espresso.onView(withId(R.id.bott_top)).perform(click());

        Espresso.onView(ViewMatchers.withId(R.id.recycleWait))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));

        Espresso.onView(withId(R.id.gototr)).perform(click());

        Espresso.onView(ViewMatchers.withId(R.id.recycleVid))
                    .perform(waitFor(7000),RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test(timeout = 10)
    public void recyclerView_recyclerViewWithAuthors_NoMatchingViewExceptionReturn() throws NoMatchingViewException {
        assertThrows(NoMatchingViewException.class, () -> {Espresso.onView(withId(R.id.recycleActors)).check(matches(isDisplayed()));
        });
    }

    @Ignore
    @Test
    public void recyclerView_recyclerViewWithTrailers_NoMatchingViewExceptionReturn() throws NoMatchingViewException {
        assertThrows(NoMatchingViewException.class, () -> {Espresso.onView(withId(R.id.recycleVid)).check(matches(isDisplayed()));
        });
    }


    public static ViewAction waitFor(long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(View.class);
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }
}