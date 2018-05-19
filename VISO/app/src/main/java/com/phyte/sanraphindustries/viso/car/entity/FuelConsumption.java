package com.phyte.sanraphindustries.viso.car.entity;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.entity
 *
 * File Description: Fuel consumption information
 * Created by: Continued to write classic
 * Created on: 16/7/3 2:50 PM
 */
public class FuelConsumption {
    /** Fuel consumption per hundred kilometers, unit: liter/km */
    private float oilMass;
    /** 100 kilometers spent, unit: yuan/km */
    private float money;
    /** Miles traveled, in kilometers */
    private long mileage;

    public FuelConsumption() { }

    public FuelConsumption(long mileage, float money, float oilMass) {
        this.mileage = mileage;
        this.money = money;
        this.oilMass = oilMass;
    }

    public float getOilMass() {
        return oilMass;
    }

    public void setOilMass(float oilMass) {
        this.oilMass = oilMass;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }
}
