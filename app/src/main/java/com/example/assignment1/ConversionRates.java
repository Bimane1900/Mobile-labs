package com.example.assignment1;

import static java.lang.Double.parseDouble;

public class ConversionRates {
    public double SEKtoUSD = 0.11;
    public double GBPtoUSD = 1.28;
    public double EURtoUSD = 1.13;
    public double CNYtoUSD = 0.14;
    public double JPYtoUSD = 0.0088;
    public double KRWtoUSD = 0.00088;
    public double USDtoSEK = 9.006;
    public double USDtoGBP = 0.78;
    public double USDtoEUR = 0.88;
    public double USDtoCNY = 6.94;
    public double USDtoJPY = 1126.13;
    public double USDtoKRW = 112.82;

    public String convert(String currency, String currency2, String value){
        //String currency = inputSpin.getSelectedItem().toString();
        //String currency2 = outputSpin.getSelectedItem().toString();
        double input = (value.equals("")) ? 0 : parseDouble(value);
        if (currency == currency2){return String.valueOf(input);}
        else {
            switch (currency) {
                case "SEK":
                    input = input * SEKtoUSD;
                    return convertFromUSD(input, currency2);
                    //System.out.println(SEKtoUSD);
                    //break;
                case "GBP":
                    input = input * GBPtoUSD;
                    return convertFromUSD(input, currency2);
                    //break;
                case "EUR":
                    input = input * EURtoUSD;
                    return convertFromUSD(input, currency2);
                    //break;
                case "CNY":
                    input = input * CNYtoUSD;
                    return convertFromUSD(input, currency2);
                    //break;
                case "JPY":
                    input = input * JPYtoUSD;
                    return convertFromUSD(input, currency2);
                    //break;
                case "KRW":
                    input = input * KRWtoUSD;
                    return convertFromUSD(input, currency2);
                    //break;
                default:
                    return convertFromUSD(input, currency2);
                    //break;

            }
        }
    }

    public String convertFromUSD(double input, String currency){
        //String currency = outputSpin.getSelectedItem().toString();
        switch (currency){
            case "SEK":
                input = input*USDtoSEK;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
            //break;
            case "GBP":
                input = input*USDtoGBP;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
            //break;
            case "EUR":
                input = input*USDtoEUR;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
            //break;
            case "CNY":
                input = input*USDtoCNY;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
            //break;
            case "JPY":
                input = input*USDtoJPY;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
            //break;
            case "KRW":
                input = input*USDtoKRW;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
            //break;
            default:
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
            //break;
        }
    }
}
