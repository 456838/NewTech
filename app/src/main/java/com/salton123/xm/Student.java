package com.salton123.xm;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/20 21:05
 * Time: 21:05
 * Description:
 */
public class Student {

    public String name ;
    public int age ;

    @Override
    public String toString() {
        System.out.println("Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}');
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
