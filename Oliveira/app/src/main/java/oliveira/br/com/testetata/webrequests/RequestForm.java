package oliveira.br.com.testetata.webrequests;


import android.content.Context;

import android.net.Uri;

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
import oliveira.br.com.testetata.model.Form;
import oliveira.br.com.testetata.utils.Utility;

public class RequestForm {
    private Context context;

    public RequestForm(Context context) {
        this.context = context;
    }

    public String getCells(){

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
        Uri.Builder builder = Uri.parse("https://floating-mountain-50292.herokuapp.com/cells.json").buildUpon();
         String mountedUrlString = builder.build().toString();
        URL mountedUrl = null;
        try {
            mountedUrl = new URL(mountedUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mountedUrl;
    }

    public static List<Form> readJSONPositionsSync(String jsonStr){
        List<Form> types = null;
        types = new ArrayList<>();

        try {

            JSONObject jObj = new JSONObject(jsonStr);
            JSONArray jsonArr = jObj.getJSONArray("cells");
            //JSONArray jsonArr = new JSONArray(jsonStr);

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject c = jsonArr.getJSONObject(i);

                Form temp = new Form();

                temp.setId(c.optInt("id", 0));
                temp.setType(c.optInt("type", 0));
                temp.setMessage(c.optString("message", null));
                temp.setTypefield(c.optString("typefield", null));
                temp.setHidden(c.optString("hidden", null));
                temp.setTopSpacing(c.optInt("topspacing", 0));
                temp.setShow(c.optString("show", null));
                temp.setRequired(c.optString("required", null));

                types.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return types;
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
