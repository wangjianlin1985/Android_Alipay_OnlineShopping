package com.mobileclient.domain;

import java.io.Serializable;

public class OrderState implements Serializable {
    /*×´Ì¬±àºÅ*/
    private int stateId;
    public int getStateId() {
        return stateId;
    }
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    /*¶©µ¥×´Ì¬Ãû³Æ*/
    private String stateName;
    public String getStateName() {
        return stateName;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}