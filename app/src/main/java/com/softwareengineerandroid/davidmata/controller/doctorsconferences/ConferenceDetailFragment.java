package com.softwareengineerandroid.davidmata.controller.doctorsconferences;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softwareengineerandroid.davidmata.model.conference.Conference;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConferenceDetailFragment extends Fragment {


    public ConferenceDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conference_detail, container, false);

        TextView title = (TextView)view.findViewById(R.id.fragConfDetail_tv_title);
        TextView textBoxy = (TextView)view.findViewById(R.id.fragfragConfDetail_tv_TextBody);
        TextView importance = (TextView) view.findViewById(R.id.fragConfDetail_importance);

        Intent intent = getActivity().getIntent();
        title.setText(intent.getExtras().getString(Conference.CONFERENCE_TITLE));
        textBoxy.setText(intent.getExtras().getString(Conference.CONFERENCE_BODY));
        importance.setText(intent.getExtras().getString(Conference.CONFERENCE_IMPORTANCE));
        //Log.w("DoctorsConferences","******* conference_importance" + intent.getExtras().getString(Conference.CONFERENCE_IMPORTANCE));
        //importance.setText("8.8");



        return view;
    }

}
