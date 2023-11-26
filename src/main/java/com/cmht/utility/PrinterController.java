package com.cmht.utility;

import javafx.print.PageLayout;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PrinterController {

    public static void print(String content) {
        // Check if printing is supported
//        if (!PrinterJob.printerJobSupported()) {
//            System.out.println("Printing is not supported on this platform.");
//            return;
//        }

        // Create a new PrinterJob
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        // Check if the printer job is created successfully
        if (printerJob != null) {
            // Create a Text object to print
            Text text = new Text(content);
            text.setFont(new Font(12)); // Set the font size

            // Set the printable area (assuming 53mm width)
            double printableWidth = 53.0; // in millimeters
            double printableHeight = 100.0; // in millimeters
            PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4, javafx.print.PageOrientation.PORTRAIT, javafx.print.Printer.MarginType.DEFAULT);
            printerJob.getJobSettings().setPageLayout(pageLayout);

            // Print the Text object
            boolean success = printerJob.printPage(text);

            // If the print job is successful, end the print job
            if (success) {
                printerJob.endJob();
            } else {
                System.out.println("Print job failed.");
            }
        } else {
            System.out.println("Printer job is null.");
        }
    }
}
