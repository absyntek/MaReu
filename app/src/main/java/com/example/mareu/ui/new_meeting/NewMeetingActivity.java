package com.example.mareu.ui.new_meeting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mareu.utils.RandomColors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewMeetingActivity extends AppCompatActivity {

    private MeetingApiService mMeetingApiService;
    private Meeting mMeeting;
    private RandomColors mRandomColors;
    private SimpleDateFormat dateFormat;

    private List<Meeting> mMeetings;
    private String mRoom;
    private String mTuto;
    private List<String> mEmailList;
    private int mHour;
    private int mMinutes;
    private int mMeetingID;
    private boolean mIsItNew;
    private Date mDate;

    private final static long MEETING_TIME = 3602000;

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
        mDate = new Date();
        dateFormat = new SimpleDateFormat ("H'h'mm", Locale.FRENCH);

        mMeetingApiService = DI.getServiceMeet();
        mMeetings = mMeetingApiService.getMeetings();
        mEmailList = new ArrayList<>();

        mIsItNew = isItNew();

        if (!mIsItNew){
            mMeeting = mMeetings.get(mMeetingID);
            setUI();
        }

        configSpinnerRoom();

        Calendar mcurrentTime = Calendar.getInstance();
        Date actualDate = mcurrentTime.getTime();
        mMeetingTime.setText(dateFormat.format(actualDate));

        mMeetingTime.setOnClickListener(view -> {
            TimePickerDialog mTimePicker;
            mHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            mMinutes = mcurrentTime.get(Calendar.MINUTE);
            mTimePicker = new TimePickerDialog(NewMeetingActivity.this, (timePicker, selectedHour, selectedMinute) -> {

                try {
                    mDate = dateFormat.parse(selectedHour + "h" + selectedMinute);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mMeetingTime.setText( dateFormat.format(mDate));
            }, mHour, mMinutes, true);
            mTimePicker.setTitle("Selectionez l'heure");
            mTimePicker.show();
        });

        /*
          List of meeting rooms
         */
        mSpinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mRoom = String.valueOf(mSpinnerRoom.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                showToast("tu compte faire ta réu dans les couloirs ??");
            }
        });

        /*
          Add Email button
         */
        mfabAddEmail.setOnClickListener(view -> {
            if (mtvAddEmail.getText().length() == 0){
                showToast("Vous n'avez pas entrer d'adresse Mail");
            }else if(!checkEmailValid(mtvAddEmail.getText().toString())){
                showToast("Ceci n'est pas une adresse Mail");
            }

            else {
                mEmailList.add(mtvAddEmail.getText().toString());
                configListViewEmail();
                showToast(mtvAddEmail.getText().toString() + "à bien été ajouté");
                mtvAddEmail.getText().clear();
            }
        });

        /*
          Validation button
         */
        mbtnValidNewMeeting.setOnClickListener(view -> {
            if (isItPossible(mDate) != null){
                showToast("Tu à déjas une reunion à " + (dateFormat.format(isItPossible(mDate))));
            }else if (mtvTuto.getText().length() == 0){
                showToast("n'oubliez pas le sujet de la réunion");
            }else if (mEmailList.isEmpty()) {
                showToast("une réunion tout seul !! étrange");
            }else if (mEmailList.size()<2){
                showToast("il faut au moin 2 participant");
            } else {
                    mRandomColors = new RandomColors(view.getContext());
                    mTuto = mtvTuto.getText().toString();
                    mMeeting = new Meeting(mDate,mRoom,mTuto,mEmailList,mRandomColors.getColor());
                    saveMeetingAndBack();
            }
        });
    }

    private void configSpinnerRoom(){
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mMeetingApiService.getMeetingPoints());
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRoom.setAdapter(dataAdapterR);
    }

    private boolean isItNew(){
        Intent intent = getIntent();
        if (intent == null){ return true; }
        else {mMeetingID = intent.getIntExtra("idMeeting",-1);}
        return mMeetingID == -1;
    }

    private void setUI(){
        mMeetingTime.setText(dateFormat.format(mMeeting.getDate()));
        mSpinnerRoom.setPrompt(mMeeting.getMeetingPoint());
        mtvTuto.setText(mMeeting.getTuto());
        mEmailList = mMeeting.getEmails();
        configListViewEmail();

        mListViewEmail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ArrayList tmp = new ArrayList(mEmailList);
                tmp.remove(pos);
                mEmailList = new ArrayList<>(tmp);
                configListViewEmail();
                return true;
            }
        });
    }

    private void saveMeetingAndBack(){
        if (!mIsItNew){
            mMeetings.remove(mMeetingID);
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
        return mat.matches();
    }

    private void showToast (String messageToShow){
        Toast.makeText(this,messageToShow,Toast.LENGTH_SHORT).show();
    }

    private void configListViewEmail (){
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mEmailList);
        mListViewEmail.setAdapter(adapter);
    }

    /*
    If Meeting time is beetwin 1H02min the last one
    we return this meeting to tell customer he already have meeting at this time
     */
    private Date isItPossible (Date timeToCheck){

        for (Meeting meeting : mMeetings) {
            long different_milli = meeting.getDate().getTime() - timeToCheck.getTime();
            if (different_milli<MEETING_TIME && different_milli> -MEETING_TIME) { return meeting.getDate(); }
        }
        return null;
    }
}
