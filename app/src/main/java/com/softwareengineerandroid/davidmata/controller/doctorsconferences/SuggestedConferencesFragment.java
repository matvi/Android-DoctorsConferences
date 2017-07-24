package com.softwareengineerandroid.davidmata.controller.doctorsconferences;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.softwareengineerandroid.davidmata.global.GlobalData;
import com.softwareengineerandroid.davidmata.global.QueryUtils;
import com.softwareengineerandroid.davidmata.global.Timeconversion;
import com.softwareengineerandroid.davidmata.model.conference.Conference;
import com.softwareengineerandroid.davidmata.model.conference.ConferenceAdapter;
import com.softwareengineerandroid.davidmata.model.conference.ConferenceBuilder;
import com.softwareengineerandroid.davidmata.model.database.SQLModel;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestedConferencesFragment extends Fragment {

    private SQLModel sqlModel;
    public SuggestedConferencesFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewRoot = inflater.inflate(R.layout.fragment_suggestedconferences, container, false);

        //ListView
        final ArrayList<Conference> conferences =  QueryUtils.extractSuggestedConferences(getContext());
        final ListView listView = (ListView)viewRoot.findViewById(R.id.suggestedConf_list_view);
        ConferenceAdapter noteAdapter = new ConferenceAdapter(getActivity(),conferences);
        listView.setAdapter(noteAdapter);
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
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(),"Toastlong",Toast.LENGTH_LONG).show();
                final Conference conference = (Conference) listView.getAdapter().getItem(position);

                //ONLY FOR ADMIN USERS
                if(GlobalData.privileges==1){
                    final ImageButton imageButtonEdit = (ImageButton)view.findViewById(R.id.lisItem_imgButt_edit);
                    final ImageButton imageButtonDelete = (ImageButton)view.findViewById(R.id.lisItem_imgButt_delete);
                    imageButtonDelete.setVisibility(View.VISIBLE);
                    imageButtonEdit.setVisibility(View.VISIBLE);

                    imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imageButtonDelete.setVisibility(View.GONE);
                            imageButtonEdit.setVisibility(View.GONE);
                            dlgAreYouShure(conference.getTitle(),conference.getId());
                        }
                    });
                    imageButtonEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imageButtonDelete.setVisibility(View.GONE);
                            imageButtonEdit.setVisibility(View.GONE);
                            dlgShowConfig(conference);
                        }
                    });

                }else {
                    //ONLY FOR LOW LEVEL USERS

                    // Toast.makeText(getActivity()," You don´t have enough permission for creating new conferences",Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

        return viewRoot;
    }

    private AlertDialog dlgConfig;
    private void dlgShowConfig(Conference conference){
        final Context parent = getContext();
        LayoutInflater li = LayoutInflater.from(parent);
        View dlg = li.inflate(R.layout.dialog_newconference, null);
        dlgConfig = new AlertDialog.Builder(parent).create();
        dlgConfig.setView(dlg);

        final Spinner importance = (Spinner)dlg.findViewById(R.id.dialog_newconf_spinnerNumber);
        final EditText title = (EditText) dlg.findViewById(R.id.dialog_newconf_title);
        final EditText body = (EditText) dlg.findViewById(R.id.dialog_newconf_body);
        final EditText location = (EditText) dlg.findViewById(R.id.dialog_newconf_place);
        final DatePicker datePicker = (DatePicker) dlg.findViewById(R.id.dialog_newconf_date);
        final TimePicker timePicker = (TimePicker) dlg.findViewById(R.id.dialog_newconf_time);
        final int idConf = conference.getId();

        title.setText(conference.getTitle());
        body.setText(conference.getBody());
        location.setText(conference.getLocation());
        Date dateObject = new Date(conference.getTimeInMilliseconds()*1000);
        datePicker.updateDate(Integer.parseInt(Timeconversion.formatDateYear(dateObject)),
                Integer.parseInt(Timeconversion.formatDateMonth(dateObject))-1,
                Integer.parseInt(Timeconversion.formatDateDay(dateObject)));
        timePicker.setCurrentHour(Integer.parseInt(Timeconversion.formatDateHour(dateObject)));
        timePicker.setCurrentMinute(Integer.parseInt(Timeconversion.formatDateMinutes(dateObject)));


        dlgConfig.setCancelable(false);
        dlgConfig.setTitle(getResources().getString(R.string.dialog_editconf_title));


        dlgConfig.setButton(DialogInterface.BUTTON_POSITIVE, "Acept",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try{
                            Conference conference = ConferenceBuilder.conference()
                                    .id(idConf)
                                    .importance(Double.parseDouble(importance.getSelectedItem().toString()))
                                    .title(title.getText().toString())
                                    .body(body.getText().toString())
                                    .location(location.getText().toString())
                                    .timeInMilliseconds(getTimeInMillis(datePicker,timePicker))
                                    .build();

                            //Add to a database
                            sqlModel = new SQLModel(getContext());
                            sqlModel.open();
                            sqlModel.editConference(conference);

                            Toast.makeText(getActivity()," Conference " + title.getText().toString() + " added successfully ",Toast.LENGTH_LONG).show();


                            //Activity reload
                            reloadActivity();

                        }catch (Exception e){
                            Toast.makeText(getActivity(),"Error al generar nueva conferencia " + e.getMessage(),Toast.LENGTH_LONG).show();
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


    private long getTimeInMillis(DatePicker date, TimePicker timePicker){
        // yyyy/MM/dd HH:mm:ss
        return new Timeconversion()
                .timeConversion(date.getYear()+"/"+date.getMonth()+1+"/"+date.getDayOfMonth()+" " + timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute() +":00");
    }

    private void dlgAreYouShure(String title, final int id){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Delete Conference");
        alertDialog.setMessage("Are you shure you want to deleted " + title + " conference?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "DELETE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            sqlModel = new SQLModel(getContext());
                            sqlModel.open();
                            sqlModel.deleteByIdConference(String.valueOf(id));
                            reloadActivity();
                        }catch (Exception e){
                            Toast.makeText(getContext(),"Error deleted conference " + e.getMessage(),Toast.LENGTH_LONG).show();
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
