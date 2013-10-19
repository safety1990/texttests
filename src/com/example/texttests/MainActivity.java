package com.example.texttests;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends Activity {
	private static final String PREF_KEY = "key";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onPause() {
		EditText editText = (EditText) findViewById(R.id.main_text);
		String message = editText.getText().toString();
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(PREF_KEY, message);
		editor.commit();
		super.onPause();
	}

	@Override
	protected void onResume() {
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		String message = preferences.getString(PREF_KEY, "");
		EditText editText = (EditText) findViewById(R.id.main_text);
		editText.setText(message);
		super.onResume();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		EditText editText = (EditText) findViewById(R.id.main_text);
		switch (item.getItemId()) {
		case R.id.action_test:
			String message = editText.getText().toString();
			Intent intent = new Intent(this, ResultActivity.class);
			intent.putExtra(getString(R.string.EXTRA_NAME), message);
			startActivity(intent);
			break;
		case R.id.action_clear:
			editText.setText("");
			break;
		case R.id.action_paste:
			ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			String s = clipboard.getPrimaryClip().getItemAt(0).getText()
					.toString();
			editText.setText(s);
			break;
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
