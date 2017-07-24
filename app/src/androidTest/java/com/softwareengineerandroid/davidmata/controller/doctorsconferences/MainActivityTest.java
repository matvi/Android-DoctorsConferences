package com.softwareengineerandroid.davidmata.controller.doctorsconferences;

import android.support.v7.view.menu.MenuBuilder;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.MenuItem;

import com.softwareengineerandroid.davidmata.global.GlobalData;

/**
 * Created by davidmata on 20/10/2016.
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * For a Admin user the item for Create conference is visible
     */
    @SmallTest
    public void testMenuCreateConferenceVisible(){
        GlobalData.privileges=1;
        MenuBuilder builder = new MenuBuilder(getActivity());
        getActivity().onCreateOptionsMenu(builder);
        MenuItem item = builder.findItem(R.id.action_addConference);
        assertNotNull(item);
        assertEquals(true,item.isVisible());
        getActivity().finish();
    }

    /**
     * For not Admin users the item for Create conferences is not visible
     */
    @SmallTest
    public void testMenuCreateConferenceNoVisible(){
        GlobalData.privileges=0;
        MenuBuilder builder = new MenuBuilder(getActivity());
        getActivity().onCreateOptionsMenu(builder);
        MenuItem item = builder.findItem(R.id.action_addConference);
        assertNotNull(item);
        assertEquals(false,item.isVisible());
        getActivity().finish();

    }

}
