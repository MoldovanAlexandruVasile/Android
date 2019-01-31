package com.alex.exam.Database;

public class Bookmark {

    private Integer ID;
    private String name;
    private String description;
    private String url;
    private String type;
    private Integer rating;

    public Bookmark() {
    }

    public Bookmark(String name, String description, String url, String type, Integer rating) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.type = type;
        this.rating = rating;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", rating=" + rating +
                '}';
    }
}
