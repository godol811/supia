package com.example.supia.Activities.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.supia.NetworkTask.CalendarNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

public class mensUpdate2 extends Dialog {

    final static String TAG = "캘린더정_마이에큐엘_월경일수_material2";

    public String urlAddr, urlIp, userId;
    private Context context;
    Button btnback, btncomplite;
    DatePicker datePicker;

    public mensUpdate2(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_updata_finish);

        userId = ShareVar.sharvarUserId;//사용자 아이디를 받아옴
        urlIp = ShareVar.urlIp;//아이피 받아옴
        urlAddr = "http://" + urlIp + ":8080/test/supiaCalendarUpdate.jsp?";

        btnback = findViewById(R.id.btn_back_mensupdate);
        btncomplite = findViewById(R.id.btn_complite_mensupdate);
        datePicker = findViewById(R.id.maincalendar_mens_finish_update);
        String date = Integer.toString(datePicker.getYear())+"-"+Integer.toString(datePicker.getMonth()+1)+"-"+Integer.toString(datePicker.getDayOfMonth());


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btncomplite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareVar.Updatemensfinishdate= date;
                Log.v(TAG, ShareVar.Updatemensfinishdate);
                dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                dismiss();
                builder.setTitle("아래의 날짜가 맞습니까?");
                builder.setMessage(ShareVar.updatemensstartdate+"~"+ShareVar.Updatemensfinishdate);
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

                                urlAddr = urlAddr + "calendarStartDate="+ShareVar.updatemensstartdate+"&calendarFinishDate="+ShareVar.Updatemensfinishdate+"&userId=" + userId;

                                connectUpdateData();
                                ShareVar.updatemensstartdate = null;
                                ShareVar.Updatemensfinishdate = null;
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ShareVar.updatemensstartdate = null;
                                ShareVar.Updatemensfinishdate = null;
                                dismiss();
                            }
                        });
                builder.show();
            }
        });

    }
    private void connectUpdateData() {
        try {
            CalendarNetworkTask updateworkTask = new CalendarNetworkTask(getContext(), urlAddr, "update");
            updateworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}