package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class TeacherSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public TeacherSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.teacher_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_teacherNumber = (TextView)convertView.findViewById(R.id.tv_teacherNumber);
	  holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
	  holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
	  holder.tv_age = (TextView)convertView.findViewById(R.id.tv_age);
	  holder.tv_courseName = (TextView)convertView.findViewById(R.id.tv_courseName);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  holder.tv_email = (TextView)convertView.findViewById(R.id.tv_email);
	  /*设置各个控件的展示内容*/
	  holder.tv_teacherNumber.setText("教师用户名：" + mData.get(position).get("teacherNumber").toString());
	  holder.tv_name.setText("教师姓名：" + mData.get(position).get("name").toString());
	  holder.tv_sex.setText("教师性别：" + mData.get(position).get("sex").toString());
	  holder.tv_age.setText("教师年龄：" + mData.get(position).get("age").toString());
	  holder.tv_courseName.setText("教授课程：" + mData.get(position).get("courseName").toString());
	  holder.tv_telephone.setText("联系电话：" + mData.get(position).get("telephone").toString());
	  holder.tv_email.setText("邮箱地址：" + mData.get(position).get("email").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_teacherNumber;
    	TextView tv_name;
    	TextView tv_sex;
    	TextView tv_age;
    	TextView tv_courseName;
    	TextView tv_telephone;
    	TextView tv_email;
    }
} 
