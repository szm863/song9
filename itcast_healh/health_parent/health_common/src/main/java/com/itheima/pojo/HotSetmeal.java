package com.itheima.pojo;

import java.io.Serializable;
/**@author stq
 * */
public class HotSetmeal implements Serializable {
private String name;
private String setmeal_count;
private String proportion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSetmeal_count() {
        return setmeal_count;
    }

    public void setSetmeal_count(String setmeal_count) {
        this.setmeal_count = setmeal_count;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }
}
