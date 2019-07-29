package com.example.mareu.ui.meeting_list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Collections;
import java.util.List;


public class MeetingFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MeetingApiService mMeetingApiService;
    private List<Meeting> mMeetingList;
    private String mSortBy = "NONE";


    public MeetingFragment() {
    }

    public static MeetingFragment newInstance() {
        return new MeetingFragment();
    }

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { //TODO est-il possible d'éviter cela ??
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("SORT_ACTION")){
                mSortBy = intent.getStringExtra("SORTBY");
            }
            initList();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver, new IntentFilter("SORT_ACTION"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);

        // Set the adapter
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mMeetingApiService = DI.getServiceMeet();
        mMeetingList = mMeetingApiService.getMeetings();
        initList();
        super.onActivityCreated(savedInstanceState);
    }

    private void initList (){
        if (mSortBy != null) {
            switch (mSortBy) {
                case "ROOM":
                    Collections.sort(mMeetingList, new DummyMeetingApiService.MeetingComparatorRoom());
                    break;
                case "DATE":
                    Collections.sort(mMeetingList, new DummyMeetingApiService.MeetingComparatorTime());
                    break;
                case "NONE":
                    break;
            }
        }
        mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mMeetingList));

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        initList();
        super.onResume();
    }

    @Subscribe
    public void onDeleteNeighbour(DeleteMeetingEvent event) {
        mMeetingApiService.deletMeeting(event.mMeeting);
        initList();
    }
}
