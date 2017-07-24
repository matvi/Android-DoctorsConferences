package com.softwareengineerandroid.davidmata.model.database;

/**
 * Created by davidmata on 15/10/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.softwareengineerandroid.davidmata.model.conference.Conference;
import com.softwareengineerandroid.davidmata.global.GlobalData;

import java.util.ArrayList;

public class SQLModel {


    //DATABASE NAME AND VERSION
    public static final String DATABASE_NAME = "doctorsconferences.db";
    private static final int DATABASE_VERSION = 7;

    //TABLE USER
    private static final String TABLE_USER = "user";
    private static final String COLUMN_US_ID = "_id";
    private static final String COLUMN_US_USER = "user";
    private static final String COLUMN_US_PASSWORD = "password";
    private static final String COLUMN_US_ADMIN = "admin";

    //TABLE CONFERENCE
    private static final String TABLE_CONFERENCE = "conference";
    private static final String COLUMN_CONF_ID = "_id";
    private static final String COLUMN_CONF_IMPORTANCE = "importance";
    private static final String COLUMN_CONF_TITLE = "title";
    private static final String COLUMN_CONF_BODY = "body";
    private static final String COLUMN_CONF_LOCATION = "location";
    private static final String COLUMN_CONF_DATE = "date";

    //TABLE SUGGESTED CONFERENCES JOIN
    private static final String TABLE_SUGGESTED = "suggested";
    private static final String COLUMN_SUGG_IDCONF = "_idconference";
    private static final String COLUMN_SUGG_IDUSER = "_iduser";


    //TABLE INVETED CONFERENCES JOIN
    private static final String TABLE_INVITED = "invited";
    private static final String COLUMN_INV_IDCONF = "_idconference";
    private static final String COLUMN_INV_IDUSER = "_iduser";

    private String [] USER_COLUMNS = {COLUMN_US_ID, COLUMN_US_USER, COLUMN_US_PASSWORD, COLUMN_US_ADMIN};
    private String [] CONFERENCE_COLUMNS = {COLUMN_CONF_ID, COLUMN_CONF_IMPORTANCE, COLUMN_CONF_TITLE, COLUMN_CONF_BODY, COLUMN_CONF_LOCATION, COLUMN_CONF_DATE};
    private String [] SUGGESTED_COLUMNS = {COLUMN_SUGG_IDCONF,COLUMN_SUGG_IDUSER};
    private String [] INVITED_COLUMNS = {COLUMN_INV_IDCONF,COLUMN_INV_IDUSER};

    //
    //QUERY FOR CREATING USER TABLE
    private static final String CREATE_TABLE_USER = "create table if not exists " + TABLE_USER + " ( "
            + COLUMN_US_ID + " integer primary key autoincrement, "
            + COLUMN_US_USER + " text not null, "
            + COLUMN_US_PASSWORD + " text not null, "
            + COLUMN_US_ADMIN + " integer );" ;

    //QUERY FOR CREATING CONFERENCE TABLE
    private static final String CREATE_TABLE_CONFERENCE = "create table if not exists " + TABLE_CONFERENCE + " ( "
            + COLUMN_CONF_ID + " integer primary key autoincrement, "
            + COLUMN_CONF_IMPORTANCE + " integer, "
            + COLUMN_CONF_TITLE + " text not null, "
            + COLUMN_CONF_BODY + " text not null, "
            + COLUMN_CONF_LOCATION + " text not null, "
            + COLUMN_CONF_DATE + " text not null );" ;

    //QUERY FOR CREATING SUGGESTED_CONFERENCES TABLE

    private static final String CREATE_TABLE_SUGGESTED = "create table if not exists " + TABLE_SUGGESTED + " ( "
            + COLUMN_SUGG_IDCONF + " integer, "
            + COLUMN_SUGG_IDUSER + " integer );" ;

    //QUERY FOR CREATING INVITED_CONFERENCES TABLE
    private static final String CREATE_TABLE_INVITED = "create table if not exists " + TABLE_INVITED + " ( "
            + COLUMN_INV_IDCONF + " integer, "
            + COLUMN_INV_IDUSER + " integer );" ;


    private SQLiteDatabase sqlDB;
    private Context context;
    private DoctorsConferencesDBHelper doctorsConferencesDBHelper;

    public SQLModel(Context context){
        this.context = context;
    }
    public SQLModel open() throws android.database.SQLException{
        doctorsConferencesDBHelper = new DoctorsConferencesDBHelper(context);
        sqlDB = doctorsConferencesDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        doctorsConferencesDBHelper.close();
    }

    // *****************************************INSERT DATABASE DATA *************************************************
     /**
     * CREATE USER
     * @param user EMAIL USER
     * @param pass ANY PASSWORD
     * @param admin ADMIN LEVEN 1 FOR ADMIN 0 FOR USER
     */

    public void createUser(String user, String pass, int admin){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_US_USER, user);
        contentValues.put(COLUMN_US_PASSWORD, pass);
        contentValues.put(COLUMN_US_ADMIN, admin);
        long insertId = sqlDB.insert(TABLE_USER,null,contentValues);
    }

    /**
     * Create conference in database
     * @param conference IMPORTANCE NUMBER 1 TO 10
     *                   TITLE NAME OF THE CONFERENCE
     *                   BODY MORE INFORMATION ABOUT THE CONFERENCE
     *                   DATE IN Epoch & Unix Timestamp Conversion
     */
    public long createConference(Conference conference){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CONF_IMPORTANCE, conference.getImportance());
        contentValues.put(COLUMN_CONF_TITLE, conference.getTitle());
        contentValues.put(COLUMN_CONF_BODY,conference.getBody());
        contentValues.put(COLUMN_CONF_LOCATION,conference.getLocation());
        contentValues.put(COLUMN_CONF_DATE,conference.getTimeInMilliseconds());
        long idConf = sqlDB.insert(TABLE_CONFERENCE,null,contentValues);
        return idConf;

    }

    public void createSuggestedConference(Conference conference){
        //first we create the conference in database
        long idConf = createConference(conference);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SUGG_IDCONF,idConf);
        contentValues.put(COLUMN_SUGG_IDUSER, GlobalData.id_user);
        sqlDB.insert(TABLE_SUGGESTED,null,contentValues);
    }

    public void createInvitation(int idConference, int idUser){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_INV_IDCONF,idConference);
        contentValues.put(COLUMN_INV_IDUSER, idUser);
        sqlDB.insert(TABLE_INVITED, null, contentValues);
    }

    // *****************************************DELETE DATABASE DATA *************************************************
    public void deleteByIdConference(String id){
        sqlDB.delete(TABLE_CONFERENCE, COLUMN_CONF_ID + "=?", new String[]{id});
        close();
    }

    public void deleteInvitation(String idConference, String idUser){
        sqlDB.delete(TABLE_INVITED,COLUMN_INV_IDCONF + " =? AND " + COLUMN_INV_IDUSER + " =?", new String[]{idConference,idUser});
        close();
    }

    // *****************************************EDIT DATABASE DATA *************************************************
    public void editConference(Conference conference){
       /* String query = "UPDATE " + TABLE_CONFERENCE
                + " SET " + COLUMN_CONF_TITLE + " = '" + conference.getTitle() + "',"
                + " SET " + COLUMN_CONF_BODY + " = '" + conference.getBody() + "',"
                + " SET " + COLUMN_CONF_LOCATION + " = '" + conference.getLocation() + "',"
                + " SET " + COLUMN_CONF_IMPORTANCE + " = " + conference.getImportance() + ","
                + " SET " + COLUMN_CONF_DATE + " = '" + conference.getTimeInMilliseconds() + "'"
                + " WHERE " + COLUMN_CONF_ID + " = " + conference.getId()+ ";";*/

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CONF_TITLE,conference.getTitle());
        contentValues.put(COLUMN_CONF_BODY,conference.getBody());
        contentValues.put(COLUMN_CONF_LOCATION,conference.getLocation() );
        contentValues.put(COLUMN_CONF_IMPORTANCE,conference.getImportance());
        contentValues.put(COLUMN_CONF_DATE,conference.getTimeInMilliseconds());

        sqlDB.update(TABLE_CONFERENCE,contentValues,COLUMN_CONF_ID + " = " + conference.getId(),null);

    }
    // *****************************************GET DATABASE DATA *************************************************
    /*public ArrayList<Object[]> getConferenceData2(){
        ArrayList<Object[]> conferenceList = null;
        Cursor rs = sqlDB.rawQuery("SELECT * FROM " + TABLE_CONFERENCE , null);
        conferenceList = getList(rs,sqlDB);
        return conferenceList;
    }*/
    //GET CONFERENCE DATA THAT ARE NOT SUGGESTED
    public ArrayList<Object[]> getConferenceData(){
        ArrayList<Object[]> conferenceList = null;
        String query = "SELECT * FROM " + TABLE_CONFERENCE + " WHERE NOT EXISTS ( SELECT 1 FROM " + TABLE_SUGGESTED + " WHERE " + TABLE_CONFERENCE + "." + COLUMN_CONF_ID + "=" + TABLE_SUGGESTED + "." + COLUMN_SUGG_IDCONF + ");";
        Cursor rs = sqlDB.rawQuery(query, null);
        conferenceList = getList(rs,sqlDB);
        return conferenceList;
    }
    //GET CONFERECE DATA THAT ARE SUGGESTED
    public ArrayList<Object[]> getSuggestedConference(){
        ArrayList<Object[]> conferenceList = null;
        String query = "SELECT * FROM " + TABLE_CONFERENCE + " WHERE EXISTS ( SELECT 1 FROM " + TABLE_SUGGESTED + " WHERE " + TABLE_CONFERENCE + "." + COLUMN_CONF_ID + "=" + TABLE_SUGGESTED + "." +COLUMN_SUGG_IDCONF + ");";
        Cursor rs = sqlDB.rawQuery(query, null);
        conferenceList = getList(rs,sqlDB);
        return conferenceList;
    }
    //GET INVITED CONFERENCES NOT READY, PLEASE CHANGE IT
    public ArrayList<Object[]> getInvitedConference(int id_user){
        ArrayList<Object[]> conferenceList = null;
        //SELECT * FROM conference WHERE EXISTS ( SELECT 1 FROM invited WHERE conference._id=invited._idconference AND invited._iduser = 1);
        String query = "SELECT * FROM " + TABLE_CONFERENCE + " WHERE EXISTS ( SELECT 1 FROM " + TABLE_INVITED + " WHERE " + TABLE_CONFERENCE + "." + COLUMN_CONF_ID + "=" + TABLE_INVITED + "." +COLUMN_INV_IDCONF + " AND " + TABLE_INVITED + "." +COLUMN_INV_IDUSER + "=" + id_user +");";
        Cursor rs = sqlDB.rawQuery(query, null);
        conferenceList = getList(rs,sqlDB);
        return conferenceList;
    }
    //GET USER DATA
    public ArrayList<Object[]> getUserList(){
        ArrayList<Object[]> userList = null;
        Cursor rs = sqlDB.rawQuery("SELECT * FROM " + TABLE_USER,null);
        userList = getList(rs,sqlDB);
        return userList;
    }
    public ArrayList<Object[]> getUserList(int idUser){
        ArrayList<Object[]> userList = null;
        Cursor rs = sqlDB.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_US_ID + " != " + idUser,null);
        userList = getList(rs,sqlDB);
        return userList;
    }

    public ArrayList<Object[]> getUser(String user){
        ArrayList<Object[]> userList = null;
        Cursor rs = sqlDB.rawQuery("SELECT * FROM " + TABLE_USER + " where " + COLUMN_US_USER + " = '" + user + "';",null);
        userList = getList(rs,sqlDB);
        return userList;
    }

    public ArrayList<Object[]> getList(Cursor rs, SQLiteDatabase db){
        Object [] fila = null;
        ArrayList<Object[]> list = new  ArrayList<Object[]>();
        try {
            if (rs.getCount() > 0) {
                rs.moveToFirst();
                do {
                    fila = new Object[rs.getColumnCount()]; //Menos uno porque quitamos el id
                    for(int i=0; i<fila.length; i++){
                        if(rs.getString(i)!=null){
                            fila[i] = rs.getString(i);
                        }else{
                            fila[i] ="null";
                        }
                    }

                    list.add(fila);
                } while (rs.moveToNext());

            }
        }
        finally{ rs.close(); db.close(); }

        return list;
    }

