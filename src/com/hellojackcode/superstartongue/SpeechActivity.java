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
	private ProgressBar mProgress;			// 프로그레스바 
	private SpeechRecognizer mRecognizer;		// 음성인식 객체 
	private final int READY=0, END=1, FINISH=2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case READY:		// 준비되었으면 프로그레스바 감추고, 마이크 이미지 보임 
				mProgress.setVisibility(View.INVISIBLE);
				findViewById(R.id.stt_layout).setVisibility(View.VISIBLE);
				break;
			case END:		// 말이 끝났으면 프로그레스바 출력(음성인식 중), 마이크 이미지 감춤 
				mProgress.setVisibility(View.VISIBLE);
				findViewById(R.id.stt_layout).setVisibility(View.INVISIBLE);
				//sendEmptyMessageDelayed(FINISH, 5000);
				break;
			case FINISH:	// 음성인식 중지 
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
		
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);	// 음성인식 intent 생성 
		i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());	// 데이터 설정 
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");		// 음성인식 언어 설정 
		
		mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);		// 음성인식 객체 생성 
		mRecognizer.setRecognitionListener(listener);						// 음성인식 리스너 
		mRecognizer.startListening(i);		// 음성인식 시작 
		
	}
	
	// 음성인식 리스너 
	private RecognitionListener listener = new RecognitionListener() {
		@Override public void onRmsChanged(float rmsdB) {}
		
		// 음성인식 결과를 받음 
		@Override public void onResults(Bundle results) {
			mHandler.removeMessages(END);		// 핸들러에 종료 메시지 삭제 
			
			Intent i = new Intent();		// 결과 반환할 intent
			i.putExtras(results);			// 결과 등록
			setResult(RESULT_OK, i);			// 결과 설정
			
			finish();						// 앱 종료 
		}
		
		// 음성인식 준비가 되었으면 
		@Override public void onReadyForSpeech(Bundle params) {
			mHandler.sendEmptyMessage(READY);
		}
		
		// 음성 입력이 끝났으면 
		@Override public void onEndOfSpeech() {
			mHandler.sendEmptyMessage(END);
		}
		
		// 에러가 발생하면
		@Override public void onError(int error) {
			setResult(error);		// 전 Activity로 에러코드 전송 
		}
		
		@Override public void onBeginningOfSpeech() {}						// 입력이 시작되면 
		@Override public void onPartialResults(Bundle partialResults) {}	// 인식 결과의 일부가 유효할 때 
		@Override public void onEvent(int eventType, Bundle params) {}		// 미래의 이벤트를 추가하기 위해 미리 예약되어진 함수 
		@Override public void onBufferReceived(byte[] buffer) {}			// 더 많은 소리를 받을 때 
	};
	
	@Override
	public void finish() {
		if(mRecognizer != null) mRecognizer.stopListening(); 	// 음성인식 중지 
		mHandler.removeMessages(READY);
		mHandler.removeMessages(END);
		mHandler.removeMessages(FINISH);
		super.finish();
	}
	
	
}
