package com.softwareengineerandroid.davidmata.global;

/**
 * Created by davidmata on 15/10/2016.
 */

import android.content.Context;

import com.softwareengineerandroid.davidmata.model.conference.Conference;
import com.softwareengineerandroid.davidmata.model.conference.ConferenceBuilder;
import com.softwareengineerandroid.davidmata.model.database.SQLModel;
import com.softwareengineerandroid.davidmata.model.user.User;

import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */
    private static final String SAMPLE_JSON_RESPONSE = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1462295443000,\"url\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.5.2\",\"limit\":10,\"offset\":1,\"count\":10},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":7.2,\"place\":\"88km N of Yelizovo, Russia\",\"time\":1454124312220,\"updated\":1460674294040,\"tz\":720,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us20004vvx\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004vvx&format=geojson\",\"felt\":2,\"cdi\":3.4,\"mmi\":5.82,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":798,\"net\":\"us\",\"code\":\"20004vvx\",\"ids\":\",at00o1qxho,pt16030050,us20004vvx,gcmt20160130032510,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,finite-fault,general-link,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.958,\"rms\":1.19,\"gap\":17,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.2 - 88km N of Yelizovo, Russia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[158.5463,53.9776,177]},\"id\":\"us20004vvx\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.176,\"place\":\"94km SSE of Taron, Papua New Guinea\",\"time\":1453777820750,\"updated\":1460156775040,\"tz\":600,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us20004uks\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004uks&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":4.1,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":572,\"net\":\"us\",\"code\":\"20004uks\",\"ids\":\",us20004uks,gcmt20160126031023,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.537,\"rms\":0.74,\"gap\":25,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 94km SSE of Taron, Papua New Guinea\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[153.2454,-5.2952,26]},\"id\":\"us20004uks\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.3,\"place\":\"50km NNE of Al Hoceima, Morocco\",\"time\":1453695722730,\"updated\":1460156773040,\"tz\":0,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004gy9\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gy9&format=geojson\",\"felt\":117,\"cdi\":7.2,\"mmi\":5.28,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":695,\"net\":\"us\",\"code\":\"10004gy9\",\"ids\":\",us10004gy9,gcmt20160125042203,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.201,\"rms\":0.92,\"gap\":20,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 50km NNE of Al Hoceima, Morocco\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-3.6818,35.6493,12]},\"id\":\"us10004gy9\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":7.1,\"place\":\"86km E of Old Iliamna, Alaska\",\"time\":1453631430230,\"updated\":1460156770040,\"tz\":-540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004gqp\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gqp&format=geojson\",\"felt\":1816,\"cdi\":7.2,\"mmi\":6.6,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":1496,\"net\":\"us\",\"code\":\"10004gqp\",\"ids\":\",at00o1gd6r,us10004gqp,ak12496371,gcmt20160124103030,\",\"sources\":\",at,us,ak,gcmt,\",\"types\":\",cap,dyfi,finite-fault,general-link,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,trump-origin,\",\"nst\":null,\"dmin\":0.72,\"rms\":2.11,\"gap\":19,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.1 - 86km E of Old Iliamna, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-153.4051,59.6363,129]},\"id\":\"us10004gqp\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.6,\"place\":\"215km SW of Tomatlan, Mexico\",\"time\":1453399617650,\"updated\":1459963829040,\"tz\":-420,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004g4l\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004g4l&format=geojson\",\"felt\":11,\"cdi\":2.7,\"mmi\":3.92,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":673,\"net\":\"us\",\"code\":\"10004g4l\",\"ids\":\",at00o1bebo,pt16021050,us10004g4l,gcmt20160121180659,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.413,\"rms\":0.98,\"gap\":74,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.6 - 215km SW of Tomatlan, Mexico\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-106.9337,18.8239,10]},\"id\":\"us10004g4l\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.765,\"place\":\"52km SE of Shizunai, Japan\",\"time\":1452741933640,\"updated\":1459304879040,\"tz\":540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004ebx\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebx&format=geojson\",\"felt\":51,\"cdi\":5.8,\"mmi\":6.45,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":720,\"net\":\"us\",\"code\":\"10004ebx\",\"ids\":\",us10004ebx,pt16014050,at00o0xauk,gcmt20160114032534,\",\"sources\":\",us,pt,at,gcmt,\",\"types\":\",associate,cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":0.281,\"rms\":0.98,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.7 - 52km SE of Shizunai, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[142.781,41.9723,46]},\"id\":\"us10004ebx\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.1,\"place\":\"12km WNW of Charagua, Bolivia\",\"time\":1452741928270,\"updated\":1459304879040,\"tz\":-240,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004ebw\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebw&format=geojson\",\"felt\":3,\"cdi\":2.2,\"mmi\":2.21,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":573,\"net\":\"us\",\"code\":\"10004ebw\",\"ids\":\",us10004ebw,gcmt20160114032528,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":5.492,\"rms\":1.04,\"gap\":16,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 12km WNW of Charagua, Bolivia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-63.3288,-19.7597,582.56]},\"id\":\"us10004ebw\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2,\"place\":\"74km NW of Rumoi, Japan\",\"time\":1452532083920,\"updated\":1459304875040,\"tz\":540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004djn\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004djn&format=geojson\",\"felt\":8,\"cdi\":3.4,\"mmi\":3.74,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":594,\"net\":\"us\",\"code\":\"10004djn\",\"ids\":\",us10004djn,gcmt20160111170803,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.139,\"rms\":0.96,\"gap\":33,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.2 - 74km NW of Rumoi, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[141.0867,44.4761,238.81]},\"id\":\"us10004djn\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.5,\"place\":\"227km SE of Sarangani, Philippines\",\"time\":1452530285900,\"updated\":1459304874040,\"tz\":480,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004dj5\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004dj5&format=geojson\",\"felt\":1,\"cdi\":2.7,\"mmi\":7.5,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":650,\"net\":\"us\",\"code\":\"10004dj5\",\"ids\":\",at00o0srjp,pt16011050,us10004dj5,gcmt20160111163807,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":3.144,\"rms\":0.72,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.5 - 227km SE of Sarangani, Philippines\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[126.8621,3.8965,13]},\"id\":\"us10004dj5\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"Pacific-Antarctic Ridge\",\"time\":1451986454620,\"updated\":1459202978040,\"tz\":-540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004bgk\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004bgk&format=geojson\",\"felt\":0,\"cdi\":1,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":554,\"net\":\"us\",\"code\":\"10004bgk\",\"ids\":\",us10004bgk,gcmt20160105093415,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":30.75,\"rms\":0.67,\"gap\":71,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - Pacific-Antarctic Ridge\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-136.2603,-54.2906,10]},\"id\":\"us10004bgk\"}],\"bbox\":[-153.4051,-54.2906,10,158.5463,59.6363,582.56]}";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static ArrayList<Conference> extractConferences2() {
        ArrayList<Conference> conferences = new ArrayList<>();
        conferences.add(getBuidConference(0,7.0,"Emergency Medicine Research Course","This course is a 5 month course that will provide students with a thorough understanding of Evidence Based Medicine (EBM) and Research Methods (RM) in the emergency medicine setting","Melbourne, Australia",1476637176));
        conferences.add(getBuidConference(0,8.5,"8th European Neurology Congress","Related Neurology Conferences| Neurology Congress | Neuroscience Events | Meetings on Neurology","amsterdam, Netherlands",1476637176));
        conferences.add(getBuidConference(0,10.0,"MSc / PGDip Musculoskeletal Medicine","Orthopaedics","Loughborough, United Kingdom",1476637176));
        conferences.add(getBuidConference(0,1.5,"Cycling CME Mountain Bike Western Colorado","We welcome you to join us for the 2016 Cycling CME Mountain Bike Western Colorado.  This is a unique CME conference, which combines education with daily mountain bike rides.","Grand Junction, United States",1476637176));
        conferences.add(getBuidConference(0,3.6,"ADAHIC — 2nd Abu Dhabi Ambulatory Healthcare International Congress","Ambulatory Healthcare","Abu Dhabi, United Arab Emirates",1476637176));
        conferences.add(getBuidConference(0,2.0,"Targeting Microbiota 2016 — 4th World Congress on Targeting Microbiota","Microbiota 2016: Recent Advances & Perspectives","Paris, France",1476637176));
        conferences.add(getBuidConference(0,1.5,"4th International Congress on Translational Medicine","EUSTM-2016 will discuss and showcase latest developments in specific areas of cellular & molecular biology, big data mining & omics sciences, biomarkers & molecular imaging, drug discovery & development","Prague, Czech Republic",1476637176));
        conferences.add(getBuidConference(0,3.5,"Primary Care Internal Medicine","Designed for busy clinicians, this comprehensive Harvard Medical School CME program ensures that you are current with the most important clinical changes and innovations impacting primary care, and prepared to integrate them into your practice.","Cambridge, United States",1476637176));
        conferences.add(getBuidConference(0,7.5,"Transplant Nurses Association National Conference","On behalf of the 2016 Conference Organising Committee, I would like to extend an invitation to all health professionals with an interest in the field of organ and tissue donation and transplantation to the 2016 TNA National Conference to be held in Adelaide , October 19 – 21 at the Adelaide Convention Centre.","Adelaide, Australia",1476637176));
        conferences.add(getBuidConference(0,5.6," RareX — Rare Disease Expo 2016","RareX is a Rare Disease related Conference & Exhibition to be hosted in Cape Town, South Africa from 19 - 22 October 2016 by Rare Diseases SA. Includes presentations, content & discussions relevant for Medical Practitioners, Pharmaceuticals and Industry ","Cape Town, South Africa",1476637176));
        conferences.add(getBuidConference(0,5.0,"RareX — RareX Rare Diseases Conference ft. ICORD","The purpose of this conference is to provide a global forum for all stakeholders within the Rare Disease field, both locally and abroad, to participate in open discussion","Cape Town, South Africa",1476637176));
        conferences.add(getBuidConference(0,1.5,"Training — Smoking Cessation in Mental Health","Smoking Cessation in Mental Health, and delivering smoke free environments is key to the government ambition for parity of esteem and to reduce the lower life expectancy of people with mental health disorders","London, United Kingdom",1476637176));

        return conferences;

    }

    public static Conference getBuidConference(int id, Double importance, String title, String body, String location, long dateMilliseconds){
        return ConferenceBuilder.conference()
                .id(id)
                .importance(importance)
                .title(title)
                .body(body)
                .location(location)
                .timeInMilliseconds(dateMilliseconds)
                .build();
    }

    /**
     * EXTRACT THE Conferences from the database
     * @param context
     * @return An ArrayList of conferences that exits in the database
     */
    public static ArrayList<Conference> extractConferences(Context context) {
        ArrayList<Conference> conferences = new ArrayList<>();
        SQLModel sqlModel = new SQLModel(context);
        sqlModel.open();
        ArrayList<Object[]> listConferences = sqlModel.getConferenceData();
        for (int i=0; i< listConferences.size(); i++){
            conferences.add(getBuidConference(Integer.parseInt(listConferences.get(i)[0].toString()),Double.parseDouble(listConferences.get(i)[1].toString()),listConferences.get(i)[2].toString(),listConferences.get(i)[3].toString(),listConferences.get(i)[4].toString(),Long.parseLong(listConferences.get(i)[5].toString())));
        }
        return conferences;

    }

    /**
     * EXTRACT THE Conferences suggested by the users.
     * @param context
     * @return An ArrayList of suggested conferences that exits in the database
     */
    public static ArrayList<Conference> extractSuggestedConferences(Context context){
        ArrayList<Conference> conferences = new ArrayList<>();
        SQLModel sqlModel = new SQLModel(context);
        sqlModel.open();
        ArrayList<Object[]> listConferences = sqlModel.getSuggestedConference();
        for (int i=0; i< listConferences.size(); i++){
            conferences.add(getBuidConference(Integer.parseInt(listConferences.get(i)[0].toString()),Double.parseDouble(listConferences.get(i)[1].toString()),listConferences.get(i)[2].toString(),listConferences.get(i)[3].toString(),listConferences.get(i)[4].toString(),Long.parseLong(listConferences.get(i)[5].toString())));
        }
        return conferences;
    }

    /**
     * Extract the conferences that the local user have
     * @param context
     * @return An ArrayList of invited conferences that exits in the database
     */
    public static ArrayList<Conference> extractInvitedConferences(Context context){
        ArrayList<Conference> conferences = new ArrayList<>();
        SQLModel sqlModel = new SQLModel(context);
        sqlModel.open();
        ArrayList<Object[]> listConferences = sqlModel.getInvitedConference(GlobalData.id_user);
        for (int i=0; i< listConferences.size(); i++){
            conferences.add(getBuidConference(Integer.parseInt(listConferences.get(i)[0].toString()),Double.parseDouble(listConferences.get(i)[1].toString()),listConferences.get(i)[2].toString(),listConferences.get(i)[3].toString(),listConferences.get(i)[4].toString(),Long.parseLong(listConferences.get(i)[5].toString())));
        }
        return conferences;
    }

    /**
     * It will get all the user existing in the database except yours
     * @param context
     * @return ArrayList<User> that contains all user except yours
     */
    public static ArrayList<User> getAllUser(Context context){
        ArrayList<User> user = new ArrayList<>();
        SQLModel sqlModel = new SQLModel(context);
        sqlModel.open();
        ArrayList<Object[]> listUsers = sqlModel.getUserList(GlobalData.id_user);
        for (int i=0; i< listUsers.size(); i++){
            user.add(new User(Integer.parseInt(listUsers.get(i)[0].toString()),listUsers.get(i)[1].toString(),listUsers.get(i)[2].toString(),Integer.parseInt(listUsers.get(i)[3].toString())));
        }
        return user;
    }




    //AN EXAMPLE USING JSON FOR EXTRACTING DATA INSTEAD OF DATABASE
    /**
     * Return a list of {@link Conference} objects that has been built up from
     * parsing a JSON response.
     */
    /*
    public static ArrayList<Conference> extractConferences() {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Conference> conferences = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject root = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray features = root.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {
                JSONObject earthquake = features.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String mapURL = properties.getString("url");
                conferences.add(getBuidConference(mag, place, time, mapURL));
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return conferences;
    }*/




}
