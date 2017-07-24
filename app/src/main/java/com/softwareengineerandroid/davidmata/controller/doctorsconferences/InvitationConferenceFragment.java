package com.softwareengineerandroid.davidmata.controller.doctorsconferences;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.softwareengineerandroid.davidmata.global.GlobalData;
import com.softwareengineerandroid.davidmata.global.QueryUtils;
import com.softwareengineerandroid.davidmata.model.conference.Conference;
import com.softwareengineerandroid.davidmata.model.conference.ConferenceAdapter;
import com.softwareengineerandroid.davidmata.model.database.SQLModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class InvitationConferenceFragment extends Fragment {

    private SQLModel sqlModel;
    public InvitationConferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View viewRoot = inflater.inflate(R.layout.fragment_invitationconference, container, false);

        //ListView
        final ArrayList<Conference> conferences =  QueryUtils.extractInvitedConferences(getContext());
        final ListView listView = (ListView)viewRoot.findViewById(R.id.invitationConf_list_view);
        ConferenceAdapter conferenceAdapter = new ConferenceAdapter(getActivity(),conferences);
        listView.setAdapter(conferenceAdapter);
        listView.setLongClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //final String mensaje = notas.get(position).getImportance();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //CALLING DETAIL ACTIVITY FOR MORE DETAIL CONFERENCE INFORMATION
                Conference conference = (Conference) listView.getAdapter().getItem(position);
                Log.w("DoctorsConferences", " ListMainFragment +++ conferenceImportance" + String.valueOf(conference.getImportance()));
                Intent intent = new Intent(getContext(),ConferenceDetailActivity.class);
                intent.putExtra(Conference.CONFERENCE_IMPORTANCE, String.valueOf(conference.getImportance()));
                intent.putExtra(Conference.CONFERENCE_TITLE,conference.getTitle());
                intent.putExtra(Conference.CONFERENCE_BODY,conference.getBody());
                intent.putExtra(Conference.CONFERENCE_LOCATION,conference.getLocation());
                intent.putExtra(Conference.CONFERENCE_DATE,conference.getTimeInMilliseconds());
                startActivity(intent);




            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Conference conference = (Conference) listView.getAdapter().getItem(position);
                final ImageButton imageButtonDelete = (ImageButton)view.findViewById(R.id.lisItem_imgButt_delete);
                final ImageButton imageButtonAceptInvitation = (ImageButton)view.findViewById(R.id.lisItem_imgButt_aceptInvitation);
                imageButtonDelete.setVisibility(View.VISIBLE);
                imageButtonAceptInvitation.setVisibility(View.VISIBLE);
                imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageButtonDelete.setVisibility(View.GONE);
                        imageButtonAceptInvitation.setVisibility(View.GONE);
                        dlgAreYouShure(conference.getTitle(),conference.getId());
                    }
                });
                imageButtonAceptInvitation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageButtonDelete.setVisibility(View.GONE);
                        imageButtonAceptInvitation.setVisibility(View.GONE);
                        addEventToCalendar(conference);
                    }
                });
                imageButtonAceptInvitation.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        imageButtonDelete.setVisibility(View.GONE);
                        imageButtonAceptInvitation.setVisibility(View.GONE);
                        callCalendarToAddConference(conference);
                        return true;
                    }
                });
                return true;
            }
        });


        return viewRoot;
    }



    private void callCalendarToAddConference(Conference conference){
        long conferenceDate = (conference.getTimeInMilliseconds())*1000;
        long today = System.currentTimeMillis();
        if(conferenceDate > today){
            Calendar cal = Calendar.getInstance();
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("rrule", "FREQ=YEARLY");
            intent.putExtra("beginTime", conference.getTimeInMilliseconds()*1000);
            intent.putExtra("allDay", true);
            intent.putExtra("endTime", conference.getTimeInMilliseconds()*1000+60*60*1000);
            intent.putExtra("title", conference.getTitle());
            intent.putExtra("description", conference.getBody());
            intent.putExtra("eventLocation", conference.getLocation());
            startActivity(intent);
        }else {
            Toast.makeText(getContext(),getActivity().getResources().getText(R.string.Toast_conference_missed),Toast.LENGTH_LONG).show();
        }

    }

    private void addEventToCalendar (Conference conference){
        try{
            long conferenceDate = (conference.getTimeInMilliseconds())*1000;
            long today = System.currentTimeMillis();
            if(conferenceDate > today){
                long calID = 3;
                long startMillis = 0;
                long endMillis = 0;

                startMillis = System.currentTimeMillis();

                endMillis = System.currentTimeMillis()+1000*60*60*24;


                ContentResolver cr = getActivity().getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, conference.getTimeInMilliseconds()*1000);
                values.put(CalendarContract.Events.DTEND, conference.getTimeInMilliseconds()*1000 + 1000*60*60);
                values.put(CalendarContract.Events.TITLE, conference.getTitle());
                values.put(CalendarContract.Events.DESCRIPTION, conference.getBody());
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                values.put(CalendarContract.Events.EVENT_LOCATION,conference.getLocation());
                values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Mexico_City");
                int permissionCheck = ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_CALENDAR);
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

                Date date = new Date(conference.getTimeInMilliseconds()*1000);
                String date_str = formatDateYear(date);
                Toast.makeText(getContext(),getActivity().getResources().getText(R.string.Toast_conference_created) + " For  " + date_str,Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(getContext(),getActivity().getResources().getText(R.string.Toast_conference_missed) ,Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Log.w("DoctorsConferences", e.getMessage());
        }

    }

    private String formatDateYear (Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(dateObject);
    }

    private void dlgAreYouShure(String title, final int idConference){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Reject Invitation");
        alertDialog.setMessage("Are you shure you want to reject the invitation to the" + title + " conference?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "DELETE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            sqlModel = new SQLModel(getContext());
                            sqlModel.open();
                            sqlModel.deleteInvitation(String.valueOf(idConference),String.valueOf(GlobalData.id_user));
                            reloadActivity();
                        }catch (Exception e){
                            Toast.makeText(getContext(),"Error deleted invitation " + e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"CANCEL",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void reloadActivity(){
        GlobalData.appEnd = false;
        getActivity().finish();
        getActivity().startActivity(getActivity().getIntent());
    }

}
