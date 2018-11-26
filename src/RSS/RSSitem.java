package RSS;

public class RSSitem {

    //atributy
    private String title, link, description, pubDate;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPubDate(String pubDate) { this.pubDate = pubDate; }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n%s", title, link, description);

    }
}
