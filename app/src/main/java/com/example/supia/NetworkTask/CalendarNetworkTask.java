package com.example.supia.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.supia.Dto.CalendarDTO;
import com.example.supia.ShareVar.ShareVar;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarNetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "캘린더네트워크타스크";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    HashSet<CalendarDTO> calendars;
    ArrayList<CalendarDTO> calendarCheck;
    ///////////////////////////////////////////////////////////////////////////////////////
    // Date : 2020.12.25
    //
    // Description:
    //  - NetworkTask를 검색, 입력, 수정, 삭제 구분없이 하나로 사용키 위해 생성자 변수 추가.
    //
    ///////////////////////////////////////////////////////////////////////////////////////
    String where = null;

    public CalendarNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.calendars = new HashSet<CalendarDTO>();
        this.calendarCheck = new ArrayList<CalendarDTO>();
        this.where = where;
        Log.v(TAG, "Start : " + mAddr);
    }




    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground()");

        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        ///////////////////////////////////////////////////////////////////////////////////////
        // Date : 2020.12.25
        //
        // Description:
        //  - NetworkTask에서 입력,수정,검색 결과값 관리.
        //
        ///////////////////////////////////////////////////////////////////////////////////////
        String result = null;
        ///////////////////////////////////////////////////////////////////////////////////////

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strline = bufferedReader.readLine();
                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");
                }
                ///////////////////////////////////////////////////////////////////////////////////////
                // Date : 2020.12.25
                //
                // Description:
                //  - 검색으로 들어온 Task는 parserSelect()로
                //  - 입력, 수정, 삭제로 들어온 Task는 parserAction()으로 구분
                //
                ///////////////////////////////////////////////////////////////////////////////////////
                if (where.equals("select")) {
                    parserSelect(stringBuffer.toString());
                } else if (where.equals("like")) {//라이크로 들어오면 파싱하지 않음

                } else {
                    //result = parserAction(stringBuffer.toString());
                }
                ///////////////////////////////////////////////////////////////////////////////////////

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////
        // Date : 2020.12.25
        //
        // Description:
        //  - 검색으로 들어온 Task는 members를 return
        //  - 입력, 수정, 삭제로 들어온 Task는 result를 return
        //
        ///////////////////////////////////////////////////////////////////////////////////////
        if (where.equals("select")) {
            return calendars;
        } else {
            return result;
        }
        ///////////////////////////////////////////////////////////////////////////////////////

    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG, "onPostExecute()");
        super.onPostExecute(o);
        progressDialog.dismiss();

    }

    @Override
    protected void onCancelled() {
        Log.v(TAG, "onCancelled()");
        super.onCancelled();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // Date : 2020.12.25
    //
    // Description:
    //  - 검색후 json parsing
    //
    ///////////////////////////////////////////////////////////////////////////////////////
    private void parserSelect(String s) {
        Log.v(TAG, "parserSelect()");

        try {

            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("calendar"));
            calendars.clear();
            Log.v(TAG, "s" + s);

            //날짜가 없을경우에 터질걸 방해할 경우//
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            calendar.add(Calendar.DATE, -1);
            String today = df.format(calendar.getTime());//파일에도 날짜를 넣기위한 메소드
            calendar.add(Calendar.DATE, 2);
            String tomorrow = df.format(calendar.getTime());//하루 추가
            calendar.add(Calendar.DATE, 2);
            String dayAfterTomorrow = df.format(calendar.getTime());//하루 추가

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String calendarStartDate = jsonObject1.getString("calendarStartDate");
                String calendarFinishDate = jsonObject1.getString("calendarFinishDate");
                String calendarDeliveryDate = jsonObject1.getString("calendarDeliveryDate");
                String calendarBirthDate = jsonObject1.getString("calendarBirthDate");

                if(calendarStartDate.equals("null")){
                    calendarStartDate = tomorrow;
                }
                if(calendarFinishDate.equals("null")){
                    calendarFinishDate = dayAfterTomorrow;
                }
                if(calendarDeliveryDate.equals("null")){
                    calendarDeliveryDate = "1990-12-28";
                }
                if(calendarBirthDate.equals("null")){
                    calendarBirthDate = today;
                }


                ShareVar.calendarsharvarStartdate = calendarStartDate;
                ShareVar.calendarsharvarFinishdate = calendarFinishDate;
                ShareVar.calendarsharvarDeliverydate = calendarDeliveryDate;
                ShareVar.calendarsharvarBirthdate = calendarBirthDate;
                Log.v(TAG, "파서셀렉트"+ ShareVar.calendarsharvarStartdate+ShareVar.calendarsharvarFinishdate+ShareVar.calendarsharvarDeliverydate+ShareVar.calendarsharvarBirthdate);

                CalendarDTO calendarDTO = new CalendarDTO(calendarStartDate,calendarFinishDate,calendarDeliveryDate,calendarBirthDate);
                calendars.add(calendarDTO);
                Log.v(TAG, "----------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////
    // Date : 2020.12.25
    //
    // Description:
    //  - 입력, 수정, 삭제후 json parsing
    //
    ///////////////////////////////////////////////////////////////////////////////////////
    private String parserAction(String s) {
        Log.v(TAG, "Parser()");
        String returnValue = null;

        try {
            Log.v(TAG, s);

            JSONObject jsonObject = new JSONObject(s);
            returnValue = jsonObject.getString("result");
            Log.v(TAG, returnValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    ///////////////////////////////////////////////////////////////////////////////////////


} // ------