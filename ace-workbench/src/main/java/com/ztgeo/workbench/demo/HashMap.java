package com.ztgeo.workbench.demo;


public class HashMap {
    public static void main(String[] args) {
//        int a = 6;
//        int b = 7;
//        System.out.println("a:"+a+",b:"+b); // 6 7
//        f1(a,b);
//        System.out.println("a:"+a+",b:"+b); // 7 6
//
//        A a1 = new A(6);
//        A b1 = new A(7);
//        System.out.println("a:"+a1.a+",b:"+b1.a); // 6 7
//        f1(a1,b1);
//        System.out.println("a:"+a1.a+",b:"+b1.a); // 7 6

        // 自增自减运算符
        int p = 0;
        int p2 = 0;
        for (int i = 0; i < 10; i++) {
            p = p++;
            p2 = ++p2;
        }
        System.out.println(p); //0
        System.out.println(p2); //10

    }

    //
    public static void f1(int a,int b){
        int temp;
        temp = a;
        a = b;
        b = temp;
    }

    public static void f1(A a1,A b1){
        int temp;
        temp = a1.a;
        a1.a = b1.a;
        b1.a = temp;
    }

    static class A {
        public int a;

        public A(int a){
            this.a = a;
        }
    }

}
