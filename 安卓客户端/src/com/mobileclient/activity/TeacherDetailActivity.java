package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
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
public class TeacherDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明教师用户名控件
	private TextView TV_teacherNumber;
	// 声明教师密码控件
	private TextView TV_password;
	// 声明教师姓名控件
	private TextView TV_name;
	// 声明教师性别控件
	private TextView TV_sex;
	// 声明教师年龄控件
	private TextView TV_age;
	// 声明教授课程控件
	private TextView TV_courseName;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明邮箱地址控件
	private TextView TV_email;
	// 声明附加信息控件
	private TextView TV_memo;
	/* 要保存的教师信息信息 */
	Teacher teacher = new Teacher(); 
	/* 教师信息管理业务逻辑层 */
	private TeacherService teacherService = new TeacherService();
	private String teacherNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.teacher_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看教师信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_teacherNumber = (TextView) findViewById(R.id.TV_teacherNumber);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_age = (TextView) findViewById(R.id.TV_age);
		TV_courseName = (TextView) findViewById(R.id.TV_courseName);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_email = (TextView) findViewById(R.id.TV_email);
		TV_memo = (TextView) findViewById(R.id.TV_memo);
		Bundle extras = this.getIntent().getExtras();
		teacherNumber = extras.getString("teacherNumber");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TeacherDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    teacher = teacherService.GetTeacher(teacherNumber); 
		this.TV_teacherNumber.setText(teacher.getTeacherNumber());
		this.TV_password.setText(teacher.getPassword());
		this.TV_name.setText(teacher.getName());
		this.TV_sex.setText(teacher.getSex());
		this.TV_age.setText(teacher.getAge() + "");
		this.TV_courseName.setText(teacher.getCourseName());
		this.TV_telephone.setText(teacher.getTelephone());
		this.TV_email.setText(teacher.getEmail());
		this.TV_memo.setText(teacher.getMemo());
	} 
}
