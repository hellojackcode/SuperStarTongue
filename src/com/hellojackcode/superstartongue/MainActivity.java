package com.hellojackcode.superstartongue;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final int GOOGLE_STT = 1000;
	protected TextView recogText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		Button startRecordBtn = (Button) findViewById(R.id.startRecordBtn);
		startRecordBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
				i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
				i.putExtra(RecognizerIntent.EXTRA_PROMPT, "읽어봐바");
				
				startActivityForResult(i, GOOGLE_STT);
			}
		});
		
		recogText = (TextView) findViewById(R.id.recogText);
		
		Button resetText = (Button) findViewById(R.id.resetText);
		resetText.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				recogText.setText("");
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK && (requestCode == GOOGLE_STT)) {
			showRecognizedText(data);		// 결과를 텍스트로 뿌려줌.
		} else {
			String msg = null;
			
			//내가 만든 activity에서 넘어오는 오류 코드를 분류
			switch(resultCode){
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
	}

	private void showRecognizedText(Intent data) {
		recogText = (TextView) findViewById(R.id.recogText);
		String key = RecognizerIntent.EXTRA_RESULTS;
		ArrayList<String> mResult;
		mResult = data.getStringArrayListExtra(key);
		String[] result = new String[mResult.size()];
		mResult.toArray(result);
		for(int i=0; i < mResult.size(); i++) {
			recogText.append(result[i] + "\n");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
