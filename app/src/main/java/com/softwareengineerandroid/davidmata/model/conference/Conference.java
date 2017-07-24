package com.softwareengineerandroid.davidmata.model.conference;

/**
 * Created by davidmata on 15/10/2016.
 */


public class Conference {

    public static final String CONFERENCE_ID = "CONFERENCE ID IDENTIFIER";
    public static final String CONFERENCE_TITLE = "CONFERENCE TITLE IDENTIFIER";
    public static final String CONFERENCE_BODY = "CONFERENCE BODY IDENTIFIER";
    public static final String CONFERENCE_LOCATION = "CONFERENCE LOCATION IDENTIFIER";
    public static final String CONFERENCE_DATE = "CONFERENCE DATE IDENTIFIER";
    public static final String CONFERENCE_IMPORTANCE = "CONFERENCE IMPORTANCE IDENTIFIER";

    private Double importance;
    private String title, body, location;
    private long timeInMilliseconds;
    private int id;

    /*
     *
     * @param importance NUMBER 1 TO 10 TO INDICATE HOW IMPORTAT IS THE CONFERENCE
     * @param conferenceTitle THE NAME OF THE CONFERENCE
     * @param conferenceBody CONFERENCE'S DETAILS
     * @param time THE DATE IN EPOCH & UNIX TIMESTAMP CONVERSION
     */
    public Conference(ConferenceBuilder conferenceBuilder){
        this.id = conferenceBuilder.id;
        this.importance = conferenceBuilder.importance;
        this.title = conferenceBuilder.title;
        this.timeInMilliseconds = conferenceBuilder.timeInMilliseconds;
        this.body = conferenceBuilder.body;
        this.location = conferenceBuilder.location;
    }
   /* public Conference(int id, Double importance, String conferenceTitle, String conferenceBody, String location, long time){
        this.id = id;
        this.importance =importance;
        this.title = conferenceTitle;
        this.timeInMilliseconds = time;
        this.body = conferenceBody;
        this.location = location;

    }*/

    public Double getImportance(){
        return this.importance;
    }

    public String getTitle(){
        return this.title;
    }
    public long getTimeInMilliseconds(){
        return this.timeInMilliseconds;
    }

    public String getBody(){
        return this.body;
    }

    public String getLocation(){
        return this.location;
    }

    public int getId(){
        return this.id;
    }


}
