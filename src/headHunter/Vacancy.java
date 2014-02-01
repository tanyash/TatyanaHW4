package headHunter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: tanyash
 * Date: 22.12.13
 * Time: 11:44
 * To change this template use File | Settings | File Templates.
 */
public class Vacancy implements Serializable {
    private String title;
    private String description;
    private String pubDate;
    private long createdDate;

    public Vacancy(String title, String description, String pubDate, long createdDate) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.createdDate = createdDate;
    }

    public Vacancy(String title, String description, String pubDate) {
        this(title, description, pubDate,System.nanoTime());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
