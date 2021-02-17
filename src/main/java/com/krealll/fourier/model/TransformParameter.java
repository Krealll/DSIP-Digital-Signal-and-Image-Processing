package com.krealll.fourier.model;

import java.util.function.Function;
import static java.lang.Math.*;

public class TransformParameter {

    private static int N;
    public static final double PERIOD = 2*PI;
    public static final Function<Double,Double> FUNCTION = (x) -> sin(x) + cos(x);

    private TransformParameter(){}

    public static int getN() {
        return N;
    }

    public static void setN(int n) {
        N = n;
    }
}
