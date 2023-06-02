package com.example.iss3;

import Controller.LoginController;
import Repository.JDBC.AngajatJDBCRepo;
import Repository.JDBC.ComandaJDBCRepo;
import Repository.JDBC.MedicamentJDBCRepo;
import Repository.ORM.AngajatORMRepo;
import Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AngajatORMRepo angajatRepo = new AngajatORMRepo();
        /*AngajatJDBCRepo angajatRepo = new AngajatJDBCRepo();*/
        ComandaJDBCRepo comandaRepo = new ComandaJDBCRepo();
        MedicamentJDBCRepo medicamentRepo = new MedicamentJDBCRepo();
        Service service = new Service(angajatRepo,comandaRepo,medicamentRepo);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}