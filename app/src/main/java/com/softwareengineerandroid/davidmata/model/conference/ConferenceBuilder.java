package com.softwareengineerandroid.davidmata.model.conference;

/**
 * Created by davidmata on 26/10/2016.
 */

public class ConferenceBuilder {
     Double importance;
     String title, body, location;
     long timeInMilliseconds;
     int id;

    public static ConferenceBuilder conference(){
        return new ConferenceBuilder();
    }
    public ConferenceBuilder title(String title){
        this.title = title;
        return this;
    }
    public ConferenceBuilder body(String body){
        this.body=body;
        return this;
    }
    public ConferenceBuilder location(String location){
        this.location = location;
        return this;
    }
    public ConferenceBuilder importance(Double importance){
        this.importance = importance;
        return this;
    }
    public ConferenceBuilder timeInMilliseconds(long timeInMilliseconds){
        this.timeInMilliseconds= timeInMilliseconds;
        return this;
    }
    public ConferenceBuilder id (int id){
        this.id = id;
        return this;
    }

    public Conference build(){
        return new Conference(this);
    }
}
