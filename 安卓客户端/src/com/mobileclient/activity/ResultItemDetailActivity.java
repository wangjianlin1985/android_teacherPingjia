package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ResultItem;
import com.mobileclient.service.ResultItemService;
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
public class ResultItemDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_resultItemId;
	// 声明结果描述控件
	private TextView TV_resultItemText;
	/* 要保存的结果指标信息 */
	ResultItem resultItem = new ResultItem(); 
	/* 结果指标管理业务逻辑层 */
	private ResultItemService resultItemService = new ResultItemService();
	private int resultItemId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.resultitem_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看结果指标详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_resultItemId = (TextView) findViewById(R.id.TV_resultItemId);
		TV_resultItemText = (TextView) findViewById(R.id.TV_resultItemText);
		Bundle extras = this.getIntent().getExtras();
		resultItemId = extras.getInt("resultItemId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ResultItemDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    resultItem = resultItemService.GetResultItem(resultItemId); 
		this.TV_resultItemId.setText(resultItem.getResultItemId() + "");
		this.TV_resultItemText.setText(resultItem.getResultItemText());
	} 
}
