package com.softwareengineerandroid.davidmata.model.conference;

/**
 * Created by davidmata on 15/10/2016.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;

import com.softwareengineerandroid.davidmata.controller.doctorsconferences.R;


public class ConferenceAdapter extends ArrayAdapter <Conference> {

    public ConferenceAdapter(Context context, ArrayList<Conference> notas){
        super(context,0,notas);

    }
    //private static final String LOCATION_SEPARATOR = " of";
    View view;
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        */
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
            Log.w("AppList","converView es null");
        }



        Conference conference = getItem(position);

        Date dateObject = new Date(conference.getTimeInMilliseconds()*1000);
        Long datemilis = conference.getTimeInMilliseconds();
        TextView conferenceTitle = (TextView)convertView.findViewById(R.id.listItem_tv_title);
        TextView conferenceBody = (TextView)convertView.findViewById(R.id.listItem_tv_body);
        TextView conferenceLocation = (TextView)convertView.findViewById(R.id.listItem_tv_location);
        TextView date = (TextView)convertView.findViewById(R.id.lisItem_tv_date);
        TextView time = (TextView)convertView.findViewById(R.id.lisItem_tv_time);
        TextView importance = (TextView) convertView.findViewById(R.id.listItem_tv_importance);
        GradientDrawable magnitudCicle = (GradientDrawable)importance.getBackground();
        magnitudCicle.setColor(getMagnitudColor(conference.getImportance()));
        String str_locationOffset, str_place;
        /*if(conference.getTitle().contains(LOCATION_SEPARATOR)){
            String [] locations = conference.getTitle().split(LOCATION_SEPARATOR);
            str_locationOffset = locations[0];
            str_place = locations[1];
        }else {
            str_place = conference.getTitle();
            str_locationOffset = getContext().getString(R.string.near_the);
        }*/
        conferenceTitle.setText(conference.getTitle());
        importance.setText( new DecimalFormat("0.0").format(conference.getImportance()));
        date.setText(formatDate(dateObject));
        conferenceBody.setText(conference.getBody());
        conferenceLocation.setText(conference.getLocation());
        time.setText(formatTime(dateObject));
        //conferenceTitle.setText(str_locationOffset);

       /* final String  mensaje = title.getText().toString();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),mensaje,Toast.LENGTH_SHORT).show();
            }
        });*/

        return convertView;
    }
    private String formatDate (Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    private String formatTime (Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return  dateFormat.format(dateObject);
    }
    private int getMagnitudColor(Double mag){
        int magnitudeColorResourceId;
        switch ((int)Math.floor(mag)){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        /*
        You can call ContextCompat getColor() to convert the color resource ID into an actual integer color value, and return the result as the return value of the getMagnitudeColor() helper method.
         */
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}