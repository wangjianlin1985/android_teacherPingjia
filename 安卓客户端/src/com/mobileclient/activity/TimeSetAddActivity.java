package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class TimeSetAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 出版开始日期控件
	private DatePicker dp_startDate;
	// 出版结束日期控件
	private DatePicker dp_endDate;
	protected String carmera_path;
	/*要保存的评价时间设置信息*/
	TimeSet timeSet = new TimeSet();
	/*评价时间设置管理业务逻辑层*/
	private TimeSetService timeSetService = new TimeSetService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.timeset_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加评价时间设置");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		dp_startDate = (DatePicker)this.findViewById(R.id.dp_startDate);
		dp_endDate = (DatePicker)this.findViewById(R.id.dp_endDate);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加评价时间设置按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取开始日期*/
					Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
					timeSet.setStartDate(new Timestamp(startDate.getTime()));
					/*获取结束日期*/
					Date endDate = new Date(dp_endDate.getYear()-1900,dp_endDate.getMonth(),dp_endDate.getDayOfMonth());
					timeSet.setEndDate(new Timestamp(endDate.getTime()));
					/*调用业务逻辑层上传评价时间设置信息*/
					TimeSetAddActivity.this.setTitle("正在上传评价时间设置信息，稍等...");
					String result = timeSetService.AddTimeSet(timeSet);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
