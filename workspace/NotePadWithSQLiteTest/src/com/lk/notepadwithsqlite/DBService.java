package com.lk.notepadwithsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBService extends SQLiteOpenHelper {

	public DBService(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String str = "create table test_1 (mytext text(255) null,"
				+"time datetime null)";
		db.execSQL(str);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	public void query(String sql , String[] args){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql, args);
	}

}
