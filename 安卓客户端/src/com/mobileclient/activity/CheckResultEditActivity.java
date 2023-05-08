package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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

public class CheckResultEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_resultId;
	// 声明评价的学生下拉框
	private Spinner spinner_studentObj;
	private ArrayAdapter<String> studentObj_adapter;
	private static  String[] studentObj_ShowText  = null;
	private List<Student> studentList = null;
	/*评价的学生管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明被评价老师下拉框
	private Spinner spinner_teacherObj;
	private ArrayAdapter<String> teacherObj_adapter;
	private static  String[] teacherObj_ShowText  = null;
	private List<Teacher> teacherList = null;
	/*被评价老师管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();
	// 声明被评价的指标下拉框
	private Spinner spinner_itemObj;
	private ArrayAdapter<String> itemObj_adapter;
	private static  String[] itemObj_ShowText  = null;
	private List<ItemInfo> itemInfoList = null;
	/*被评价的指标管理业务逻辑层*/
	private ItemInfoService itemInfoService = new ItemInfoService();
	// 声明评价结果下拉框
	private Spinner spinner_resultObj;
	private ArrayAdapter<String> resultObj_adapter;
	private static  String[] resultObj_ShowText  = null;
	private List<ResultItem> resultItemList = null;
	/*评价结果管理业务逻辑层*/
	private ResultItemService resultItemService = new ResultItemService();
	protected String carmera_path;
	/*要保存的考核结果信息*/
	CheckResult checkResult = new CheckResult();
	/*考核结果管理业务逻辑层*/
	private CheckResultService checkResultService = new CheckResultService();

	private int resultId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.checkresult_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑考核结果信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_resultId = (TextView) findViewById(R.id.TV_resultId);
		spinner_studentObj = (Spinner) findViewById(R.id.Spinner_studentObj);
		// 获取所有的评价的学生
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int studentCount = studentList.size();
		studentObj_ShowText = new String[studentCount];
		for(int i=0;i<studentCount;i++) { 
			studentObj_ShowText[i] = studentList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		studentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentObj_ShowText);
		// 设置图书类别下拉列表的风格
		studentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentObj.setAdapter(studentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_studentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				checkResult.setStudentObj(studentList.get(arg2).getStudentNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentObj.setVisibility(View.VISIBLE);
		spinner_teacherObj = (Spinner) findViewById(R.id.Spinner_teacherObj);
		// 获取所有的被评价老师
		try {
			teacherList = teacherService.QueryTeacher(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int teacherCount = teacherList.size();
		teacherObj_ShowText = new String[teacherCount];
		for(int i=0;i<teacherCount;i++) { 
			teacherObj_ShowText[i] = teacherList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		teacherObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, teacherObj_ShowText);
		// 设置图书类别下拉列表的风格
		teacherObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_teacherObj.setAdapter(teacherObj_adapter);
		// 添加事件Spinner事件监听
		spinner_teacherObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				checkResult.setTeacherObj(teacherList.get(arg2).getTeacherNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_teacherObj.setVisibility(View.VISIBLE);
		spinner_itemObj = (Spinner) findViewById(R.id.Spinner_itemObj);
		// 获取所有的被评价的指标
		try {
			itemInfoList = itemInfoService.QueryItemInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int itemInfoCount = itemInfoList.size();
		itemObj_ShowText = new String[itemInfoCount];
		for(int i=0;i<itemInfoCount;i++) { 
			itemObj_ShowText[i] = itemInfoList.get(i).getItemTitle();
		}
		// 将可选内容与ArrayAdapter连接起来
		itemObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, itemObj_ShowText);
		// 设置图书类别下拉列表的风格
		itemObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_itemObj.setAdapter(itemObj_adapter);
		// 添加事件Spinner事件监听
		spinner_itemObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				checkResult.setItemObj(itemInfoList.get(arg2).getItemId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_itemObj.setVisibility(View.VISIBLE);
		spinner_resultObj = (Spinner) findViewById(R.id.Spinner_resultObj);
		// 获取所有的评价结果
		try {
			resultItemList = resultItemService.QueryResultItem(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int resultItemCount = resultItemList.size();
		resultObj_ShowText = new String[resultItemCount];
		for(int i=0;i<resultItemCount;i++) { 
			resultObj_ShowText[i] = resultItemList.get(i).getResultItemText();
		}
		// 将可选内容与ArrayAdapter连接起来
		resultObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, resultObj_ShowText);
		// 设置图书类别下拉列表的风格
		resultObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_resultObj.setAdapter(resultObj_adapter);
		// 添加事件Spinner事件监听
		spinner_resultObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				checkResult.setResultObj(resultItemList.get(arg2).getResultItemId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_resultObj.setVisibility(View.VISIBLE);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		resultId = extras.getInt("resultId");
		/*单击修改考核结果按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*调用业务逻辑层上传考核结果信息*/
					CheckResultEditActivity.this.setTitle("正在更新考核结果信息，稍等...");
					String result = checkResultService.UpdateCheckResult(checkResult);
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
	    checkResult = checkResultService.GetCheckResult(resultId);
		this.TV_resultId.setText(resultId+"");
		for (int i = 0; i < studentList.size(); i++) {
			if (checkResult.getStudentObj().equals(studentList.get(i).getStudentNumber())) {
				this.spinner_studentObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < teacherList.size(); i++) {
			if (checkResult.getTeacherObj().equals(teacherList.get(i).getTeacherNumber())) {
				this.spinner_teacherObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < itemInfoList.size(); i++) {
			if (checkResult.getItemObj() == itemInfoList.get(i).getItemId()) {
				this.spinner_itemObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < resultItemList.size(); i++) {
			if (checkResult.getResultObj() == resultItemList.get(i).getResultItemId()) {
				this.spinner_resultObj.setSelection(i);
				break;
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
