package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ItemInfo;
import com.mobileclient.service.ItemInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class ItemInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_itemId;
	// 声明指标标题控件
	private TextView TV_itemTitle;
	// 声明指标描述控件
	private TextView TV_itemDesc;
	/* 要保存的评价指标信息 */
	ItemInfo itemInfo = new ItemInfo(); 
	/* 评价指标管理业务逻辑层 */
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
		setContentView(R.layout.iteminfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看评价指标详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_itemId = (TextView) findViewById(R.id.TV_itemId);
		TV_itemTitle = (TextView) findViewById(R.id.TV_itemTitle);
		TV_itemDesc = (TextView) findViewById(R.id.TV_itemDesc);
		Bundle extras = this.getIntent().getExtras();
		itemId = extras.getInt("itemId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ItemInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    itemInfo = itemInfoService.GetItemInfo(itemId); 
		this.TV_itemId.setText(itemInfo.getItemId() + "");
		this.TV_itemTitle.setText(itemInfo.getItemTitle());
		this.TV_itemDesc.setText(itemInfo.getItemDesc());
	} 
}
