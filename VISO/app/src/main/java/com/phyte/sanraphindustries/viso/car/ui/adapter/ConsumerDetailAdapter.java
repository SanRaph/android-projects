package com.phyte.sanraphindustries.viso.car.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.phyte.sanraphindustries.viso.R;
import com.phyte.sanraphindustries.viso.car.consts.Consts;
import com.phyte.sanraphindustries.viso.car.entity.ConsumerDetail;
import com.phyte.sanraphindustries.viso.car.utils.DateUtil;
import com.phyte.sanraphindustries.viso.car.utils.Util;
import java.util.List;
import rx.functions.Action1;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.ui.adapter
 *
 * File Description:TODO
 * Created by: Continued to write classic
 * Created: 16/6/28 6:22 PM
 */
public class ConsumerDetailAdapter extends CommonRecyclerAdapter<ConsumerDetail>
        implements Action1<List<ConsumerDetail>> {

    private final int mLayoutResId;

    public ConsumerDetailAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
        this.mLayoutResId = layoutResId;
    }

    @Override public void onUpdate(BaseAdapterHelper helper, ConsumerDetail item, int position) {
        if(position==0) return;
        final boolean isNotesEmpty = TextUtils.isEmpty(item.getNotes());
        helper.setText(R.id.item_consumer_detail_money, Util.formatMoney(item.getMoney()))
              .setText(R.id.item_consumer_detail_tag, Consts.TYPE_MENUS[item.getType()])
              .setText(R.id.item_consumer_detail_time,
                      DateUtil.formatDate(DateUtil.FORMAT_DATE, item.getConsumptionTime()))
              .setText(R.id.item_consumer_detail_notes, item.getNotes())
              .setBackgroundRes(R.id.item_consumer_detail_top_layout, Util.getBackgroundByType(item.getType()))
              .setTextColorRes(R.id.item_consumer_detail_tag, Util.getColorByType(item.getType()))
              .setImageResource(R.id.item_consumer_detail_icon, Util.getIconByType(item.getType()))
              .setVisible(R.id.item_consumer_detail_notes, !isNotesEmpty)
              .setVisible(R.id.item_consumer_detail_notes_icon, !isNotesEmpty);
    }

    @Override public void call(List<ConsumerDetail> list) {
        list.add(0, new ConsumerDetail());
        replaceAll(list);
    }

    @Override public int getLayoutResId(ConsumerDetail item, int position) {
        return position==0 ? R.layout.item_header : mLayoutResId;
    }
}
