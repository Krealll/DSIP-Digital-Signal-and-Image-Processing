package com.krealll.fourier.model;

public class ComplexNumber {
    private final double re;
    private final double im;

    private static final double DELTA = 0.000001;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public ComplexNumber(double real) {
        re = real;
        im = 0.0;
    }

    public double abs() {
        return Math.hypot(re, im);
    }

    public double phase() {

        return Math.atan(im / re);
    }

    public ComplexNumber plus(ComplexNumber b) {
        ComplexNumber a = this;
        double real = a.re + b.re;
        double imag = a.im + b.im;

        return new ComplexNumber(real, imag);
    }

    public ComplexNumber minus(ComplexNumber b) {
        ComplexNumber a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;

        return new ComplexNumber(real, imag);
    }

    public ComplexNumber times(ComplexNumber b) {
        ComplexNumber a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;

        return new ComplexNumber(real, imag);
    }

    public ComplexNumber times(double alpha) {
        return new ComplexNumber(alpha * re, alpha * im);
    }

    public ComplexNumber conjugate() {
        return new ComplexNumber(re, -im);
    }

    public ComplexNumber reciprocal() {
        double scale = re * re + im * im;
        return new ComplexNumber(re / scale, -im / scale);
    }

    public double re() {
        return re;
    }

    public double im() {
        return im;
    }

    public ComplexNumber divides(int param) {
        return new ComplexNumber(this.re / param, this.im / param);
    }

    public ComplexNumber sin() {
        return new ComplexNumber(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    public ComplexNumber cos() {
        return new ComplexNumber(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    public static ComplexNumber plus(ComplexNumber a, ComplexNumber b) {
        double real = a.re + b.re;
        double imag = a.im + b.im;

        return new ComplexNumber(real, imag);
    }

    private static ComplexNumber normalize(ComplexNumber a) {
        return new ComplexNumber((Math.abs(a.re) < DELTA)?0:a.re, (Math.abs(a.im) < DELTA)?0:a.im);
    }

    public String toString() {
        if (im == 0) {
            return re + "";
        }
        if (re == 0) {
            return im + "i";
        }
        if (im < 0) {
            return re + " - " + (-im) + "i";
        }
        return re + " + " + im + "i";
    }
}
