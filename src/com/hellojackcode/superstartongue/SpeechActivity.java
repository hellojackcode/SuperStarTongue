package com.hellojackcode.superstartongue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ProgressBar;

public class SpeechActivity extends Activity {
	private ProgressBar mProgress;			// ���α׷����� 
	private SpeechRecognizer mRecognizer;		// �����ν� ��ü 
	private final int READY=0, END=1, FINISH=2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case READY:		// �غ�Ǿ����� ���α׷����� ���߰�, ����ũ �̹��� ���� 
				mProgress.setVisibility(View.INVISIBLE);
				findViewById(R.id.stt_layout).setVisibility(View.VISIBLE);
				break;
			case END:		// ���� �������� ���α׷����� ���(�����ν� ��), ����ũ �̹��� ���� 
				mProgress.setVisibility(View.VISIBLE);
				findViewById(R.id.stt_layout).setVisibility(View.INVISIBLE);
				//sendEmptyMessageDelayed(FINISH, 5000);
				break;
			case FINISH:	// �����ν� ���� 
				finish();
				break;
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speech_ui);
		
		mProgress = (ProgressBar) findViewById(R.id.progress);
		
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);	// �����ν� intent ���� 
		i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());	// ������ ���� 
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");		// �����ν� ��� ���� 
		
		mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);		// �����ν� ��ü ���� 
		mRecognizer.setRecognitionListener(listener);						// �����ν� ������ 
		mRecognizer.startListening(i);		// �����ν� ���� 
		
	}
	
	// �����ν� ������ 
	private RecognitionListener listener = new RecognitionListener() {
		@Override public void onRmsChanged(float rmsdB) {}
		
		// �����ν� ����� ���� 
		@Override public void onResults(Bundle results) {
			mHandler.removeMessages(END);		// �ڵ鷯�� ���� �޽��� ���� 
			
			Intent i = new Intent();		// ��� ��ȯ�� intent
			i.putExtras(results);			// ��� ���
			setResult(RESULT_OK, i);			// ��� ����
			
			finish();						// �� ���� 
		}
		
		// �����ν� �غ� �Ǿ����� 
		@Override public void onReadyForSpeech(Bundle params) {
			mHandler.sendEmptyMessage(READY);
		}
		
		// ���� �Է��� �������� 
		@Override public void onEndOfSpeech() {
			mHandler.sendEmptyMessage(END);
		}
		
		// ������ �߻��ϸ�
		@Override public void onError(int error) {
			setResult(error);		// �� Activity�� �����ڵ� ���� 
		}
		
		@Override public void onBeginningOfSpeech() {}						// �Է��� ���۵Ǹ� 
		@Override public void onPartialResults(Bundle partialResults) {}	// �ν� ����� �Ϻΰ� ��ȿ�� �� 
		@Override public void onEvent(int eventType, Bundle params) {}		// �̷��� �̺�Ʈ�� �߰��ϱ� ���� �̸� ����Ǿ��� �Լ� 
		@Override public void onBufferReceived(byte[] buffer) {}			// �� ���� �Ҹ��� ���� �� 
	};
	
	@Override
	public void finish() {
		if(mRecognizer != null) mRecognizer.stopListening(); 	// �����ν� ���� 
		mHandler.removeMessages(READY);
		mHandler.removeMessages(END);
		mHandler.removeMessages(FINISH);
		super.finish();
	}
	
	
}
