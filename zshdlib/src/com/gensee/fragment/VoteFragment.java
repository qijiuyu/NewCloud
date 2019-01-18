package com.gensee.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gensee.player.Player;
import com.gensee.rtmpresourcelib.R;
import com.gensee.view.GSImplVoteView;

@SuppressLint("ValidFragment")
public class VoteFragment extends Fragment {

    private Player mPlayer;
    private GSImplVoteView mGSImplVoteView;

    public VoteFragment(Player player) {
        this.mPlayer = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.imvote, null);
        mGSImplVoteView = (GSImplVoteView) main.findViewById(R.id.impvoteview);
        mPlayer.setGSVoteView(mGSImplVoteView);
        return main;
    }

}