package com.krealll.fourier.controller;

import com.krealll.fourier.service.DSPService;
import com.krealll.fourier.model.OperationCounter;
import com.krealll.fourier.model.TransformParameter;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController  {

    private final static String NUMBER_FORMAT = "(1|2|4|8|16|32|64|128|256|512|1024|2048|4096|8192|16384|32768|65536)";
    private final static Pattern pattern = Pattern.compile(NUMBER_FORMAT);
    private int displayedTimesCalculations;
    private int displayedPlusCalculations;
    @FXML
    Button computeButton;
    @FXML
    TextField inputN;
    @FXML
    Button clearButton;


    @FXML
    LineChart<Number,Number> cosX;
    @FXML
    LineChart<Number,Number> sinX;
    @FXML
    LineChart<Number,Number> Convolution ;
    @FXML
    LineChart<Number,Number> FFTConvolution;
    @FXML
    LineChart<Number,Number> Correlation;
    @FXML
    LineChart<Number,Number> FFTCorrelation;

    @FXML
    Label FFTCorCalcLabel;
    @FXML
    Label FFTCorCalc;
    @FXML
    Label CorCalcLabel;
    @FXML
    Label CorCalc;
    @FXML
    Label FFTConvCalsLabel;
    @FXML
    Label FFTConvCals;
    @FXML
    Label ConvCalsLabel;
    @FXML
    Label ConvCals;

    public void initialize(){
        computeButton.setOnMouseClicked((MouseEvent event) ->{
            String someInput = inputN.getText();
            Matcher matcher = pattern.matcher(someInput);
            if(matcher.matches()){
                TransformParameter.setN(Integer.parseInt(someInput));
                ChartCreator.showCos(cosX);
                ChartCreator.showSin(sinX);
                operateConv();
                ConvCals.setText("+: "+displayedPlusCalculations+", *: "+displayedTimesCalculations);
                ConvCalsLabel.setVisible(true);
                ConvCals.setVisible(true);
                OperationCounter.nullAll();
                operateFFTConv();
                FFTConvCals.setText("+: "+displayedPlusCalculations+", *: "+displayedTimesCalculations);
                FFTConvCalsLabel.setVisible(true);
                FFTConvCals.setVisible(true);
                OperationCounter.nullAll();
                operateCorr();
                CorCalc.setText("+: "+displayedPlusCalculations+", *: "+displayedTimesCalculations);
                CorCalcLabel.setVisible(true);
                CorCalc.setVisible(true);
                OperationCounter.nullAll();
                operateFFTCorr();
                FFTCorCalc.setText("+: "+displayedPlusCalculations+", *: "+displayedTimesCalculations);
                FFTCorCalcLabel.setVisible(true);
                FFTCorCalc.setVisible(true);
                OperationCounter.nullAll();
            }
        });
        clearButton.setOnMouseClicked((MouseEvent event) ->{
            FFTCorCalcLabel.setVisible(false);
            CorCalcLabel.setVisible(false);
            FFTConvCalsLabel.setVisible(false);
            ConvCalsLabel.setVisible(false);
            ConvCals.setVisible(false);
            FFTConvCals.setVisible(false);
            CorCalc.setVisible(false);
            FFTCorCalc.setVisible(false);
            displayedTimesCalculations = 0;
            displayedPlusCalculations = 0;
            cosX.getData().clear();
            sinX.getData().clear();
            Convolution.getData().clear();
            FFTConvolution.getData().clear();
            Correlation.getData().clear();
            FFTCorrelation.getData().clear();
        });
    }

    private void operateConv(){
        DSPService dspService = new DSPService();
        double[] result = dspService.computeTransform(TransformParameter.CONVOLUTION);
       // logResult(result,"Convolution");
        displayedPlusCalculations = OperationCounter.getPlusCounter();
        displayedTimesCalculations = OperationCounter.getMulCounter();
        ChartCreator.showChart(Convolution,result);
    }
    private void operateFFTConv(){
        DSPService dspService = new DSPService();
        double[] result = dspService.computeFFTConvolution();
        //logResult(result,"FFT Convolution");
        displayedPlusCalculations = OperationCounter.getPlusCounter();
        displayedTimesCalculations = OperationCounter.getMulCounter();
        ChartCreator.showChart(FFTConvolution,result);
    }
    private void operateCorr(){
        DSPService dspService = new DSPService();
        double[] result = dspService.computeTransform(TransformParameter.CORRELATION);
        //logResult(result,"Correlation");
        displayedPlusCalculations = OperationCounter.getPlusCounter();
        displayedTimesCalculations = OperationCounter.getMulCounter();
        ChartCreator.showChart(Correlation,result);
    }

    private void operateFFTCorr(){
        DSPService dspService = new DSPService();
        double[] result = dspService.computeFFTCorrelation();
       // logResult(result,"FFT Correlation");
        displayedPlusCalculations = OperationCounter.getPlusCounter();
        displayedTimesCalculations = OperationCounter.getMulCounter();
        ChartCreator.showChart(FFTCorrelation,result);
    }

    private void logResult(double[] numbers, String name){
        System.out.println(name);
        for (int i = 0; i < TransformParameter.getN() ; i++) {
            System.out.println("Number["+i+"]="+numbers[i]);
        }
    }


}
