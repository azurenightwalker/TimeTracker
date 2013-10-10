package uk.co.xlabsystems.timetracker.network;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public final class NetworkHelper {
    public static final String BASE_REST_URL = "";

    public static JSONObject Get()
    {
        // TODO: Use getData
        return null;
    }

    public static JSONObject Post()
    {
        // TODO: Use postData
        return null;
    }

    private void postData(String url, List<NameValuePair> data) throws IOException {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = getHttpClient();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(data));
        // Execute HTTP Post Request
        HttpResponse response = httpclient.execute(httppost);
        // TODO: Handle response?
    }

    private JSONObject getData(String url) throws IOException {
        // Create a new HttpClient and Get Header
        HttpClient httpclient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        // Execute HTTP Get Request
        HttpResponse response = httpclient.execute(httpGet);
        // TODO: Parse into JSON object
        JSONObject res = new JSONObject();
        return res;
    }

    private HttpClient getHttpClient()
    {
        HttpClient httpclient = new DefaultHttpClient();
        // TODO: Add basic auth
        return  httpclient;
    }


}
