package i.maxmol.hackapp;

import com.ramotion.expandingcollection.ECCardData;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    private static List<CountryInfo> countryInfos = new ArrayList<CountryInfo>();

    public static void setCountryInfos(List<CountryInfo> countryInfos) {
        JsonParser.countryInfos = countryInfos;
    }

    static public List<ECCardData> getData(){
        List<ECCardData> cards = new ArrayList<>();
        // for test
        CountryInfo countryInfo = new CountryInfo(R.drawable.city_scape,R.drawable.city_scape_head,"Vienne","big big big big big big big big big big big big big big big big big big big big big big text");
        CountryInfo countryInfo1 = new CountryInfo(R.drawable.city_scape,R.drawable.city_scape_head,"London","big big big big big big big big big big big big big big big big big big big big big big text");
        countryInfos.add(countryInfo);
        countryInfos.add(countryInfo1);
        // for test
        for     (CountryInfo el: countryInfos) {
            List<String> tmp = new ArrayList<>();
            tmp.add(el.cardText);
            cards.add(new CardDataImpl(el.cardTitle, el.mainBackgroundResource,el.headBackgroundResource,tmp));
        }
        return cards;
    }

}

class CountryInfo {
    int mainBackgroundResource,headBackgroundResource;
    String cardTitle,cardText;

    public CountryInfo(int mainBackgroundResource, int headBackgroundResource, String cardTitle, String cardText) {
        this.mainBackgroundResource = mainBackgroundResource;
        this.headBackgroundResource = headBackgroundResource;
        this.cardTitle = cardTitle;
        this.cardText = cardText;
    }
}
