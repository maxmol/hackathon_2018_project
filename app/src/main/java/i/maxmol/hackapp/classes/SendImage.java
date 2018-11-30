package i.maxmol.hackapp.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cz.msebera.android.httpclient.Header;
import i.maxmol.hackapp.MainActivity;
import com.loopj.android.http.*;

public class SendImage {
    private static String url = "https://lampserv.org/hack2018/index.php";

    public static void upload(File file) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        try {
            params.put("img_upload", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //TODO: Reaming body with id "property". prepareJson converts property class to Json string. Replace this with with your own method
        //params.put("property",prepareJson(property));
        client.post(MainActivity.context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("fail" + statusCode);
            }
        });
    }

    public static void getData() {
        try {
            File file = new File(MainActivity.context.getFilesDir() + "/db.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                scanner.next(); // data line
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}