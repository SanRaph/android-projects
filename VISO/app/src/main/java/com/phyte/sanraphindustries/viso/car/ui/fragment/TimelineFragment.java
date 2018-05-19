package com.phyte.sanraphindustries.viso.car.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.classic.adapter.CommonRecyclerAdapter;
import com.phyte.sanraphindustries.viso.R;
import com.phyte.sanraphindustries.viso.car.app.CarApplication;
import com.phyte.sanraphindustries.viso.car.db.dao.ConsumerDao;
import com.phyte.sanraphindustries.viso.car.entity.ConsumerDetail;
import com.phyte.sanraphindustries.viso.car.ui.activity.MainActivity;
import com.phyte.sanraphindustries.viso.car.ui.adapter.TimelineAdapter;
import com.phyte.sanraphindustries.viso.car.ui.base.AppBaseFragment;
import com.phyte.sanraphindustries.viso.car.utils.RxUtil;
import java.util.List;
import javax.inject.Inject;
import rx.Subscription;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.ui.fragment
 *
 * File Description: Timeline page
 * Created by: Continued to write classic
 * Created: 16:5/29 2:21 PM
 */
public class TimelineFragment extends AppBaseFragment {

    @BindView(R.id.timeline_recycler_view) RecyclerView mRecyclerView;
    @Inject ConsumerDao     mConsumerDao;
    private TimelineAdapter mAdapter;

    public static TimelineFragment newInstance() {
        return new TimelineFragment();
    }

    @Override public int getLayoutResId() {
        return R.layout.fragment_timeline;
    }

    @Override public void initView(View parentView, Bundle savedInstanceState) {
        ((CarApplication)mActivity.getApplicationContext()).getAppComponent().inject(this);
        super.initView(parentView, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mAppContext));
        mRecyclerView.addOnScrollListener(new CommonRecyclerAdapter.AbsScrollControl() {
            @Override public void onShow() {
                ((MainActivity)mActivity).onShow();
            }

            @Override public void onHide() {
                ((MainActivity)mActivity).onHide();
            }
        });
        mAdapter = new TimelineAdapter(mAppContext, R.layout.item_timeline);
        mRecyclerView.setAdapter(mAdapter);

        addSubscription(loadData());
    }

    private Subscription loadData(){
        return mConsumerDao.queryAll()
                           .compose(RxUtil.<List<ConsumerDetail>>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                           .subscribe(mAdapter, RxUtil.ERROR_ACTION);
    }
}
