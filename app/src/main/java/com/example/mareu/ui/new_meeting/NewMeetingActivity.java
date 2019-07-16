package com.example.mareu.ui.new_meeting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewMeetingActivity extends AppCompatActivity {

    MeetingApiService mMeetingApiService;
    Meeting mMeeting;

    String mRoom,mTuto;
    List<String> mEmailList;
    int mHour,mMinutes,mMeetingID;
    boolean mIsItNew;

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

    @BindView(R.id.listViewEmail)
    ListView mListViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        ButterKnife.bind(this);

        mMeetingApiService = DI.getServiceMeet();
        mEmailList = new ArrayList<>();

        mIsItNew = isItNew();
        if (!mIsItNew){
            mMeeting = mMeetingApiService.getMeetings().get(mMeetingID);
            setUI();
        }
        configSpinnerRoom();

        mMeetingTime.setOnClickListener(view -> {
            Calendar mcurrentTime = Calendar.getInstance();
            if (mIsItNew){
                mHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                mMinutes = mcurrentTime.get(Calendar.MINUTE);
            }else{
                mHour = mMeeting.getHour();
                mMinutes = mMeeting.getMinutes();
            }


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
                showToast("tu compte faire ta réu dans les couloirs ??");
                //Toast.makeText(adapterView.getContext(),"tu compte faire ta réu dans les couloirs ??",Toast.LENGTH_SHORT).show();
            }
        });

        mfabAddEmail.setOnClickListener(view -> {
            if (mtvAddEmail.getText().length() == 0){
                showToast("Vous n'avez pas entrer d'adresse Mail");
                //Toast.makeText(view.getContext(),"Vous n'avez pas entrer d'adresse Mail",Toast.LENGTH_SHORT).show();
            }else if(!checkEmailValid(mtvAddEmail.getText().toString())){
                showToast("Ceci n'est pas une adresse Mail");
                //Toast.makeText(view.getContext(),"Vous n'avez pas entrer d'adresse Mail",Toast.LENGTH_SHORT).show();
            }

            else {
                mEmailList.add(mtvAddEmail.getText().toString());
                configListViewEmail();
                showToast(mtvAddEmail.getText().toString() + "à bien été ajouté");
                mtvAddEmail.getText().clear();
            }
        });

        mbtnValidNewMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mtvTuto.getText().length() == 0){
                    showToast("n'oubliez pas le sujet de la réunion");
                    //Toast.makeText(view.getContext(),"n'oubliez pas le sujet de la réunion",Toast.LENGTH_SHORT).show();
                }else if (mEmailList.isEmpty()) {
                    showToast("une réunion tout seul !! étrange");
                    //Toast.makeText(view.getContext(),"une réunion tout seul !! étrange",Toast.LENGTH_SHORT).show();
                }else {
                        mTuto = mtvTuto.getText().toString();
                        mMeeting = new Meeting();
                        mMeeting.setHour(mHour);
                        mMeeting.setMinutes(mMinutes);
                        mMeeting.setMeetingPoint(mRoom);
                        mMeeting.setTuto(mTuto);
                        mMeeting.setEmails(mEmailList);
                        saveMeetingAndBack();
                }
            }
        });
    }

    private void configSpinnerRoom(){
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mMeetingApiService.getMeetingPoints());
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRoom.setAdapter(dataAdapterR);
    }

    private boolean isItNew(){
        Intent intent = getIntent();
        if (intent == null){ return true; }
        else {mMeetingID = intent.getIntExtra("idMeeting",-1);}
        if (mMeetingID == -1){
            return true;
        }else {return false;}
    }

    private void setUI(){
        mMeetingTime.setText(mMeeting.getHour() + ":" + mMeeting.getMinutes());
        mSpinnerRoom.setPrompt(mMeeting.getMeetingPoint());
        mtvTuto.setText(mMeeting.getTuto());
        mEmailList = mMeeting.getEmails();
        configListViewEmail();
    }

    private void saveMeetingAndBack(){
        if (!mIsItNew){
            mMeetingApiService.getMeetings().remove(mMeetingID);
            mMeetingApiService.addMeeting(mMeeting);
            showToast("La réunion à bien été modifier");
            this.finish();

        }
        else if (mMeeting != null){
            mMeetingApiService.addMeeting(mMeeting);
            showToast("La réunion à bien été enregistrer");
            this.finish();
        }
    }

    private boolean checkEmailValid (String email){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        if (mat.matches()){
            return true;
        }else {
            return false;
        }
    }

    private void showToast (String messageToShow){
        Toast.makeText(this,messageToShow,Toast.LENGTH_SHORT).show();
    }

    private void configListViewEmail (){
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mEmailList);
        mListViewEmail.setAdapter(adapter);
    }
}
