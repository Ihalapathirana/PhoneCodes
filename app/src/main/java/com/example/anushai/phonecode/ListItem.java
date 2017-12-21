package com.example.anushai.phonecode;

/**
 * Created by anushai on 12/21/17.
 */

public class ListItem {

    private String name;
    private String code;
    private int id;

    public ListItem(String name, String code, int id) {
        this.name = name;
        this.code = code;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
