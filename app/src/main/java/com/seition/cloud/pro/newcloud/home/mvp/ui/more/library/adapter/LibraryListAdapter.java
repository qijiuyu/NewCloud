package com.seition.cloud.pro.newcloud.home.mvp.ui.more.library.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.download.InitDownloadBean;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.app.utils.download.DBUtils;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.view_holder.DownloadViewHolder;

/**
 * Created by addis on 2018/3/21.
 */

public class LibraryListAdapter extends BaseQuickAdapter<LibraryItemBean, DownloadViewHolder> {
    DownloadViewHolder holder;
    boolean isMe;

    public LibraryListAdapter() {
        super(R.layout.item_library_list);
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    @Override
    protected void convert(DownloadViewHolder helper, LibraryItemBean item) {
        helper.setmContext(mContext);
        holder = helper;
        item.setExtension(item.getAttach_info().getExtension());
        item.setDownloadbean(DBUtils.init(mContext).queryCacheKeyOne(InitDownloadBean.initDownloadBean(item)));
        helper.setText(R.id.book_name, item.getTitle());
        helper.setText(R.id.updata_time, "兑换次数：" + item.getAxchange_num() + " 次");
        helper.setText(R.id.exchange_integral, "所需积分：" + item.getPrice());

        helper.showCover(item);
        helper.showBuy(item);
        if (isMe) helper.setVisible(R.id.exchange_integral, false);
    }

    public DownloadViewHolder getHelper() {
        return holder;
    }
}
