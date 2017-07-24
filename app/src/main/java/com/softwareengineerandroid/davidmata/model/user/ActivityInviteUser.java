package com.softwareengineerandroid.davidmata.model.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.softwareengineerandroid.davidmata.model.conference.Conference;
import com.softwareengineerandroid.davidmata.controller.doctorsconferences.MainActivity;
import com.softwareengineerandroid.davidmata.controller.doctorsconferences.R;
import com.softwareengineerandroid.davidmata.model.database.SQLModel;

import java.util.ArrayList;

public class ActivityInviteUser extends AppCompatActivity {
    FragmentUserList fragmentUserList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentUserList = new FragmentUserList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.listFragmentUser_container,fragmentUserList).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        //MenuItem item = menu.findItem(R.id.action_sendInvitations);
        return true;
    }

    private SQLModel sqlModel;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sendInvitations :
                Toast.makeText(this,"Send invitations",Toast.LENGTH_SHORT).show();

                ArrayList<User> arrayListUser = fragmentUserList.getListOfUsersInvitation();
                if(arrayListUser.size()>0){
                    Intent intent = getIntent();
                    int conferenceID = intent.getExtras().getInt(Conference.CONFERENCE_ID);
                    sqlModel = new SQLModel(this);
                    sqlModel.open();
                    for(int i=0; i<arrayListUser.size();i++){
                        sqlModel.createInvitation(conferenceID,arrayListUser.get(i).getId());
                    }
                    Intent intent1 = new Intent(this, MainActivity.class);
                    startActivity(intent1);
                    //ArrayList<Object[]> listConf = sqlModel.getConferenceData();*/
                }else{
                    Toast.makeText(this,"Please select at leat one user ",Toast.LENGTH_SHORT).show();
                }
                return true;


            default: return super.onOptionsItemSelected(item);

        }

    }


}
