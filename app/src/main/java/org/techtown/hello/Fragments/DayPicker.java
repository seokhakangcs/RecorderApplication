package org.techtown.hello.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import org.techtown.hello.R;

import java.util.Calendar;
import java.util.Date;

public class DayPicker extends FragmentActivity {

    private TimePicker timePicker;
    private AlarmManager alarmManager;
    private int hour, minute;
    CheckBox cbSun, cbMon, cbTue, cbWed, cbThu, cbFri, cbSat;
    View view;
    Button register, unregister;

    static String TAG="MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        view = inflater.inflate(R.layout.daypicker_layout, container, false);
//        timePicker = view.findViewById(R.id.tp_timepicker);
//        alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        cbSun = view.findViewById(R.id.cb_sun);
//        cbMon = view.findViewById(R.id.cb_mon);
//        cbTue = view.findViewById(R.id.cb_thu);
//        cbWed = view.findViewById(R.id.cb_wed);
//        cbThu = view.findViewById(R.id.cb_thu);
//        cbFri = view.findViewById(R.id.cb_fri);
//        cbSat = view.findViewById(R.id.cb_sat);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daypicker_layout);

        timePicker=findViewById(R.id.tp_timepicker);
        alarmManager= (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        cbSun=findViewById(R.id.cb_sun);
        cbMon=findViewById(R.id.cb_mon);
        cbTue=findViewById(R.id.cb_thu);
        cbWed=findViewById(R.id.cb_wed);
        cbThu=findViewById(R.id.cb_thu);
        cbFri=findViewById(R.id.cb_fri);
        cbSat=findViewById(R.id.cb_sat);
        //
        register = findViewById(R.id.button);
        unregister = findViewById(R.id.button2);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                regist(v);
            }
        });
        unregister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                unregist(v);
            }
        });


    }

//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        view = inflater.inflate(R.layout.daypicker_layout, container, false);
//        timePicker = view.findViewById(R.id.tp_timepicker);
//        alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        cbSun = view.findViewById(R.id.cb_sun);
//        cbMon = view.findViewById(R.id.cb_mon);
//        cbTue = view.findViewById(R.id.cb_thu);
//        cbWed = view.findViewById(R.id.cb_wed);
//        cbThu = view.findViewById(R.id.cb_thu);
//        cbFri = view.findViewById(R.id.cb_fri);
//        cbSat = view.findViewById(R.id.cb_sat);
//
//        return view;
//    }
    public void regist(View view) {

        boolean[] week = { false, cbSun.isChecked(), cbMon.isChecked(), cbTue.isChecked(), cbWed.isChecked(),
                cbThu.isChecked(), cbFri.isChecked(), cbSat.isChecked() }; // cbSun??? 1????????? ???????????? ?????? ?????? 0?????? false??? ??????

        if(!cbSun.isChecked() &&  !cbMon.isChecked() &&  !cbTue.isChecked() && !cbWed.isChecked() &&  !cbThu.isChecked() && !cbFri.isChecked() && !cbSat.isChecked()){
            Toast.makeText(view.getContext(), "????????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour=timePicker.getHour();
            minute=timePicker.getMinute();
        }else{
            Toast.makeText(view.getContext(), "????????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("????????????", "abc");
        Intent intent = new Intent(view.getContext(), Alarm.class);
        intent.putExtra("weekday", week);
        Log.e("????????????2", "abc");
        PendingIntent pIntent = PendingIntent.getBroadcast(view.getContext(), 0,intent, 0); //PendingIntent.FLAG_UPDATE_CURRENT

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date today = new Date();
        long intervalDay = 24 * 60 * 60 * 1000;// 24??????

        long selectTime=calendar.getTimeInMillis();
        long currenTime=System.currentTimeMillis();

        //?????? ????????? ?????????, ?????? ???????????? ????????? ????????? ??????????????? ????????? ????????? ????????? ????????? ??????
        if(currenTime>selectTime){
            selectTime += intervalDay;
        }

        Log.e(TAG,"?????? ????????? ?????? ?????? : "+today+"  ????????? ?????? : "+calendar.getTime());

        Log.d(TAG,"calendar.getTimeInMillis()  : "+calendar.getTimeInMillis());

        // ????????? ????????? ?????? ??????
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectTime,  intervalDay, pIntent);

    }// regist()..

    public void unregist(View view) {
        Intent intent = new Intent(view.getContext(), Alarm.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(view.getContext(), 0, intent, 0);
        alarmManager.cancel(pIntent);
    }// unregist()..

}// MainActivity class..