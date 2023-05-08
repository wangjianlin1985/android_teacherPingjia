package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.ResultItem;
import com.mobileclient.service.ResultItemService;
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

public class ResultItemEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_resultItemId;
	// 声明结果描述输入框
	private EditText ET_resultItemText;
	protected String carmera_path;
	/*要保存的结果指标信息*/
	ResultItem resultItem = new ResultItem();
	/*结果指标管理业务逻辑层*/
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
		setContentView(R.layout.resultitem_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑结果指标信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_resultItemId = (TextView) findViewById(R.id.TV_resultItemId);
		ET_resultItemText = (EditText) findViewById(R.id.ET_resultItemText);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		resultItemId = extras.getInt("resultItemId");
		/*单击修改结果指标按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取结果描述*/ 
					if(ET_resultItemText.getText().toString().equals("")) {
						Toast.makeText(ResultItemEditActivity.this, "结果描述输入不能为空!", Toast.LENGTH_LONG).show();
						ET_resultItemText.setFocusable(true);
						ET_resultItemText.requestFocus();
						return;	
					}
					resultItem.setResultItemText(ET_resultItemText.getText().toString());
					/*调用业务逻辑层上传结果指标信息*/
					ResultItemEditActivity.this.setTitle("正在更新结果指标信息，稍等...");
					String result = resultItemService.UpdateResultItem(resultItem);
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
	    resultItem = resultItemService.GetResultItem(resultItemId);
		this.TV_resultItemId.setText(resultItemId+"");
		this.ET_resultItemText.setText(resultItem.getResultItemText());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
