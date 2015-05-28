package com.vivo.test.systemui.activity.recent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.vivo.test.systemui.R;

public class RecentTaskTestActivity extends Activity {

    private final static String TAG =  RecentTaskTestActivity.class.getName();
    private static final int DISPLAY_TASKS = 20;
    private static final int MAX_TASKS = DISPLAY_TASKS + 1;
    public static final int REMOVE_TASK_KILL_PROCESS = 0x0001;
    public static final int RECENT_INCLUDE_PROFILES = 0x0004;
    List<TaskInfo> taskArray;
    List<TaskInfo> recentArray;
    ListView mRecentListView = null;
    ListView mRunnigListView = null;
    MyListAdapter recentAdapter = null;
    MyListAdapter runningAdapter = null;
    AlertDialog typeDialog = null;
    String killpackageNmae = null;
    int taskId = -1;
    int itemIndex = 500;
    boolean taskFlag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_task_test);

        mRecentListView = (ListView)findViewById(R.id.recent_list);
        mRunnigListView = (ListView)findViewById(R.id.runing_list);

        taskArray = new ArrayList<TaskInfo>();
        recentArray = new ArrayList<TaskInfo>();
        recentAdapter = new MyListAdapter(this);
        runningAdapter = new MyListAdapter(this);

        // 顺序要注意
        recentAdapter.setData(recentArray);
        runningAdapter.setData(taskArray);


        mRecentListView.setAdapter(recentAdapter);
        mRunnigListView.setAdapter(runningAdapter);

        initDialog();
        mRecentListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {
                taskFlag = false;
                killpackageNmae = getPackageName(taskFlag,position);
                taskId = getTaskId(taskFlag,position);
                itemIndex = position;
                showDialog();
            }
        });

        mRunnigListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {
                taskFlag = true;
                killpackageNmae = getPackageName(taskFlag,position);
                taskId = getTaskId(taskFlag,position);
                itemIndex = position;
                showDialog();
            }
        });
        Log.d(TAG,"onCreate end");
    }

    @Override
    protected void onResume(){
        super.onResume();
        getRuningTask();
        getRecentTask();
        recentAdapter.notifyDataSetChanged();
        runningAdapter.notifyDataSetChanged();
        Log.d(TAG,"onResume end");
    }
    class MyListAdapter extends BaseAdapter {

        private Context mContext;
        private List<TaskInfo> mData;
        public MyListAdapter(Context context) {
            mContext = context;

        }

        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public void setData(List<TaskInfo> data){
            Log.d(TAG," setData");
            mData = data;
        }

        @SuppressLint("NewApi") public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder  = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.task_list_item, null);
                holder.icon = (ImageView) convertView.findViewById(R.id.color_image);
                holder.appName =(TextView) convertView.findViewById(R.id.color_title);
                holder.packageName = (TextView) convertView.findViewById(R.id.color_text);
                convertView.setTag(holder);
            } else{
                holder = (ViewHolder) convertView.getTag();
            }

            if(mData != null && mData.size() >= position){
                holder.appName.setText(mData.get(position).getAppName());
                holder.packageName.setText(mData.get(position).getPackageName());
                holder.icon.setBackground(mData.get(position).getAppIcon());
            }
            return convertView;
        }


    }

    @SuppressLint("NewApi") public void getRecentTask(){

        if (recentArray != null) {
            recentArray.clear();
        }else{
            recentArray = new ArrayList<TaskInfo>();
        }
        final PackageManager pm = getPackageManager();
        List<ActivityManager.RecentTaskInfo> recentTasks = getRecentTasks(MAX_TASKS,ActivityManager.RECENT_IGNORE_UNAVAILABLE | RECENT_INCLUDE_PROFILES);

        int numTasks = recentTasks.size();
        Log.d(TAG ,"Recent task num : "  + numTasks);
        for (int i = 0; i < numTasks ; ++i) {
            final ActivityManager.RecentTaskInfo info = recentTasks.get(i);
            Intent intent = new Intent(info.baseIntent);
            if (info.origActivity != null) {
                intent.setComponent(info.origActivity);
            }
            Log.d(TAG,"recent task:" +  i + " ,info :   "+  info);
            final ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
            if (resolveInfo != null) {
                final ActivityInfo activityInfo = resolveInfo.activityInfo;
                final String title = activityInfo.loadLabel(pm).toString();
                Drawable icon = activityInfo.loadIcon(pm);
                String packageName = activityInfo.packageName;
                int id = info.persistentId;
                if(!"com.vivo.test.systemui".equals(packageName)){
                    TaskInfo item = new TaskInfo(packageName,title,icon,id);
                    recentArray.add(item);
                }
            }
        }
    }

    public List<ActivityManager.RecentTaskInfo> getRecentTasks(int maxNum, int flags) {
        List<ActivityManager.RecentTaskInfo> recentTasks = new ArrayList<ActivityManager.RecentTaskInfo>();
        try {
        final ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        Method method = Class.forName("android.app.ActivityManager").getMethod("getRecentTasksForUser", new Class[] {
                int.class, int.class,int.class});
        recentTasks = (List<ActivityManager.RecentTaskInfo>)method.invoke(am, new Object[]{new Integer(maxNum), new Integer(flags),new Integer(-2)});

        } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return recentTasks;
    }

    public void getRuningTask(){
        if (taskArray != null) {
            taskArray.clear();
        }else{
            taskArray = new ArrayList<TaskInfo>();
        }

        ActivityManager am = (ActivityManager)getSystemService(Activity.ACTIVITY_SERVICE);

        List<RunningTaskInfo> task = am.getRunningTasks(Integer.MAX_VALUE);

        PackageManager pack=this.getPackageManager();

        for(int i=0;i< task.size(); i++){

            String packageName = task.get(i).topActivity.getPackageName();
            Drawable d=null;
            String appName="";

            try {
                d=pack.getApplicationIcon(packageName);

                appName=(String)pack.getApplicationLabel(pack.getApplicationInfo(packageName,PackageManager.GET_META_DATA));

            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            if(!"com.vivo.test.systemui".equals(packageName)){
                TaskInfo item = new TaskInfo(packageName,appName,d);
                taskArray.add(item);
            }
        }
    }

    public class TaskInfo{
        private String appName;
        private String packageName;
        private Drawable appIcon;
        private int id;
        public TaskInfo(){

        }
        public TaskInfo(String pName,String aName, Drawable icon){
            packageName = pName;
            appName = aName;
            appIcon = icon;
            id = - 1;
        }

        public TaskInfo(String pName,String aName, Drawable icon,int  taskID){
            packageName = pName;
            appName = aName;
            appIcon = icon;
            id = taskID;
        }

        public String getPackageName(){
            return packageName;
        }

        public String getAppName(){
            return appName;
        }

        public Drawable getAppIcon(){
            return appIcon;
        }


        public int getID(){
            return id;
        }
    }

    public void  updateAdpater(boolean flag, int index){
        if(flag) {
            if(taskArray != null && taskArray.size() > index){
                taskArray.remove(index);
                runningAdapter.notifyDataSetChanged();
            }
        } else{
            if(recentArray != null && recentArray.size() > index){
                recentArray.remove(index);
                recentAdapter.notifyDataSetChanged();
            }
        }

    }

    public  String getPackageName(boolean flag,int id){
        if(flag) {
            if(taskArray != null){
                return taskArray.get(id).getPackageName();
            }
        } else{
            if(recentArray != null){
                return recentArray.get(id).getPackageName();
            }
        }
        return null;
    }

    public  int getTaskId(boolean flag,int id){
        if(flag) {
            if(taskArray != null){
                return taskArray.get(id).getID();
            }
        } else{
            if(recentArray != null){
                return recentArray.get(id).getID();
            }
        }
        return -1;
    }

    public void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.slect_type))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setSingleChoiceItems(new String[] {"RemoveTask","forceStopPackage","killBackground","other"}, 0,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "onClick :"  +which  );
                                try {
                                switch(which){
                                    case 0:
                                        userRemoveTask(taskId);
                                        updateAdpater(taskFlag,itemIndex);
                                        break;
                                    case 1:
                                        userForceStopPackage(killpackageNmae);
                                        updateAdpater(taskFlag,itemIndex);
                                        break;
                                    case 2:
                                        userKillBackgroundProcesses(killpackageNmae);
                                        updateAdpater(taskFlag,itemIndex);
                                        break;
                                    default:
                                        break;
                                }
                                } catch (IllegalAccessException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (IllegalArgumentException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (NoSuchMethodException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                            }
                        }
                );

        typeDialog = builder.create();
    }

    public void showDialog(){
        if(typeDialog != null){
            if(typeDialog.isShowing())
                typeDialog.dismiss();
            else
                typeDialog.show();
        }

    }

    public void userRemoveTask(int id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
        final ActivityManager am = (ActivityManager)
                getSystemService(Context.ACTIVITY_SERVICE);
        Method method = Class.forName("android.app.ActivityManager").getMethod("removeTask", new Class[] {
                int.class, int.class });
        method.invoke(am, new Object[]{new Integer(id),new Integer(REMOVE_TASK_KILL_PROCESS)} );
    }

    public void userForceStopPackage(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
        Log.d(TAG, "ForceStopPackage:  " +  name);
        ActivityManager mActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        Method method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String.class);
        method.invoke(mActivityManager, name);
    }

    @SuppressLint("NewApi") public void userKillBackgroundProcesses(String name){
        final ActivityManager am = (ActivityManager)
                getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            Log.d(TAG, "KillBackgroundProcesses:  " +  name);
            am.killBackgroundProcesses(name);
        }
    }

    static class ViewHolder {

        TextView packageName;

        ImageView icon;

        TextView appName;

    }
}
