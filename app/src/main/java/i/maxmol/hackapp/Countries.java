package i.maxmol.hackapp;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;

import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerViewAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ramotion.expandingcollection.ECBackgroundSwitcherView;
import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerView;
import com.ramotion.expandingcollection.ECPagerViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Scanner;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class Countries extends Activity {

    private ECPagerView ecPagerView;
    public static Countries context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        context = this;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Get pager from layout
        ecPagerView = (ECPagerView) findViewById(R.id.ec_pager_element);

        // Generate example dataset
        List<ECCardData> dataset = CardDataImpl.generateData();

        // Implement pager adapter and attach it to pager view
        ECPagerViewAdapter ecPagerViewAdapter = new ECPagerViewAdapter(getApplicationContext(), dataset) {
            @Override
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup head, final ListView list, ECCardData data) {
                // Data object for current card
                CardDataImpl cardData = (CardDataImpl) data;

                Typeface typeface = ResourcesCompat.getFont(context, R.font.raleway_semibold);
                // Set adapter and items to current card content list
                final List<String> listItems = cardData.getListItems();
                final CardListItemAdapter listItemAdapter = new CardListItemAdapter(getApplicationContext(), listItems);
                list.setAdapter(listItemAdapter);
                // Also some visual tuning can be done here
                list.setBackgroundColor(Color.parseColor("#3BB273"));

                // Here we can create elements for head view or inflate layout from xml using inflater service
                TextView cardTitle = new TextView(getApplicationContext());
                cardTitle.setText(cardData.getCardTitle());
                cardTitle.setTextColor(Color.parseColor("#F7F7FF"));
                cardTitle.setTextSize(COMPLEX_UNIT_DIP, 42);
                cardTitle.setTypeface(typeface);
                cardTitle.setShadowLayer(4, 0, 0, Color.BLACK);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                head.addView(cardTitle, layoutParams);

                // Card toggling by click on head element
                head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        ecPagerView.toggle();
                    }
                });
            }
        };
        ecPagerView.setPagerViewAdapter(ecPagerViewAdapter);

        // Add background switcher to pager view
        ecPagerView.setBackgroundSwitcherView((ECBackgroundSwitcherView) findViewById(R.id.ec_bg_switcher_element));


    }

    // Card collapse on back pressed
    @Override
    public void onBackPressed() {
        if (!ecPagerView.collapse())
            super.onBackPressed();
    }

    public static ArrayList<CountryInfo> calc(JSONObject jsonObj) {
        ArrayList<CountryInfo> list = new ArrayList<>();
        try {
            Resources res = MainActivity.context.getResources();
            InputStream in_s = res.openRawResource(R.raw.face_travel);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in_s));

            String line;
            while (true) {
                line = reader.readLine();

                if (line == null) break;

                String[] s = line.split(";");

                int count = 0;
                if (s.length > 4) {
                    JSONArray faces = jsonObj.getJSONArray("faces");

                    JSONArray tags = faces.getJSONObject(0).getJSONArray("tags");
                    for (int i = 0; i < tags.length(); i++) {
                        JSONObject jsonObject = tags.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String value = jsonObject.getString("value");
                        boolean b1 = !value.equals("no");

                        //Log.i("face info", name + ": " + value + ", " + b1);
                        if (name.equals("young")) {
                            if (b1 == (s[0].equals("0"))) {
                                count++;
                            }
                        }
                        else if (name.equals("beard")) {
                            if (b1 == (s[1].equals("0"))) {
                                count++;
                            }
                        }
                        else if (name.equals("glasses")) {
                            if (b1 == (s[2].equals("0"))) {
                                count++;
                            }
                        }
                    }
                }

                if (count > 2) {
                    list.add(new CountryInfo(
                            MainActivity.context.getResources().getIdentifier(s[3].toLowerCase().replaceAll(" ", "_")+"2", "raw", MainActivity.context.getPackageName()),
                            MainActivity.context.getResources().getIdentifier(s[3].toLowerCase().replaceAll(" ", "_"), "raw", MainActivity.context.getPackageName()),
                            s[3],
                            s[4]
                    ));
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

}
