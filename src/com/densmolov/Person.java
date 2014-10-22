package com.densmolov;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Denis Smolov
 */

public class Person {

    public int id;
    public double height;
    public char symbol;
    public boolean yesNo;
    public int age;
    public String someString;

    public Person(){
    }

    public Person(int id, double height, char symbol, boolean yesNo, int age, String someString) {
        this.id = id;
        this.height = height;
        this.symbol = symbol;
        this.yesNo = yesNo;
        this.age = age;
        this.someString = someString;
    }

    public Person(Map<String, Object> data) throws Exception {
        for (Field field : Person.class.getDeclaredFields()) {
            field.set(this, data.get(field.getName()));
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "id = " + id +
                ", height = " + height +
                ", symbol = '" + symbol + "'" +
                ", yesNo = " + yesNo +
                ", age = " + age +
                ", someString = \"" + someString + "\"" +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isYesNo() {
        return yesNo;
    }

    public void setYesNo(boolean yesNo) {
        this.yesNo = yesNo;
    }

    public String getSomeString() {
        return someString;
    }

    public void setSomeString(String someString) {
        this.someString = someString;
    }

}