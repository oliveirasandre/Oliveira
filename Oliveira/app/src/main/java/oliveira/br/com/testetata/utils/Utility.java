package oliveira.br.com.testetata.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    public class Constantes{
        public static final String TAG = "TATA";
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }


}
