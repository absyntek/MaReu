package com.example.mareu.ui.meeting_list;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;

import java.util.ArrayList;
import java.util.List;


public class MeetingFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected List<Meeting> mMeetings;
    protected MeetingApiService mMeetingApiService;


    public MeetingFragment() {
    }

    public static MeetingFragment newInstance() {
        return new MeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mMeetings = new ArrayList<>();
        initList();
        super.onActivityCreated(savedInstanceState);
    }

    private void initList (){
        if (mMeetingApiService.getMeetings() != null){
            mMeetings = mMeetingApiService.getMeetings();
            mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mMeetings));
        }
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        initList();
        super.onResume();
    }
}
