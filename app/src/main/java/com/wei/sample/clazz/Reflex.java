package com.wei.sample.clazz;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

import io.reactivex.internal.functions.ObjectHelper;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class Reflex implements Serializable {
    private String test;
    private String hehh;

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static void main(String[] qweqweqw) {
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
                    System.out.println("parameter:" + parameter.getName());
                    System.out.println("parameter.getParameterizedType:" + parameter.getParameterizedType().getTypeName());
                    System.out.println("parameter.getType:" + parameter.getType().getName());
                }
            }

            //javassist
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.get(Reflex.class.getName());
//            CtMethod[] declaredMethods1 = ctClass.getDeclaredMethods();
//            for (CtMethod method : declaredMethods1) {
//                MethodInfo methodInfo = method.getMethodInfo();
//                CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
//                LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
//                int pos = Modifier.isStatic(method.getModifiers()) ? 0 : 1;
//
//                for (int i = 0; i < attr.length(); i++) {
//                    System.out.println(attr.variableName(i + pos));
//
//                }
//            }


            //获取要操作的方法参数类型数组，为获取该方法代表的CtMethod做准备
//            Method method = Reflex.class.getMethod("test", String.class, int.class);
//            int count = method.getParameterCount();
//            Class<?>[] paramTypes = method.getParameterTypes();
//            CtClass[] ctParams = new CtClass[count];
//            for (int i = 0; i < count; i++) {
//                ctParams[i] = classPool.getCtClass(paramTypes[i].getName());
//            }
            CtMethod[] methods = ctClass.getDeclaredMethods();
            for (CtMethod ctMethod : methods) {

//            CtMethod ctMethod = ctClass.getDeclaredMethod("test", ctParams);
                //得到该方法信息类
                MethodInfo methodInfo = ctMethod.getMethodInfo();

                //获取属性变量相关
                CodeAttribute codeAttribute = methodInfo.getCodeAttribute();

                //获取方法本地变量信息，包括方法声明和方法体内的变量
                //需注意，若方法为非静态方法，则第一个变量名为this
                LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
                int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;

                for (int i = 0; i < ctMethod.getParameterTypes().length; i++) {
                    System.out.println("variableName:" + ctMethod.getName() + "==" + attr.variableName(i + pos));
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

    public void als(ObjectHelper aaa) {
    }

    public void als(Map<String, String> bbbb) {
    }

    public void als(String[] cccc) {
    }
}
