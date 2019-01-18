package com.seition.cloud.pro.newcloud.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.widget.emoji.ExpressionAdapter;
import com.seition.cloud.pro.newcloud.widget.emoji.ExpressionPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 类说明： 表情框
 */
public class ListFaceView extends LinearLayout implements AdapterView.OnItemClickListener {
    // 用于根据表型调用对应的字符串，发送到外部
    private static ArrayList<Integer> faceDisplayList = new ArrayList();
    public static HashMap<Integer, String> facesKeySrc = new LinkedHashMap();
    // 用于text中根据字符串找表情
    public static HashMap<String, Integer> facesKeyString = new LinkedHashMap();

    private Context m_Context;
    private ViewPager expressionViewpager;
    private EditText mEditTextContent;
    private FaceAdapter m_faceAdapter;


    /**
     * 一行10列显示
     ***/
    private static final int column = 7;
    /**
     * 表情显示行数
     ***/
    private static final int row = 4;
    /***
     * 表情文件前缀
     ***/
    private static final String prefix = "";
    /***
     * 表情名称
     ****/
    public static final String faceNames[] = {
            "aini", "aoman",
            "baiyan", "baobao", "bishi", "bizui",
            "cahan", "ciya", "chajing",
            "dabian", "dabing", "daku", "deyi",
            "fadai", "fanu", "fendou", "ganga",
            "gouyin", "guzhang",
            "haha", "haixiu", "haochi", "haqian", "huaixiao",
            "jingkong", "jingya",
            "kafei", "keai", "kelian", "ku", "kuaikule", "kulou", "kun",
            "lanqiu", "lenghan", "liuhan", "liulei",
            "ma",
            "nanguo", "no",
            "ok",
            "peifu", "pingpang", "pizui",
            "qiang", "qiaoda", "qinqin", "qioudale",
            "ruo",
            "se", "shuai", "shuijiao",
            "tiaopi", "touxiao", "tu",
            "wabi", "weiqu", "weixiao", "woquan", "woshou",
            "xia", "xu",
            "yeah", "yinxian", "yiwen", "youhengheng", "yueliang", "yun",
            "zaijian", "zhemo", "zhu", "zhuakuang", "zuohengheng"
    };


    public ListFaceView(Context paramContext) {
        this(paramContext, null);
    }

    public ListFaceView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.m_Context = paramContext;
        initFaceSources();
        initViews();
    }

    /**
     * 获取本地图片资源
     **/
    public int getResource(String imageName) {
        int resId = getResources().getIdentifier(imageName, "drawable", m_Context.getPackageName());
        return resId;
    }

    private void initFaceSources() {
        for (int i = 0; i < faceNames.length; i++) {
            Integer resId = getResource(prefix + faceNames[i]);
            if (!faceDisplayList.contains(resId))
                faceDisplayList.add(resId);
            facesKeyString.put(faceNames[i], resId);
            facesKeySrc.put(resId, faceNames[i]);
        }
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.emoji_smile_layout,
                this);
        expressionViewpager = (ViewPager) findViewById(R.id.vPager);
    }

    public void initSmileView(EditText edit) {
        this.mEditTextContent = edit;
        // 初始化表情viewpager
        List<View> views = new ArrayList<View>();
        int onePage = column * row - 1;
        int total = faceDisplayList.size();
        int page = total % onePage == 0 ? total / onePage : (total / onePage) + 1;
        for (int i = 1; i <= page; i++) {
            View gv = getGridChildView(i);
            views.add(gv);
        }

        expressionViewpager.setAdapter(new ExpressionPagerAdapter(views));
    }

    private View getGridChildView(int i) {
        View view = View.inflate(getContext(), R.layout.emoji_expression_gridview, null);
        GridView gv = (GridView) view.findViewById(R.id.gridview);
        gv.setNumColumns(column);
        //计算每页应该显示的资源
        int start = (i - 1) * (column * row) - 1;
        if (start < 0)
            start = 0;
        int end = i * (column * row) - i;
        if (end > faceNames.length)
            end = faceNames.length;

        List<String> pageList = new ArrayList<String>();
        for (int j = start; j < end; j++) {
            pageList.add(faceNames[j]);
        }
        //添加删除按钮
        pageList.add("delete_expression");

        final ExpressionAdapter expressionAdapter = new ExpressionAdapter(getContext(), 1, pageList);
        gv.setAdapter(expressionAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename = expressionAdapter.getItem(position);
                try {
                    // 文字输入框可见时，才可输入表情
                    // 按住说话可见，不让输入表情
                    if (filename != "delete_expression") {
                        int i = mEditTextContent.getSelectionStart();
                        String str1 = "[" + filename + "]";
                        Editable edit = mEditTextContent.getEditableText();
                        Spannable sp = new SpannableString(str1);
                        //将选择表情插入光标开始处  updated by leiyan on 11/29/2016
                        edit.insert(i, Utils.showContentFaceView(m_Context,
                                sp));
                        //这步是为了防止复制粘贴导致表情显示文字
                        mEditTextContent.setText(Utils.showContentFaceView(m_Context,
                                new SpannableStringBuilder(mEditTextContent.getEditableText())));
                        mEditTextContent.setSelection(i + str1.length());

                    } else { // 删除文字或者表情
                        if (!TextUtils.isEmpty(mEditTextContent.getText())) {
                            //调用系统软键盘的删除功能
                            int keyCode = KeyEvent.KEYCODE_DEL;
                            KeyEvent keyEventDown = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
                            KeyEvent keyEventUp = new KeyEvent(KeyEvent.ACTION_UP, keyCode);
                            mEditTextContent.onKeyDown(keyCode, keyEventDown);
                            mEditTextContent.onKeyUp(keyCode, keyEventUp);
                        }

                    }
//					}
                } catch (Exception e) {
                }

            }
        });

        return view;
    }

    public FaceAdapter getFaceAdapter() {
        return this.m_faceAdapter;
    }

    @Override
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
                            int paramInt, long paramLong) {
        if (this.m_faceAdapter != null) {
            int i = faceDisplayList.get(paramInt).intValue();
            String str = facesKeySrc.get(Integer.valueOf(i));
            this.m_faceAdapter.doAction(i, str);
        }
    }


    public void setFaceAdapter(FaceAdapter paramFaceAdapter) {
        this.m_faceAdapter = paramFaceAdapter;
    }

    public static abstract interface FaceAdapter {
        public abstract void doAction(int paramInt, String paramString);
    }

}