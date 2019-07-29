package com.example.mareu.ui.meeting_list;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.R;
import com.example.mareu.ui.meeting_list.utils.DeleteViewAction;
import com.example.mareu.ui.new_meeting.NewMeetingActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.mareu.ui.meeting_list.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeetingListActivityTest {

    private static int ITEMS_COUNT = 4;

    @Rule
    public ActivityTestRule<MeetingListActivity> mActivityTestRule =
            new ActivityTestRule<>(MeetingListActivity.class);

    @Before
    public void setUp(){
        MeetingListActivity meetingListActivity = mActivityTestRule.getActivity();
        assertThat(meetingListActivity, notNullValue());
    }

    @Test
    public void meetingListActivity_shouldNotBeEmpty() {
        onView(withId(R.id.list)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void meetingListActivity_deleteAction_shouldRemoveItem() {
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

    @Test
    public void add_New_Meeting(){
        ViewInteraction interaction = onView(withId(R.id.fab));
        interaction.perform(click());

        ViewInteraction tvTuto = onView(Matchers.allOf(withId(R.id.tvTuto),isDisplayed()));
        ViewInteraction tvAddMail = onView(Matchers.allOf(withId(R.id.tvAddEmail),isDisplayed()));
        ViewInteraction fabAddMail = onView(Matchers.allOf(withId(R.id.fabAddEmail),isDisplayed()));
        ViewInteraction tvTime = onView(Matchers.allOf(withId(R.id.tvMeetingTime),isDisplayed()));
        ViewInteraction buttonValid = onView(Matchers.allOf(withId(R.id.btnValidNewMeeting),isDisplayed()));

        tvTuto.perform(replaceText("test"));
        tvTime.perform(replaceText("17:15"));

        tvAddMail.perform(replaceText("test0@test.fr"));
        fabAddMail.perform(click());
        tvAddMail.perform(replaceText("test1@test.fr"));
        fabAddMail.perform(click());
        tvAddMail.perform(replaceText("test2@test.fr"));
        fabAddMail.perform(click());

        buttonValid.perform(click());

        onView(withId(R.id.list)).check(withItemCount(ITEMS_COUNT + 1));
    }
}