package com.example.mareu.ui.new_meeting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewMeetingActivity extends AppCompatActivity {

    MeetingApiService mMeetingApiService;
    Meeting mMeeting;

    String mRoom,mTuto;
    List<String> mEmailList;
    int mHour,mMinutes;

    @BindView(R.id.tvMeetingTime)
    EditText mMeetingTime;

    @BindView(R.id.spinnerRoom)
    Spinner mSpinnerRoom;

    @BindView(R.id.tvTuto)
    EditText mtvTuto;

    @BindView(R.id.tvAddEmail)
    EditText mtvAddEmail;

    @BindView(R.id.fabAddEmail)
    FloatingActionButton mfabAddEmail;

    @BindView(R.id.btnValidNewMeeting)
    Button mbtnValidNewMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        ButterKnife.bind(this);

        mMeetingApiService = DI.getServiceMeet();
        mEmailList = new ArrayList<>();

        configSpinnerRoom();

        mMeetingTime.setOnClickListener(view -> {
            Calendar mcurrentTime = Calendar.getInstance();
            mHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            mMinutes = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(NewMeetingActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                mMeetingTime.setText( selectedHour + ":" + selectedMinute);
                mHour = selectedHour;
                mMinutes = selectedMinute;
            }, mHour, mMinutes, true);
            mTimePicker.setTitle("Selectionez l'heure");
            mTimePicker.show();
        });

        mSpinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mRoom = String.valueOf(mSpinnerRoom.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(adapterView.getContext(),"tu compte faire ta réu dans les couloirs ??",Toast.LENGTH_SHORT).show();
            }
        });

        mfabAddEmail.setOnClickListener(view -> {
            if (mtvAddEmail.getText() == null){
                Toast.makeText(view.getContext(),"Vous n'avez pas entrer d'adresse Mail",Toast.LENGTH_SHORT).show();
            }else {
                mEmailList.add(mtvAddEmail.getText().toString());
            }
        });

        mbtnValidNewMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mtvTuto.getText() == null){
                    Toast.makeText(view.getContext(),"n'oubliez pas le sujet de la réunion",Toast.LENGTH_SHORT).show();
                }else if (mEmailList.isEmpty()) {
                    Toast.makeText(view.getContext(),"une réunion tout seul !! étrange",Toast.LENGTH_SHORT).show();
                }else {
                        mTuto = mtvTuto.getText().toString();
                        mMeeting = new Meeting();
                        mMeeting.setHour(mHour);
                        mMeeting.setMinutes(mMinutes);
                        mMeeting.setMeetingPoint(mRoom);
                        mMeeting.setTuto(mTuto);
                        mMeeting.setEmails(mEmailList);
                        saveMeetingandback();
                }
            }
        });
    }

    private void configSpinnerRoom(){
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mMeetingApiService.getMeetingPoints());
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRoom.setAdapter(dataAdapterR);
    }

    private void saveMeetingandback(){
        if (mMeeting != null){
            mMeetingApiService.addMeeting(mMeeting);
            this.finish();
        }
    }
}
