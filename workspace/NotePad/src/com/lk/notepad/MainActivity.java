package com.lk.notepad;

import java.util.ArrayList;
import java.util.List;

import com.lk.db.DBService;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

	RecyclerView rv ;
	List<String> noteList = new ArrayList<String>();
	List<String> timeList = new ArrayList<String>();
	List<String> contentList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initDatabase();
		initRecyclerView();
		
		//ActionBar设置
		ActionBar actionBar = getActionBar();
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.actionbar_background);
		BitmapDrawable drawable = new BitmapDrawable(bitmap);
		actionBar.setBackgroundDrawable(drawable);
//		actionBar.setCustomView(R.layout.ad);
//		actionBar.getCustomView().findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(MainActivity.this, CreateNote.class);
//				startActivity(intent);
//			}
//		});
//		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM  
//		        | ActionBar.DISPLAY_SHOW_HOME);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		initDatabase();
		initRecyclerView();
	}
	
	//初始化数据库和列表信息
	public void initDatabase(){
		DBService dbService = new DBService(MainActivity.this, "notepad", null, 1);
		SQLiteDatabase dbReadable = dbService.getReadableDatabase();
		noteList = dbService.getNoteList();
		timeList = dbService.getTimeList();
		contentList = dbService.getContentList();
	}
	
	//初始化RecyclerView
	public void initRecyclerView(){
			
			
			rv = (RecyclerView) findViewById(R.id.rvList);
			rv.setLayoutManager(new LinearLayoutManager(this));
			rv.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
				
			
			@Override
			public int getItemCount() {
				// TODO Auto-generated method stub
				return noteList.size();
			}
		
			@Override
			public void onBindViewHolder(ViewHolder arg0, int arg1) {
				mViewHolder mvh = (mViewHolder) arg0;
				mvh.getTitle().setText(" " + noteList.get(arg1));
				mvh.getContent().setText(timeList.get(arg1) + " ");
				mvh.setPosition(arg1);			//每个Item设置一个position
			}
		
			@Override
			public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				return new mViewHolder(LayoutInflater.from(arg0.getContext())
						.inflate(R.layout.rvlayout, arg0, false));
			}
					
			class mViewHolder extends ViewHolder {
						
				TextView tvTitle;
				TextView tvContent;
				View root;
				int position;
		
				public mViewHolder(View arg0) {
					super(arg0);
					root = arg0;
					tvTitle = (TextView) root.findViewById(R.id.tvTitle);
					tvContent = (TextView) root.findViewById(R.id.tvContent);
							
					//设置Item的点击和触摸事件
					root.setOnTouchListener(new OnTouchListener() {
								
						@Override
						public boolean onTouch(View v, MotionEvent event) {
									
							return false;
						}
					});
					root.setOnLongClickListener(new OnLongClickListener() {
								
						@Override
						public boolean onLongClick(View v) {
							new AlertDialog.Builder(MainActivity.this).setMessage("sdf")
								.show();
							return false;
						}
					});
					root.setOnClickListener(new OnClickListener() {
								
						@Override
						public void onClick(View v) {
							//将title和content传入CreateNote
							int position = mViewHolder.this.position;
							String title = tvTitle.getText().toString();
							String content = contentList.get(position);
							Intent intent = new Intent(MainActivity.this, CreateNote.class);
							intent.putExtra("title", title);
							intent.putExtra("content", content);
							startActivity(intent);
						}
					});
				}
						
				public TextView getTitle(){
					return tvTitle;
				}
						
				public TextView getContent(){
					return tvContent;
				}
				
				public void setPosition(int position){
					this.position = position;
				}
		
			}
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.createNote:
			Intent intent = new Intent(this, CreateNote.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
}
