package com.krealll.fourier.service;

import com.krealll.fourier.model.ComplexNumber;
import com.krealll.fourier.model.OperationCounter;
import com.krealll.fourier.model.TransformParameter;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public class DSPService {

    private final FourierService fourierService = new FourierService();

    private ComplexNumber[] initComplex(Function<Double,Double> func){
        ComplexNumber[] result = new ComplexNumber[TransformParameter.getN()];
        for (int i = 0; i < TransformParameter.getN(); i++) {
            double x = TransformParameter.PERIOD*(double)i/ TransformParameter.getN();
            result[i] = new ComplexNumber(func.apply(x),0.0);
        }
        return result;
    }

    private double[] init(Function<Double,Double> func){
        double[] result = new double[TransformParameter.getN()];
        for (int i = 0; i < TransformParameter.getN(); i++) {
            double x = TransformParameter.PERIOD*(double)i/ TransformParameter.getN();
            result[i] = func.apply(x);
        }
        return result;
    }


    public double[] computeTransform(BinaryOperator<Integer> func){
        double[] sinNums = init(TransformParameter.SIN_FUNCTION);
        double[] cosNums = init(TransformParameter.COS_FUNCTION);
        double[] result = new double[TransformParameter.getN()];
        for (int m = 0; m < TransformParameter.getN(); m++) {
            double Zn = 0.0;
            for (int h = 0; h < TransformParameter.getN(); h++) {
                Zn += sinNums[h] * cosNums[func.apply(m,h)];
                OperationCounter.incAll();
            }
            result[m] = Zn / TransformParameter.getN();
        }
        return result;
    }

    public double[] computeFFTCorrelation(){
        ComplexNumber[] sinNum = initComplex(TransformParameter.SIN_FUNCTION);
        ComplexNumber[] cosNum = initComplex(TransformParameter.COS_FUNCTION);

        ComplexNumber[] Cx = fourierService.computeFFT(sinNum,-1);
        ComplexNumber[] Cy = fourierService.computeFFT(cosNum,-1);
        ComplexNumber[] Cz = new ComplexNumber[TransformParameter.getN()];
        ComplexNumber[] Z ;
        double[] result = new double[TransformParameter.getN()];
        for (int i = 0; i < TransformParameter.getN(); i++) {
            ComplexNumber CxConjugate = Cx[i].conjugate();
            Cx[i] = CxConjugate.divides(TransformParameter.getN());
            Cy[i] = Cy[i].divides(TransformParameter.getN());
            Cz[i]=Cx[i].times(Cy[i]);
            OperationCounter.incMul();
        }

        Z = fourierService.computeFFT(Cz,1);
        for (int i = 0; i < TransformParameter.getN(); i++) {
            result[i] = Z[i].re();
        }
        return result;
    }

    public double[] computeFFTConvolution(){
        ComplexNumber[] sinNum = initComplex(TransformParameter.SIN_FUNCTION);

        ComplexNumber[] cosNum = initComplex(TransformParameter.COS_FUNCTION);

        ComplexNumber[] Cx = fourierService.computeFFT(sinNum,-1);

        ComplexNumber[] Cy = fourierService.computeFFT(cosNum,-1);

        ComplexNumber[] Cz = new ComplexNumber[TransformParameter.getN()];
        ComplexNumber[] Z;
        double[] result = new double[TransformParameter.getN()];
        for (int i = 0; i < TransformParameter.getN(); i++) {
            Cx[i] = Cx[i].divides(TransformParameter.getN());
            Cy[i] = Cy[i].divides(TransformParameter.getN());
            Cz[i]=Cx[i].times(Cy[i]);
            OperationCounter.incMul();
        }

        Z = fourierService.computeFFT(Cz,1);

        for (int i = 0; i < TransformParameter.getN(); i++) {
            result[i] = Z[i].re();
        }
        return result;
    }




}
