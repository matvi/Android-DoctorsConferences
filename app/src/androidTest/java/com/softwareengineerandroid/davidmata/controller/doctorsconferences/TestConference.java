package com.softwareengineerandroid.davidmata.controller.doctorsconferences;

import android.test.AndroidTestCase;
import android.util.Log;

import com.softwareengineerandroid.davidmata.global.QueryUtils;
import com.softwareengineerandroid.davidmata.model.conference.Conference;
//Log.w("DoctorsConferencesTest","");
/**
 * Created by davidmata on 20/10/2016.
 */

public class TestConference extends AndroidTestCase {

    public void testConference(){
        Log.w("DoctorsConferencesTest","testConference");
        Conference conference = QueryUtils.getBuidConference(0,7.0,"Emergency Medicine Research Course","This course is a 5 month course that will provide students with a thorough understanding of Evidence Based Medicine (EBM) and Research Methods (RM) in the emergency medicine setting","Melbourne, Australia",1476637176);
        assertEquals(conference.getId(),0);
        assertEquals(conference.getImportance(),7.0);
        assertEquals(conference.getBody(),"This course is a 5 month course that will provide students with a thorough understanding of Evidence Based Medicine (EBM) and Research Methods (RM) in the emergency medicine setting");
        assertEquals(conference.getTitle(),"Emergency Medicine Research Course");
        assertEquals(conference.getLocation(),"Melbourne, Australia");
        assertEquals(conference.getTimeInMilliseconds(),1476637176);
    }
}
