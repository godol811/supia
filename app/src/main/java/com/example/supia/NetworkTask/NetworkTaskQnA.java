package com.example.supia.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.supia.Dto.Product.ProductDto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTaskQnA extends AsyncTask<Integer, String, Object> {

    final static String TAG = "네트워크타스크큐엔에이";

    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<ProductDto> Product;

    ///////////////////////////////////////////////////////////////////////////////////////
    // Date : 2020.12.25
    //
    // Description:
    //  - NetworkTask를 검색, 입력, 수정, 삭제 구분없이 하나로 사용키 위해 생성자 변수 추가.
    //
    ///////////////////////////////////////////////////////////////////////////////////////


    String where = null;

    public NetworkTaskQnA(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.Product = new ArrayList<ProductDto>();
        this.where = where;
        Log.v(TAG, "Start : " + mAddr);

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
        Log.v(TAG, "doInBackground()1");
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
                    Log.v(TAG, "select");
                    parserSelect(stringBuffer.toString());
                    Log.d(TAG,"select");
                } else if (where.equals("like")) {//라이크로 들어오면 파싱하지 않음

                } else {
                    result = parserAction(stringBuffer.toString());
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
            return Product;
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
            JSONArray jsonArray = new JSONArray(jsonObject.getString("product"));
            Product.clear();
            Log.v(TAG, "s" + s);


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int productNo = jsonObject1.getInt("productNo");
                String productName = jsonObject1.getString("productName");
                String productPrice = jsonObject1.getString("productPrice");
                String productQuantity = jsonObject1.getString("productQuantity");
                String productBrand = jsonObject1.getString("productBrand");
                String productImagePath = jsonObject1.getString("productImagePath");
                String productInfo = jsonObject1.getString("productInfo");
                String productCategory1 = jsonObject1.getString("productCategory1");
                String productCategory2 = jsonObject1.getString("productCategory2");
                String likeUserId = jsonObject1.getString("likeUserId");
                String likeProductId = jsonObject1.getString("likeProductId");
                String likeCheck = jsonObject1.getString("likeCheck");


                ProductDto product = new ProductDto(productNo, productName, productPrice, productQuantity, productBrand, productImagePath, productInfo, productCategory1, productCategory2 , likeUserId, likeProductId, likeCheck);

//                , likeUserId, likeProductId, likeCheck

                Product.add(product);


                // Log.v(TAG, member.toString());
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
