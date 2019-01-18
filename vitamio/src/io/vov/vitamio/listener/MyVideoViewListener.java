package io.vov.vitamio.listener;

public class MyVideoViewListener {
	public interface OnFullScreenListener{
		void onFullScreenListener(boolean isFullScreen);
		void addRecode(String tid, long time);
	}
	
}
