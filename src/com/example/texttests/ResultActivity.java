package com.example.texttests;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		doTests();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	void doTests() {
		String message = getIntent().getStringExtra(
				getString(R.string.EXTRA_NAME));
		ArrayList<String> list = new ArrayList<String>();
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		if (sharedPreferences.getBoolean("pref_basic_chars", false)) {
			int i = message.length();
			list.add(getString(R.string.pref_basic_chars_title) + ": " + i);
		}
		if (sharedPreferences.getBoolean("pref_basic_chars_no_spaces", false)) {
			int i = count(message, "[^ ]");
			list.add(getString(R.string.pref_basic_chars_no_spaces_title)
					+ ": " + i);
		}
		if (sharedPreferences.getBoolean("pref_basic_words", false)) {
			int i = count(message, "\\w+");
			list.add(getString(R.string.pref_basic_words_title) + ": " + i);
		}
		if (sharedPreferences.getBoolean("pref_basic_sentences", false)) {
			int i = count(message, "\\w(\\!|\\?|\\.|(\\.\\.\\.))");
			list.add(getString(R.string.pref_basic_sentences_title) + ": " + i);
		}
		if (sharedPreferences.getBoolean("pref_basic_paragraphs", false)) {
			int i = count(message, "\\n");
			list.add(getString(R.string.pref_basic_paragraphs_title) + ": "
					+ (i + 1));
		}

		ListView lvResult = (ListView) findViewById(R.id.result_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		lvResult.setAdapter(adapter);
	}

	public static int count(String str, String regex) {
		int i = 0;
		Matcher m = Pattern.compile(regex).matcher(str);
		while (m.find())
			i++;
		return i;
	}
}
