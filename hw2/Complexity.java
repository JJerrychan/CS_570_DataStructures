package hw2;
/*
 * course: CS 570 ws
 * Homework Assignment 2
 * name: Junjie Chen
 * CWID: 10476718
 * */

public class Complexity {

    //method1 has time complexity O(n^2)
    public static void method1(int n) {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("Operation " + counter);
                counter++;
            }
        }
    }

    //method2 has time complexity O(n^3)
    public static void method2(int n) {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    System.out.println("Operation " + counter);
                    counter++;
                }
            }
        }
    }

    //method3 has time complexity O(logn)
    public static void method3(int n) {
        int counter = 0;
        for (int i = 1; i < n; i *= 2) {
            System.out.println("Operation " + counter);
            counter++;
        }
    }

    //method4 has time complexity O(n*logn)
    public static void method4(int n) {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j *= 2) {
                System.out.println("Operation " + counter);
                counter++;
            }
        }
    }

    //method5 has time complexity O(loglogn)
    public static void method5(int n) {
        int counter = 0;
        for (int i = 2; i < n; i *= i) {
            System.out.println("Operation " + counter);
            counter++;
        }
    }

    //method6 has time complexity O(2^n)
    public static int method6(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return method6(n - 1) + method6(n - 2);
        }
    }
}
