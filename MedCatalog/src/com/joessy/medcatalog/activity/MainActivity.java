package com.joessy.medcatalog.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.Toast;

public class MainActivity extends ListActivity implements OnScrollListener {
	private ListView listView;
	private ListViewAdapter adapter;
	private SQLiteDBAdapter sqlAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sqlAdapter = new SQLiteDBAdapter(getResources().openRawResource(
				R.raw.yao));
		listView = getListView(); // ��ȡid��list��ListView
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		adapter = new ListViewAdapter(this, list);
		setListAdapter(adapter); // �Զ�Ϊid��list��ListView����������
		listView.setOnScrollListener(this); // ��ӻ�������
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
		// ���list
		adapter.removeItems();
		// ��ȡ��Ļ���벢�Դ�Ϊ�ؼ��ֲ�ѯ
		EditText editText = (EditText) findViewById(R.id.search_text_name);
		String str = editText.getText().toString();
		StringBuilder tmp = new StringBuilder();
		tmp.append("select * from yao where ҩƷ���� like \"%").append(str)
				.append("%\" order by ҩƷ����");
		Log.d("SQL", tmp.toString());
		Cursor cursor = SQLiteDBAdapter.getCursor(tmp.toString());
		tmp.delete(0, tmp.length());

		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("id", cursor.getString(0));
			item.put("title", cursor.getString(1));
			tmp.append(cursor.getString(2)).append("/")
					.append(cursor.getString(3)).append(" ")
					.append(cursor.getString(4));
			item.put("info", tmp.toString());
			tmp.delete(0, tmp.length());
			adapter.addItem(item);
		}
		
		tmp.append("��������").append(cursor.getCount()).append("����¼");
		Toast.makeText(this, tmp.toString(), Toast.LENGTH_SHORT).show();
		tmp.delete(0, tmp.length());
		cursor.close();
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		sqlAdapter.closeDB();// �ر����ݿ����
	}

}
