package com.omt.omtkosher;

public class AppModel {

    String Name;
    String Link;
    String ImageLink;

    AppModel(){

    }

    public AppModel(String name, String link, String imageLink) {
        Name = name;
        Link = link;
        ImageLink = imageLink;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
