package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.ItemInfo;
import com.mobileclient.service.ItemInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class ItemInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_itemId;
	// 声明指标标题输入框
	private EditText ET_itemTitle;
	// 声明指标描述输入框
	private EditText ET_itemDesc;
	protected String carmera_path;
	/*要保存的评价指标信息*/
	ItemInfo itemInfo = new ItemInfo();
	/*评价指标管理业务逻辑层*/
	private ItemInfoService itemInfoService = new ItemInfoService();

	private int itemId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.iteminfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑评价指标信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_itemId = (TextView) findViewById(R.id.TV_itemId);
		ET_itemTitle = (EditText) findViewById(R.id.ET_itemTitle);
		ET_itemDesc = (EditText) findViewById(R.id.ET_itemDesc);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		itemId = extras.getInt("itemId");
		/*单击修改评价指标按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取指标标题*/ 
					if(ET_itemTitle.getText().toString().equals("")) {
						Toast.makeText(ItemInfoEditActivity.this, "指标标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_itemTitle.setFocusable(true);
						ET_itemTitle.requestFocus();
						return;	
					}
					itemInfo.setItemTitle(ET_itemTitle.getText().toString());
					/*验证获取指标描述*/ 
					if(ET_itemDesc.getText().toString().equals("")) {
						Toast.makeText(ItemInfoEditActivity.this, "指标描述输入不能为空!", Toast.LENGTH_LONG).show();
						ET_itemDesc.setFocusable(true);
						ET_itemDesc.requestFocus();
						return;	
					}
					itemInfo.setItemDesc(ET_itemDesc.getText().toString());
					/*调用业务逻辑层上传评价指标信息*/
					ItemInfoEditActivity.this.setTitle("正在更新评价指标信息，稍等...");
					String result = itemInfoService.UpdateItemInfo(itemInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    itemInfo = itemInfoService.GetItemInfo(itemId);
		this.TV_itemId.setText(itemId+"");
		this.ET_itemTitle.setText(itemInfo.getItemTitle());
		this.ET_itemDesc.setText(itemInfo.getItemDesc());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
