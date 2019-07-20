package com.example.mareu.ui.meeting_list;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.ui.new_meeting.NewMeetingActivity;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    private List<Meeting> mMeetingList;
    protected MeetingApiService mMeetingApiService;


    public MyMeetingRecyclerViewAdapter(List<Meeting> items) {
        mMeetingList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mMeetingApiService = DI.getServiceMeet();
        Meeting meeting = mMeetingList.get(position);

        holder.mItemAvatar.setColorFilter(meeting.getMeetingColor());

        SimpleDateFormat dateFormat = new SimpleDateFormat ("H'h'mm");
        String mTimeMeeting =  dateFormat.format(meeting.getDate());
        holder.mtvInfoMeeting.setText(meeting.getTuto() + " - " + mTimeMeeting + " - " + meeting.getMeetingPoint());
        holder.mtvEmailMeeting.setText(meeting.getEmails().toString());

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewMeetingActivity.class);
                intent.putExtra("idMeeting",mMeetingApiService.getMeetingID(mMeetingList.get(position)));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_list_avatar)
        public AppCompatImageView mItemAvatar;

        @BindView(R.id.tvEmailMeeting)
        public TextView mtvEmailMeeting;

        @BindView(R.id.tvInfoMeeting)
        public TextView mtvInfoMeeting;

        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
