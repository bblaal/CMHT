package com.cmht.main;

import com.cmht.utility.UtilityOperation;
import com.db.utils.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginView {

    private PreparedStatement prepare;
    private ResultSet result;

    public TextField loginField;
    public TextField passwordField;

    UtilityOperation utilityOperation = new UtilityOperation();


    @FXML
    public void initialize() {

    }

    public void onLoginToApplication(){

        try{
            String SQL = "SELECT * FROM ADMIN WHERE USERNAME = ? AND PASSWORD = ?";
            Connection connect = Database.connectDb();
            prepare = connect.prepareStatement(SQL);
            prepare.setString(1, loginField.getText());
            prepare.setString(2, passwordField.getText());
            result = prepare.executeQuery();

            if (result.next()) {
                // Close the login view
                Stage loginStage = (Stage) loginField.getScene().getWindow();
                loginStage.close();

                // Load and show the main view
                FXMLLoader mainLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
                Parent mainRoot = mainLoader.load();
                MainController mainController = mainLoader.getController();
                mainController.initialize();

                Stage mainStage = new Stage();
                int[] screenRatioArray = utilityOperation.getScreenRatio();
                mainStage.setScene(new Scene(mainRoot, screenRatioArray[0], screenRatioArray[1]));
                mainStage.setTitle("Point of Sale - Champaran Meat House & Tandoor");
                mainStage.show();
            } else {
                utilityOperation.showMessage("Invalid Username or Password", "Login Failed !!!");
            }
        } catch (Exception e){e.printStackTrace();}
    }

    public void onCancelLogin(){
        // Close the login view
        Stage loginStage = (Stage) loginField.getScene().getWindow();
        loginStage.close();
    }


}