// FOR UNIT TEST
    public boolean isOpen(){
        return sqlDB.isOpen();
    }
 //*********************************************************************************************************************


    private static class DoctorsConferencesDBHelper extends SQLiteOpenHelper{

        DoctorsConferencesDBHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate (SQLiteDatabase db){
            Log.w("DoctoresConferences", "Se crea la base de datos     " + CREATE_TABLE_USER);
            Log.w("DoctoresConferences", "Se crea la base de datos     " + CREATE_TABLE_CONFERENCE);
            Log.w("DoctoresConferences", "Se crea la base de datos     " + CREATE_TABLE_SUGGESTED);
            Log.w("DoctoresConferences", "Se crea la base de datos     " + CREATE_TABLE_INVITED);
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_CONFERENCE);
            db.execSQL(CREATE_TABLE_SUGGESTED);
            db.execSQL(CREATE_TABLE_INVITED);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w("DoctoresConferences", "BORRANDO TABLASSSS     " );
            db.execSQL("DELETE FROM " + TABLE_CONFERENCE);
            db.execSQL("DELETE FROM " + TABLE_USER);
           // db.execSQL("DELETE FROM " + TABLE_SUGGESTED);
           // db.execSQL("DELETE FROM " + TABLE_INVITED);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONFERENCE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUGGESTED);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVITED);
            onCreate(db);
        }
    }
}
