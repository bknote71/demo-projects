package com.example.onlineJudge.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class Hellojava3 {

    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);
        final int n = in.nextInt();
        int sum = 0;

        for (int i = 0; i < n; ++i) {
            final int anInt = in.nextInt();
            sum += anInt;
        }

        System.out.println(sum);
    }
}
