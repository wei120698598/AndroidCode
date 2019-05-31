package com.wei.sample.clazz;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class Reflex implements Serializable {
    private String test;
    private String hehh;

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static void main(String[] args) {
        try {
            Class<?> reflex = Class.forName(Reflex.class.getName());

            Object o = reflex.newInstance();
            Field[] fields = Reflex.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(o, "haha");
            }


            Method[] declaredMethods = Reflex.class.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                System.out.println("---------------------" + declaredMethod.getName() + "---------------------");
                System.out.println("getModifiers : " + declaredMethod.getModifiers());

                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                for (Class<?> parameterType : parameterTypes) {
                    System.out.println("parameterType : " + parameterType.getName());
                }

                System.out.println("getReturnType:" + declaredMethod.getReturnType().getName());

                TypeVariable<Method>[] typeParameters = declaredMethod.getTypeParameters();
                for (TypeVariable<Method> typeParameter : typeParameters) {
                    Type[] bounds = typeParameter.getBounds();
                    for (Type bound : bounds) {
                        System.out.println("type:" + bound.getTypeName());
                    }
                    System.out.println("getTypeName:" + typeParameter.getTypeName());
                    System.out.println("getGenericDeclaration:" + typeParameter.getGenericDeclaration().getName());
                }

                Annotation[] declaredAnnotations = declaredMethod.getDeclaredAnnotations();
                for (Annotation declaredAnnotation : declaredAnnotations) {
                    System.out.println("declaredAnnotation:" + declaredAnnotation.annotationType().getName());
                }

                System.out.println("getDeclaringClass:" + declaredMethod.getDeclaringClass().getName());

                try {
                    Object defaultValue = declaredMethod.getDefaultValue();
                    System.out.println("defaultValue:" + defaultValue.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Type[] genericParameterTypes = declaredMethod.getGenericParameterTypes();
                for (Type bound : genericParameterTypes) {
                    System.out.println("genericParameterTypes:" + bound.getTypeName());
                }

                Parameter[] parameters = declaredMethod.getParameters();
                for (Parameter parameter : parameters) {
                    System.out.println("parameter:"+parameter.getName());
                    System.out.println("parameter.getType(:"+parameter.getType().getName());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Reflex{" +
                "test='" + test + '\'' +
                ", hehh='" + hehh + '\'' +
                '}';
    }

    @NotNull
    public Long test(@NotNull String lala, int a) {
        String nihao = "lasjdlkajldf";
        return 0L;
    }
}
