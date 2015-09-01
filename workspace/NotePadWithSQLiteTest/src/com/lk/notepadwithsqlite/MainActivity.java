package com.lk.notepadwithsqlite;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btnInsert;
	Button btnCreate;
	Button btnSelect;
	EditText et;
	EditText et1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		et = (EditText) findViewById(R.id.et);
		et1 = (EditText) findViewById(R.id.et1);
		btnInsert = (Button) findViewById(R.id.btnInsert);
		btnCreate = (Button) findViewById(R.id.btnCreate);
		btnSelect = (Button) findViewById(R.id.btnSelect);
		btnCreate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DBService dbService = new DBService(MainActivity.this, "test", null, 1);
				SQLiteDatabase db = dbService.getReadableDatabase();
			}
		});
		btnInsert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = format.format(new Date(System.currentTimeMillis()));
				DBService dbService = new DBService(MainActivity.this, "test", null, 1);
				String sql = "insert into test_1 values(?,?)";
				dbService.query(sql, new String[]{et.getText().toString(), currentTime});
			}
		});
		btnSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DBService dbService = new DBService(MainActivity.this, "test", null, 1);
				SQLiteDatabase db = dbService.getReadableDatabase();
				String str = "select * from test_1 where mytext=?";
				String str1 = "12345";
				Cursor cursor = db.rawQuery(str, new String[] {str1});
				while(cursor.moveToNext()){
					String text = cursor.getString(cursor.getColumnIndex("mytext"));
					String time = cursor.getString(cursor.getColumnIndex("time"));
					et1.setText(text + "ʱ�䣺" + time);
				}
			}
		});
		
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
