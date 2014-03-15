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
				i.putExtra(RecognizerIntent.EXTRA_PROMPT, "�о����");
				
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
			showRecognizedText(data);		// ����� �ؽ�Ʈ�� �ѷ���.
		} else {
			String msg = null;
			
			//���� ���� activity���� �Ѿ���� ���� �ڵ带 �з�
			switch(resultCode){
				case SpeechRecognizer.ERROR_AUDIO:
					msg = "����� �Է� �� ������ �߻��߽��ϴ�.";
					break;
				case SpeechRecognizer.ERROR_CLIENT:
					msg = "�ܸ����� ������ �߻��߽��ϴ�.";
					break;
				case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
					msg = "������ �����ϴ�.";
					break;
				case SpeechRecognizer.ERROR_NETWORK:
				case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
					msg = "��Ʈ��ũ ������ �߻��߽��ϴ�.";
					break;
				case SpeechRecognizer.ERROR_NO_MATCH:
					msg = "��ġ�ϴ� �׸��� �����ϴ�.";
					break;
				case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
					msg = "�����ν� ���񽺰� ������ �Ǿ����ϴ�.";
					break;
				case SpeechRecognizer.ERROR_SERVER:
					msg = "�������� ������ �߻��߽��ϴ�.";
					break;
				case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
					msg = "�Է��� �����ϴ�.";
					break;
			}
			
			if(msg != null)		//���� �޽����� null�� �ƴϸ� �޽��� ���
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
