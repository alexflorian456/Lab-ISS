package Controller;

import Domain.Comanda;
import Domain.ItemComanda;
import Domain.PM;
import Domain.StatusComanda;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SpitalController {
    public TableView<Comanda> commandTBL;
    public TableColumn<Comanda,String> deadlineCLMN;
    public TableColumn<Comanda,String> statusCLMN;
    public TableView<ItemComanda> selectedTBL;
    public TableColumn<ItemComanda,String> selectedTipCLMN;
    public TableColumn<ItemComanda,Integer> selectedCantCLMN;
    public TableView<ItemComanda> currentTBL;
    public TableColumn<ItemComanda,String> currentTipCLMN;
    public TableColumn<ItemComanda,Integer> currentCantCLMN;
    public Button delBTN;
    public ComboBox<String> tipCB;
    public DatePicker deadlineDP;
    public Button addBTN;
    public Spinner<Integer> cantSPN;
    public Button addMedBTN;
    public Button delMedBTN;
    public Button modBTN;
    public Button elimBTN;
    private Service service;
    private PM loggedInPM;

    ObservableList<Comanda> modelComanda = FXCollections.observableArrayList();
    ObservableList<ItemComanda> modelSelected = FXCollections.observableArrayList();
    ObservableList<ItemComanda> modelCurrent = FXCollections.observableArrayList();
    ObservableList<String> modelCombo =  FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        deadlineCLMN.setCellValueFactory(new PropertyValueFactory<Comanda,String>("deadline"));
        statusCLMN.setCellValueFactory(new PropertyValueFactory<Comanda,String>("stringStatus"));
        selectedTipCLMN.setCellValueFactory(new PropertyValueFactory<ItemComanda,String>("tip"));
        selectedCantCLMN.setCellValueFactory(new PropertyValueFactory<ItemComanda,Integer>("cantitate"));
        currentTipCLMN.setCellValueFactory(new PropertyValueFactory<ItemComanda,String>("tip"));
        currentCantCLMN.setCellValueFactory(new PropertyValueFactory<ItemComanda,Integer>("cantitate"));

        commandTBL.setItems(modelComanda);
        selectedTBL.setItems(modelSelected);
        currentTBL.setItems(modelCurrent);
        tipCB.setItems(modelCombo);
    }
    public void setService(Service service) {
        this.service = service;
        initModel();
    }

    private void initModel() {
        modelComanda.setAll(service.getCommandsOf(loggedInPM.getId()));
        modelCombo.setAll(service.getMedTypes());
    }

    public void setLoggedInPM(PM loggedInPM) {
        this.loggedInPM = loggedInPM;
    }

    public void onSelect(MouseEvent mouseEvent) {
        Comanda comanda = commandTBL.getSelectionModel().getSelectedItem();
        if(comanda!=null) {
            modelSelected.setAll(service.getMedsOf(comanda.getId()));
        }
    }

    public void onRetragere(ActionEvent actionEvent) {
        Comanda comanda = commandTBL.getSelectionModel().getSelectedItem();
        if(comanda!=null) {
            service.removeCommand(comanda);
            initModel();
        }
    }

    public void onAddMed(ActionEvent actionEvent) {
        ItemComanda itemComanda = new ItemComanda(0,
                cantSPN.getValue(),
                tipCB.getSelectionModel().getSelectedItem()
        );
        modelCurrent.add(itemComanda);
    }

    public void onDelMed(ActionEvent actionEvent) {
        ItemComanda itemComanda = currentTBL.getSelectionModel().getSelectedItem();
        modelCurrent.remove(itemComanda);
    }

    public void onAdd(ActionEvent actionEvent) {
        service.addCommand(modelCurrent,deadlineDP.getValue().toString(),loggedInPM.getId());
        initModel();
    }

    public void onMod(ActionEvent actionEvent) {
        Comanda comanda = commandTBL.getSelectionModel().getSelectedItem();
        if(comanda!=null){
            service.modCommand(comanda,modelCurrent,deadlineDP.getValue().toString(),loggedInPM.getId());
            initModel();
        }
    }

    public void onElim(ActionEvent actionEvent) {
        Comanda comanda = commandTBL.getSelectionModel().getSelectedItem();
        if(comanda!=null) {
            if(comanda.getStatus()==StatusComanda.ONORATA) {
                service.removeCommand(comanda);
                initModel();
            }
        }
    }
}
