package com.hellojackcode.superstartongue;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends BaseActivity {
	protected TextView recogText;
	protected TextView sentenceText;
	
	public Context mContext = null;		// 메인 액티비티값을 저장 

	Intent i;
	SpeechRecognizer mRecognizer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_main);
		recogText = (TextView) findViewById(R.id.recogText);
		
		/* 현재 스테이지 표시 */
		TextView stage = (TextView)findViewById(R.id.stage);
		stage.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumPen.otf"));
		
		
		i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
		
		/* 
		 * 음성 인식 객체 생성
		 */
		mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		mRecognizer.setRecognitionListener(listener);
		
		/*  
		 * 마이크를 클릭하면 녹음 시작 함
		 */
		ImageButton recordBtn = (ImageButton) findViewById(R.id.recordBtn);
		recordBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mRecognizer.startListening(i);
			}
		});
		
		/*
		 * 발음할 단어 표시 
		 */
		sentenceText = (TextView) findViewById(R.id.sentenceText);
		sentenceText.setText(getSentence());
		
	}

	// 발음할 문자열을 가져 옴(추후에 클래스로 떼어내야 할 듯)
	protected String getSentence() {
		String rtnSentence = "";
		
		// 추후에는 DB에서 가져오는 걸로 바꿔야 할 부분임.
		String sentence = "슈퍼스타 혀";
		rtnSentence = sentence.replaceAll("\\p{Space}", "");
		
		return sentence;
		//return rtnSentence;
	}
	
	/* 
	 * 음성인식 리스너 
	 */
	private RecognitionListener listener = new RecognitionListener() {
        
        @Override
        public void onRmsChanged(float rmsdB) {
            // TODO Auto-generated method stub
             
        }

    	@Override
    	public void onResults(Bundle results) {
    		String key = "";
    		key = SpeechRecognizer.RESULTS_RECOGNITION;
    		ArrayList<String> mResult = results.getStringArrayList(key);
    		String[] rs = new String[mResult.size()];
    		mResult.toArray(rs);
    		recogText.setText(""+rs[1]);
    	}
    	
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getBaseContext(), "지금 말하세요.", Toast.LENGTH_SHORT).show();
        }
         
        @Override
        public void onPartialResults(Bundle partialResults) {
            // TODO Auto-generated method stub
             
        }
         
        @Override
        public void onEvent(int eventType, Bundle params) {
            // TODO Auto-generated method stub
             
        }
         
        @Override
        public void onError(int error) {
        	String msg = null;
			// 오류 코드를 분류
			switch(error){
				case SpeechRecognizer.ERROR_AUDIO:
					msg = "오디오 입력 중 오류가 발생했습니다.";
					break;
				case SpeechRecognizer.ERROR_CLIENT:
					msg = "단말에서 오류가 발생했습니다.";
					break;
				case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
					msg = "권한이 없습니다.";
					break;
				case SpeechRecognizer.ERROR_NETWORK:
				case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
					msg = "네트워크 오류가 발생했습니다.";
					break;
				case SpeechRecognizer.ERROR_NO_MATCH:
					msg = "일치하는 항목이 없습니다.";
					break;
				case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
					msg = "음성인식 서비스가 과부하 되었습니다.";
					break;
				case SpeechRecognizer.ERROR_SERVER:
					msg = "서버에서 오류가 발생했습니다.";
					break;
				case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
					msg = "입력이 없습니다.";
					break;
			}
			
			if(msg != null)		//오류 메시지가 null이 아니면 메시지 출력
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
         
        @Override
        public void onEndOfSpeech() {
            Toast.makeText(getBaseContext(), "음성인식이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }
         
        @Override
        public void onBufferReceived(byte[] buffer) {
            // TODO Auto-generated method stub
             
        }
         
        @Override
        public void onBeginningOfSpeech() {
            // TODO Auto-generated method stub
             
        }
    };
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
