package com.example.mareu.ui.new_meeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.utils.RandomColors;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewMeetingActivity extends AppCompatActivity implements View.OnClickListener{

    private MeetingApiService mMeetingApiService;
    private Meeting mMeeting;
    private RandomColors mRandomColors;
    private SimpleDateFormat mSimpleDateFormat;
    private Calendar mcurrentTime;

    private List<Meeting> mMeetings;
    private List<String> mEmailList;
    private String mRoom, mTuto;
    private int mMeetingID,mColor;
    private boolean mIsItNew;
    private Date mDate, mActualDate;

    private final static long MEETING_TIME = 3602000;

    @BindView(R.id.tvMeetingDate)
    TextInputEditText mMeetingDate;

    @BindView(R.id.spinnerRoom)
    MaterialBetterSpinner mSpinnerRoom;

    @BindView(R.id.tvTuto)
    TextInputEditText mtvTuto;

    @BindView(R.id.tvAddEmail)
    TextInputEditText mtvAddEmail;

    @BindView(R.id.btnValidNewMeeting)
    Button mbtnValidNewMeeting;

    @BindView(R.id.chipGroupEmail)
    ChipGroup mChipGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        ButterKnife.bind(this);
        configVars();

        mIsItNew = isItNew();      // Check if we modifying a meeting or macking new one
        if (!mIsItNew){
            mMeeting = mMeetings.get(mMeetingID);
            setUiIfEdit();
        }else {
            mRandomColors = new RandomColors(this);
            mColor = mRandomColors.getColor();
        }

        configAndSetUpSpinnerRoom();
        setUpDateListener();
        setUpMailButton();
        setUpValidButton();

    }

    private void configVars() {
        mDate = null;
        mSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy H'h'mm", Locale.FRENCH);
        mMeetingApiService = DI.getServiceMeet();
        mMeetings = mMeetingApiService.getMeetings();
        mEmailList = new ArrayList<>();
        mcurrentTime = Calendar.getInstance();
        mActualDate = mcurrentTime.getTime();
    }

    private boolean isItNew(){
        Intent intent = getIntent();
        if (intent == null){ return true; }
        else {mMeetingID = intent.getIntExtra("idMeeting",-1);}
        return mMeetingID == -1;
    }

    private void setUiIfEdit(){
        mColor = mMeeting.getMeetingColor();
        mDate = mMeeting.getDate();
        mMeetingDate.setText(mSimpleDateFormat.format(mMeeting.getDate()));
        mSpinnerRoom.setText(mMeeting.getMeetingPoint());
        mtvTuto.setText(mMeeting.getTuto());
        mEmailList = mMeeting.getEmails();
        configListViewEmail();
    }

    private void configAndSetUpSpinnerRoom(){
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mMeetingApiService.getMeetingPoints());
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRoom.setAdapter(dataAdapterR);
    }

    private void setUpDateListener(){
        mMeetingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NewMeetingActivity.this, mDateDataSet, mcurrentTime.get(Calendar.YEAR),
                        mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    /* After user decided on a date, store those in our calendar variable and then start the TimePickerDialog immediately */
    private final DatePickerDialog.OnDateSetListener mDateDataSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mcurrentTime.set(Calendar.YEAR, year);
            mcurrentTime.set(Calendar.MONTH, monthOfYear);
            mcurrentTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new TimePickerDialog(NewMeetingActivity.this, mTimeDataSet, mcurrentTime.get(Calendar.HOUR_OF_DAY), mcurrentTime.get(Calendar.MINUTE), true).show();
        }
    };

    /* After user decided on a time, save them into Date*/
    private final TimePickerDialog.OnTimeSetListener mTimeDataSet = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mcurrentTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mcurrentTime.set(Calendar.MINUTE, minute);
            mDate = mcurrentTime.getTime();
            mMeetingDate.setText(mSimpleDateFormat.format(mcurrentTime.getTime()));
        }
    };

    private void setUpMailButton(){
        mtvAddEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE && checkEmailValid(mtvAddEmail.getText().toString())){
                    creatChip(mtvAddEmail.getText().toString());
                    mEmailList.add(mtvAddEmail.getText().toString());
                    mtvAddEmail.getText().clear();
                    return true;
                }
                return false;
            }
        });
    }

    private void setUpValidButton(){
        mbtnValidNewMeeting.setOnClickListener(view -> {

            mRoom = mSpinnerRoom.getText().toString();
            mTuto = mtvTuto.getText().toString();

            if (mDate == null){
                showToast("Tu n'à pas entré(e) de date");
            }else if (mDate.before(mActualDate)){
                showToast("La date que tu à entré est passé");
            }else if (isItPossible(mDate) != null){
                showToast("Tu à déjas une reunion le " + (mSimpleDateFormat.format(Objects.requireNonNull(isItPossible(mDate)))));
            }else if (mRoom.isEmpty()){
                showToast("N'oubli pas la salle de réu");
            }else if (mTuto.length() == 0){
                    showToast("n'oubliez pas le sujet de la réunion");
            }else if (mEmailList.isEmpty()) {
                showToast("une réunion tout seul !! étrange");
            }else if (mEmailList.size()<2){
                showToast("il faut au moin 2 participant");
            } else {
                mMeeting = new Meeting(mDate,mRoom,mTuto,mEmailList,mColor);
                saveMeetingAndBack();
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


    // --------- UTLIS --------- //

    /*
    Check email form is valide
     */
    private boolean checkEmailValid (String email){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        if (email.length() == 0){
            showToast("il n'y à rien d'écrit");
        }else if (!mat.matches()){
            showToast("Cette adresse mail n'est pas valid");
        }
        return mat.matches();
    }

    private void showToast (String messageToShow){
        Toast.makeText(this,messageToShow,Toast.LENGTH_SHORT).show();
    }

    private void configListViewEmail (){
        for (String email : mEmailList){
            creatChip(email);
        }
    }

    /*
    If Meeting time is beetwin 1H02min the last one
    we return this meeting to tell customer he already have meeting at this time
     */
    private Date isItPossible (Date timeToCheck){
        for (Meeting meeting : mMeetings) {
            if (!mIsItNew && !meeting.equals(mMeeting)){
                long different_milli = meeting.getDate().getTime() - timeToCheck.getTime();
                if (different_milli<MEETING_TIME && different_milli> -MEETING_TIME) { return meeting.getDate(); }
            }
        }
        return null;
    }

    private void creatChip (String email){
        Chip chip = new Chip(this);
        chip.setText(email);
        chip.setCloseIconVisible(true);
        chip.setCheckable(false);
        chip.setClickable(false);
        chip.setChipIcon(ContextCompat.getDrawable(this,R.drawable.round_contact_mail_black_24dp));
        chip.setOnCloseIconClickListener(this);
        mChipGroup.addView(chip);
    }

    @Override
    public void onClick(View view) {
        Chip chip = (Chip) view;
        mChipGroup.removeView(chip);
        ArrayList tmp = new ArrayList(mEmailList);
        tmp.remove(chip.getText());
        mEmailList = new ArrayList<>(tmp);
    }
}
