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
			case READY:		// �غ�Ǿ����� ���α׷����� ���߰�, ����ũ �̹��� ���� 
				//findViewById(R.id.recordBtn).setVisibility(View.VISIBLE);
				break;
			case END:		// ���� �������� ���α׷����� ���(�����ν� ��), ����ũ �̹��� ���� 
				//mProgress.setVisibility(View.VISIBLE);
				//findViewById(R.id.stt_layout).setVisibility(View.INVISIBLE);
				//sendEmptyMessageDelayed(FINISH, 5000);
				break;
			case FINISH:	// �����ν� ���� 
				//finish();
				break;
			}
		}
	};
}