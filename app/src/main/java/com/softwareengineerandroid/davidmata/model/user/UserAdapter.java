package com.softwareengineerandroid.davidmata.model.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.softwareengineerandroid.davidmata.controller.doctorsconferences.R;

import java.util.ArrayList;

/**
 * Created by davidmata on 19/10/2016.
 */

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Context context, ArrayList<User> users){

        super(context,0,users);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_user_item,parent,false);
            Log.w("AppList","converView es null");
        }
        User user = getItem(position);

       final CheckedTextView userChecked = (CheckedTextView)convertView.findViewById(R.id.list_user_item_checkUser);
        userChecked.setText(user.getUser());
       /*userChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userChecked.isChecked()){
                    Toast.makeText(getContext(),"UNchecked",Toast.LENGTH_SHORT).show();
                    userChecked.setChecked(false);
                }else{
                    Toast.makeText(getContext(),"checked",Toast.LENGTH_SHORT).show();
                    userChecked.setChecked(true);
                }
            }
        });*/
        return convertView;//super.getView(position, convertView, parent);
    }
}
