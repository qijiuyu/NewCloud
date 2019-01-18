package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddress;

public class ReceiveAddressRecyclerAdapter extends BaseQuickAdapter<ReceiveGoodsAddress, BaseViewHolder> {

    public ReceiveAddressRecyclerAdapter() {
        super(R.layout.item_receive_goods_address_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ReceiveGoodsAddress bean) {
        viewHolder.setText(R.id.receive_name, bean.getName());
        viewHolder.setText(R.id.receive_phone, bean.getPhone());
        viewHolder.setText(R.id.receive_address,  ""+bean.getProvince()+bean.getCity()+bean.getArea()+bean.getAddress());


        if(bean.getIs_default().equals("1")){
//            viewHolder.setBackgroundRes(R.id.receive_defult_img,R.mipmap.r_serverselect_sel);
            viewHolder.setText(R.id.receive_defult_address,"默认地址");
            viewHolder.setTextColor(R.id.receive_defult_address,viewHolder.itemView.getContext().getResources().getColor(R.color.red));
        }else if(bean.getIs_default().equals("0")){
//            viewHolder.setBackgroundRes(R.id.receive_defult_img,R.mipmap.r_serverselect);
            viewHolder.setText(R.id.receive_defult_address,"设为默认");
            viewHolder.setTextColor(R.id.receive_defult_address,viewHolder.itemView.getContext().getResources().getColor(R.color.color_3));
        }
        viewHolder.addOnClickListener(R.id.goods_address_list_do_defult_ll).addOnClickListener(R.id.receive_address_modifycation).addOnClickListener(R.id.receive_address_delete);

    }


}
