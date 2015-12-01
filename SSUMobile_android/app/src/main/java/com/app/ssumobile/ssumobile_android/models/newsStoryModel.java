package com.app.ssumobile.ssumobile_android.models;

import java.util.Comparator;

/**
 * Created by ben on 11/12/15.
 */
public class newsStoryModel {
    public String ID;
    public String Published;
    public String Link;
    public String Content;
    public String Summary;
    public String ImageURL;
    public String Title;
    public String Updated;
    public String Category;

    public static Comparator<newsStoryModel> COMPARE_BY_PUBLISHED = new Comparator<newsStoryModel>() {
        public int compare(newsStoryModel one, newsStoryModel other) {
            return other.Published.compareTo(one.Published);
        }
    };

}
