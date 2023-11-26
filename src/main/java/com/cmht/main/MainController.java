package com.cmht.main;

import com.cmht.models.BillingItem;
import com.cmht.models.FinalBill;
import com.cmht.models.Product;
import com.cmht.utility.UtilityOperation;
import com.db.utils.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainController {

    // initialize UI components
    public ChoiceBox<String> itemDDL;
    public ChoiceBox<Integer> itemQty;
    public ChoiceBox<String> paymentChoices;
    public TextField itemRate;
    public TextField itemTotal;
    public TextField invoiceNo;
    public TextField totalBill;
    public TextField discountPercentage;
    public TextField discountValue;
    public TextField addItemName;
    public TextField addItemPrice;
    public Label totalQuantitySold;
    public Label totalAmountSold;
    public TableView<BillingItem> billingTable;
    public TableColumn billingItemSL;
    public TableColumn billingItemName;
    public TableColumn billingItemQty;
    public TableColumn billingItemRate;
    public TableColumn billingTotal;
    public TableView<Product> productTable;
    public TableColumn colProductID;
    public TableColumn colProductName;
    public TableColumn colProductPrice;
    public DatePicker fromDatePicker;
    public DatePicker toDatePicker;
    public Pagination pagination;
    public VBox billingSection;

    // initialze Objects
    private ObservableList<BillingItem> billingItems = FXCollections.observableArrayList();
    FinalBill finalBill = new FinalBill();
    UtilityOperation utilityOperation = new UtilityOperation();



    @FXML
    public void initialize() {

        loadItemName();
        loadStaticQuantity();
        loadPaymentMethodDiscount();

        totalBill.setEditable(false);
        itemRate.setEditable(false);
        itemTotal.setEditable(false);

        itemDDL.setOnAction(this::populateItemRate);
        itemQty.setOnAction(this::populateTotalAmount);

    }

    public void loadItemName(){
        itemDDL.getItems().clear();
        List<String> values = new ArrayList<>();
        String SQL = "SELECT * FROM Product";
        try(Connection connection = Database.connectDb();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet result = preparedStatement.executeQuery()) {

            while (result.next()) {
                values.add(result.getString("name"));
            }
        } catch (Exception e){
            e.printStackTrace();
            utilityOperation.showMessage("Something Went Wrong, Try Again", "Alert !!!");}

        itemDDL.getItems().addAll(values);
        itemDDL.setValue("--Select Item--");
    }

    private void loadStaticQuantity(){
        for(int i=1; i<=10; i++){
            itemQty.getItems().add(i);
        }
        itemQty.setValue(0);
    }

    private void loadPaymentMethodDiscount(){

        paymentChoices.getItems().clear();
        discountPercentage.setText(String.valueOf(0.0));
        discountValue.setText(String.valueOf(0.0));
        paymentChoices.getItems().addAll("-- Select Payment --", "UPI", "Cash", "Card");

    }

    private void populateItemRate(ActionEvent event) {
        String selectedItem = itemDDL.getValue();
        String SQL = "SELECT * FROM Product WHERE name = ?";

        try(Connection connection = Database.connectDb();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, selectedItem);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                itemRate.setText(result.getString("rate"));
                itemQty.setDisable(false);

            }
        } catch (Exception e){
            e.printStackTrace();
            utilityOperation.showMessage("Something Went Wrong, Try Again", "Alert !!!");}
    }

    private void populateTotalAmount(ActionEvent actionEvent) {
        int selectedQty = itemQty.getValue();
        double selectedItemRate = Double.parseDouble(itemRate.getText());

        itemTotal.setText(String.valueOf(selectedQty * selectedItemRate));
    }

    public void onAddProduct(){
        if(Objects.equals(itemDDL.getValue(), "--Select Item--")){
            utilityOperation.showMessage("Please Select Item", "Alert!!!");
        } else if(itemQty.getValue() == 0){
            utilityOperation.showMessage("Please Select Quantity", "Alert!!!");
        } else {

            String selectedItem = itemDDL.getValue();
            int selectedQty = itemQty.getValue();
            String selectedItemRate = itemRate.getText();
            String totalAmount = itemTotal.getText();

            // Create a new BillingItem and add it to the ObservableList
            BillingItem newItem = new BillingItem(1, selectedItem, selectedQty, selectedItemRate, totalAmount);
            billingItems.add(newItem);

            billingTable.setItems(billingItems);

            billingItemSL.setCellValueFactory(new PropertyValueFactory<>("slno"));
            billingItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
            billingItemQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            billingItemRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
            billingTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            // Clear the input fields or perform any other necessary actions
            itemDDL.setValue("--Select Item--");
            itemQty.setValue(0);
            itemRate.clear();
            itemTotal.clear();
        }

    }

    public void onPreviewBill(){

        if(paymentChoices.getValue() == null){
            utilityOperation.showMessage("Please Select Payment Method", "Alert!!!");
        } else {
            // Clear previous content in billingSection
            billingSection.getChildren().clear();

            Date currentDateTime = new Date();
            String currentDate = utilityOperation.getCurrentDate(currentDateTime);
            String currentTime = utilityOperation.getCurrentTime(currentDateTime);

            int invoiceIndex = 0;
            String SQL1 = "SELECT MAX(ID) FROM SELL";
            try(Connection connection = Database.connectDb();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL1);
                ResultSet result = preparedStatement.executeQuery()) {

                if(result.next()){
                    invoiceIndex = result.getInt(1) + 1;
                }
            }catch (Exception e){
                e.printStackTrace();
                utilityOperation.showMessage("Something Went Wrong, Try Again", "Alert !!!");}

            BillDesign billDesign = new BillDesign();
            Label designingHeader = new Label(
                    billDesign.formatHeader() +
                            billDesign.formatSubHeader("CM-" + invoiceIndex ,currentDate, currentTime) +
                            billDesign.formatBillingTableHeader());

            billingSection.getChildren().add(designingHeader);


            // Add new content to billingSection
            int totalQuantity = 0;
            double totalBillAmount = 0.0;
            int sl = 0;

            for (BillingItem item : billingItems) {

                totalQuantity += item.getQuantity();
                totalBillAmount += Double.parseDouble(item.getTotal());
                sl = sl + 1;
                Label designTableItems = new Label(billDesign.formatBillingTableItems(sl, item.getName(), item.getQuantity(), item.getRate(), item.getTotal()));
                billingSection.getChildren().add(designTableItems);

            }

            double discountedPercentage = Double.parseDouble(discountPercentage.getText());
            double discountedValue = Double.parseDouble(discountValue.getText());

            totalBillAmount = utilityOperation.calculateDiscountedAmount(totalBillAmount, discountedPercentage, discountedValue, discountValue);

            Label designDiscount = new Label(billDesign.formatDiscount(discountedValue));
            billingSection.getChildren().add(designDiscount);

            totalBill.setText(String.valueOf(totalBillAmount));

            // Add total bill label
            Label designTotalBill = new Label(billDesign.formatTotalBillPayment(totalQuantity, totalBillAmount, paymentChoices.getValue()));
//        designTotalBill.setStyle("-fx-font-weight: bold");
            billingSection.getChildren().add(designTotalBill);


            Label designFooter = new Label(billDesign.formatFooter());
            billingSection.getChildren().add(designFooter);

            finalBill.setInvoiceNo(invoiceIndex);
            finalBill.setBillingDate(currentDate);
            finalBill.setBillingTime(currentTime);
            finalBill.setDiscountedAmount(discountedValue);
            finalBill.setTotalAmount(totalBillAmount);
            finalBill.setTotalQty(totalQuantity);
            finalBill.setPaymentMethod(paymentChoices.getValue());
        }

    }

    public void onPrintBill(){

        String SQL2 = "INSERT INTO SELL (ID, ITEM_QTY, DISCOUNT, AMOUNT, DATE, TIME, PAYMENT)\n" +
                "VALUES(?,?,?,?,?,?,?);";
        try(Connection connection = Database.connectDb();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL2)) {

            preparedStatement.setInt(1, finalBill.getInvoiceNo());
            preparedStatement.setInt(2, finalBill.getTotalQty());
            preparedStatement.setDouble(3, finalBill.getDiscountedAmount());
            preparedStatement.setDouble(4, finalBill.getTotalAmount());
            preparedStatement.setString(5, finalBill.getBillingDate());
            preparedStatement.setString(6, finalBill.getBillingTime());
            preparedStatement.setString(7, finalBill.getPaymentMethod());

            if(preparedStatement.executeUpdate() == 1){
                System.out.println("Printing bill");
            } else {
                System.out.println("Please Use handbook");

            }

            PrinterJob printerJob = PrinterJob.createPrinterJob();

            if (printerJob != null) {
                // Set up the print job with paper size (assuming 79mm width)
//                PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A6, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
//                printerJob.getJobSettings().setPageLayout(pageLayout);

                Paper customPaper = PrinterJob.createPrinterJob().getPrinter().createPageLayout(Paper.A6, PageOrientation.PORTRAIT, 5.0, 5.0, 5.0, 5.0).getPaper();
                PageLayout customPageLayout = printerJob.getPrinter().createPageLayout(customPaper, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
                printerJob.getJobSettings().setPageLayout(customPageLayout);

                // Create a Node representing your content (replace this with your actual content)
                // For example, assuming billingSection is a Node that represents your bill content
                Node printingSection = (Node) billingSection.getChildren();

//                System.out.println(billingSection.getChildren());

                // Check if the print job can be executed
                if (printerJob.printPage(billingSection.getStyleableNode())) {
                    // End the print job
                    printerJob.endJob();
                } else {
                    // Handle print job failure
                    System.out.println("Failed to print the bill.");
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            utilityOperation.showMessage("Something Went Wrong, Try Again", "Alert !!!");}


//        if (printerJob != null) {
//            boolean success = printerJob.printPage(billingSection); // Replace with your actual content
//            if (success) {
//                System.out.println("If executed");
//                printerJob.endJob();
//            }
//        }

    }


    public void onClearFields(){
        itemDDL.setValue("--Select Item--");
        itemQty.setValue(0);
        itemRate.clear();
        itemTotal.clear();
    }
    public void onClearAll(){
        onClearFields();
        paymentChoices.setValue("--Select Payment--");
        billingTable.getItems().clear();
        billingSection.getChildren().clear();
        totalBill.clear();
        discountPercentage.clear();
        discountValue.clear();

    }

    public void getSalesReport() throws SQLException {
        ObservableList<FinalBill> values = FXCollections.observableArrayList(); // Use the appropriate type for your data

        Connection connect = Database.connectDb();
        PreparedStatement prepare;
        String SQL1 = "SELECT * FROM SELL WHERE ID = ?";
        String SQL2 = "SELECT * FROM SELL WHERE DATE BETWEEN ? AND ?";
        try{
            if(!Objects.equals(invoiceNo.getText(), "")){
                prepare = connect.prepareStatement(SQL1);
                prepare.setInt(1, Integer.parseInt(invoiceNo.getText()));
            } else {
                prepare = connect.prepareStatement(SQL2);
                prepare.setString(1, utilityOperation.getCurrentDate(java.sql.Date.valueOf(fromDatePicker.getValue())));
                prepare.setString(2, utilityOperation.getCurrentDate(java.sql.Date.valueOf(toDatePicker.getValue())));
            }
            ResultSet result = prepare.executeQuery();
            double subTotalSellValue = 0.0;
            int subTotalQuanytitySold = 0;
            while (result.next()) {
                FinalBill salesReportData = new FinalBill(
                        result.getInt("ID"),
                        result.getInt("ITEM_QTY"),
                        result.getDouble("DISCOUNT"),
                        result.getDouble("AMOUNT"),
                        result.getString("DATE"),
                        result.getString("TIME"),
                        result.getString("PAYMENT")
                );
                subTotalSellValue += salesReportData.getTotalAmount();
                subTotalQuanytitySold += salesReportData.getTotalQty();
                values.add(salesReportData);
            }

            int pageCount = values.size() > 18 ? values.size()/18 : 1;
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(pageIndex -> createPage(pageIndex, values));
            pagination.setVisible(true);

            totalAmountSold.setText("Total Amount Sold : " + subTotalSellValue);
            totalQuantitySold.setText("Total No of Dishes Served : " + subTotalQuanytitySold);

        } catch (Exception e){
            e.printStackTrace();
            utilityOperation.showMessage("Something Went Wrong, Try Again", "Alert !!!");}
        finally {
            connect.close();
        }
    }

    private Node createPage(int pageIndex, ObservableList<FinalBill> data) {
        int itemsPerPage = 18;
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, data.size());

        TableView<FinalBill> tableView = new TableView<>();
        tableView.setPrefWidth(970.0);

        // Assuming FinalBill has getters corresponding to the column names
        TableColumn<FinalBill, Integer> column1 = new TableColumn<>("Invoice ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
        column1.setPrefWidth(60.0);

        TableColumn<FinalBill, String> column2 = new TableColumn<>("Quantity");
        column2.setCellValueFactory(new PropertyValueFactory<>("totalQty"));
        column2.setPrefWidth(65.0);

        TableColumn<FinalBill, String> column3 = new TableColumn<>("Discount");
        column3.setCellValueFactory(new PropertyValueFactory<>("discountedAmount"));
        column3.setPrefWidth(100.0);

        TableColumn<FinalBill, String> column4 = new TableColumn<>("Amount");
        column4.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        column4.setPrefWidth(150.0);

        TableColumn<FinalBill, String> column5 = new TableColumn<>("Date");
        column5.setCellValueFactory(new PropertyValueFactory<>("billingDate"));
        column5.setPrefWidth(200.0);

        TableColumn<FinalBill, String> column6 = new TableColumn<>("Time");
        column6.setCellValueFactory(new PropertyValueFactory<>("billingTime"));
        column6.setPrefWidth(200.0);

        TableColumn<FinalBill, String> column7 = new TableColumn<>("Payment Mode");
        column7.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        column7.setPrefWidth(200.0);

        // Add columns to the TableView
        tableView.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7);

        // Set items to the TableView
        tableView.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));

        return tableView; // Return the TableView containing your data
    }

    public void getAllItems(){
        ObservableList<Product> values = FXCollections.observableArrayList(); // Use the appropriate type for your data
        String SQL = "SELECT * FROM Product";

        try(Connection connection = Database.connectDb();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet result = preparedStatement.executeQuery()) {

            while (result.next()) {
                Product product = new Product(
                        result.getInt("ID"),
                        result.getString("name"),
                        result.getDouble("rate")
                );

                values.add(product);
                productTable.setItems(values);
                colProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
                colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
                colProductPrice.setCellValueFactory(new PropertyValueFactory<>("productRate"));
            }



        } catch (Exception e){
            e.printStackTrace();
            utilityOperation.showMessage("Something Went Wrong, Try Again", "Alert !!!");}


    }

    public void onAddItemToDB(){

        if (addItemName.getText().matches("^[a-zA-Z ]+$") && addItemPrice.getText().matches("^[0-9]+(\\.[0-9]+)?$")) {
            String SQL = "INSERT INTO PRODUCT (name, rate)\n" +
                    "VALUES(?,?);";
            try(Connection connection = Database.connectDb();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

                preparedStatement.setString(1, addItemName.getText());
                preparedStatement.setDouble(2, Double.parseDouble(addItemPrice.getText()));

                if(preparedStatement.executeUpdate() == 1){
                    utilityOperation.showMessage(addItemName.getText() + " Added Successfully", "Success");
                    addItemName.clear();
                    addItemPrice.clear();
                    getAllItems();
                } else {
                    utilityOperation.showMessage("Please Try Again, Something Went Wrong", "Failure");

                }

            } catch (Exception e){
                e.printStackTrace();
                utilityOperation.showMessage("Something Went Wrong, Try Again", "Alert !!!");}
        } else {
            utilityOperation.showMessage("Please Add Item Name & Price Properly", "Alert!!!");
        }

    }


}