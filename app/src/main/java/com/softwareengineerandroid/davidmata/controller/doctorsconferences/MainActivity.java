package com.softwareengineerandroid.davidmata.controller.doctorsconferences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.softwareengineerandroid.davidmata.global.GlobalData;
import com.softwareengineerandroid.davidmata.global.Timeconversion;
import com.softwareengineerandroid.davidmata.model.conference.Conference;
import com.softwareengineerandroid.davidmata.model.conference.ConferenceBuilder;
import com.softwareengineerandroid.davidmata.model.database.SQLModel;

public class MainActivity extends AppCompatActivity {

    private int CREATECONFERENCE = 0;
    private int CREATESUGGESTEDCONFERENCE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager()));


        TabLayout tableLayout = (TabLayout) findViewById(R.id.tabs);
        tableLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_addConference);
        if(GlobalData.privileges==1){
            item.setTitle(getResources().getString(R.string.action_addConference));
        }else{
           // item.setTitle("Suggest Conference");
            item.setVisible(false);
        }
        return true;
    }

    private SQLModel sqlModel;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_addConference :
                if(GlobalData.privileges==1){
                    dlgShowConfig(CREATECONFERENCE);
                }else {
                   // Toast.makeText(this," You don´t have enough permission for creating new conferences",Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_suggestConference :
                    dlgShowConfig(CREATESUGGESTEDCONFERENCE);
                return true;
            case R.id.action_logout:
                GlobalData.appEnd = true;
                GlobalData.resetData();
                finish();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                return true;

            default: return super.onOptionsItemSelected(item);

        }

    }
    private AlertDialog dlgConfig;
    private void dlgShowConfig(final int opc){
        final Context parent = MainActivity.this;
        LayoutInflater li = LayoutInflater.from(parent);
        View dlg = li.inflate(R.layout.dialog_newconference, null);
        dlgConfig = new AlertDialog.Builder(parent).create();
        dlgConfig.setView(dlg);

        //DatePicker picker = new DatePicker(this);
        //picker.setCalendarViewShown(false);
        final Spinner importance = (Spinner)dlg.findViewById(R.id.dialog_newconf_spinnerNumber);
        final EditText title = (EditText) dlg.findViewById(R.id.dialog_newconf_title);
        final EditText body = (EditText) dlg.findViewById(R.id.dialog_newconf_body);
        final EditText location = (EditText) dlg.findViewById(R.id.dialog_newconf_place);
        final DatePicker date = (DatePicker) dlg.findViewById(R.id.dialog_newconf_date);
        final TimePicker time = (TimePicker) dlg.findViewById(R.id.dialog_newconf_time);

        dlgConfig.setCancelable(false);
        dlgConfig.setTitle(getResources().getString(R.string.dialog_newconf_title));
        date.setMinDate(System.currentTimeMillis()- 1000);
       // dlgConfig.setMessage(getResources().getString(R.string.dialog_newconf_message));

        dlgConfig.setButton(DialogInterface.BUTTON_POSITIVE, "Acept",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                         try{
                            //get the information
                            String imp = importance.getSelectedItem().toString();
                            String title_str = title.getText().toString();
                            String body_str = body.getText().toString();
                            String location_str = location.getText().toString();
                            int day = date.getDayOfMonth();
                            int month = date.getMonth()+1;
                            int year = date.getYear();
                            int hh = time.getCurrentHour();
                            int mm = time.getCurrentMinute();
                            String date_str = year+"/"+month+"/"+day+" " + hh+":"+mm +":00"; // yyyy/MM/dd HH:mm:ss
                            long dateMilliseconds = new Timeconversion().timeConversion(date_str);

                            //Conference conference = new Conference(0,Double.parseDouble(imp),title_str,body_str,location_str,dateMilliseconds);
                             Conference conference = ConferenceBuilder.conference()
                                     .id(0)
                                     .importance(Double.parseDouble(imp))
                                     .title(title_str)
                                     .body(body_str)
                                     .location(location_str)
                                     .timeInMilliseconds(dateMilliseconds)
                                     .build();
                            //Add to a database
                            sqlModel = new SQLModel(MainActivity.this);
                            sqlModel.open();
                             if(opc == CREATECONFERENCE){
                                 sqlModel.createConference(conference);
                             }else {
                                 sqlModel.createSuggestedConference(conference);
                             }
                             sqlModel.close();


                            Toast.makeText(MainActivity.this," Conference " + title_str + " added successfully ",Toast.LENGTH_LONG).show();
                            //reload activity
                             GlobalData.appEnd = false;
                            finish();
                            startActivity(getIntent());


                        }catch (Exception e){
                            Toast.makeText(MainActivity.this,"Error on genering new conference " + e.getMessage(),Toast.LENGTH_LONG).show();
                        }




                        dialog.dismiss();


                    }
                });
        dlgConfig.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //login.this.finish();
                    }
                });

        dlgConfig.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(GlobalData.appEnd){
            GlobalData.resetData();
        }
        GlobalData.appEnd=true;

    }
}
