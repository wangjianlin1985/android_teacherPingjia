package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.QuestionResult;
import com.mobileclient.service.QuestionResultService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
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
public class QuestionResultDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_resultId;
	// 声明评价的学生控件
	private TextView TV_studentObj;
	// 声明被评价老师控件
	private TextView TV_teacherObj;
	// 声明问卷回答控件
	private TextView TV_answer;
	/* 要保存的问卷结果信息 */
	QuestionResult questionResult = new QuestionResult(); 
	/* 问卷结果管理业务逻辑层 */
	private QuestionResultService questionResultService = new QuestionResultService();
	private StudentService studentService = new StudentService();
	private TeacherService teacherService = new TeacherService();
	private int resultId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.questionresult_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看问卷结果详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_resultId = (TextView) findViewById(R.id.TV_resultId);
		TV_studentObj = (TextView) findViewById(R.id.TV_studentObj);
		TV_teacherObj = (TextView) findViewById(R.id.TV_teacherObj);
		TV_answer = (TextView) findViewById(R.id.TV_answer);
		Bundle extras = this.getIntent().getExtras();
		resultId = extras.getInt("resultId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				QuestionResultDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    questionResult = questionResultService.GetQuestionResult(resultId); 
		this.TV_resultId.setText(questionResult.getResultId() + "");
		Student studentObj = studentService.GetStudent(questionResult.getStudentObj());
		this.TV_studentObj.setText(studentObj.getName());
		Teacher teacherObj = teacherService.GetTeacher(questionResult.getTeacherObj());
		this.TV_teacherObj.setText(teacherObj.getName());
		this.TV_answer.setText(questionResult.getAnswer());
	} 
}
