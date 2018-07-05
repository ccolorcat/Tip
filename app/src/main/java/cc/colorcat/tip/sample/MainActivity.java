package cc.colorcat.tip.sample;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cc.colorcat.tip.Tip;

public class MainActivity extends AppCompatActivity implements Tip.OnTipClickListener {
    private static final int CAPACITY = 30;

    private Random mRandom = new Random();
    private SwipeRefreshLayout mRefreshLayout;
    private List<String> mData = new ArrayList<>(CAPACITY);
    private BaseAdapter mAdapter;
    private Tip mTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new SimpleAdapter(mData);
        ListView listView = findViewById(R.id.lv_content);
        listView.setAdapter(mAdapter);

        mRefreshLayout = findViewById(R.id.srl_layout);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(mRandom.nextBoolean());
            }
        });
        refresh(false);
    }

    @Override
    public void onTipClick() {
        refresh(mRandom.nextBoolean());
    }

    private void refresh(boolean networkError) {
        mData.clear();
        if (networkError) {
            mAdapter.notifyDataSetChanged();
            lazyTip().showTip();
        } else {
            for (int i = 0; i < CAPACITY; ++i) {
                mData.add("item " + i);
            }
            mAdapter.notifyDataSetChanged();
            lazyTip().hideTip();
        }
        mRefreshLayout.setRefreshing(false);
    }

    private Tip lazyTip() {
        if (mTip == null) {
            mTip = Tip.from(mRefreshLayout, R.layout.network_error, this);
//            mTip = Tip.from(this, R.layout.network_error, this); // or
        }
        return mTip;
    }
}
