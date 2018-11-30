package i.maxmol.hackapp;

import com.ramotion.expandingcollection.ECCardData;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    static public List<ECCardData> getData(){
        List<ECCardData> cards = new ArrayList<>();
        List<CountryInfo> countryInfos = new ArrayList<CountryInfo>();
        countryInfos.add(new CountryInfo(R.drawable.city_scape,R.drawable.city_scape_head,"city_scape","some cool text"));
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
