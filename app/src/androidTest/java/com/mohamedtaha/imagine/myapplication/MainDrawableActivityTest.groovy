package com.mohamedtaha.imagine.myapplication

import com.google.android.material.navigation.NavigationView
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.RecursiveAction

class MainDrawableActivityTest{
     @Rule
    public MainDrawableActivityTest<MainDrawableActivity> mainDrawableActivityMainDrawableActivityTest =
             new MainDrawableActivityTest(MainDrawableActivity.class);
    @Test
    public void NextThroughNotes(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open);
        onView(withId(R.id.nav_notes)).perform(NavigationViewActions.navigateTo(R.id.nav_notes));
        onView(withId(R.id.LV_list)).perform(RecursiveActions.actionOnITemAtPosition(0,click()));
        List<NoteInfo> noteInfoList = DataManager.getInstance().getNotes();
        for (int i=0; index < noteInfoList.size();i++) {
            NoteInfo note = noteInfoList.get(index);
            onView(withId(R.id.spinner)).check(matches(withSpinnerText(note.getCourse().getTitle())));
            onView(withId(R.id.editTextTitle)).check(matches(withText(note.getTitle())));
            onView(withId(R.id.editTextText)).check(matches(withText(note.getText())));
            if (index <noteInfoList.size() - 1)
            onView(allOf(withId(R.id.action_next),isEnabled())).perform(click());
        }
        onView(withId(R.id.action_next)).check(matches(not(isEnabled())));
        pressBack();

    }
}

































