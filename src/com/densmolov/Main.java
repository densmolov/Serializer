package com.densmolov;

/**
 * Created by Denis Smolov
 */

public class Main {

    public static void main(String[] args) {

        //Uncomment the Passport instance and comment the Person instance
        // to see that ObjSerializer works fine on both classes.
        Person anInstance = new Person(3, 189.75, 'Z', true, 77, "Show must go on!");
        //Passport anInstance = new Passport(10, 'Y', 225588);

        //  SERIALIZATION :
        ObjSerializer objSerializer = new ObjSerializer();
        objSerializer.doSerialize(anInstance);

        //  DESERIALIZATION :
        objSerializer.doDeserialize();

    }

}