package com.softwareengineerandroid.davidmata.controller.doctorsconferences;

import android.test.AndroidTestCase;
import android.util.Log;

import com.softwareengineerandroid.davidmata.global.DummieData;
import com.softwareengineerandroid.davidmata.model.database.SQLModel;

import java.util.ArrayList;

/**
 * Created by davidmata on 20/10/2016.
 */

public class TestDataBase extends AndroidTestCase {

    public void testCreateDb() throws Throwable{
        Log.w("DoctorsConferencesTest","testCreateDb");
        mContext.deleteDatabase(SQLModel.DATABASE_NAME);
        SQLModel db = new SQLModel(this.getContext()).open();
        db.open();
        assertEquals(true,db.isOpen());
        db.close();
    }
    public void testUserDb() throws Throwable{
        Log.w("DoctorsConferencesTest","testUserDb");
        DummieData dummieData = new DummieData(mContext);
        dummieData.createUsersInDB();
        SQLModel sqlModel = new SQLModel(mContext);
        sqlModel.open();
        ArrayList<Object[]> listUser = sqlModel.getUserList();
        assertEquals("david@gmail.com",listUser.get(0)[1].toString());
        assertEquals("crossover@gmail.com",listUser.get(1)[1].toString());
        assertEquals("jhony@gmail.com",listUser.get(2)[1].toString());
        assertEquals("omar@gmail.com",listUser.get(3)[1].toString());
        assertEquals("daniel@gmail.com",listUser.get(4)[1].toString());
        assertEquals("angel@gmail.com",listUser.get(5)[1].toString());
        assertEquals("ram@gmail.com",listUser.get(6)[1].toString());
        assertEquals("michel@gmail.com",listUser.get(7)[1].toString());
        assertEquals("noadmin@gmail.com",listUser.get(8)[1].toString());
        sqlModel.close();
    }

    public void testConferenceDb() throws Throwable{
        Log.w("DoctorsConferencesTest","testConferenceDb");
        DummieData dummieData = new DummieData(mContext);
        dummieData.createConferencesInDB();
        SQLModel sqlModel = new SQLModel(mContext);
        sqlModel.open();
        ArrayList<Object[]> listConference = sqlModel.getConferenceData();
        assertEquals("Emergency Medicine Research Course",listConference.get(0)[2].toString());
        assertEquals("8th European Neurology Congress",listConference.get(1)[2].toString());
        assertEquals("MSc / PGDip Musculoskeletal Medicine",listConference.get(2)[2].toString());
        assertEquals("Cycling CME Mountain Bike Western Colorado",listConference.get(3)[2].toString());
        assertEquals("ADAHIC — 2nd Abu Dhabi Ambulatory Healthcare International Congress",listConference.get(4)[2].toString());
        assertEquals("Targeting Microbiota 2016 — 4th World Congress on Targeting Microbiota",listConference.get(5)[2].toString());
        assertEquals("4th International Congress on Translational Medicine",listConference.get(6)[2].toString());
        assertEquals("Primary Care Internal Medicine",listConference.get(7)[2].toString());
        assertEquals("Transplant Nurses Association National Conference",listConference.get(8)[2].toString());
        assertEquals(" RareX — Rare Disease Expo 2016",listConference.get(9)[2].toString());
        assertEquals("RareX — RareX Rare Diseases Conference ft. ICORD",listConference.get(10)[2].toString());
        assertEquals("Training — Smoking Cessation in Mental Health",listConference.get(11)[2].toString());


    }


}
