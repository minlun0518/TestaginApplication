package com.lunlun.testaginapplication;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    boolean logon =false;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!logon){
            Intent login = new Intent(this, LoginActivity.class);
            startActivityForResult(login, REQUEST_CODE);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        findlist();
    }

    private void findlist() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager( new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

        List<ApplicationItem> applicationItemList = new ArrayList<>();
        applicationItemList.add(new ApplicationItem (1,getString(R.string.app_time_attendance_system),R.drawable.icon_immigration));
        applicationItemList.add(new ApplicationItem (2,getString(R.string.app_staff_scheduling_system),R.drawable.icon_calendar));
        applicationItemList.add(new ApplicationItem (3,"會議室簽到系統",R.drawable.icon_conversation));
        applicationItemList.add(new ApplicationItem (4,"教學評量系統",R.drawable.icon_checklist));
        applicationItemList.add(new ApplicationItem (5,"採檢及生理量測系統",R.drawable.icon_blood_sample));

        recyclerView.setAdapter(new ApplicationItemAdapter(this,applicationItemList));

        RecyclerView noterecyclerView = findViewById(R.id.notionrecyclerView);
        noterecyclerView.setLayoutManager( new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task (1,"PGY 提醒",R.drawable.icon_realdr));
        taskList.add(new Task (2,"明日班表",R.drawable.icon_mon_card));
        taskList.add(new Task (3,"今日會議",R.drawable.icon_conversation));

        noterecyclerView.setAdapter(new TaskAdapter(this,taskList));
    }

    private class ApplicationItemAdapter extends RecyclerView.Adapter < ApplicationItemAdapter.ViewHolder > {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<ApplicationItem> applicationItemList;

        public ApplicationItemAdapter(Context context, List<ApplicationItem> applicationItemList) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            this.applicationItemList = applicationItemList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView imageview;
            TextView appname;
            View itemView;

            public ViewHolder(View itemView){
                super(itemView);
                this.itemView = itemView;
                imageview= itemView.findViewById(R.id.imageView2);
                appname=itemView.findViewById(R.id.textView5);
            }
        }

        @Override
        public int getItemCount() {
            return applicationItemList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
            LayoutInflater layoutInflater =LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.itemview,viewGroup,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder,final  int position){
            ApplicationItem applicationItem = applicationItemList.get(position);
            viewHolder.appname.setText(applicationItem.appName);
            viewHolder.imageview.setImageResource(applicationItem.imageViewpost);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setIcon(applicationItem.imageViewpost)
                            .setMessage("早安你好")
                            .setTitle(applicationItem.appName)
                            .show();
                }
            });
        }
    }

    public class ApplicationItem{
        public int position;
        public String appName;
        public int imageViewpost;

        public ApplicationItem(int position, String appName, int imageViewpost){
            this.position=position;
            this.appName=appName;
            this.imageViewpost=imageViewpost;
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter < TaskAdapter.ViewHolder > {
        private Context taskcontext;
        private LayoutInflater tasklayoutInflater;
        private List<Task> taskList;

        public TaskAdapter(Context taskcontext, List<Task> taskList) {
            this.taskcontext = taskcontext;
            tasklayoutInflater = LayoutInflater.from(taskcontext);
            this.taskList = taskList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView taskimageview;
            TextView taskname;
            View taskitemView;

            public ViewHolder(View taskItemView){
                super(taskItemView);
                this.taskitemView = itemView;
                taskimageview= taskitemView.findViewById(R.id.imageView3);
                taskname=taskitemView.findViewById(R.id.textView4);
            }
        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
            LayoutInflater layoutInflater =LayoutInflater.from(taskcontext);
            View taskitemView = layoutInflater.inflate(R.layout.taskitemview,viewGroup,false);
            return new ViewHolder(taskitemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder,final  int position){
            Task task = taskList.get(position);
            viewHolder.taskname.setText(task.taskName);
            viewHolder.taskimageview.setImageResource(task.taskImageView);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(taskcontext)
                            .setIcon(task.taskImageView)
                            .setMessage("測試測試")
                            .setTitle(task.taskName)
                            .setPositiveButton("已讀",null)
                            .setNeutralButton("cancel/back",null)
                            .show();
                }
            });
        }
    }

    public class Task{
        public int taskPosition;
        public String taskName;
        public int taskImageView;

        public Task(int taskPosition, String taskName, int taskImageView){
            this.taskPosition=taskPosition;
            this.taskName=taskName;
            this.taskImageView=taskImageView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==REQUEST_CODE){
//            if(resultCode==RESULT_OK){
//                TextView imeiview = findViewById(R.id.imeiTextView);
//                imeiview.setText(data.getStringExtra("LOGIN_IMEI"));
//                TextView useridtextView =findViewById(R.id.useridTextView);
//                useridtextView.setText(data.getStringExtra("LOGIN_ID"));
//            }
//        }
//
//    }

}