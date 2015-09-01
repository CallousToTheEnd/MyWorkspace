package com.lk.notepad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lk.db.DBService;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateNote extends Activity {
	
	EditText et ;
	String SDCARD_PATH = Environment.getExternalStorageDirectory().toString() + "/Note";
	String intentText = "";
	Intent intent ;
	DBService dbService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_note);
		
		dbService = new DBService(CreateNote.this, "notepad", null, 1);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		//初始化文本内容
		intent = getIntent();
		if(intent.hasExtra("content") && intent.hasExtra("title")){
			intentText = intent.getStringExtra("content");
			actionBar.setTitle(intent.getStringExtra("title"));
		}
		et = (EditText) findViewById(R.id.etCreateNote);
		et.setText(intentText);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.create_note, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		 case android.R.id.home:
			 finish();
			 break;
		 case R.id.saveNote:
			 final LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.create_note_ok, null);
			 new AlertDialog.Builder(this).setTitle("click")
			 	.setView(linearLayout)
			 	.setPositiveButton("取消", null)
			 	.setNegativeButton("保存", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						EditText etOk = (EditText) linearLayout.findViewById(R.id.etOk);
						String fileName = etOk.getText().toString();
						String content = et.getText().toString();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String time = format.format(new Date(System.currentTimeMillis()));
						//判断数据库内是否有相同title的数据,并判断是否是MainActivity传进数据
						if(dbService.isExist(fileName)){
							if(intent.hasExtra("content") && intent.hasExtra("title") 
									&& fileName == intent.getStringExtra("title")){
								dbService.dataUpdate(content, time, fileName);
								new AlertDialog.Builder(CreateNote.this).setTitle("保存成功")
								.setNegativeButton("继续编辑", null)
								.setPositiveButton("返回主界面", new OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										finish();
									}
								})
								.show();
							}else{
							new AlertDialog.Builder(CreateNote.this).setTitle("命名重复，请重新保存")
								.setNegativeButton("返回", null)
								.show();
							}
						}else{
						dbService.dataInsert(fileName, content, time);
						new AlertDialog.Builder(CreateNote.this).setTitle("保存成功")
							.setNegativeButton("继续编辑", null)
							.setPositiveButton("返回主界面", new OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									finish();
								}
							})
							.show();
						}
					}
				})
			 	.show();
			 break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
}
