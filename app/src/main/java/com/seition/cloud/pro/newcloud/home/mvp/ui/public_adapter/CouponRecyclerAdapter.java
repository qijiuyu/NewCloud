package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

public class CouponRecyclerAdapter extends BaseQuickAdapter<CouponBean, BaseViewHolder> {
    boolean isMy = true;
    boolean isNotUse = false;

    public CouponRecyclerAdapter() {
        super(R.layout.item_coupon_list);
    }

    public void setIsMy(boolean ismy) {
        isMy = ismy;
    }

    public void setIsNotUse() {
        isNotUse = true;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CouponBean bean) {
        int type = bean.getType();
        switch (type) {
            case 1://优惠券
                String over = "<font color='#2069cf'>" + bean.getSchool_title() + "</font>";
                viewHolder.setText(R.id.coupon_tv, (bean.getPrice() == (int) bean.getPrice() ? (int) bean.getPrice() : bean.getPrice()) + "")
                        .setVisible(R.id.coupon_tv, true)
                        .setVisible(R.id.coupon_tv2, false)
                        .setText(R.id.coupon_type, "优惠券")
                        .setBackgroundRes(R.id.coupon_type, R.drawable.shape_frame_coupon_txt_yh)
                        .setBackgroundRes(R.id.use, R.drawable.shape_frame_coupon_bg_yh)
                        .setVisible(R.id.symbol, true)
                        .setVisible(R.id.coupon_symbol, false)
                        .setText(R.id.condition, "满" + bean.getMaxprice() + "元可用！")
                        .setText(R.id.use_tv, isMy ? "立即使用" : "立即领取")
                        .setText(R.id.astrict, Html.fromHtml("仅限" + over + "机构内所有课程可用"))
                        .setBackgroundRes(R.id.couponline, R.mipmap.coupon_line_yh);
//                use.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (isMy) {
//                            Intent intent = new Intent(mContext, OrganDetailsActivity.class);
//                            intent.putExtra("SCHOOLID", coupon.getSid());
//                            mContext.startActivity(intent);
//                        } else getCoupon(coupon.getCode());
//                    }
//                });

                break;
            case 2://打折券
                String astrict = "<font color='#2069cf'>" + bean.getSchool_title() + "</font>";
                viewHolder.setText(R.id.coupon_tv, (bean.getDiscount() == (int) bean.getDiscount() ? (int) bean.getDiscount() : bean.getDiscount()) + "")
                        .setText(R.id.coupon_type, "打折券")
                        .setVisible(R.id.coupon_tv, true)
                        .setVisible(R.id.coupon_tv2, false)
                        .setBackgroundRes(R.id.coupon_type, R.drawable.shape_frame_coupon_txt_dz)
                        .setBackgroundRes(R.id.use, R.drawable.shape_frame_coupon_bg_dz)
                        .setVisible(R.id.symbol, false)
                        .setVisible(R.id.coupon_symbol, true)
                        .setText(R.id.coupon_symbol, "折")
                        .setText(R.id.condition, "满" + bean.getMaxprice() + "元可用！")
                        .setText(R.id.use_tv, isMy ? "立即使用" : "立即领取")
                        .setText(R.id.astrict, Html.fromHtml("仅限" + astrict + "机构内所有课程可用"))
                        .setBackgroundRes(R.id.couponline, R.mipmap.coupon_line_dz);
                break;
            case 3://会员卡
                viewHolder.setVisible(R.id.coupon_tv, true)
                        .setVisible(R.id.coupon_tv2, false)
                        .setText(R.id.coupon_tv, bean.getVip_date())
                        .setText(R.id.coupon_type, "会员卡")
                        .setBackgroundRes(R.id.coupon_type, R.drawable.shape_frame_coupon_txt_hy)
                        .setBackgroundRes(R.id.use, R.drawable.shape_frame_coupon_bg_hy)
                        .setVisible(R.id.symbol, false)
                        .setVisible(R.id.coupon_symbol, true)
                        .setText(R.id.coupon_symbol, "天")
                        .setText(R.id.astrict, "")
                        .setText(R.id.condition, bean.getVip_grade_list().getTitle() + "  " + bean.getVip_date() + "天内有效")
                        .setBackgroundRes(R.id.couponline, R.mipmap.coupon_line_hy);
                break;
            case 4://充值卡
            case 5://课程卡
                viewHolder.setVisible(R.id.coupon_tv2, true)
                        .setVisible(R.id.coupon_tv, false)
                        .setText(R.id.coupon_tv2, bean.getVideo_title())
                        .setText(R.id.coupon_type, "课程卡")
                        .setBackgroundRes(R.id.coupon_type, R.drawable.shape_frame_coupon_txt_kc)
                        .setBackgroundRes(R.id.use, R.drawable.shape_frame_coupon_bg_kc)
                        .setVisible(R.id.symbol, false)
                        .setVisible(R.id.coupon_symbol, false)
                        .setText(R.id.astrict, "")
                        .setText(R.id.condition, "仅限指定课程使用")
                        .setText(R.id.use_tv, isMy ? "立即使用" : "立即领取")
                        .setBackgroundRes(R.id.couponline, R.mipmap.coupon_line_kc);
                break;
        }
        if (isMy)
            viewHolder.setText(R.id.start_or_end, "有效期：" + TimeUtils.MyFormatTime4(bean.getStime() * 1000l) + " - " + TimeUtils.MyFormatTime4(bean.getEtime() * 1000l));
        else
            viewHolder.setText(R.id.start_or_end, "有效期：" + TimeUtils.MyFormatTime4(bean.getCtime() * 1000l) + " - " + TimeUtils.MyFormatTime4(bean.getEnd_time() * 1000l));

        if (!MyConfig.isOpenAboutSchool)
            viewHolder.setText(R.id.astrict, "");
        if (isNotUse)
            viewHolder.setText(R.id.use_tv, "不可使用");
        viewHolder
//                .addOnClickListener(R.id.use_tv)
                .addOnClickListener(R.id.use);
    }


}
