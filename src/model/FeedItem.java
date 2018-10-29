package model;


public class FeedItem {
    String url;
    long adddedTime;
    boolean shouldShow;
    String alias;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getAdddedTime() {
        return adddedTime;
    }

    public void setAdddedTime(long adddedTime) {
        this.adddedTime = adddedTime;
    }

    public boolean isShouldShow() {
        return shouldShow;
    }

    public void setShouldShow(boolean shouldShow) {
        this.shouldShow = shouldShow;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s",url, adddedTime, shouldShow, alias);

    }

    public static FeedItem parseFromCSV(String line){
        //fixme osetrit pocet hodnot
        String[] values = line.split(";");
        FeedItem feedItem = new FeedItem();
        feedItem.setUrl(values[0]);
        feedItem.setAdddedTime(Long.parseLong(values[1]));
        feedItem.setShouldShow(Boolean.parseBoolean(values[2]));
        feedItem.setAlias(values[3]);
        return feedItem;
    }
}
