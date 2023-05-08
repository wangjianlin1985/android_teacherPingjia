package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.CheckResult;
import com.mobileclient.service.CheckResultService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
import com.mobileclient.domain.ItemInfo;
import com.mobileclient.service.ItemInfoService;
import com.mobileclient.domain.ResultItem;
import com.mobileclient.service.ResultItemService;
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
public class CheckResultDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_resultId;
	// 声明评价的学生控件
	private TextView TV_studentObj;
	// 声明被评价老师控件
	private TextView TV_teacherObj;
	// 声明被评价的指标控件
	private TextView TV_itemObj;
	// 声明评价结果控件
	private TextView TV_resultObj;
	/* 要保存的考核结果信息 */
	CheckResult checkResult = new CheckResult(); 
	/* 考核结果管理业务逻辑层 */
	private CheckResultService checkResultService = new CheckResultService();
	private StudentService studentService = new StudentService();
	private TeacherService teacherService = new TeacherService();
	private ItemInfoService itemInfoService = new ItemInfoService();
	private ResultItemService resultItemService = new ResultItemService();
	private int resultId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.checkresult_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看考核结果详情");
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
		TV_itemObj = (TextView) findViewById(R.id.TV_itemObj);
		TV_resultObj = (TextView) findViewById(R.id.TV_resultObj);
		Bundle extras = this.getIntent().getExtras();
		resultId = extras.getInt("resultId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckResultDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    checkResult = checkResultService.GetCheckResult(resultId); 
		this.TV_resultId.setText(checkResult.getResultId() + "");
		Student studentObj = studentService.GetStudent(checkResult.getStudentObj());
		this.TV_studentObj.setText(studentObj.getName());
		Teacher teacherObj = teacherService.GetTeacher(checkResult.getTeacherObj());
		this.TV_teacherObj.setText(teacherObj.getName());
		ItemInfo itemObj = itemInfoService.GetItemInfo(checkResult.getItemObj());
		this.TV_itemObj.setText(itemObj.getItemTitle());
		ResultItem resultObj = resultItemService.GetResultItem(checkResult.getResultObj());
		this.TV_resultObj.setText(resultObj.getResultItemText());
	} 
}
