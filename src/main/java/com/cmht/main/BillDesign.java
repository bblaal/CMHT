package com.cmht.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cmht.utility.UtilityOperation.convertToWords;

public class BillDesign {
    public String formatHeader(){
        return(
                "-------------------------------------------------------------\n" +
                "                               Champaran Meat House & Tandoor\n" +
                "                                               Address Line 1\n" +
                "                                               Address Line 2\n" +
                "                                                   Contact No\n" +
                "                                               Retail Invoice\n" +
                "-------------------------------------------------------------\n"
        );
    }

    public String formatSubHeader(String memoNo, String formattedDate, String formattedTime){

        return(
                "  Memo# " + memoNo + "                                         "+formattedTime+"    "+formattedDate+"\n" +
                "-------------------------------------------------------------\n"
        );
    }

    public String formatBillingTableHeader(){

        return(
                "  SL                          Dish                           Qty         Rate          Amount  \n" +
                "-------------------------------------------------------------\n"
        );
    }

    public String formatBillingTableItems(int sl, String itemName, int itemQuantity, String itemRate, String itemTotal){

        return(
                "  " + sl + "           " + itemName + "                       " + itemQuantity + "            " + itemRate + "          " + itemTotal + "  \n"
        );
    }

    public String formatDiscount(double discount){

        return(
                "-------------------------------------------------------------\n" +
                "                                                                                   Discount:     " + discount + "                  \n"
        );
    }

    public String formatTotalBillPayment(int qty, double totalAmount, String paymentMethod){

        return(
                "-------------------------------------------------------------\n" +
                "  Total                                             Qty:     " + qty + "           Amount      " + totalAmount + "  \n" +
                "-------------------------------------------------------------\n" +
                "  Rupees: " + convertToWords((long) totalAmount) + " \n" +
                "  Only\n" +
                "  Payment Mode: " + paymentMethod + "\n"
        );
    }

    public String formatFooter(){

        return(
                "-------------------------------------------------------------\n" +
                "                             Thank You !!! Please Visit Again\n" +
                "-------------------------------------------------------------\n"
        );
    }
}
