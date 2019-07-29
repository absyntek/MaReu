package com.example.mareu.ui.meeting_list;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.R;
import com.example.mareu.ui.meeting_list.utils.DeleteViewAction;
import com.example.mareu.ui.new_meeting.NewMeetingActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.mareu.ui.meeting_list.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeetingListActivityTest {

    private static int ITEMS_COUNT = 3;
    private MeetingListActivity mMeetingListActivity;

    @Rule
    public ActivityTestRule<MeetingListActivity> mActivityTestRule =
            new ActivityTestRule<>(MeetingListActivity.class);

    @Before
    public void setUp(){
        mMeetingListActivity = mActivityTestRule.getActivity();
        assertThat(mMeetingListActivity, notNullValue());
    }

    @Test
    public void meetingListActivity_shouldNotBeEmpty() {
        onView(withId(R.id.list)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void meetingListActivity_eleteAction_shouldRemoveItem() {
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT));
        onView(allOf(withId(R.id.list), isDisplayed())).perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT - 1));
    }

    @Test
    public void meetingListActivity_ShowNewOnPressActivity() {
        Intents.init();
        ViewInteraction interaction = onView(withId(R.id.list));
        interaction.perform(actionOnItemAtPosition(0,click()));

        Intents.intended(IntentMatchers.hasComponent(NewMeetingActivity.class.getName()));
    }
}