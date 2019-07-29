package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
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
public class TeacherAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明教师用户名输入框
	private EditText ET_teacherNumber;
	// 声明教师密码输入框
	private EditText ET_password;
	// 声明教师姓名输入框
	private EditText ET_name;
	// 声明教师性别输入框
	private EditText ET_sex;
	// 声明教师年龄输入框
	private EditText ET_age;
	// 声明教授课程输入框
	private EditText ET_courseName;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明邮箱地址输入框
	private EditText ET_email;
	// 声明附加信息输入框
	private EditText ET_memo;
	protected String carmera_path;
	/*要保存的教师信息信息*/
	Teacher teacher = new Teacher();
	/*教师信息管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.teacher_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加教师信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_teacherNumber = (EditText) findViewById(R.id.ET_teacherNumber);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		ET_age = (EditText) findViewById(R.id.ET_age);
		ET_courseName = (EditText) findViewById(R.id.ET_courseName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_email = (EditText) findViewById(R.id.ET_email);
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加教师信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取教师用户名*/
					if(ET_teacherNumber.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "教师用户名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teacherNumber.setFocusable(true);
						ET_teacherNumber.requestFocus();
						return;
					}
					teacher.setTeacherNumber(ET_teacherNumber.getText().toString());
					/*验证获取教师密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "教师密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					teacher.setPassword(ET_password.getText().toString());
					/*验证获取教师姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "教师姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					teacher.setName(ET_name.getText().toString());
					/*验证获取教师性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "教师性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					teacher.setSex(ET_sex.getText().toString());
					/*验证获取教师年龄*/ 
					if(ET_age.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "教师年龄输入不能为空!", Toast.LENGTH_LONG).show();
						ET_age.setFocusable(true);
						ET_age.requestFocus();
						return;	
					}
					teacher.setAge(Integer.parseInt(ET_age.getText().toString()));
					/*验证获取教授课程*/ 
					if(ET_courseName.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "教授课程输入不能为空!", Toast.LENGTH_LONG).show();
						ET_courseName.setFocusable(true);
						ET_courseName.requestFocus();
						return;	
					}
					teacher.setCourseName(ET_courseName.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					teacher.setTelephone(ET_telephone.getText().toString());
					/*验证获取邮箱地址*/ 
					if(ET_email.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "邮箱地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_email.setFocusable(true);
						ET_email.requestFocus();
						return;	
					}
					teacher.setEmail(ET_email.getText().toString());
					/*验证获取附加信息*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					teacher.setMemo(ET_memo.getText().toString());
					/*调用业务逻辑层上传教师信息信息*/
					TeacherAddActivity.this.setTitle("正在上传教师信息信息，稍等...");
					String result = teacherService.AddTeacher(teacher);
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
