package com.krealll.fourier.model;

public class OperationCounter {
    private static int mulCounter = 0;
    private static int plusCounter = 0;

    public static void incPlus(){
        plusCounter++;
    }

    public static void incMul(){
        mulCounter++;
    }

    public static void incAll(){
        plusCounter++;
        mulCounter++;
    }

    public static void nullAll(){
        mulCounter = 0;
        plusCounter = 0;
    }

    public static void nullPlus(){
        plusCounter = 0;
    }

    public static void nullMul(){
        mulCounter = 0;
    }

    public static int getMulCounter() {
        return mulCounter;
    }

    public static int getPlusCounter() {
        return plusCounter;
    }
}
