package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.CheckResult;
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
public class CheckResultQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明评价的学生下拉框
	private Spinner spinner_studentObj;
	private ArrayAdapter<String> studentObj_adapter;
	private static  String[] studentObj_ShowText  = null;
	private List<Student> studentList = null; 
	/*学生信息管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明被评价老师下拉框
	private Spinner spinner_teacherObj;
	private ArrayAdapter<String> teacherObj_adapter;
	private static  String[] teacherObj_ShowText  = null;
	private List<Teacher> teacherList = null; 
	/*教师信息管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();
	// 声明被评价的指标下拉框
	private Spinner spinner_itemObj;
	private ArrayAdapter<String> itemObj_adapter;
	private static  String[] itemObj_ShowText  = null;
	private List<ItemInfo> itemInfoList = null; 
	/*评价指标管理业务逻辑层*/
	private ItemInfoService itemInfoService = new ItemInfoService();
	// 声明评价结果下拉框
	private Spinner spinner_resultObj;
	private ArrayAdapter<String> resultObj_adapter;
	private static  String[] resultObj_ShowText  = null;
	private List<ResultItem> resultItemList = null; 
	/*结果指标管理业务逻辑层*/
	private ResultItemService resultItemService = new ResultItemService();
	/*查询过滤条件保存到这个对象中*/
	private CheckResult queryConditionCheckResult = new CheckResult();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.checkresult_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置考核结果查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_studentObj = (Spinner) findViewById(R.id.Spinner_studentObj);
		// 获取所有的学生信息
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int studentCount = studentList.size();
		studentObj_ShowText = new String[studentCount+1];
		studentObj_ShowText[0] = "不限制";
		for(int i=1;i<=studentCount;i++) { 
			studentObj_ShowText[i] = studentList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		studentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentObj_ShowText);
		// 设置评价的学生下拉列表的风格
		studentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentObj.setAdapter(studentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_studentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCheckResult.setStudentObj(studentList.get(arg2-1).getStudentNumber()); 
				else
					queryConditionCheckResult.setStudentObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentObj.setVisibility(View.VISIBLE);
		spinner_teacherObj = (Spinner) findViewById(R.id.Spinner_teacherObj);
		// 获取所有的教师信息
		try {
			teacherList = teacherService.QueryTeacher(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int teacherCount = teacherList.size();
		teacherObj_ShowText = new String[teacherCount+1];
		teacherObj_ShowText[0] = "不限制";
		for(int i=1;i<=teacherCount;i++) { 
			teacherObj_ShowText[i] = teacherList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		teacherObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, teacherObj_ShowText);
		// 设置被评价老师下拉列表的风格
		teacherObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_teacherObj.setAdapter(teacherObj_adapter);
		// 添加事件Spinner事件监听
		spinner_teacherObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCheckResult.setTeacherObj(teacherList.get(arg2-1).getTeacherNumber()); 
				else
					queryConditionCheckResult.setTeacherObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_teacherObj.setVisibility(View.VISIBLE);
		spinner_itemObj = (Spinner) findViewById(R.id.Spinner_itemObj);
		// 获取所有的评价指标
		try {
			itemInfoList = itemInfoService.QueryItemInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int itemInfoCount = itemInfoList.size();
		itemObj_ShowText = new String[itemInfoCount+1];
		itemObj_ShowText[0] = "不限制";
		for(int i=1;i<=itemInfoCount;i++) { 
			itemObj_ShowText[i] = itemInfoList.get(i-1).getItemTitle();
		} 
		// 将可选内容与ArrayAdapter连接起来
		itemObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, itemObj_ShowText);
		// 设置被评价的指标下拉列表的风格
		itemObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_itemObj.setAdapter(itemObj_adapter);
		// 添加事件Spinner事件监听
		spinner_itemObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCheckResult.setItemObj(itemInfoList.get(arg2-1).getItemId()); 
				else
					queryConditionCheckResult.setItemObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_itemObj.setVisibility(View.VISIBLE);
		spinner_resultObj = (Spinner) findViewById(R.id.Spinner_resultObj);
		// 获取所有的结果指标
		try {
			resultItemList = resultItemService.QueryResultItem(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int resultItemCount = resultItemList.size();
		resultObj_ShowText = new String[resultItemCount+1];
		resultObj_ShowText[0] = "不限制";
		for(int i=1;i<=resultItemCount;i++) { 
			resultObj_ShowText[i] = resultItemList.get(i-1).getResultItemText();
		} 
		// 将可选内容与ArrayAdapter连接起来
		resultObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, resultObj_ShowText);
		// 设置评价结果下拉列表的风格
		resultObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_resultObj.setAdapter(resultObj_adapter);
		// 添加事件Spinner事件监听
		spinner_resultObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCheckResult.setResultObj(resultItemList.get(arg2-1).getResultItemId()); 
				else
					queryConditionCheckResult.setResultObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_resultObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionCheckResult", queryConditionCheckResult);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
