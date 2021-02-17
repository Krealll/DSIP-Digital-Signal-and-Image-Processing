package com.krealll.fourier.controller;

import com.krealll.fourier.model.ComplexNumber;
import com.krealll.fourier.model.TransformParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class ChartCreator {

    public static void showChart(LineChart<Number,Number> chart){
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        Series series = new Series();
        for (int i = 0; i < TransformParameters.getN(); i++) {
            double x = TransformParameters.PERIOD*(double)i/TransformParameters.getN();
            series.getData().add(new XYChart.Data<>(x,TransformParameters.FUNCTION.apply(x)));
        }
        chart.getData().add(series);
    }

    public static void showChart(LineChart<Number,Number> chart,double[] numbers){
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        double[] xValues = new double[numbers.length];
        for (int i = 0; i < TransformParameters.getN(); i++) {
            xValues[i] = TransformParameters.PERIOD*(double)i/TransformParameters.getN();
        }

        Series series = new Series();
        for (int i = 0; i <TransformParameters.getN(); i++) {
            series.getData().add(new XYChart.Data<>(xValues[i],numbers[i]));
        }
        chart.getData().add(series);
    }

    public static void showAmplitude(LineChart<Number,Number> chart, ComplexNumber[] numbers){
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        Series series = new Series();
        for (int i = -TransformParameters.getN()+1, j=TransformParameters.getN()-1; i <0; j--, i++) {
            series.getData().add(new XYChart.Data<>(i,numbers[j].abs()));
        }
        for (int i = 0; i < TransformParameters.getN(); i++) {
            series.getData().add(new XYChart.Data<>(i,numbers[i].abs()));
        }
        chart.getData().add(series);
    }

    public static void showPhase(ScatterChart<Number,Number> chart, ComplexNumber[] numbers){
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        Series series = new Series();
        for (int i = -TransformParameters.getN()+1, j=TransformParameters.getN()-1; i <0; j--, i++) {
            series.getData().add(new XYChart.Data<>(i,-numbers[j].phase()));
        }
        for (int i = 0; i < TransformParameters.getN(); i++) {
            series.getData().add(new XYChart.Data<>(i,numbers[i].phase()));
        }
        chart.getData().add(series);
    }


}
