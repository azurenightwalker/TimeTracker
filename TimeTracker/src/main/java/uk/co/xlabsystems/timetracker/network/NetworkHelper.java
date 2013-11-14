package uk.co.xlabsystems.timetracker.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.xlabsystems.timetracker.ProjectCache;

public final class NetworkHelper {
    public static final String BASE_REST_URL = "http://timesheet.service.x-labsystems.co.uk/api/";

    private static NetworkHelper _networkHelper;
    private final Context mContext;

    private NetworkHelper(Context con)
    {
        mContext = con;
    }

    public static NetworkHelper getInstance()
    {
        if (_networkHelper == null)
            throw new IllegalAccessError("This has not already been instantiated");
        return _networkHelper;
    }

    public static void initialize(Context con)
    {
        if (_networkHelper != null)
            throw new IllegalAccessError("This has already been instantiated");
        _networkHelper = new NetworkHelper(con);

    }

    public JSONObject Get(String url)
    {
        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo is3g = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mWifi.isConnected() || is3g.isConnected()){
            try{
                if (checkCache()){
                    return new JSONObject(getData(BASE_REST_URL  + url));
                }
                else{
                    return new JSONObject(ProjectCache.getInstance().get());
                }
            } catch(JSONException e){
                return null;
            } catch (IOException e){
                return null;
            }
        }
        else{
            try {
                return new JSONObject(ProjectCache.getInstance().get());
            }catch (JSONException e){
                return null;
            }
        }
    }

    public JSONArray GetArray(String url)
    {
        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo is3g = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mWifi.isConnected() || is3g.isConnected()){
            try{
                if (checkCache()){
                    return new JSONArray(getData(BASE_REST_URL  + url));
                }
                else{
                    return new JSONArray(ProjectCache.getInstance().get());
                }
            } catch(Exception e){
                e.printStackTrace();
                try {
                    return new JSONArray(ProjectCache.getInstance().get());
                }catch (JSONException e2){
                    return null;
                }
            }
        }
        else{
            try {
                JSONArray a = new JSONArray(ProjectCache.getInstance().get());
                return a;
            }catch (JSONException e){
                return null;
            }
        }
    }

    public JSONObject Post(Map<String, Object> data, String url)
    {
        try{
            List<NameValuePair> dataToPost = new ArrayList<NameValuePair>();

            for (Map.Entry<String, Object> entry : data.entrySet()){
                dataToPost.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            postData(BASE_REST_URL + url, dataToPost);

        } catch (NullPointerException e){
            return null;
        } catch (IOException e){
            return null;
        }
        return null;
    }

    private void postData(String url, List<NameValuePair> data) throws IOException {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = getHttpClient();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(data));
        // Execute HTTP Post Request
        HttpResponse response = httpclient.execute(httppost);

        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null){
            result.append(line);
        }
    }

    private String getData(String url) throws IOException, JSONException{
        // Create a new HttpClient and Get Header
        HttpClient httpclient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        // Execute HTTP Get Request
        HttpResponse response = httpclient.execute(httpGet);
        String responseAsString = EntityUtils.toString(response.getEntity());
        ProjectCache.getInstance().save(new JSONArray(responseAsString));
        return responseAsString;
    }

    private HttpClient getHttpClient()
    {
        CredentialsProvider credProvider = new BasicCredentialsProvider();
        credProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials("YOUR USER NAME HERE", "YOUR PASSWORD HERE"));
        DefaultHttpClient http = new DefaultHttpClient();
        //http.setCredentialsProvider(credProvider);
        return  http;
    }

    private Boolean checkCache(){
        Calendar timestamp = ProjectCache.getInstance().getTimestamp();
        final Calendar oneDayAgo = Calendar.getInstance(Locale.getDefault());
        oneDayAgo.add(Calendar.DAY_OF_MONTH,-1);
        return timestamp.before(oneDayAgo);
    }
}
