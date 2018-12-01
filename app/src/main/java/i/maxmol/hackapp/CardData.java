package i.maxmol.hackapp;

import com.ramotion.expandingcollection.ECCardData;

import java.util.List;
import java.util.Random;

public class CardData implements ECCardData<CountryInfo> {

    private String headTitle;
    private Integer headBackgroundResource;
    private Integer mainBackgroundResource;

    private Integer placePictureResource;
    private List<CountryInfo> listItems;

    public CardData() {
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    public Integer getHeadBackgroundResource() {
        return headBackgroundResource;
    }

    public void setHeadBackgroundResource(Integer headBackgroundResource) {
        this.headBackgroundResource = headBackgroundResource;
    }

    public Integer getMainBackgroundResource() {
        return mainBackgroundResource;
    }

    public void setMainBackgroundResource(Integer mainBackgroundResource) {
        this.mainBackgroundResource = mainBackgroundResource;
    }
    @Override
    public List<CountryInfo> getListItems() {
        return listItems;
    }

    public void setListItems(List<CountryInfo> listItems) {
        this.listItems = listItems;
    }
}