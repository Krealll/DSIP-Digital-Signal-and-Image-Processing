package com.krealll.fourier.model;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import static java.lang.Math.*;

public class TransformParameter {

    private static int N;
    public static final double PERIOD = 2*PI;
    public static final Function<Double,Double> FUNCTION = (x) -> sin(x) + cos(x);
    public static final Function<Double,Double> SIN_FUNCTION = Math::sin;
    public static final Function<Double,Double> COS_FUNCTION = Math::cos;
    public static final BinaryOperator<Integer> CORRELATION = (x,y)
            -> (x + y + TransformParameter.getN()) % TransformParameter.getN();
    public static final BinaryOperator<Integer> CONVOLUTION = (x,y)
            -> (x - y + TransformParameter.getN()) % TransformParameter.getN();
    private TransformParameter(){}

    public static int getN() {
        return N;
    }

    public static void setN(int n) {
        N = n;
    }
}
