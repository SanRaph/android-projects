package com.phyte.sanraphindustries.viso.car.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

//import com.phyte.sanraphindustries.viso.car.ui.base.//.android.base.BaseActivity;
import com.phyte.sanraphindustries.viso.R;
import com.phyte.sanraphindustries.viso.car.app.CarApplication;
import com.phyte.sanraphindustries.viso.car.consts.Consts;
import com.phyte.sanraphindustries.viso.car.db.dao.ConsumerDao;
import com.phyte.sanraphindustries.viso.car.entity.ChartType;
import com.phyte.sanraphindustries.viso.car.entity.ConsumerDetail;
import com.phyte.sanraphindustries.viso.car.ui.chart.BarChartDisplayImpl;
import com.phyte.sanraphindustries.viso.car.ui.chart.IChartDisplay;
import com.phyte.sanraphindustries.viso.car.ui.chart.LineChartDisplayImpl;
import com.phyte.sanraphindustries.viso.car.ui.chart.PieChartDisplayImpl;
import com.phyte.sanraphindustries.viso.car.utils.RxUtil;
import com.phyte.sanraphindustries.viso.car.utils.ToastUtil;
import com.phyte.sanraphindustries.viso.car.utils.Util;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.ui.activity
 *
 * File Description: TODO
 * Creator: Write a classic
 * Created: 2017/3/25 10:53
 */
@SuppressWarnings("unchecked") public class ChartActivity extends BaseActivity {
    private static final String PARAMS_CHART_TYPE = "chartType";
    private static final String PARAMS_START_TIME = "startTime";
    private static final String PARAMS_END_TIME   = "endTime";
    private static final int    ANIMATE_DURATION  = 400;

    @Inject ConsumerDao mConsumerDao;
    @BindView(R.id.chart_download) ImageButton mDownloadBtn;

    private Context       mAppContext;
    private IChartDisplay mChartDisplay;
    private Integer       mDataType;
    private int           mChartType;
    private long          mStartTime;
    private long          mEndTime;
    private Chart         mChart;
    private boolean       isAsc;

    private CompositeSubscription mCompositeSubscription;

    @BindView(R.id.chart_layout) FrameLayout mChartLayout;

    public static void start(@NonNull Activity activity, @ChartType int chartType, long startTime,
                             long endTime) {
        Intent intent = new Intent(activity, ChartActivity.class);
        intent.putExtra(PARAMS_CHART_TYPE, chartType);
        intent.putExtra(PARAMS_START_TIME, startTime);
        intent.putExtra(PARAMS_END_TIME, endTime);
        activity.startActivity(intent);
    }

    @Override public int getLayoutResId() {
        return R.layout.activity_chart;
    }

    @Override public void initView(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.initView(savedInstanceState);
        ((CarApplication) mActivity.getApplicationContext()).getAppComponent().inject(this);
        ButterKnife.bind(this);
        mAppContext = getApplicationContext();
        mCompositeSubscription = new CompositeSubscription();
        getParams();
        createChart(mChartType);
    }

    @Override protected void onStart() {
        super.onStart();
        loadData();
    }

    @Override protected void onStop() {
        super.onStop();
        if (null != mCompositeSubscription) {
            mCompositeSubscription.clear();
        }
    }

    private void getParams() {
        if (!getIntent().hasExtra(PARAMS_CHART_TYPE)) {
            finish();
            return;
        }
        mChartType = getIntent().getIntExtra(PARAMS_CHART_TYPE, ChartType.BAR_CHART);
        mStartTime = getIntent().getLongExtra(PARAMS_START_TIME, 0);
        mEndTime = getIntent().getLongExtra(PARAMS_END_TIME, 0);
        if (mChartType == ChartType.LINE_CHART) {
            mDataType = Consts.TYPE_FUEL;
            isAsc = true;
        }
    }

    private Subscription loadData() {
        return mConsumerDao.query(mDataType, mStartTime, mEndTime, false, isAsc)
                           .compose(RxUtil.<List<ConsumerDetail>>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                           .map(new Func1<List<ConsumerDetail>, Object>() {
                               @Override public Object call(List<ConsumerDetail> list) {
                                   return mChartDisplay.convert(list);
                               }
                           })
                           .subscribe(new Action1<Object>() {
                               @Override public void call(Object data) {
                                   mDownloadBtn.setVisibility(null == data ? View.GONE : View.VISIBLE);
                                   mChartDisplay.animationDisplay(mChart, data, ANIMATE_DURATION);
                               }
                           }, RxUtil.ERROR_ACTION);
    }

    @SuppressWarnings("unused") @OnClick(R.id.chart_back) void onBack(View view) {
        finish();
    }

    @SuppressWarnings("unused") @OnClick(R.id.chart_download) void download(View view) {
        if (null == mChart) { return; }
        mCompositeSubscription.add(Observable.unsafeCreate(new Observable.OnSubscribe<Boolean>() {
                                       @Override public void call(Subscriber<? super Boolean> subscriber) {
                                           if (!subscriber.isUnsubscribed()) {
                                               subscriber.onNext(mChart.saveToGallery(Util.createImageName(),
                                                                                      IChartDisplay.QUALITY));
                                               subscriber.onCompleted();
                                           }
                                       }
                                   })
                                   .compose(RxUtil.<Boolean>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                                   .subscribe(new Action1<Boolean>() {
                                       @Override public void call(Boolean result) {
                                           ToastUtil.showToast(mAppContext, result ?
                                                   R.string.chart_save_success : R.string.chart_save_fail);
                                       }
                                   }, new Action1<Throwable>() {
                                       @Override public void call(Throwable throwable) {
                                           ToastUtil.showToast(mAppContext, R.string.chart_save_fail);
                                       }
                                   }));
    }

    private void createChart(int chartType) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                                       FrameLayout.LayoutParams.MATCH_PARENT);
        final int margin = Util.dp2px(mAppContext, 16);
        params.setMargins(margin, margin, margin, margin);
        switch (chartType) {
            case ChartType.BAR_CHART:
                mChart = new BarChart(mChartLayout.getContext());
                mChart.setLayoutParams(params);
                mChartDisplay = new BarChartDisplayImpl();
                break;
            case ChartType.PIE_CHART:
                mChart = new PieChart(mChartLayout.getContext());
                mChart.setLayoutParams(params);
                mChartDisplay = new PieChartDisplayImpl();
                break;
            case ChartType.LINE_CHART:
                mChart = new LineChart(mChartLayout.getContext());
                mChart.setLayoutParams(params);
                mChartDisplay = new LineChartDisplayImpl();
                break;
        }
        mChartDisplay.init(mChart, true);
        if (null != mChart) {
            mChartLayout.addView(mChart, 0);
        }
    }
}
