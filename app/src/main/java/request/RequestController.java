package request;

import java.util.concurrent.ExecutionException;

/**
 * Created by misha on 18.2.18.
 */


public class RequestController {
    public static final String HOST_IP = "translator.mycloud.by";
    private static final String VERSON_PREFIX = "version = ";

    public static String selectRequest(String request) {
        return executeRequest(request);
    }

    public static int getVersion(String fac) {
        fac = fac.toUpperCase();
        //String response = executeRequest("http://" + RequestController.HOST_IP + "/ServletForTranslator/dictionarydb?REQ=version" + fac);
        String response = executeRequest("http://" + RequestController.HOST_IP + "/dictionarydb?REQ=version" + fac);
        if (response != null && response.startsWith(VERSON_PREFIX)) {
            response = response.substring(VERSON_PREFIX.length());
            return Integer.parseInt(response);
        }
        return -1;
    }

    public static String getDB(String fac) {
        fac = fac.toUpperCase();
        //return executeRequest("http://" + RequestController.HOST_IP + "/ServletForTranslator/dictionarydb?REQ=db" + fac);
        return executeRequest("http://" + RequestController.HOST_IP + "/dictionarydb?REQ=db" + fac);
    }

    private static String executeRequest(String request) {
        String response = "";
        OkHttpHandler handler = new OkHttpHandler();
        try {
            response = handler.execute(request).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }
}

