package com.hellojackcode.superstartongue;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends BaseActivity {
	private final int SST_SPH = 1000;
	protected TextView recogText;
	protected TextView sentenceText;
	protected TextView rateText;
	
	public Context mContext = null;		// ���� ��Ƽ��Ƽ���� ���� 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_main);
		
		// �����ϱ� ��ư 
		Button startRecordBtn = (Button) findViewById(R.id.startRecordBtn);
		startRecordBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivityForResult(new Intent(mContext, SpeechActivity.class), SST_SPH);
			}
		});
		
		recogText = (TextView) findViewById(R.id.recogText);
		sentenceText = (TextView) findViewById(R.id.sentenceText);
		sentenceText.setText(getSentence());
	}
	
	// ������ ���ڿ��� ���� ��(���Ŀ� Ŭ������ ����� �� ��)
	protected String getSentence() {
		String rtnSentence = "";
		
		// ���Ŀ��� DB���� �������� �ɷ� �ٲ�� �� �κ���.
		String sentence = "����û ��â�� ��öâ, ����û ��â�� ��öâ";
		rtnSentence = sentence.replaceAll("\\p{Space}", "");
		
		return sentence;
		//return rtnSentence;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK && (requestCode == SST_SPH)) {
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
		String key = SpeechRecognizer.RESULTS_RECOGNITION;
		ArrayList<String> mResult;
		mResult = data.getStringArrayListExtra(key);
		String[] result = new String[mResult.size()];
		mResult.toArray(result);
		
		recogText.setText(result[0]);
		
		
		/*
		for(int i=0; i < mResult.size(); i++) {
			recogText.append(result[i] + "\n");
		}
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
