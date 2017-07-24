package com.softwareengineerandroid.davidmata.model.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.softwareengineerandroid.davidmata.global.QueryUtils;
import com.softwareengineerandroid.davidmata.controller.doctorsconferences.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserList extends Fragment {


    public FragmentUserList() {
        // Required empty public constructor
    }

    final ArrayList<User> userCheckedList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        final ListView userListView = (ListView) view.findViewById(R.id.list_user_view);
        final ArrayList<User> user = QueryUtils.getAllUser(getContext());
        UserAdapter userAdapter = new UserAdapter(getActivity(),user);
        userListView.setAdapter(userAdapter);

       userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) userListView.getAdapter().getItem(position);
                CheckedTextView checkedTextView = (CheckedTextView)view.findViewById(R.id.list_user_item_checkUser);
                if(!checkedTextView.isChecked()){
                    checkedTextView.setChecked(true);
                    userCheckedList.add(user);
                }else{
                    checkedTextView.setChecked(false);
                    if(userCheckedList.size()>0){ //Evita excepcones
                        userCheckedList.remove(user);
                    }

                }
            }
        });



        return view;
    }

    public ArrayList<User> getListOfUsersInvitation(){
        return userCheckedList;
    }

}
