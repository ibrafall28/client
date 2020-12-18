package projet.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import projet.java.model.DetailFacture;
import projet.java.model.Entres;
import projet.java.model.Produit;
import projet.utils.Fabrique;
import projet.utils.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BilanController implements Initializable {



        @FXML
        private TextField libtdf;
        @FXML
        private TextField libelle;

         @FXML
        private TextField qtedisponibletfd;

        @FXML
        private TableView<Produit> tabeproduit;

        @FXML
        private TableColumn<Produit, Long> idcolonne;

        @FXML
        private TableColumn<Produit, String> libcolonne;

        @FXML
        private TableColumn<Produit, Long> pucolonne;

        @FXML
        private TableColumn<Produit, Long> stockcolonne;

        @FXML
        private TextField lobtfd;

         @FXML
        private TextField qtetfd;

        @FXML
        private Button btnRechercher;
    @FXML
    public void rechercher(ActionEvent actionEvent) {

        if(libtdf.getText().trim().isEmpty() || qtedisponibletfd.getText().trim().isEmpty()){
            Utils.remplirechamp();
        }else {
            Produit produit = null;
            try {
                String lib = libtdf.getText().toLowerCase();
                long stock = 0;
                long sortie = 0;
                long qt = Long.parseLong(qtedisponibletfd.getText());
                produit =Fabrique.getiProduit().find(lib);
                if(produit != null){
                    stock+= produit.getStock();


                    if(produit.getLibelle() == libtdf.getText() && stock == qt)
                        System.out.println(produit.getLibelle());
                    libelle.setText(produit.getLibelle());
                    qtetfd.setText(String.valueOf(stock));


                }else {
                    Utils.showMessage("","bichri informatique"," Stock non disponible !!!!");

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        affichetab();
          libelle.setDisable(true);
          qtetfd.setDisable(true);
    }
    public void affichetab() {
        idcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        libcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        pucolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrixunitiare()));


        try {
            tabeproduit.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().findAll()));
        } catch (Exception e) {
            Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
        }


    }
}
