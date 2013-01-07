package com.joessy.medcatalog.activity;

import java.util.ArrayList;

import com.joessy.medcatalog.adapter.*;

import android.os.Bundle;
import android.app.ListActivity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class MainActivity extends ListActivity implements OnScrollListener {
	private ListView listView;
	private ListViewAdapter adapter;
	private SQLiteDBAdapter sqlAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sqlAdapter=new SQLiteDBAdapter(getResources().openRawResource(R.raw.yao));
		listView = getListView(); // 获取id是list的ListView
		ArrayList<String> items = new ArrayList<String>();
		adapter = new ListViewAdapter(this, items);
		setListAdapter(adapter); // 自动为id是list的ListView设置适配器
		listView.setOnScrollListener(this); // 添加滑动监听
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	public void searchResult(View view) {
		// 清空list
		adapter.removeItems();
		// 获取屏幕输入并以此为关键字查询
		EditText editText = (EditText) findViewById(R.id.search_text_name);
		String str = editText.getText().toString();
		StringBuilder tmp = new StringBuilder();
		tmp.append("select * from \"201209\" where 药品名称 like \"%").append(str)
				.append("%\"");
		Log.d("SQL", tmp.toString());
		Cursor cursor = SQLiteDBAdapter.getCursor(tmp.toString());
		tmp.delete(0, tmp.length());
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			adapter.addItem(cursor.getString(1));
		}
		cursor.close();
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		sqlAdapter.closeDB();// 关闭数据库对象
	}

}
