package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.TimeSet;
import com.mobileclient.service.TimeSetService;
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
public class TimeSetDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_timeId;
	// 声明开始日期控件
	private TextView TV_startDate;
	// 声明结束日期控件
	private TextView TV_endDate;
	/* 要保存的评价时间设置信息 */
	TimeSet timeSet = new TimeSet(); 
	/* 评价时间设置管理业务逻辑层 */
	private TimeSetService timeSetService = new TimeSetService();
	private int timeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.timeset_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看评价时间设置详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_timeId = (TextView) findViewById(R.id.TV_timeId);
		TV_startDate = (TextView) findViewById(R.id.TV_startDate);
		TV_endDate = (TextView) findViewById(R.id.TV_endDate);
		Bundle extras = this.getIntent().getExtras();
		timeId = extras.getInt("timeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TimeSetDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    timeSet = timeSetService.GetTimeSet(timeId); 
		this.TV_timeId.setText(timeSet.getTimeId() + "");
		Date startDate = new Date(timeSet.getStartDate().getTime());
		String startDateStr = (startDate.getYear() + 1900) + "-" + (startDate.getMonth()+1) + "-" + startDate.getDate();
		this.TV_startDate.setText(startDateStr);
		Date endDate = new Date(timeSet.getEndDate().getTime());
		String endDateStr = (endDate.getYear() + 1900) + "-" + (endDate.getMonth()+1) + "-" + endDate.getDate();
		this.TV_endDate.setText(endDateStr);
	} 
}
