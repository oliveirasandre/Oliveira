package oliveira.br.com.testetata.webrequests;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import oliveira.br.com.testetata.model.Fund;
import oliveira.br.com.testetata.utils.Utility;

public class RequestFund {
    private Context context;

    public RequestFund(Context context) {
        this.context = context;
    }

    public String getFund(){

            URL url;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonStr = "";

            url = montaURLCells();

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(20000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                boolean isError = urlConnection.getResponseCode() >= 400;
                InputStream inputStream = isError ? urlConnection.getErrorStream() : urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    jsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                jsonStr = buffer.toString();

                if (urlConnection.getResponseCode() != 200) {
                    jsonStr = "";
                }
            }catch (ProtocolException e) {
                jsonStr = null;
            } catch (IOException e) {
                jsonStr = null;
            }finally {
                assert urlConnection != null;
                urlConnection.disconnect();
            }

            return jsonStr;
        }


    private URL montaURLCells(){
        Uri.Builder builder = Uri.parse("https://floating-mountain-50292.herokuapp.com/fund.json").buildUpon();
        String mountedUrlString = builder.build().toString();
        URL mountedUrl = null;
        try {
            mountedUrl = new URL(mountedUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mountedUrl;
    }

    public static Fund readJSONPositionsSync(String jsonStr){
        Fund fund = new Fund();

        try {

            JSONObject jObj = new JSONObject(jsonStr);
            JSONObject obj_scr = jObj.getJSONObject("screen");

            fund.setTitle(obj_scr.getString("title"));
            fund.setFundName(obj_scr.getString("fundName"));
            fund.setWhatIs(obj_scr.getString("whatIs"));
            fund.setDefinition(obj_scr.getString("definition"));
            fund.setRiskTitle(obj_scr.getString("riskTitle"));
            fund.setRisk(obj_scr.getString("risk"));
            fund.setInfoTitle(obj_scr.getString("infoTitle"));

            String jsonStrInfo = obj_scr.getString("info");
            JSONArray objectInfo = new JSONArray(jsonStrInfo);

            List<String> lst_info = new ArrayList<>();
            for (int e = 0; e < objectInfo.length(); e++) {
                JSONObject ce = objectInfo.getJSONObject(e);

                String name = ce.getString("name");
                String data = ce.getString("data");
                lst_info.add(name+";"+data);
            }
            fund.setInfo(lst_info);

            String jsonStrDownInfo = obj_scr.getString("downInfo");
            JSONArray objectDownInfo = new JSONArray(jsonStrDownInfo);

            List<String> lst_Downinfo = new ArrayList<>();
            for (int e = 0; e < objectDownInfo.length(); e++) {
                JSONObject ce = objectDownInfo.getJSONObject(e);

                String uid_e = ce.getString("name");
                String value = ce.getString("data");

                lst_Downinfo.add(uid_e+";"+value);

            }
            fund.setDownInfo(lst_Downinfo);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fund;
    }

    private String readJSONError(String jsonStr){
        String message = null;
        if (Utility.isJSONValid(jsonStr)) {
            try {
                JSONObject result = new JSONObject(jsonStr);
                message = result.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return message;
        }else{
            return null;
        }
    }

}
