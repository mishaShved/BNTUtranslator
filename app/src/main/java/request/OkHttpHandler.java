package request;

import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by misha on 18.2.18.
 */


public class OkHttpHandler extends AsyncTask<String, Void, String> {

    OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(String... params) {
        FormBody.Builder formBuilder = new FormBody.Builder();

        if(params != null && params.length > 1){
            formBuilder.add("req", params[1]);
        }

        RequestBody formBody = formBuilder.build();
        Request.Builder builder = new Request.Builder();
        builder.url(params[0]);
        builder.method("POST", formBody);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        try {
            System.out.println(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
