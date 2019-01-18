package com.gensee.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gensee.player.OnQaListener;
import com.gensee.player.Player;
import com.gensee.rtmpresourcelib.R;
import com.gensee.utils.StringUtil;
import com.gensee.view.GSImplQaView;

@SuppressLint("ValidFragment")
public class QaFragment extends Fragment {

	private Player mPlayer;
	private View mView;
	private GSImplQaView mGSQaView;

	public QaFragment(Player player) {
		this.mPlayer = player;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.imqa, null);
		mGSQaView = (GSImplQaView) mView.findViewById(R.id.impqaview);
		mPlayer.setGSQaView(mGSQaView);
		mPlayer.setOnQaListener(new OnQaListener() {
			@Override
			public void onRoomMute(boolean arg0) {
				// TODO Auto-generated method stub
				mGSQaView.onRoomMute(arg0);
			}

			@Override
			public void onQaMute(boolean arg0) {
				// TODO Auto-generated method stub
				mGSQaView.onQaMute(arg0);
			}

			@Override
			public void onQa(String questionId, String question, String questionOwner, String answerId, String answer,
					String answerOwner, int questionTime, int answerTime, long qaOwnerId, boolean isCancel) {
				// TODO Auto-generated method stub
				addMsg(questionId, question, questionOwner, answerId, answer, answerOwner, questionTime, answerTime,
						qaOwnerId, isCancel);
			}
		});
		return mView;
	}
	public void addMsg(String questionId, String question, String questionOwner, String answerId, String answer,
			String answerOwner, int questionTime, int answerTime, long qaOwnerId, boolean isCancel) {
		if (StringUtil.isEmpty(answer) && mPlayer.getSelfInfo().getUserId() == qaOwnerId) {
		} else
			mGSQaView.addMsg(questionId, question, questionOwner, answerId, answer, answerOwner, questionTime,
					answerTime, qaOwnerId, isCancel);
	}

}