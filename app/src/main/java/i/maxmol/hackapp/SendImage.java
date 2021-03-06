package i.maxmol.hackapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import i.maxmol.hackapp.MainActivity;
import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

public class SendImage {
    public static void upload(final File file) {
        final AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        try {
            params.put("api_key", "d45fd466-51e2-4701-8da8-04351c872236");
            params.put("file", file);
            params.put("detection_flags", "bestface,classifiers");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO: Reaming body with id "property". prepareJson converts property class to Json string. Replace this with with your own method
        //params.put("property", prepareJson(property));
        client.post(MainActivity.context, "https://www.betafaceapi.com/api/v2/media/file", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("File send", "image uploaded");
                MainActivity.context.progressBar.setMessage("Processing...");

                RequestParams params = new RequestParams();
                try {
                    params.put("api_key", "d45fd466-51e2-4701-8da8-04351c872236");

                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    String hex = checksum(file.getAbsolutePath(), md);
                    params.put("checksum", hex);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i("File send", "answer requested");
                client.get(MainActivity.context, "https://www.betafaceapi.com/api/v2/media/hash", params, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.i("File send", "successful return");
                        MainActivity.context.progressBar.dismiss();
                        String json = new String(responseBody);

                        try {
                            JSONObject jsonObj = new JSONObject(json);
                            ArrayList<CountryInfo> ci = Countries.calc(jsonObj);
                            if (ci == null) {
                                new AlertDialog.Builder(MainActivity.context)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Try again!")
                                        .setMessage("We can't see you on the photo.")
                                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                MainActivity.context.onResume();
                                            }

                                        })
                                        .show();
                                return;
                            }
                            JsonParser.setCountryInfos(ci);

                            Intent intent = new Intent(MainActivity.context, Countries.class);
                            MainActivity.context.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("fail" + statusCode);
            }
        });


    }

    private static String checksum(String filepath, MessageDigest md) throws IOException {

        // file hashing with DigestInputStream
        try (DigestInputStream dis = new DigestInputStream(new FileInputStream(filepath), md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        }

        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();

    }
}