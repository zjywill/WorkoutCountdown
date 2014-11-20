package com.comic.workoutcountdown;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.comic.workoutcountdown.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private ListCardAdapter mAdapter;
    private List<CountdownData> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                startActivityForResult(intent, 1);
            }
            break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            new GetDataTask().execute();
        }
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
            return mDataset.size() > 0 ? "ok" : "fail";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("ok")) {
                mAdapter.setData(mDataset);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
