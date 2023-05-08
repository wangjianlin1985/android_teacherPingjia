package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
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
public class StudentDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明学生用户名控件
	private TextView TV_studentNumber;
	// 声明学生密码控件
	private TextView TV_password;
	// 声明学生姓名控件
	private TextView TV_name;
	// 声明学生性别控件
	private TextView TV_sex;
	// 声明学生年龄控件
	private TextView TV_age;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明联系qq控件
	private TextView TV_qq;
	// 声明家庭地址控件
	private TextView TV_address;
	// 声明附加信息控件
	private TextView TV_memo;
	/* 要保存的学生信息信息 */
	Student student = new Student(); 
	/* 学生信息管理业务逻辑层 */
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
		setContentView(R.layout.student_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看学生信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_studentNumber = (TextView) findViewById(R.id.TV_studentNumber);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_age = (TextView) findViewById(R.id.TV_age);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_qq = (TextView) findViewById(R.id.TV_qq);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_memo = (TextView) findViewById(R.id.TV_memo);
		Bundle extras = this.getIntent().getExtras();
		studentNumber = extras.getString("studentNumber");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StudentDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    student = studentService.GetStudent(studentNumber); 
		this.TV_studentNumber.setText(student.getStudentNumber());
		this.TV_password.setText(student.getPassword());
		this.TV_name.setText(student.getName());
		this.TV_sex.setText(student.getSex());
		this.TV_age.setText(student.getAge() + "");
		this.TV_telephone.setText(student.getTelephone());
		this.TV_qq.setText(student.getQq());
		this.TV_address.setText(student.getAddress());
		this.TV_memo.setText(student.getMemo());
	} 
}
