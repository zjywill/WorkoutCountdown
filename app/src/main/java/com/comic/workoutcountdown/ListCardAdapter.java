package com.comic.workoutcountdown;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zjy on 11/17/14.
 */
public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ViewHolder> {

    private String[] mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mWorkoutTimeTextView;

        private View mPreparePanel;
        private View mRestPanel;

        public TextView mPrepareTitleTextView;
        public TextView mPrepareTimeTextView;

        public TextView mRestTitleTextView;
        public TextView mRestTimeTextView;


        public ViewHolder(View v) {
            super(v);
            mWorkoutTimeTextView = (TextView) v.findViewById(R.id.text_big_content);

            mPreparePanel = (View) v.findViewById(R.id.prepare_text);
            mPrepareTitleTextView = (TextView) mPreparePanel.findViewById(R.id.text_small_title);
            mPrepareTitleTextView.setText(R.string.prepare_title);
            mPrepareTimeTextView = (TextView) mPreparePanel.findViewById(R.id.text_small_content);

            mRestPanel = (View) v.findViewById(R.id.rest_text);
            mRestTitleTextView = (TextView) mRestPanel.findViewById(R.id.text_small_title);
            mRestTitleTextView.setText(R.string.rest_title);
            mRestTimeTextView = (TextView) mRestPanel.findViewById(R.id.text_small_content);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListCardAdapter(Context context, String[] myDataset) {
        mContext = context;
        mDataset = myDataset;
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
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
