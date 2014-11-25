package com.comic.workoutcountdown;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.comic.workoutcountdown.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private ListCardAdapter mAdapter;
    private Button mNewButton;
    private List<CountdownData> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewButton = (Button) findViewById(R.id.new_button);
        mNewButton.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.section_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mItemDecoration = new DividerDecoration(this);
        mRecyclerView.addItemDecoration(mItemDecoration);

        mAdapter = new ListCardAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        new GetDataTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: {
                Loge.d("Action add clicked");
                Intent intent = new Intent();
                intent.setClass(this, EditActivity.class);
                startActivityForResult(intent, 1001);
            }
            break;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this, EditActivity.class);
        startActivityForResult(intent, 1001);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Loge.d("requestCode: " + requestCode + " resultCode: " + resultCode);
        refresh();
    }

    public void refresh() {
        new GetDataTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            if (mDataset == null) {
                mDataset = new ArrayList<CountdownData>();
            }
            mDataset.clear();
            mDataset.addAll(DatabaseUtils.getCountDownData(MainActivity.this));
            Loge.d("mDataset size: "+mDataset.size());
            return mDataset.size() > 0 ? "ok" : "fail";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("ok")) {
                mAdapter.setData(mDataset);
                mAdapter.notifyDataSetChanged();

                mRecyclerView.setVisibility(View.VISIBLE);
                mNewButton.setVisibility(View.GONE);
            } else {
                mNewButton.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        }
    }
}
