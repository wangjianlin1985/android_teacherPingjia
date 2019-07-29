package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
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

public class StudentEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明学生用户名TextView
	private TextView TV_studentNumber;
	// 声明学生密码输入框
	private EditText ET_password;
	// 声明学生姓名输入框
	private EditText ET_name;
	// 声明学生性别输入框
	private EditText ET_sex;
	// 声明学生年龄输入框
	private EditText ET_age;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明联系qq输入框
	private EditText ET_qq;
	// 声明家庭地址输入框
	private EditText ET_address;
	// 声明附加信息输入框
	private EditText ET_memo;
	protected String carmera_path;
	/*要保存的学生信息信息*/
	Student student = new Student();
	/*学生信息管理业务逻辑层*/
	private StudentService studentService = new StudentService();

	private String studentNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.student_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑学生信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_studentNumber = (TextView) findViewById(R.id.TV_studentNumber);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		ET_age = (EditText) findViewById(R.id.ET_age);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_qq = (EditText) findViewById(R.id.ET_qq);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		studentNumber = extras.getString("studentNumber");
		/*单击修改学生信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取学生密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(StudentEditActivity.this, "学生密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					student.setPassword(ET_password.getText().toString());
					/*验证获取学生姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(StudentEditActivity.this, "学生姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					student.setName(ET_name.getText().toString());
					/*验证获取学生性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(StudentEditActivity.this, "学生性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					student.setSex(ET_sex.getText().toString());
					/*验证获取学生年龄*/ 
					if(ET_age.getText().toString().equals("")) {
						Toast.makeText(StudentEditActivity.this, "学生年龄输入不能为空!", Toast.LENGTH_LONG).show();
						ET_age.setFocusable(true);
						ET_age.requestFocus();
						return;	
					}
					student.setAge(Integer.parseInt(ET_age.getText().toString()));
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(StudentEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					student.setTelephone(ET_telephone.getText().toString());
					/*验证获取联系qq*/ 
					if(ET_qq.getText().toString().equals("")) {
						Toast.makeText(StudentEditActivity.this, "联系qq输入不能为空!", Toast.LENGTH_LONG).show();
						ET_qq.setFocusable(true);
						ET_qq.requestFocus();
						return;	
					}
					student.setQq(ET_qq.getText().toString());
					/*验证获取家庭地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(StudentEditActivity.this, "家庭地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					student.setAddress(ET_address.getText().toString());
					/*验证获取附加信息*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(StudentEditActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					student.setMemo(ET_memo.getText().toString());
					/*调用业务逻辑层上传学生信息信息*/
					StudentEditActivity.this.setTitle("正在更新学生信息信息，稍等...");
					String result = studentService.UpdateStudent(student);
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
	    student = studentService.GetStudent(studentNumber);
		this.TV_studentNumber.setText(studentNumber);
		this.ET_password.setText(student.getPassword());
		this.ET_name.setText(student.getName());
		this.ET_sex.setText(student.getSex());
		this.ET_age.setText(student.getAge() + "");
		this.ET_telephone.setText(student.getTelephone());
		this.ET_qq.setText(student.getQq());
		this.ET_address.setText(student.getAddress());
		this.ET_memo.setText(student.getMemo());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
