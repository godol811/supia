package com.example.supia.NetworkTask.MyPage;

import android.app.ProgressDialog;
import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import com.example.supia.Dto.MyPage.MyLikeListDto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyPageLikeListNetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "MyPageLikeNetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    String where = null;
    String likecheck = null;
    ArrayList<MyLikeListDto> members;


    //construct


    public MyPageLikeListNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.members = new ArrayList<MyLikeListDto>();
        this.where = where;

    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("");
        progressDialog.setMessage("로딩중");
        progressDialog.show();

    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground()");
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;


        String result = null;
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

                if (where.equals("select")) {
                    Log.d("제이슨", stringBuffer.toString());
                    parser1(stringBuffer.toString());
                } else {
                    parserAction(stringBuffer.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (where.equals("select")) {
            return members;
        } else {

            return result;
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Log.v(TAG, "onProgressUpdate()");
        super.onProgressUpdate(values);
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

    private void parser1(String s) {
        Log.v(TAG, "parser1()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("product,liked"));
            members.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int productNo = jsonObject1.getInt("productNo");
                String productName = jsonObject1.getString("productName");
                int productPrice = jsonObject1.getInt("productPrice");
                String productImagePath = jsonObject1.getString("productImagePath");
                String likeUserId = jsonObject1.getString("likeUserId");
                int likeProductId = jsonObject1.getInt("likeProductId");
                String likeCheck = jsonObject1.getString("likeCheck");


                MyLikeListDto like = new MyLikeListDto(productNo, productName, productPrice, productImagePath, likeUserId, likeProductId, likeCheck);
                likecheck = likeCheck;


                Log.v("여기", "Tast_parser2_likecheck : " + likeCheck);
                members.add(like);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private String parserAction(String s) {
        Log.v(TAG, "Parser()");
        String returnValue = null;

        try {
            Log.v(TAG, s);

            JSONObject jsonObject = new JSONObject(s);
            returnValue = jsonObject.getString("delete");
            Log.v(TAG, returnValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

}
