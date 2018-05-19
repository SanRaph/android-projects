package com.phyte.sanraphindustries.viso.car.entity;

import java.io.Serializable;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.entity
 *
 * File Description: Consumption Details
 * Created by: Continued to write classic
 * Created on: 16/5/29 10:26 AM
 */
public class ConsumerDetail implements Serializable {
    private static final long serialVersionUID = 6708495983469206253L;

    private long   id;
    private long   createTime;
    private long   lastUpdateTime;
    private long   consumptionTime;
    private float  money;
    private int    type;
    private String notes;

    //Refueling Affiliation Information
    private int   oilType; //Oil type
    private float unitPrice; //unit price
    private long  currentMileage;//Current mileage

    public ConsumerDetail() { }

    public ConsumerDetail(int type, float money, long lastUpdateTime, long consumptionTime, long createTime, String notes) {
        this.type = type;
        this.money = money;
        this.lastUpdateTime = lastUpdateTime;
        this.consumptionTime = consumptionTime;
        this.createTime = createTime;
        this.notes = notes;
    }

    public ConsumerDetail(int type, float money, long lastUpdateTime, long consumptionTime, long createTime, int oilType,
                          float unitPrice, long currentMileage, String notes) {
        this.type = type;
        this.money = money;
        this.lastUpdateTime = lastUpdateTime;
        this.consumptionTime = consumptionTime;
        this.createTime = createTime;
        this.oilType = oilType;
        this.unitPrice = unitPrice;
        this.currentMileage = currentMileage;
        this.notes = notes;
    }

    public ConsumerDetail(long id, long createTime, long lastUpdateTime, long consumptionTime, float money, int type, String notes,
                          int oilType, float unitPrice, long currentMileage) {
        this.id = id;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.consumptionTime = consumptionTime;
        this.money = money;
        this.type = type;
        this.notes = notes;
        this.oilType = oilType;
        this.unitPrice = unitPrice;
        this.currentMileage = currentMileage;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getConsumptionTime() {
        return consumptionTime;
    }

    public void setConsumptionTime(long consumptionTime) {
        this.consumptionTime = consumptionTime;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getOilType() {
        return oilType;
    }

    public void setOilType(int oilType) {
        this.oilType = oilType;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(long currentMileage) {
        this.currentMileage = currentMileage;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
