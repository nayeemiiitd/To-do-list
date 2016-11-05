package com.iiitd.to_do_list;

import java.io.Serializable;

/**
 * Created by Nayeem on 11/4/2016.
 */

public class Detail implements Serializable{

    private String title;
    private String detail;

    public Detail(String title, String detail) {
        super();
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

}