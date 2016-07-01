package com.mr.game2048;

import ofs.ahd.dii.AdManager;
import ofs.ahd.dii.br.AdSize;
import ofs.ahd.dii.br.AdView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private TextView tvBest;
	private TextView tvScore;
	private int score = 0;
	private Boolean frist;
	private static MainActivity mainActivity = null;

	public MainActivity() {
		mainActivity = this;
	}

	public static MainActivity getMainActivity() {
		return mainActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AdManager.getInstance(this).init("c7d6ac447abb97d3", "edd10f78f6754c12",false);
		setContentView(R.layout.activity_main);
		AdView adView = new AdView(this,AdSize.FIT_SCREEN);
		LinearLayout adLayout = (LinearLayout)findViewById(R.id.adLayout);
		adLayout.addView(adView);
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvBest = (TextView) findViewById(R.id.tvBest);
	}

	public void clearScore() {
		score = 0;
		showScore();
	}

	public void showScore() {
		tvScore.setText(score + "");

	}

	public void showBest() {
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		frist = preferences.getBoolean("fristTime", true);
		if (frist) {
			String best = preferences.getString("bestScore", "0");
			tvBest.setText(best);
		}else{
			String best = preferences.getString("bestScore", "");
			tvBest.setText(best);
		}
	}

	public void addScore(int s) {
		score += s;
		showScore();
	}

	public void prefBestScore() {
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();
		if (frist) {
			editor.putBoolean("fristTime", false);
			editor.putString("bestScore", tvScore.getText().toString());
			editor.commit();
		} else {
			String best = preferences.getString("bestScore", "");
			String str = tvScore.getText().toString();
			if (Integer.valueOf(str) > Integer.valueOf(best)) {
				editor.putString("bestScore", tvScore.getText().toString());
				editor.commit();
			}
		}
	}

}