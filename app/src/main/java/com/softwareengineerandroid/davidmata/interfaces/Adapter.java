package com.softwareengineerandroid.davidmata.interfaces;

import android.content.Context;
import android.telecom.Conference;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by davidmata on 26/10/2016.
 */

public interface Adapter  {

    void ConferenceAdapter(Context context, ArrayList<com.softwareengineerandroid.davidmata.model.conference.Conference> notas);
}
