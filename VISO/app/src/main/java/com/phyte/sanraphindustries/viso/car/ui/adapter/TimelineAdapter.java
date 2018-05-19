package com.phyte.sanraphindustries.viso.car.ui.adapter;

import android.content.Context;
import android.support.v4.graphics.ColorUtils;
import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.phyte.sanraphindustries.viso.R;
import com.phyte.sanraphindustries.viso.car.consts.Consts;
import com.phyte.sanraphindustries.viso.car.entity.ConsumerDetail;
import com.phyte.sanraphindustries.viso.car.utils.DateUtil;
import com.phyte.sanraphindustries.viso.car.utils.Util;
import com.phyte.sanraphindustries.viso.car.widget.CircleImageView;
import java.util.List;
import rx.functions.Action1;

/**
 *
 Application Name: CarAssistant
 * Package Name: com.classic.car.ui.adapter
 *
 * File Description:TODO
 * Created by: Continued to write classic
 * Created: 16/6/28 6:22 PM
 */
public class TimelineAdapter extends CommonRecyclerAdapter<ConsumerDetail>
        implements Action1<List<ConsumerDetail>> {
    private static final int ALPHA = 100;

    private       Context mContext;
    private final int     mLayoutResId;

    public TimelineAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
        this.mContext = context.getApplicationContext();
        this.mLayoutResId = layoutResId;
    }

    @SuppressWarnings("deprecation") @Override public void onUpdate(BaseAdapterHelper helper, ConsumerDetail item,
                                                                    int position) {
        if (position == 0) return;
        CircleImageView civ = helper.getView(R.id.item_timeline_icon_bg);
        int color = mContext.getResources().getColor(Util.getColorByType(item.getType()));
        civ.setFillColor(color);
        civ.setBorderColor(ColorUtils.setAlphaComponent(color, ALPHA));
        helper.setText(R.id.item_timeline_time,
                DateUtil.formatDate(DateUtil.FORMAT_DATE, item.getConsumptionTime()) + "\n" +
                        DateUtil.formatDate(DateUtil.FORMAT_TIME, item.getConsumptionTime()))
              .setImageResource(R.id.item_timeline_icon, Util.getIconByType(item.getType()))
              .setText(R.id.item_timeline_content, Consts.TYPE_MENUS[item.getType()])
              .setText(R.id.item_timeline_money, Util.formatMoney(item.getMoney()))
              .setTextColorRes(R.id.item_timeline_content, Util.getColorByType(item.getType()))
              .setTextColorRes(R.id.item_timeline_money, Util.getColorByType(item.getType()));
    }

    @Override public void call(List<ConsumerDetail> list) {
        list.add(0, new ConsumerDetail());
        replaceAll(list);
    }

    @Override public int getLayoutResId(ConsumerDetail item, int position) {
        return position == 0 ? R.layout.item_header : mLayoutResId;
    }
}
