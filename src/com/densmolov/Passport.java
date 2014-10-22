package com.densmolov;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Denis Smolov
 */

public class Passport {

    public int id;
    public char passportSeries;
    public int passportNumber;

    public Passport(){
    }

    public Passport(int id, char passportSeries, int passportNumber) {
        this.id = id;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
    }

    public Passport(Map<String, Object> data) throws Exception {
        for (Field field : Passport.class.getDeclaredFields()) {
            field.set(this, data.get(field.getName()));
        }
    }

    @Override
    public String toString() {
        return "Passport{" + "id = " + id + ", passportSeries = '"
                + passportSeries + "', passportNumber = " + passportNumber + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(char passportSeries) {
        this.passportSeries = passportSeries;
    }

    public int getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }

}