package com.densmolov;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denis Smolov
 */

public class ObjSerializer {

    public static final String PATH = "D:\\TestSerializer.txt";
    public static final String ENCODING = "UTF-8";

    public void doSerialize(Object anInstance) {
        ArrayList<Byte> arrayBefore = new ArrayList<>();
        String className = anInstance.getClass().getCanonicalName();
        try {
            arrayBefore.add(0, new Integer(className.getBytes(ENCODING).length).byteValue());
            for (byte b : className.getBytes(ENCODING)) {
                arrayBefore.add(b);
            }
            getFieldsForSerialization(anInstance, arrayBefore);
        } catch (IllegalAccessException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            writeToFile(arrayBefore);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(anInstance);
        System.out.println("Serialization was done successfully.");
    }


    public void doDeserialize() {
        File file = new File(PATH);
        byte[] byteArray = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int read = fileInputStream.read(byteArray);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int byteArrIterator = byteArray[0];
        char[] charArray = new char[byteArrIterator];
        for (int i = 0; i < byteArrIterator; i++) {
            charArray[i] = (char) byteArray[i + 1];
        }
        try {
            Class<?> aClass = Class.forName(String.valueOf(charArray));
            Constructor constructor = aClass.getConstructor(new Class[]{Map.class});
            Object obj = constructor.newInstance(getValuesForFields(byteArrIterator, byteArray, aClass));
            System.out.println("\nDeserialization was done successfully.");
            System.out.println(obj);
            if (getMethodsForObjectAsAString(aClass) != null) {
                System.out.println(getMethodsForObjectAsAString(aClass));
            }
            System.out.println(getConstructorsAsAString(aClass));
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void getFieldsForSerialization(Object anInstance, ArrayList<Byte> arrayBefore)
            throws IllegalAccessException, UnsupportedEncodingException {
        Map<String, Object> fields = new HashMap<>();
        for (Field field : anInstance.getClass().getDeclaredFields()) {
            fields.put(field.getName(), field.get(anInstance));
        }
        for (Object obj : fields.values()) {
            int length = String.valueOf(obj).getBytes(ENCODING).length;
            arrayBefore.add(new Integer(length).byteValue());
            for (byte b : obj.toString().getBytes(ENCODING)) {
                arrayBefore.add(b);
            }
        }
    }

    private void writeToFile(ArrayList<Byte> arrayBefore) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(PATH);
        for (Byte oneByte : arrayBefore) {
            fileOutputStream.write(oneByte);
        }
        fileOutputStream.close();
    }

    private Map<String, Object> getValuesForFields(int byteArrIterator, byte[] byteArray, Class aClass) {
        Map<String, Object> valuesForFields = new HashMap<>();
        for (Field field : aClass.getDeclaredFields()) {
            int length = byteArray[++byteArrIterator];
            char[] chars = new char[length];
            for (int i = 0; i < length; i++) {
                chars[i] = (char) byteArray[++byteArrIterator];
            }
            switch (field.getType().getName()) {
                case ("int"):
                    String s1 = new String(chars);
                    int arg1 = Integer.parseInt(s1);
                    valuesForFields.put(field.getName(), arg1);
                    break;
                case ("double"):
                    String s2 = new String(chars);
                    double arg2 = Double.parseDouble(s2);
                    valuesForFields.put(field.getName(), arg2);
                    break;
                case ("char"):
                    char arg3 = chars[0];
                    valuesForFields.put(field.getName(), arg3);
                    break;
                case ("boolean"):
                    String s3 = new String(chars);
                    boolean arg4 = Boolean.parseBoolean(s3);
                    valuesForFields.put(field.getName(), arg4);
                    break;
                case ("java.lang.String"):
                    valuesForFields.put(field.getName(), new String(chars));
                    break;
            }
        }
        return valuesForFields;
    }

    private String getMethodsForObjectAsAString(Class aClass) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (Method m : aClass.getDeclaredMethods()) {
            switch (m.getModifiers()) {
                case (0):
                    stringBuilder.append("package ");
                    break;
                case (1):
                    stringBuilder.append("public ");
                    break;
                case (2):
                    stringBuilder.append("private ");
                    break;
                case (4):
                    stringBuilder.append("protected ");
                    break;
            }
            stringBuilder.append(m.getReturnType());
            stringBuilder.append(" ");
            stringBuilder.append(m.getName());
            if (m.getParameterTypes() != null) {
                stringBuilder.append("(");
                for (Class cl : m.getParameterTypes()) {
                    stringBuilder.append(cl);
                    stringBuilder.append(" ");
                }
                stringBuilder.append(")");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private String getConstructorsAsAString(Class aClass) {
        StringBuilder stringBuilder = new StringBuilder();
        Constructor ctorList[] = aClass.getDeclaredConstructors();
        for (int i = 0; i < ctorList.length; i++) {
            stringBuilder.append(i + 1).append(" constructor gets these parameters:\n");
            Class paramList[] = ctorList[i].getParameterTypes();
            for (int j = 0; j < paramList.length; j++) {
                stringBuilder.append("param #").append(j + 1).append(" - ").append(paramList[j]).append("\n");
            }
            stringBuilder.append("\n-----\n");
        }
        return stringBuilder.toString();
    }

}