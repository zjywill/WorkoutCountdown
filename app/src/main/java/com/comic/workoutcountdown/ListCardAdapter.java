package com.comic.workoutcountdown;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.comic.workoutcountdown.utils.DatabaseUtils;
import com.comic.workoutcountdown.utils.Utils;

import java.util.List;

/**
 * Created by zjy on 11/17/14.
 */
public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ViewHolder> {

    private List<CountdownData> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mWorkoutTitleTextView;
        public TextView mWorkoutTimeTextView;

        private View mPreparePanel;
        private View mRestPanel;

        public TextView mPrepareTitleTextView;
        public TextView mPrepareTimeTextView;

        public TextView mRestTitleTextView;
        public TextView mRestTimeTextView;

        public ImageButton mDeleteButton;
        public ImageButton mEditButton;
        public Button mStartButton;


        public ViewHolder(View v) {
            super(v);
            mWorkoutTitleTextView = (TextView) v.findViewById(R.id.text_big_title);
            mWorkoutTimeTextView = (TextView) v.findViewById(R.id.text_big_content);

            mPreparePanel = (View) v.findViewById(R.id.prepare_text);
            mPrepareTitleTextView = (TextView) mPreparePanel.findViewById(R.id.text_small_title);
            mPrepareTitleTextView.setText(R.string.prepare_title);
            mPrepareTimeTextView = (TextView) mPreparePanel.findViewById(R.id.text_small_content);

            mRestPanel = (View) v.findViewById(R.id.rest_text);
            mRestTitleTextView = (TextView) mRestPanel.findViewById(R.id.text_small_title);
            mRestTitleTextView.setText(R.string.rest_title);
            mRestTimeTextView = (TextView) mRestPanel.findViewById(R.id.text_small_content);

            mEditButton = (ImageButton) v.findViewById(R.id.edit_button);
            mDeleteButton = (ImageButton) v.findViewById(R.id.delete_button);
            mStartButton = (Button) v.findViewById(R.id.start_button);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListCardAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<CountdownData> data) {
        mDataset = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if (mDataset != null && holder != null && mDataset.size() > position) {
            final CountdownData itemData = mDataset.get(position);

            holder.mWorkoutTitleTextView.setText(itemData.getName());
            holder.mWorkoutTimeTextView.setText(Utils.formatTimeText(itemData.getWorkoutTime()));
            holder.mRestTimeTextView.setText(Utils.formatTimeText(itemData.getRestTime()));
            holder.mPrepareTimeTextView.setText(Utils.formatTimeText(itemData.getPrepareTime()));

            holder.mStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, CountdownActivity.class);
                    putData(intent, itemData);
                    mContext.startActivity(intent);
                }
            });


            holder.mEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, EditActivity.class);
                    putData(intent, itemData);
                    ((MainActivity) mContext).startActivityForResult(intent, 1001);
                }
            });


            holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseUtils.deleteCountdownData(mContext, itemData.getId());
                    ((MainActivity) mContext).refresh();
                }
            });

        }
    }

    private void putData(Intent intent, CountdownData countdownData) {
        if (intent != null) {
            intent.putExtra(CountdownData.KEY_TIMER_ID, countdownData.getId());
            intent.putExtra(CountdownData.KEY_TIMER_WORKOUT_NAME, countdownData.getName());
            intent.putExtra(CountdownData.KEY_TIMER_WORKOUT_TIME, countdownData.getWorkoutTime());
            intent.putExtra(CountdownData.KEY_TIMER_PREPARE_TIME, countdownData.getPrepareTime());
            intent.putExtra(CountdownData.KEY_TIMER_REST_TIME, countdownData.getRestTime());
            intent.putExtra(CountdownData.KEY_TIMER_SETS, countdownData.getSets());
            intent.putExtra(CountdownData.KEY_TIMER_REPET, countdownData.getRepets());
            intent.putExtra(CountdownData.KEY_TIMER_CREATE_DATE, countdownData.getCreateTime());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }
}
