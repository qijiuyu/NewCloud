package com.bokecc.dwlivedemo_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.module.ChatEntity;
import com.bokecc.dwlivedemo_new.util.EmojiUtil;
import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.pojo.Viewer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 直播公聊 适配器
 * 作者 ${郭鹏飞}.<br/>
 */
public class LivePublicChatAdapter extends RecyclerView.Adapter<LivePublicChatAdapter.ChatViewHolder> {

    private Context mContext;
    private ArrayList<ChatEntity> mChatEntities;
    private LayoutInflater mInflater;
    private String selfId;

    public LivePublicChatAdapter(Context context) {
        mChatEntities = new ArrayList<>();
        mContext = context;
        mInflater = LayoutInflater.from(context);
        Viewer viewer = DWLive.getInstance().getViewer();
        if (viewer == null) {
            selfId = "";
        } else {
            selfId = viewer.getId();
        }
    }

    /**
     * 添加数据，用于回放的添加
     */
    public void add(ArrayList<ChatEntity> mChatEntities) {
        this.mChatEntities = mChatEntities;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void add(ChatEntity chatEntity) {
        mChatEntities.add(chatEntity);
        if (mChatEntities.size() > 300) { // 当消息达到300条的时候，移除最早的消息
            mChatEntities.remove(0);
        }
        notifyDataSetChanged();
    }

    public ArrayList<ChatEntity> getChatEntities() {
        return mChatEntities;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType == selfType) {
            // 展示自己发出去的公聊
            itemView = mInflater.inflate(R.layout.live_portrait_chat_single_self, parent, false);
            return new ChatViewHolder(itemView);
        } else if (viewType == otherType) {
            // 展示收到的别人发出去的公聊
            itemView = mInflater.inflate(R.layout.live_portrait_chat_single_other, parent, false);
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            return new ChatViewHolder(itemView);
        } else {
            // 展示收到的广播消息
            itemView = mInflater.inflate(R.layout.live_protrait_system_broadcast, parent, false);
            return new ChatViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatEntity chatEntity = mChatEntities.get(position);

        if (chatEntity.getUserId().isEmpty() && chatEntity.getUserName().isEmpty() && !chatEntity.isPrivate()
                && chatEntity.isPublisher() && chatEntity.getTime().isEmpty() && chatEntity.getUserAvatar().isEmpty()) {
            // 展示广播信息
            holder.mBroadcast.setText(chatEntity.getMsg());
        } else {
            // 展示聊天信息
            String msg = chatEntity.getMsg();
            SpannableString ss = new SpannableString(msg);
            holder.mContent.setText(EmojiUtil.parseFaceMsg(mContext, ss));
            holder.mName.setText(chatEntity.getUserName());
        }
    }

    private int otherType = 0; // 别人发送的聊天
    private int selfType = 1; // 自己发送的聊天
    private int systemType = 2;  // 系统广播

    @Override
    public int getItemViewType(int position) {

        ChatEntity chat = mChatEntities.get(position);

        // 系统广播 --- 只有 chatEntity.getMsg() 不为空
        if (chat.getUserId().isEmpty() && chat.getUserName().isEmpty()
                && !chat.isPrivate() && chat.isPublisher()
                && chat.getTime().isEmpty() && chat.getUserAvatar().isEmpty()) {
            return systemType;
        }

        // 聊天
        if (chat.getUserId().equals(selfId)) {
            return selfType; // 自己发出去的
        } else {
            return otherType; // 收到别人的
        }
    }

    @Override
    public int getItemCount() {
        return mChatEntities == null ? 0 : mChatEntities.size();
    }

    final class ChatViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.pc_chat_single_msg)
        TextView mContent;

        @BindView(R2.id.pc_chat_single_name)
        TextView mName;

        @BindView(R2.id.pc_chat_system_broadcast)
        TextView mBroadcast;

        ChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
