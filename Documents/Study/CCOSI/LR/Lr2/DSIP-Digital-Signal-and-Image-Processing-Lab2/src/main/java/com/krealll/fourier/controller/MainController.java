package com.krealll.fourier.controller;

import com.krealll.fourier.service.DSPService;
import com.krealll.fourier.model.OperationCounter;
import com.krealll.fourier.model.TransformParameter;
import com.krealll.fourier.service.WalshTransform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.xml.crypto.dsig.Transform;
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
    LineChart<Number,Number> Function;
    @FXML
    LineChart<Number,Number> FWT;
    @FXML
    LineChart<Number,Number> DWT;
    @FXML
    LineChart<Number,Number> convFWT;
    @FXML
    LineChart<Number,Number> convDWT;

    @FXML
    Label FWTCalcLabel;
    @FXML
    Label FWTCorCalc;
    @FXML
    Label DWTCalcLabel;
    @FXML
    Label DWTCorCalc;
    @FXML
    Label FWTConvCalcLabel;
    @FXML
    Label FWTConvCalc;
    @FXML
    Label DWTConvCalcLabel;
    @FXML
    Label DWTConvCalc;

    public void initialize(){
        computeButton.setOnMouseClicked((MouseEvent event) ->{
            String someInput = inputN.getText();
            Matcher matcher = pattern.matcher(someInput);
            if(matcher.matches()){
                TransformParameter.setN(Integer.parseInt(someInput));

                ChartCreator.showChart(Function);

                operateFWT();
                FWTCorCalc.setText("+: "+displayedPlusCalculations+", -: "+displayedTimesCalculations);
                FWTCalcLabel.setVisible(true);
                FWTCorCalc.setVisible(true);
                OperationCounter.nullAll();

                operateDWT();
                DWTCorCalc.setText("*: "+displayedTimesCalculations);
                DWTCalcLabel.setVisible(true);
                DWTCorCalc.setVisible(true);
                OperationCounter.nullAll();

                operateFWTconv();
                FWTConvCalc.setText("+: "+displayedPlusCalculations+", -: "+displayedTimesCalculations);
                FWTConvCalcLabel.setVisible(true);
                FWTConvCalc.setVisible(true);
                OperationCounter.nullAll();

                operateDWTconv();
                DWTConvCalc.setText("*: "+displayedTimesCalculations);
                DWTConvCalcLabel.setVisible(true);
                DWTConvCalc.setVisible(true);
                OperationCounter.nullAll();
            }
        });
        clearButton.setOnMouseClicked((MouseEvent event) ->{
            FWTCalcLabel.setVisible(false);
            FWTCorCalc.setVisible(false);
            DWTCalcLabel.setVisible(false);
            DWTCorCalc.setVisible(false);
            FWTConvCalcLabel.setVisible(false);
            FWTConvCalc.setVisible(false);
            DWTConvCalcLabel.setVisible(false);
            DWTConvCalc.setVisible(false);
            displayedTimesCalculations = 0;
            displayedPlusCalculations = 0;
            Function.getData().clear();
            FWT.getData().clear();
            DWT.getData().clear();
            convFWT.getData().clear();
            convDWT.getData().clear();
        });
    }

    private double[] operateFWT(){
        WalshTransform walshTransform = new WalshTransform();
        double[] resultFWT = walshTransform.FastWalshTransform(WalshTransform.CalculateFunctionValuesVector(TransformParameter.PERIOD, TransformParameter.getN()), 1);
        logResult(resultFWT,"FWT");
        displayedPlusCalculations = OperationCounter.getPlusCounter();
        displayedTimesCalculations = OperationCounter.getMinusCounter();
        ChartCreator.showChart(FWT,resultFWT);
        return resultFWT;
    }

    private double[] operateDWT(){
        WalshTransform walshTransform = new WalshTransform();
        double[] resultDWT = walshTransform.DiscreteWalshTransform(WalshTransform.CalculateFunctionValuesVector(TransformParameter.PERIOD, TransformParameter.getN()), 1);
        logResult(resultDWT,"DWT");
        displayedTimesCalculations = OperationCounter.getMulCounter();
        ChartCreator.showChart(DWT,resultDWT);
        return resultDWT;
    }

    private void operateFWTconv(){
        WalshTransform walshTransform = new WalshTransform();
        double[] result = walshTransform.DiscreteWalshTransform(operateFWT(),-1);
        logResult(result,"converse FWT");
        displayedPlusCalculations = OperationCounter.getPlusCounter();
        displayedTimesCalculations = OperationCounter.getMinusCounter();
        ChartCreator.showChart(convFWT,result);
    }

    private void operateDWTconv(){
        WalshTransform walshTransform = new WalshTransform();
        double[] result = walshTransform.DiscreteWalshTransform(operateDWT(), -1);
        logResult(result,"converse DWT");
        displayedTimesCalculations = OperationCounter.getMulCounter();
        ChartCreator.showChart(convDWT,result);
    }

    private void logResult(double[] numbers, String name){
        System.out.println(name);
        for (int i = 0; i < TransformParameter.getN() ; i++) {
            System.out.println("Number["+i+"]="+numbers[i]);
        }
    }

}