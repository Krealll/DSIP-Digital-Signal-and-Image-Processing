package com.krealll.fourier.service;

import com.krealll.fourier.model.ComplexNumber;
import com.krealll.fourier.model.OperationCounter;
import com.krealll.fourier.model.TransformParameter;

import static java.lang.Math.*;

public class FourierService {

    public ComplexNumber[] discreteFourierTransform(int dir){
        ComplexNumber[] result = new ComplexNumber[TransformParameter.getN()];
        for (int k = 0; k < TransformParameter.getN() ; k++) {
            result[k] = new ComplexNumber(0,0);
            for (int m = 0; m < TransformParameter.getN() ; m++) {
                double parameter = TransformParameter.PERIOD/ TransformParameter.getN();
                ComplexNumber complexNumber = new ComplexNumber(cos(parameter*k*m),dir * sin(parameter*k*m));
                double x = TransformParameter.FUNCTION.apply(parameter*m);
                result[k] = result[k].plus(complexNumber.times(x));
                OperationCounter.incAll();
            }
            if (dir == -1) {
                result[k] = result[k].divides(TransformParameter.getN());
            }
        }
        return result;
    }

    public ComplexNumber[] fastFourierTransform(){
        ComplexNumber[] functionNumbers = new ComplexNumber[TransformParameter.getN()];
        for (int i = 0; i < TransformParameter.getN(); i++) {
            functionNumbers[i] = new ComplexNumber(TransformParameter
                    .FUNCTION.apply(TransformParameter.PERIOD*(double)i/ TransformParameter.getN()));
        }
        ComplexNumber[] result = computeFFT(functionNumbers, 1);
        for (int i = 0; i < TransformParameter.getN() ; i++) {
            result[i] = result[i].divides(TransformParameter.getN());
        }
        return result;
    }

    public double[] inverseFourierTransform(ComplexNumber[] numbers){
        double[] result = new double[numbers.length];
        for (int i = 0; i <numbers.length ; i++) {
            result[i] = 0;
        }
        for (int k = 0; k <numbers.length; k++) {
            for (int m = 0; m <numbers.length ; m++) {
                double parameter = TransformParameter.PERIOD/ TransformParameter.getN();
                ComplexNumber W = new ComplexNumber(cos(parameter*(-m*k)),sin(parameter*(-m*k)));
                result[k] += numbers[m].times(W).re();
            }
        }
        return result;
    }

    public ComplexNumber[] computeFFT(ComplexNumber[] array, int dir){
        if(array.length==1){
            return array;
        }
        ComplexNumber[] even = new ComplexNumber[array.length/2];
        ComplexNumber[] odd = new ComplexNumber[array.length/2];
        for (int i = 0; i <array.length ; i++) {
            if(i%2==0){
                even[i/2] = array[i];
            } else {
                odd[i/2] = array[i];
            }
        }
        ComplexNumber[] evenResult = computeFFT(even,dir);
        ComplexNumber[] oddResult = computeFFT(odd,dir);
        ComplexNumber[] result = new ComplexNumber[array.length];
        ComplexNumber WN = new ComplexNumber(cos(TransformParameter.PERIOD/array.length),
                dir * sin(TransformParameter.PERIOD/array.length));
        ComplexNumber w = new ComplexNumber(1.0,0.0);
        for (int i = 0; i <array.length/2; i++) {
            result[i] = evenResult[i].plus(w.times(oddResult[i]));
            result[i+array.length/2] = evenResult[i].minus(w.times(oddResult[i]));
            w = w.times(WN);
            OperationCounter.incAll();
            OperationCounter.incAll();
            OperationCounter.incMul();
        }
        return result;
    }
}
