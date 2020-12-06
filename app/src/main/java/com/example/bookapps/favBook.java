package com.example.bookapps;

public class favBook {
    String type;
    String example;
    String exampleStory;
    String url;

        favBook(){

        }

    public favBook(String example, String url, String type, String exampleStory) {
        this.example = example;
        this.url = url;
        this.type = type;
        this.exampleStory = exampleStory;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
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

    public String getExampleStory() {
        return exampleStory;
    }

    public void setExampleStory(String exampleStory) {
        this.exampleStory = exampleStory;
    }
}
