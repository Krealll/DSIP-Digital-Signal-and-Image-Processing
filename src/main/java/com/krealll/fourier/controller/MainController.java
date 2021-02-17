package com.krealll.fourier.controller;

import com.krealll.fourier.model.ComplexNumber;
import com.krealll.fourier.model.FourierService;
import com.krealll.fourier.model.TransformParameters;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
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
    LineChart<Number,Number> inverseFFT;
    @FXML
    ScatterChart<Number,Number> phaseFFT;
    @FXML
    ScatterChart<Number,Number> phaseDFT ;
    @FXML
    LineChart<Number,Number> amplitudeFFT;
    @FXML
    LineChart<Number,Number> inverseDFT;
    @FXML
    LineChart<Number,Number> amplitudeDTF;
    @FXML
    LineChart<Number, Number> origin;
    @FXML
    Button clearButton;
    @FXML
    Label DFTCalculationLabel;
    @FXML
    Label FFTCalculationLabel;
    @FXML
    Label DFTCalculations;
    @FXML
    Label FFTCalculations;

    public void initialize(){
        computeButton.setOnMouseClicked((MouseEvent event) ->{
            String someInput = inputN.getText();
            Matcher matcher = pattern.matcher(someInput);
            if(matcher.matches()){
                TransformParameters.setN(Integer.parseInt(someInput));
                ChartCreator.showChart(origin);
                operateDFT();
                DFTCalculations.setText("+: "+displayedPlusCalculations+", *: "+displayedTimesCalculations);
                DFTCalculationLabel.setVisible(true);
                DFTCalculations.setVisible(true);
                ComplexNumber.nullifyAllCounters();
                operateFFT();
                FFTCalculations.setText("+: "+displayedPlusCalculations+", *: "+displayedTimesCalculations);
                FFTCalculationLabel.setVisible(true);
                FFTCalculations.setVisible(true);
                ComplexNumber.nullifyAllCounters();
            }
        });
        clearButton.setOnMouseClicked((MouseEvent event) ->{
            DFTCalculationLabel.setVisible(false);
            FFTCalculationLabel.setVisible(false);
            DFTCalculations.setVisible(false);
            FFTCalculations.setVisible(false);
            displayedTimesCalculations = 0;
            displayedPlusCalculations = 0;
            origin.getData().clear();
            amplitudeDTF.getData().clear();
            inverseFFT.getData().clear();
            phaseFFT.getData().clear();
            phaseDFT.getData().clear();
            amplitudeFFT.getData().clear();
            inverseDFT.getData().clear();
        });
    }

    private void operateDFT(){
        FourierService service = new FourierService();
        ComplexNumber[] resultDFT = service.discreteFourierTransform();
        displayedPlusCalculations = ComplexNumber.getPlusCounter();
        displayedTimesCalculations = ComplexNumber.getMultiplyCounter();
        logResult(resultDFT,"DFT");
        ChartCreator.showAmplitude(amplitudeDTF,resultDFT);
        ChartCreator.showPhase(phaseDFT,resultDFT);
        double[] inverseDFTResult = service.inverseFourierTransform(resultDFT);
        ChartCreator.showChart(inverseDFT,inverseDFTResult);
    }

    private void operateFFT(){
        FourierService service = new FourierService();
        ComplexNumber[] resultFFT = service.fastFourierTransform();
        displayedPlusCalculations = ComplexNumber.getPlusCounter();
        displayedTimesCalculations = ComplexNumber.getMultiplyCounter();
        logResult(resultFFT,"FFT");
        ChartCreator.showAmplitude(amplitudeFFT,resultFFT);
        ChartCreator.showPhase(phaseFFT,resultFFT);
        double[] inverseFFTResult = service.inverseFourierTransform(resultFFT);
        ChartCreator.showChart(inverseFFT,inverseFFTResult);
    }

    private void logResult(ComplexNumber[] numbers, String name){
        System.out.println(name);
        for (int i = 0; i <TransformParameters.getN() ; i++) {
            System.out.println("Number["+i+"]="+numbers[i].toString());
        }
    }


}
