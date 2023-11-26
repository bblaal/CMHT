package com.cmht.utility;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilityOperation {

    private Stage stage;

    @FXML
    private StackPane contentPane;

    private static final String[] units = {"", "Thousand", "Lakh", "Crore"};

    public String getCurrentDate(Date currentDateTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return dateFormat.format(currentDateTime);
    }
    public String getCurrentTime(Date currentDateTime){
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(currentDateTime);
    }
    public double calculateDiscountedAmount(double totalBillAmount, double discountedPercentage, double discountedValue, TextField discountValueField){

        if(discountedPercentage > 0){
            double discountedAmount = ((totalBillAmount / 100.0) * discountedPercentage);
            totalBillAmount = totalBillAmount - discountedAmount;
            discountValueField.setText(String.valueOf(discountedAmount));
        } else if(discountedValue > 0){
            totalBillAmount = totalBillAmount - discountedValue;
        }
        return totalBillAmount;
    }

    public static String convertToWords(long n)
    {
        long limit = 1000000000000L, curr_hun, t = 0;
        if (n == 0)
            return ("Zero");
        String multiplier[] = { "", "Trillion", "Billion",
                "Million", "Thousand" };
        String first_twenty[] = {
                "",        "One",       "Two",      "Three",
                "Four",    "Five",      "Six",      "Seven",
                "Eight",   "Nine",      "Ten",      "Eleven",
                "Twelve",  "Thirteen",  "Fourteen", "Fifteen",
                "Sixteen", "Seventeen", "Eighteen", "Nineteen"
        };
        String tens[] = { "",        "Twenty", "Thirty",
                "Forty",   "Fifty",  "Sixty",
                "Seventy", "Eighty", "Ninety" };
        if (n < 20L)
            return (first_twenty[(int)n]);
        String answer = "";
        for (long i = n; i > 0; i %= limit, limit /= 1000) {
            curr_hun = i / limit;
            while (curr_hun == 0) {
                i %= limit;
                limit /= 1000;
                curr_hun = i / limit;
                ++t;
            }
            if (curr_hun > 99)
                answer += (first_twenty[(int)curr_hun / 100]
                        + " Hundred ");
            curr_hun = curr_hun % 100;
            if (curr_hun > 0 && curr_hun < 20)
                answer
                        += (first_twenty[(int)curr_hun] + " ");
            else if (curr_hun % 10 == 0 && curr_hun != 0)
                answer
                        += (tens[(int)curr_hun / 10 - 1] + " ");
            else if (curr_hun > 20 && curr_hun < 100)
                answer
                        += (tens[(int)curr_hun / 10 - 1] + " "
                        + first_twenty[(int)curr_hun % 10]
                        + " ");
            if (t < 4)
                answer += (multiplier[(int)++t] + " ");
        }
        return (answer);
    }

    public void showMessage(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    public int[] getScreenRatio(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Get the screen size
        Dimension screenSize = toolkit.getScreenSize();
        // Display the screen size
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        return (new int[]{screenWidth, screenHeight});
    }
}
