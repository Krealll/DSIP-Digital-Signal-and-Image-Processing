package com.krealll.fourier.service;

import com.krealll.fourier.model.OperationCounter;

import java.io.Console;

import static java.lang.Math.*;

public class WalshTransform {

    static public double[] CalculateFunctionValuesVector(double period, int N) {
        double interval = period / (N+1);
        double[] funcValues = new double[N];
        double x = interval;
        for (int i = 0; i < N; i++) {
            funcValues[i] = sin(x) + cos(x);
            x += interval;
        }
        return funcValues;
    }

    private static double CreateWalsh(int value, double t, int length) {
        int N = (int) (Math.log(length) / Math.log(2)) + 1;
        double[] list = new double[N];
        for (int i = 1; i < N; i++) {
            boolean first = GetBit(value, i - 1);
            boolean second = GetBit(value, i);
            list[i] = pow(Radamaher(i, t), first ^ second ? 1 : 0);
        }
        for (int i = 0; i < list.length; i++)
            System.out.println(list[i]);
        return Multiplication(list);
    }

    private static boolean GetBit(int value, int pos) {
        if ((value & (1 << pos)) != 0)
            return true;
        else
            return false;
    }

    private static double Multiplication(double[] inputValues) {
        double result = 1;
        for (int i = 1; i < inputValues.length; i++)
            result *= inputValues[i];
        return result;
    }

    private static int Radamaher(int value, double t) {
        return (sin(pow(2, value) * t * Math.PI)) > 0 ? 1 : -1;
    }

    public static double[] DiscreteWalshTransform(double[] inputValues, int dir) {
        double[] outputValues = new double[inputValues.length];
        double correction = 0.01;

        for (int i = 0; i < inputValues.length; i++) {
            double value = 0;
            for (int j = 0; j < inputValues.length; j++) {
                if (dir == 1)
                    value += inputValues[j] * CreateWalsh(i, j / (double) inputValues.length + correction, inputValues.length);
                else
                    value += inputValues[j] * CreateWalsh(j, i / (double) inputValues.length + correction, inputValues.length);
                OperationCounter.incMul();
            }
            outputValues[i] = value;

            if (dir == 1)
                outputValues[i] /= outputValues.length;
        }
        return outputValues;
    }

    public static double[] FastWalshTransform(double[] inputValues, int dir) {
        double[] outputValues = inputValues.clone();

        for (int i = (int)(Math.log(inputValues.length)/Math.log(2)); i > 0; i--) {
            int pos = (int) (Math.pow(2, i) / 2);
            for (int j = 0; j < pos; j++) {
                for (int k = 0; k < inputValues.length; k += (int) Math.pow(2, i)) {
                    OperationCounter.incPlus();
                    OperationCounter.incMinus();
                    double first = outputValues[j + k];
                    double second = outputValues[j + k + pos];
                    outputValues[j + k] = first + second;
                    outputValues[j + k + pos] = first - second;
                }
            }
        }

        if (dir == 1)
            for (int i = 0; i < outputValues.length; i++)
                outputValues[i] /= outputValues.length;

        double[] tmpValues = outputValues.clone();

        for (int i = 0; i < outputValues.length; i++)
            outputValues[i] = tmpValues[Reverse(i ^ (i >> 1), (int)(Math.log(inputValues.length)/Math.log(2)))];

        return outputValues;
    }

    private static int Reverse(int value, int length) {
        int result = 0;
        for (int i = 0; i < length; i++) {
            int next = value & 1;
            value >>= 1;
            result <<= 1;
            result |= next;
        }
        return result;
    }
}
