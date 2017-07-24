package com.softwareengineerandroid.davidmata.global;

import android.content.Context;

import com.softwareengineerandroid.davidmata.model.database.SQLModel;

/**
 * Created by davidmata on 16/10/2016.
 */

public class DummieData {
    private Context context;
    private SQLModel sqlModel;

    public DummieData(Context context){
        this.context = context;
    }


    public void createUsersInDB(){
        sqlModel = new SQLModel(context);
        sqlModel.open();
        sqlModel.createUser("david@gmail.com","123",1);
        sqlModel.createUser("dalis@gmail.com","123",1);
        sqlModel.createUser("jhony@gmail.com","123",0);
        sqlModel.createUser("omar@gmail.com","123",0);
        sqlModel.createUser("daniel@gmail.com","123",0);
        sqlModel.createUser("angel@gmail.com","123",0);
        sqlModel.createUser("ram@gmail.com","123",0);
        sqlModel.createUser("michel@gmail.com","123",0);
        sqlModel.createUser("noadmin@gmail.com","123",0);
        sqlModel.close();
    }

    public void createConferencesInDB(){
        sqlModel = new SQLModel(context);
        sqlModel.open();
        sqlModel.createConference(QueryUtils.getBuidConference(0,7.0,"Emergency Medicine Research Course","This course is a 5 month course that will provide students with a thorough understanding of Evidence Based Medicine (EBM) and Research Methods (RM) in the emergency medicine setting","Melbourne, Australia",1476637176));
        sqlModel.createConference(QueryUtils.getBuidConference(0,8.5,"8th European Neurology Congress","Related Neurology Conferences| Neurology Congress | Neuroscience Events | Meetings on Neurology","amsterdam, Netherlands",1476637176));
        sqlModel.createConference(QueryUtils.getBuidConference(0,10.0,"MSc / PGDip Musculoskeletal Medicine","Orthopaedics","Loughborough, United Kingdom",1476637176));
        sqlModel.createConference(QueryUtils.getBuidConference(0,1.5,"Cycling CME Mountain Bike Western Colorado","We welcome you to join us for the 2016 Cycling CME Mountain Bike Western Colorado.  This is a unique CME conference, which combines education with daily mountain bike rides.","Grand Junction, United States",1476637176));
        sqlModel.createConference(QueryUtils.getBuidConference(0,3.6,"ADAHIC — 2nd Abu Dhabi Ambulatory Healthcare International Congress","Ambulatory Healthcare","Abu Dhabi, United Arab Emirates",1476637176));
        sqlModel.createConference(QueryUtils.getBuidConference(0,2.0,"Targeting Microbiota 2016 — 4th World Congress on Targeting Microbiota","Microbiota 2016: Recent Advances & Perspectives","Paris, France",1476637176));
        sqlModel.createConference(QueryUtils.getBuidConference(0,1.5,"4th International Congress on Translational Medicine","EUSTM-2016 will discuss and showcase latest developments in specific areas of cellular & molecular biology, big data mining & omics sciences, biomarkers & molecular imaging, drug discovery & development","Prague, Czech Republic",1476637176));
        sqlModel.createConference(QueryUtils.getBuidConference(0,3.5,"Primary Care Internal Medicine","Designed for busy clinicians, this comprehensive Harvard Medical School CME program ensures that you are current with the most important clinical changes and innovations impacting primary care, and prepared to integrate them into your practice.","Cambridge, United States",1476637176));
        sqlModel.createConference(QueryUtils.getBuidConference(0,7.5,"Transplant Nurses Association National Conference","On behalf of the 2016 Conference Organising Committee, I would like to extend an invitation to all health professionals with an interest in the field of organ and tissue donation and transplantation to the 2016 TNA National Conference to be held in Adelaide , October 19 – 21 at the Adelaide Convention Centre.","Adelaide, Australia",1476637176));
        sqlModel.createConference(QueryUtils.getBuidConference(0,5.6," RareX — Rare Disease Expo 2016","RareX is a Rare Disease related Conference & Exhibition to be hosted in Cape Town, South Africa from 19 - 22 October 2016 by Rare Diseases SA. Includes presentations, content & discussions relevant for Medical Practitioners, Pharmaceuticals and Industry ","Cape Town, South Africa",1476637176));
        sqlModel.createConference(QueryUtils.getBuidConference(0,5.0,"RareX — RareX Rare Diseases Conference ft. ICORD","The purpose of this conference is to provide a global forum for all stakeholders within the Rare Disease field, both locally and abroad, to participate in open discussion","Cape Town, South Africa",1476637176));
        sqlModel.createConference(QueryUtils.getBuidConference(0,1.5,"Training — Smoking Cessation in Mental Health","Smoking Cessation in Mental Health, and delivering smoke free environments is key to the government ambition for parity of esteem and to reduce the lower life expectancy of people with mental health disorders","London, United Kingdom",1476637176));
        sqlModel.close();


    }
}
