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
	
	public Context mContext = null;		// ���� ��Ƽ��Ƽ���� ���� 

	Intent i;
	SpeechRecognizer mRecognizer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_main);
		recogText = (TextView) findViewById(R.id.recogText);
		
		/* ���� �������� ǥ�� */
		TextView stage = (TextView)findViewById(R.id.stage);
		stage.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumPen.otf"));
		
		
		i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
		
		/* 
		 * ���� �ν� ��ü ����
		 */
		mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		mRecognizer.setRecognitionListener(listener);
		
		/*  
		 * ����ũ�� Ŭ���ϸ� ���� ���� ��
		 */
		ImageButton recordBtn = (ImageButton) findViewById(R.id.recordBtn);
		recordBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mRecognizer.startListening(i);
			}
		});
		
		/*
		 * ������ �ܾ� ǥ�� 
		 */
		sentenceText = (TextView) findViewById(R.id.sentenceText);
		sentenceText.setText(getSentence());
		
	}

	// ������ ���ڿ��� ���� ��(���Ŀ� Ŭ������ ����� �� ��)
	protected String getSentence() {
		String rtnSentence = "";
		
		// ���Ŀ��� DB���� �������� �ɷ� �ٲ�� �� �κ���.
		String sentence = "���۽�Ÿ ��";
		rtnSentence = sentence.replaceAll("\\p{Space}", "");
		
		return sentence;
		//return rtnSentence;
	}
	
	/* 
	 * �����ν� ������ 
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
            Toast.makeText(getBaseContext(), "���� ���ϼ���.", Toast.LENGTH_SHORT).show();
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
			// ���� �ڵ带 �з�
			switch(error){
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
         
        @Override
        public void onEndOfSpeech() {
            Toast.makeText(getBaseContext(), "�����ν��� �Ϸ�Ǿ����ϴ�.", Toast.LENGTH_SHORT).show();
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
