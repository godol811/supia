package com.example.supia.Activities.MyPage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.supia.NetworkTask.UserInfoNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

public class MypagePwChangeDialog extends Dialog {


    ///////////////////////////////////////////
    // MyinfoActivity 에서 쓰이는 커스텀 다이얼로그 //
    ///////////////////////////////////////////




    private Context context;
    private String userId = ShareVar.sharvarUserId;
    private String please;
    private String userName;
    private String userTel;
    private String urlIp = ShareVar.urlIp;


    String urlAddr = null;


    public MypagePwChangeDialog(@NonNull Context context, String userId, String userName, String userTel) {
        super(context);
        this.context = context;
        this.userId = userId;
        this.userName = userName;
        this.userTel = userTel;

    }

    public void callFunction(final String pwChange) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pwchange_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        final EditText pw = dialog.findViewById(R.id.et_pwchange_mypage_info);
        final EditText pwCh = dialog.findViewById(R.id.et_pwcheck_mypage_info);
        final TextView tv_ok = dialog.findViewById(R.id.tv_changepw_mypage_info);
        final TextView tv_cxl = dialog.findViewById(R.id.tv_cxlchange_mypage_info);
        final TextView tv_check = dialog.findViewById(R.id.chage_pw_alert);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String m = pw.getText().toString();
                String n = pwCh.getText().toString();
                please =m;


                if (m.length() == 0 && n.length() == 0) {
                    tv_check.setText("정보를 입력해주세요.");
                    tv_check.setTextColor(0xAAef484a);


                }
//                 if(m.length() <=8 && n.length()<=8){
//                    tv_check.setText("8글자 이상 입력해주세요.");
//                    tv_check.setTextColor(0xAAef484a);
//                }
//




            if(m.equals(n)){

                    new AlertDialog.Builder(context)
                            .setTitle("알림")
                            .setMessage("비밀번호 변경이 완료되었습니다.")
                            .show();


                    connectUpdateData();
                    Intent intent = new Intent(context, MyInfoActivity.class);
//                    intent.putExtra("userId",userId);
                    intent.putExtra("userPw",please);
                    intent.putExtra("userTel",userTel);
                    intent.putExtra("userName",userName);
                    context.startActivity(intent);


                } else {
                tv_check.setText("비밀번호가 일치하지 않습니다.");
                tv_check.setTextColor(0xE53935);
                }



            }
        });


        tv_cxl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    private String connectUpdateData() {
        String result = null;
        try {
            String url = "http://"+urlIp+":8080/test/supiaPwChange.jsp?userId=" + userId + "&userPw=" + please;
            Log.v("제발", "" + url);
            UserInfoNetworkTask updateworkTask = new UserInfoNetworkTask(context, url, "update");
            Object obj = updateworkTask.execute().get();
            result = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}//---------------
