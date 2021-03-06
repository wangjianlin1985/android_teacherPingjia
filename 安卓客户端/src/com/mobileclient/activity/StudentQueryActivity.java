package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class StudentQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明学生用户名输入框
	private EditText ET_studentNumber;
	// 声明学生姓名输入框
	private EditText ET_name;
	// 声明学生性别输入框
	private EditText ET_sex;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明联系qq输入框
	private EditText ET_qq;
	/*查询过滤条件保存到这个对象中*/
	private Student queryConditionStudent = new Student();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.student_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置学生信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_studentNumber = (EditText) findViewById(R.id.ET_studentNumber);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_qq = (EditText) findViewById(R.id.ET_qq);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionStudent.setStudentNumber(ET_studentNumber.getText().toString());
					queryConditionStudent.setName(ET_name.getText().toString());
					queryConditionStudent.setSex(ET_sex.getText().toString());
					queryConditionStudent.setTelephone(ET_telephone.getText().toString());
					queryConditionStudent.setQq(ET_qq.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionStudent", queryConditionStudent);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
