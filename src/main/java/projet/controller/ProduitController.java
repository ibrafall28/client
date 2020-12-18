package projet.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import projet.java.model.Admin;
import projet.java.model.Produit;
import projet.java.model.Profil;
import projet.java.model.User;
import projet.utils.Fabrique;
import projet.utils.Utils;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {

    @FXML
    private TableView<Produit> tabproduit;

    @FXML
    private TableColumn<Produit, Long> idcolonne;

    @FXML
    private TableColumn<Produit, String> codecolonne;

    @FXML
    private TableColumn<Produit, String> libcolonne;

    @FXML
    private TableColumn<Produit, Long> pucolonne;

    @FXML
    private Button btnajoutt;

    @FXML
    private Button btnupdate;

    @FXML
    private Button suprimerbtn;

    @FXML
    private Button btnchoisir;

    @FXML
    private TextField recherchetfd;

    @FXML
    private Button btnrechercher;

    @FXML
    private TextField libproduit;

    @FXML
    private TextField prixproduit;


    @FXML
    private ComboBox<Admin> cbbadmin;

    @FXML
    void add(ActionEvent event) {
        String libelle = libproduit.getText().toLowerCase();
        String code = frmatcode(libelle);
        if (libproduit.getText().trim().isEmpty() ||
                prixproduit.getText().trim().isEmpty() ||
                cbbadmin.getSelectionModel().isEmpty()) {
            Utils.remplirechamp();
        } else {
            try {
                Produit produit = new Produit();
                Admin admin = cbbadmin.getSelectionModel().getSelectedItem();
                produit.setLibelle(libelle);
                produit.setPrixunitiare(Long.parseLong(prixproduit.getText().trim()));
                produit.setAdmin(admin);
                produit.setCode(code);
                Produit produit1 = Fabrique.getiProduit().find(libproduit.getText());
                if (produit1 == null) {
                    Fabrique.getiProduit().add(produit);
                    Utils.insertionvalider();
                    viderchamps();
                } else {
                    Utils.existe();
                }
                viderchamps();

                affichetab();

            } catch (Exception e) {
                Utils.showMessage("Projet java", "Bichri infomatique", "Erreur : " + e.getMessage());
            }
        }


    }

    @FXML
    void delate(ActionEvent event) {
        try {
            if (tabproduit.getSelectionModel().getSelectedIndex() == -1) {
                Utils.selectionElementsup();
            } else {


                Produit produit = new Produit();
                produit = tabproduit.getSelectionModel().getSelectedItem();
                int id = (int) produit.getId();

                Fabrique.getiProduit().delate(id);
                affichetab();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        viderchamps();


    }

    @FXML
    void rechercher(ActionEvent event) {

    }
    @FXML
    void click(MouseEvent event) {
        Produit produit=tabproduit.getSelectionModel().getSelectedItem();
         libproduit.setText(produit.getLibelle());
         prixproduit.setText(String.valueOf(produit.getPrixunitiare()));


    }
    @FXML
    void select(ActionEvent event) {
        if (tabproduit.getSelectionModel().getSelectedIndex() == -1) {
            Utils.selectionElementsup();

        } else {
            Produit produit = tabproduit.getSelectionModel().getSelectedItem();

            libproduit.setText(produit.getLibelle());
            prixproduit.setText(String.valueOf(produit.getPrixunitiare()));


        }

    }

    @FXML
    void update(ActionEvent event) {
        String libelle = libproduit.getText().toLowerCase();
        String code = frmatcode(libelle);
        if (libelle.trim().isEmpty() || prixproduit.getText().trim().isEmpty() ||
                cbbadmin.getSelectionModel().isEmpty()) {
            Utils.remplirechamp();
        } else {
            try {
                Produit produit = new Produit();
                long id =tabproduit.getSelectionModel().getSelectedItem().getId();
                try {
                    produit.setLibelle(libproduit.getText());
                    produit.setCode(code);
                    produit.setAdmin(cbbadmin.getValue());
                    produit.setPrixunitiare(Long.parseLong(prixproduit.getText()));
                    produit.setId(id);

                Produit produit1 = Fabrique.getiProduit().find(libelle);


                    Fabrique.getiProduit().update(produit);
                    Utils.modificationvalider();


              viderchamps();

                affichetab();

                } catch (Exception e) {
                    e.printStackTrace();
                        }

            } catch (Exception e) {
                Utils.showMessage("Projet", "bichri informatique", "Erreur : " + e.getMessage());
            }
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Admin admin = Fabrique.getIAdmin().verifiermail(LoginController.email);
            List<Admin> list = new ArrayList<>();
            list.add(admin);
            // List<Admin> listeadmin= Fabrique.getIAdmin().findAll();
            cbbadmin.setItems(FXCollections.observableArrayList(list));

            affichetab();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void viderchamps() {

        libproduit.setText(" ");
        prixproduit.setText(" ");

        //datetfd.setValue(LocalDate.parse(" "));
    }

    public void affichetab() {
        libcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        codecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCode()));
        pucolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrixunitiare()));
        idcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        try {
            tabproduit.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().findAll()));
        } catch (Exception e) {
            Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
        }


    }

    public String frmatcode(String libelle) {

        String nom = "BI";
        String nom1 = libelle;
        String nom2 = nom1.substring(0, 2);
        String code = nom + nom2;

        return code;
    }

    public void rechercher(String libelle) {
        libcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCode()));
        codecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        pucolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrixunitiare()));
        idcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        try {
            tabproduit.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().find(libelle)));
        } catch (Exception e) {
            Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
        }
    }
}

