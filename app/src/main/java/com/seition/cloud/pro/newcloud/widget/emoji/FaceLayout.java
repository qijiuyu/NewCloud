package com.seition.cloud.pro.newcloud.widget.emoji;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.seition.cloud.pro.newcloud.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FaceLayout extends LinearLayout implements OnItemClickListener {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final ArrayList<Integer> faceDisplayList = new ArrayList();
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final HashMap<Integer, String> facesKeySrc = new LinkedHashMap();
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final HashMap<String, Integer> facesKeyString = new LinkedHashMap();
    private Context m_Context;
    private GridView m_GridView;
    private FaceAdapter m_faceAdapter;

    public static int[] biaoqing_mipmap = {R.mipmap.ciya, R.mipmap.tiaopi, R.mipmap.liuhan,
            R.mipmap.touxiao, R.mipmap.zaijian, R.mipmap.qiaoda, R.mipmap.cahan,
            R.mipmap.zhu, R.mipmap.kun, R.mipmap.liulei, R.mipmap.daku,
            R.mipmap.xu, R.mipmap.ku, R.mipmap.zhuakuang, R.mipmap.weiqu,
            R.mipmap.dabian, R.mipmap.kuaikule, R.mipmap.xia, R.mipmap.keai,
            R.mipmap.se, R.mipmap.haixiu, R.mipmap.deyi, R.mipmap.tu,
            R.mipmap.weixiao, R.mipmap.fanu, R.mipmap.ganga, R.mipmap.jingkong,
            R.mipmap.lenghan, R.mipmap.ma, R.mipmap.zhemo, R.mipmap.baiyan,
            R.mipmap.aoman, R.mipmap.img_nanguo, R.mipmap.jingya, R.mipmap.yiwen,
            R.mipmap.shuijiao, R.mipmap.qinqin, R.mipmap.haha, R.mipmap.wabi,
            R.mipmap.shuai, R.mipmap.pizui, R.mipmap.yinxian, R.mipmap.fendou,
            R.mipmap.fadai, R.mipmap.zuohengheng, R.mipmap.baobao, R.mipmap.huaixiao,
            R.mipmap.no, R.mipmap.bishi, R.mipmap.yun, R.mipmap.dabing,
            R.mipmap.kelian, R.mipmap.qiang, R.mipmap.ruo, R.mipmap.woshou,
            R.mipmap.yeah, R.mipmap.peifu, R.mipmap.guzhang, R.mipmap.qioudale,
            R.mipmap.youhengheng, R.mipmap.haqian, R.mipmap.haochi, R.mipmap.bizui,
            R.mipmap.chajing, R.mipmap.gouyin, R.mipmap.ok, R.mipmap.aini,
            R.mipmap.kafei, R.mipmap.yueliang, R.mipmap.lanqiu, R.mipmap.pingpang,
            R.mipmap.woquan, R.mipmap.kulou
    };
    public static String[] biaoqing_str = {"ciya", "tiaopi", "liuhan", "touxiao", "zaijian",
            "qiaoda", "cahan", "zhu", "kun", "liulei", "daku", "xu",
            "ku", "zhuakuang", "weiqu", "dabian", "kuaikule", "xia", "keai",
            "se", "haixiu", "deyi", "tu", "weixiao", "fanu", "ganga",
            "jingkong", "lenghan", "ma", "zhemo", "baiyan", "aoman", "img_nanguo",
            "jingya", "yiwen", "shuijiao", "qinqin", "haha", "wabi", "shuai",
            "pizui", "yinxian", "fendou", "fadai", "zuohengheng", "baobao", "huaixiao",
            "no", "bishi", "yun", "dabing", "kelian", "qiang", "ruo",
            "woshou", "yeah", "peifu", "guzhang", "qioudale", "youhengheng", "haqian",
            "haochi", "bizui", "chajing", "gouyin", "ok", "aini", "kafei",
            "yueliang", "lanqiu", "pingpang", "woquan", "kulou"
    };

    static {
        for (int i = 0; i < biaoqing_mipmap.length; i++) {
            faceDisplayList.add(Integer.valueOf(biaoqing_mipmap[i]));
            facesKeySrc.put(Integer.valueOf(biaoqing_mipmap[i]), biaoqing_str[i]);
            facesKeyString.put(biaoqing_str[i], Integer.valueOf(biaoqing_mipmap[i]));
        }
    }

    public FaceLayout(Context paramContext) {
        super(paramContext);
        this.m_Context = paramContext;
        initViews();
    }

    public FaceLayout(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.m_Context = paramContext;
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.emoji_face_main, this);
        this.m_GridView = ((GridView) findViewById(R.id.gridView));
        GridViewAdapter localGridViewAdapter = new GridViewAdapter(this.m_Context, faceDisplayList);
        this.m_GridView.setAdapter(localGridViewAdapter);
        this.m_GridView.setOnItemClickListener(this);
    }

    public FaceAdapter getFaceAdapter() {
        return this.m_faceAdapter;
    }

    @Override
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        if (this.m_faceAdapter != null) {
            int i = faceDisplayList.get(paramInt).intValue();
            String str = facesKeySrc.get(Integer.valueOf(i));
            this.m_faceAdapter.doAction(i, str);
        }
    }

    public void reBuildViews() {
        removeAllViews();
        initViews();
        requestLayout();
        invalidate();
    }

    public void setFaceAdapter(FaceAdapter paramFaceAdapter) {
        this.m_faceAdapter = paramFaceAdapter;
    }

    public static abstract interface FaceAdapter {
        public abstract void doAction(int paramInt, String paramString);
    }

    class GridViewAdapter extends BaseAdapter {
        Context ct;
        List<Integer> list;

        public GridViewAdapter(Context mContext, ArrayList<Integer> arg2) {
            this.ct = mContext;
            this.list = arg2;
        }

        @Override
        public int getCount() {
            return this.list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return this.list.get(position);
        }

        @Override
        public long getItemId(int paramInt) {
            return paramInt;
        }

        @Override
        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
            ImageView localImageView = null;
            if (paramView == null) {
                localImageView = new ImageView(this.ct);
                localImageView.setBackgroundResource(R.drawable.bg_face);
                int j = FaceLayout.this.getResources().getDimensionPixelSize(R.dimen.face_item_view_height);
                localImageView.setPadding(0, j, 0, j);
                paramView = localImageView;
                paramView.setTag(localImageView);
            } else {
                localImageView = (ImageView) paramView.getTag();
            }

            int i = ((Integer) getItem(paramInt)).intValue();

            localImageView.setImageResource(i);
            return paramView;
        }
    }
}
