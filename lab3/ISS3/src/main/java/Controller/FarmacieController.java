package Controller;

import Domain.Comanda;
import Domain.Farmacist;
import Domain.ItemComanda;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class FarmacieController {
    public TableView<Comanda> commandTBL;
    public TableColumn<Comanda,String> deadlineCLMN;
    public TableColumn<Comanda,String> statusCLMN;
    public TableView<ItemComanda> selectedTBL;
    public TableColumn<ItemComanda,String> selectedTipCLMN;
    public TableColumn<ItemComanda,Integer> selectedCantCLMN;
    public Button onorareBTN;
    public ComboBox<String> filterCB;
    public Button filterBTN;
    public Button resetBTN;
    public TextField tipTF;
    public DatePicker deadlineDP;
    private Farmacist loggedInFarmacist;
    private Service service;

    ObservableList<Comanda> modelComanda = FXCollections.observableArrayList();
    ObservableList<ItemComanda> modelSelected = FXCollections.observableArrayList();
    ObservableList<String> modelFilter = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        deadlineCLMN.setCellValueFactory(new PropertyValueFactory<Comanda,String>("deadline"));
        statusCLMN.setCellValueFactory(new PropertyValueFactory<Comanda,String>("stringStatus"));
        selectedTipCLMN.setCellValueFactory(new PropertyValueFactory<ItemComanda,String>("tip"));
        selectedCantCLMN.setCellValueFactory(new PropertyValueFactory<ItemComanda,Integer>("cantitate"));

        commandTBL.setItems(modelComanda);
        selectedTBL.setItems(modelSelected);
        filterCB.setItems(modelFilter);
    }
    public void setLoggedInFarmacist(Farmacist angajat) {
        loggedInFarmacist=angajat;
    }

    public void setService(Service service) {
        this.service=service;
        initModel();
    }

    public void onHonor(ActionEvent actionEvent) {
        Comanda comanda = commandTBL.getSelectionModel().getSelectedItem();
        if(comanda!=null){
            service.onorareComanda(comanda);
            initModel();
        }
    }

    private void initModel() {
        modelComanda.setAll(service.getAllCommands());
        modelFilter.clear();
        modelFilter.add("Tip");
        modelFilter.add("Deadline");
    }

    public void onFilter(ActionEvent actionEvent) {
        String filter = filterCB.getSelectionModel().getSelectedItem();
        if(filter.equals("Tip")){
            modelComanda.setAll(service.filterByType(tipTF.getText()));
        }else { //Deadline
            modelComanda.setAll(service.filterByDeadline(deadlineDP.getValue().toString()));
        }
    }

    public void onReset(ActionEvent actionEvent) {
        initModel();
    }

    public void onSelect(MouseEvent mouseEvent) {
        Comanda comanda = commandTBL.getSelectionModel().getSelectedItem();
        if(comanda!=null) {
            modelSelected.setAll(service.getMedsOf(comanda.getId()));
        }
    }
}
