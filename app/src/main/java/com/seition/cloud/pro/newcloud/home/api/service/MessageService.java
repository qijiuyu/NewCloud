package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageComment;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageLatter;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageLatterItem;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageSystem;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface MessageService {

    String MessageList = "message.getList";//私信列表
    String MessageSend = "message.send";//发私信
    String MessageReply = "message.reply";//回复私信
    String MessageInfo = "message.getInfo";//私信详情
    String MessageDelete = "message.delete";//私信删除
    String MessageComment = "message.comment";//收到的评论
    String MessageCommentDelete = "message.deleteComment";//删除评论
    String MessageSystem = "message.system";//系统消息

    @POST(MessageList)
    Observable<Arr_MessageLatter> getMessagePrivateLetters(@Header("oauth-token") String oauthToken);

    @POST(MessageComment)
    Observable<Arr_MessageComment> getMessageComment(@Header("oauth-token") String oauthToken);

    @POST(MessageSystem)
    Observable<Arr_MessageSystem> getMessageSystems(@Header("oauth-token") String oauthToken);

    @POST(MessageInfo)
    Observable<Arr_MessageLatterItem> getChatList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(MessageReply)
    Observable<DataBean> replyMsg(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
    @POST(MessageSend)
    Observable<DataBean> sendMsg(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
}
