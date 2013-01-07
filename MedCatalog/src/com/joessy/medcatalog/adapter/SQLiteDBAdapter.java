package com.joessy.medcatalog.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class  SQLiteDBAdapter {
	static SQLiteDatabase db=null;
	private final static String DATABASE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/yao";
	private  String DATABASE_FILENAME = "yao.db";
	private  String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;

	//初始化数据
	public SQLiteDBAdapter(InputStream is) {
		try {
			
			File dir = new File(DATABASE_PATH);
			if (!dir.exists())
				dir.mkdir();
			if (!(new File(databaseFilename)).exists()) {
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获得数据库
	public SQLiteDBAdapter() {
		
		db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
	}
	
	public static Cursor getCursor(String sql)
	{
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}

	public void closeDB()
	{
		db.close();
	}
}
