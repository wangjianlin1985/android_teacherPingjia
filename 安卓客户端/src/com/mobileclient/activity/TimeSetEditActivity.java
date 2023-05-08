package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.TimeSet;
import com.mobileclient.service.TimeSetService;
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

public class TimeSetEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_timeId;
	// 出版开始日期控件
	private DatePicker dp_startDate;
	// 出版结束日期控件
	private DatePicker dp_endDate;
	protected String carmera_path;
	/*要保存的评价时间设置信息*/
	TimeSet timeSet = new TimeSet();
	/*评价时间设置管理业务逻辑层*/
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
		setContentView(R.layout.timeset_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑评价时间设置信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_timeId = (TextView) findViewById(R.id.TV_timeId);
		dp_startDate = (DatePicker)this.findViewById(R.id.dp_startDate);
		dp_endDate = (DatePicker)this.findViewById(R.id.dp_endDate);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		timeId = extras.getInt("timeId");
		/*单击修改评价时间设置按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取出版日期*/
					Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
					timeSet.setStartDate(new Timestamp(startDate.getTime()));
					/*获取出版日期*/
					Date endDate = new Date(dp_endDate.getYear()-1900,dp_endDate.getMonth(),dp_endDate.getDayOfMonth());
					timeSet.setEndDate(new Timestamp(endDate.getTime()));
					/*调用业务逻辑层上传评价时间设置信息*/
					TimeSetEditActivity.this.setTitle("正在更新评价时间设置信息，稍等...");
					String result = timeSetService.UpdateTimeSet(timeSet);
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
	    timeSet = timeSetService.GetTimeSet(timeId);
		this.TV_timeId.setText(timeId+"");
		Date startDate = new Date(timeSet.getStartDate().getTime());
		this.dp_startDate.init(startDate.getYear() + 1900,startDate.getMonth(), startDate.getDate(), null);
		Date endDate = new Date(timeSet.getEndDate().getTime());
		this.dp_endDate.init(endDate.getYear() + 1900,endDate.getMonth(), endDate.getDate(), null);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
