package com.example.bookapps;

import java.io.Serializable;

public class FetchData implements Serializable {

    String type;
    String example;
    String exampleStory;
    String url;

    public FetchData(){

    }

    public FetchData(String type, String example, String exampleStory, String url) {
        this.type = type;
        this.example = example;
        this.exampleStory = exampleStory;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExampleStory() {
        return exampleStory;
    }

    public void setExampleStory(String exampleStory) {
        this.exampleStory = exampleStory;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
