package com.bokecc.dwlivedemo_new.popup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.adapter.VoteSummaryAdapter;
import com.bokecc.dwlivedemo_new.base.BasePopupWindow;
import com.bokecc.dwlivedemo_new.base.PopupAnimUtil;
import com.bokecc.sdk.mobile.live.DWLive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class VotePopupBackup extends BasePopupWindow {

    public VotePopupBackup(Context context) {
        super(context);
    }

    ImageView qsClose;

    //-----------------答题界面-----------------------------------
    LinearLayout selectLayout;
    ImageView selectNav;

    RadioGroup multiGroup;
    RadioButton multi0;
    RadioButton multi1;
    RadioButton multi2;
    RadioButton multi3;
    RadioButton multi4;

    RadioGroup doubleGroup;
    RadioButton double0;
    RadioButton double1;

    Button submit;

    //-----------------统计界面-----------------------------------
    LinearLayout summaryLayout;
    ImageView summaryNav;
    TextView tvVoteCount;
    TextView tvUserResult;
    RecyclerView summaryList;


    private ArrayList<RadioButton> rbs; //TODO 这里初始化为什么不行
    public int selectOption = -1;
    VoteSummaryAdapter adapter;

    @Override
    protected void onViewCreated() {
        qsClose = findViewById(R.id.qs_close);
        qsClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // 答题界面
        selectLayout = findViewById(R.id.qs_select_layout);
        selectNav = findViewById(R.id.qs_select_nav);

        multiGroup = findViewById(R.id.rg_qs_multi);
        multi0 = findViewById(R.id.rb_multi_0);
        multi1 = findViewById(R.id.rb_multi_1);
        multi2 = findViewById(R.id.rb_multi_2);
        multi3 = findViewById(R.id.rb_multi_3);
        multi4 = findViewById(R.id.rb_multi_4);

        rbs = new ArrayList<>();
        rbs.add(multi0);
        rbs.add(multi1);
        rbs.add(multi2);
        rbs.add(multi3);
        rbs.add(multi4);

        multiGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_multi_0) {
                    selectOption = 0;

                } else if (i == R.id.rb_multi_1) {
                    selectOption = 1;

                } else if (i == R.id.rb_multi_2) {
                    selectOption = 2;

                } else if (i == R.id.rb_multi_3) {
                    selectOption = 3;

                } else if (i == R.id.rb_multi_4) {
                    selectOption = 4;

                }
            }
        });



        doubleGroup = findViewById(R.id.rg_qs_double);
        double0 = findViewById(R.id.rb_double_0);
        double1 = findViewById(R.id.rb_double_1);

        doubleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_double_0) {
                    selectOption = 0;

                } else if (i == R.id.rb_double_1) {
                    selectOption = 1;

                }
            }
        });

        submit = findViewById(R.id.btn_qs_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectOption == -1) {
                    Toast.makeText(mContext, "请先选择答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                DWLive.getInstance().sendVoteResult(selectOption);
                dismiss();
            }
        });

        //统计界面
        summaryLayout = findViewById(R.id.qs_summary_layout);
        summaryNav = findViewById(R.id.qs_summary_nav);
        tvVoteCount = findViewById(R.id.qs_vote_people_number);
        tvUserResult = findViewById(R.id.tv_user_result);
        summaryList = findViewById(R.id.qs_summary_list);

        summaryList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new VoteSummaryAdapter(mContext);
        summaryList.setAdapter(adapter);
    }


    @Override
    protected int getContentView() {
        return R.layout.vote_layout;
    }

    @Override
    protected Animation getEnterAnimation() {
        return PopupAnimUtil.getDefScaleEnterAnim();
    }

    @Override
    protected Animation getExitAnimation() {
        return PopupAnimUtil.getDefScaleExitAnim();
    }


    int voteCount;
    int voteType; // 目前只有单选，暂时不判断
    public void startVote(int voteCount, int VoteType) {
        this.voteCount = voteCount;
        this.voteType = voteType;

        showSelectLayout();
    }

    private void showSelectLayout() {
        changeLayoutShow(true);

        selectOption = -1;
        double0.setChecked(false);
        double1.setChecked(false);

        if (voteCount == 2) {
            multiGroup.setVisibility(View.GONE);
            doubleGroup.setVisibility(View.VISIBLE);
        } else {
            multiGroup.setVisibility(View.VISIBLE);
            doubleGroup.setVisibility(View.GONE);

            //TODO 右边距

            for (int i=0; i<rbs.size();i++) {
                RadioButton rb = rbs.get(i);
                rb.setChecked(false);
                if (i < voteCount) {
                    rb.setVisibility(View.VISIBLE);
                } else {
                    rb.setVisibility(View.GONE);
                }
            }
        }
    }

    private void changeLayoutShow(boolean isSelectSummary) {
        if (isSelectSummary) {
            selectLayout.setVisibility(View.VISIBLE);
            selectNav.setVisibility(View.VISIBLE);

            summaryLayout.setVisibility(View.GONE);
            summaryNav.setVisibility(View.GONE);
        } else {
            selectLayout.setVisibility(View.GONE);
            selectNav.setVisibility(View.GONE);

            summaryLayout.setVisibility(View.VISIBLE);
            summaryNav.setVisibility(View.VISIBLE);
        }

    }

    //{"correctOption":3,"statisics":
    // [{"count":0,"option":0,"percent":"0.0"},
    // {"count":0,"option":1,"percent":"0.0"},
    // {"count":0,"option":2,"percent":"0.0"},
    // {"count":0,"option":3,"percent":"0.0"},
    // {"count":0,"option":4,"percent":"0.0"}],
    // "voteCount":5,
    // "voteId":"20170110153449252","voteType":0}

    int correntOption;
    ArrayList<VotePopup.VoteSingleStatisics> voteStatisices = new ArrayList<>();
    public void onVoteResult(JSONObject jsonObject) {
        initVoteResult();
        showSummaryLayout();

        try {
            this.correntOption = jsonObject.getInt("correctOption");
            JSONArray statisics = jsonObject.getJSONArray("statisics");
            for (int i=0; i < statisics.length(); i++) {
                voteStatisices.add(new VotePopup.VoteSingleStatisics(statisics.getJSONObject(i)));
            }

            adapter.add(voteStatisices);

        } catch (JSONException e) {
            Log.e("demo", e.getLocalizedMessage());
        }

        int count = 0;

        for (VotePopup.VoteSingleStatisics voteSingleStatisics: voteStatisices) {
            count += voteSingleStatisics.getCount();
        }

        tvVoteCount.setText("回答结束，共" + count + "人回答。");

        if (correntOption == selectOption) {
            isVoteRight = true;
        } else {
            isVoteRight = false;
        }

        if (voteCount > 2) {
            setOtherResult();
        } else {
            setTwoResult();
        }

    }

    boolean isVoteRight;

    String wrongTextColor = "#fc512b";
    String rightTextColor = "#12b88f";

    int[] rightImage = new int[]{R.drawable.qs_pic_option_right_0, R.drawable.qs_pic_option_right_1};
    int[] wrongImage = new int[]{R.drawable.qs_pic_option_wrong_0, R.drawable.qs_pic_option_wrong_1};

    private void setTwoResult() {
        String myAnswer = "您的答案：" + getVoteOptionString(selectOption);

        String rightAnswer = "正确答案：" + getVoteOptionString(correntOption);

        String msg = myAnswer + "\u3000\u3000" + rightAnswer;

        SpannableString ss = new SpannableString(msg);

        ss.setSpan(new ForegroundColorSpan(Color.parseColor(getMyAnswerColor())),
                0,
                myAnswer.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new ForegroundColorSpan(Color.parseColor(rightTextColor)),
                myAnswer.length() + 1,
                msg.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (selectOption > -1) {
            if (isVoteRight) {
                setImageSpan(ss, rightImage[selectOption], myAnswer.length() - 1);
            } else {
                setImageSpan(ss, wrongImage[selectOption], myAnswer.length() - 1);
            }
        }

        if (correntOption > -1) {
            setImageSpan(ss, rightImage[correntOption], msg.length() - 1);
        }

        tvUserResult.setText(ss);
    }

    private void setImageSpan(SpannableString ss, int resId, int position) {
        Bitmap imgBitmap = BitmapFactory.decodeResource(mContext.getResources(), resId);
        imgBitmap = ThumbnailUtils.extractThumbnail(imgBitmap, imgBitmap.getWidth(), imgBitmap.getHeight());
        ImageSpan imgSpan = new ImageSpan(mContext, imgBitmap);
        ss.setSpan(imgSpan, position, position + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private String getMyAnswerColor() {
        if (isVoteRight) {
            return rightTextColor;
        } else {
            return wrongTextColor;
        }
    }

    private void setOtherResult() {

        String myAnswer = "您的答案：" + getVoteOptionString(selectOption);

        String rightAnswer = "正确答案：" + getVoteOptionString(correntOption);

        String msg = myAnswer + "\u3000\u3000" + rightAnswer;

        SpannableString ss = new SpannableString(msg);

        ss.setSpan(new ForegroundColorSpan(Color.parseColor(getMyAnswerColor())),
                0,
                myAnswer.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new ForegroundColorSpan(Color.parseColor(rightTextColor)),
                myAnswer.length() + 1,
                msg.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvUserResult.setText(ss);
    }

    String[] voteNumbers = new String[]{"A", "B", "C", "D", "E"};
    private String getVoteOptionString(int index) {
        if (index > -1 && index < 5) {
            return voteNumbers[index];
        } else {
            return " ";
        }
    }

    private void initVoteResult() {
        voteStatisices.clear();
    }

    private void showSummaryLayout() {
        changeLayoutShow(false);
    }

    public final class VoteSingleStatisics {

        int count;
        int option;
        String percent;

        public int getCount() {
            return count;
        }

        public int getOption() {
            return option;
        }

        public String getPercent() {
            return percent;
        }

        // [{"count":0,"option":0,"percent":"0.0"},
        VoteSingleStatisics(JSONObject jsonObject) {
            try {
                this.count = jsonObject.getInt("count");
                this.option = jsonObject.getInt("option");
                this.percent = jsonObject.getString("percent");
            } catch (JSONException e) {
                Log.e("demo", e.getLocalizedMessage());
            }

        }
    }
}
