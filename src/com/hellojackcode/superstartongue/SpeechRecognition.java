package com.hellojackcode.superstartongue;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.SpeechRecognizer;

public class SpeechRecognition {
	private SpeechRecognizer mRecognizer;
	private final int READY=0, END=1, FINISH=2;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case READY:		// 준비되었으면 프로그레스바 감추고, 마이크 이미지 보임 
				//findViewById(R.id.recordBtn).setVisibility(View.VISIBLE);
				break;
			case END:		// 말이 끝났으면 프로그레스바 출력(음성인식 중), 마이크 이미지 감춤 
				//mProgress.setVisibility(View.VISIBLE);
				//findViewById(R.id.stt_layout).setVisibility(View.INVISIBLE);
				//sendEmptyMessageDelayed(FINISH, 5000);
				break;
			case FINISH:	// 음성인식 중지 
				//finish();
				break;
			}
		}
	};
}