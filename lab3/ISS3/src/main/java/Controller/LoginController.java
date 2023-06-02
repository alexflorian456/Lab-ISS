package Controller;

import Domain.Angajat;
import Domain.Farmacist;
import Domain.PM;
import Service.Service;
import com.example.iss3.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Button loginBTN;
    public TextField usernameTF;
    public PasswordField passwordTF;
    public Label errLBL;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    public void onLogin(ActionEvent actionEvent) {
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        Angajat angajat = service.login(username,password);
        if(angajat!=null) {
            if (angajat instanceof PM) {
                Stage stage = (Stage) loginBTN.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("spital.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 678, 400);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SpitalController spitalController = fxmlLoader.getController();
                spitalController.setLoggedInPM((PM) angajat);
                spitalController.setService(service);
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.show();
            }
            if (angajat instanceof Farmacist) {
                Stage stage = (Stage) loginBTN.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("farmacie.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 702, 400);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FarmacieController farmacieController = fxmlLoader.getController();
                farmacieController.setLoggedInFarmacist((Farmacist) angajat);
                farmacieController.setService(service);
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.show();
            }
        }else {
           errLBL.setText("userpass not found");
        }
    }
}
