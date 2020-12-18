package projet.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import projet.java.model.Admin;
import projet.java.model.Produit;
import projet.java.model.Profil;
import projet.java.model.User;
import projet.utils.Fabrique;
import projet.utils.LoadView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import projet.utils.Utils;

public class ProfilController implements Initializable {

    @FXML
    private TextField nomtfd;

    @FXML
    private ComboBox<Admin> cbbadmin;
    @FXML
    private TableView<Profil> tableprofil;

    @FXML
    private TableColumn<Profil, Long> idclonne;

    @FXML
    private TableColumn<Profil, String> nomcolonne;

    @FXML
    private Button btnajout;

    @FXML
    private Button btnmodifier;

    @FXML
    private Button btnbloque;

    @FXML
    private Button btnchoir;


    @FXML
    void Ajout(ActionEvent event) {
        String nom = nomtfd.getText().toLowerCase();
        if(nom.trim().isEmpty()||cbbadmin.getSelectionModel().isEmpty()){
            Utils.remplirechamp();
        }else {
            try {
                Profil profil = new Profil();
                Admin admin = cbbadmin.getSelectionModel().getSelectedItem();
              profil.setNom(nom);
              profil.setAdmin(admin);


                   Profil profil1 = Fabrique.getiProfil().find(nom);
                   if(profil1 ==null){
                       Fabrique.getiProfil().add(profil);
                      Utils.insertionvalider();
                       viderchamps();
                   }else {
                       Utils.existe();
                   }


                affichetab();

            } catch (Exception e) {
                Utils.showMessage("Projet java", "Bichri infomatique", "Erreur : " + e.getMessage());
            }
        }

    }
    public void affichetab () {
        nomcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNom()));
        idclonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        try {
            tableprofil.setItems(FXCollections.
                    observableArrayList(Fabrique.getiProfil().findAll()));
        } catch (Exception e) {
            Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
        }


    }
    @FXML
    void click(MouseEvent event) {
        Profil profil=tableprofil.getSelectionModel().getSelectedItem();
        nomtfd.setText(profil.getNom());
    }
    @FXML
    void boloque(ActionEvent event) throws Exception {

        try {
            if (tableprofil.getSelectionModel().getSelectedIndex() == -1) {
              Utils.selectionElementsup();
            } else {


                Profil profil = new Profil();
                profil = tableprofil.getSelectionModel().getSelectedItem();
                int id = (int) profil.getId();

                Fabrique.getiProfil().delate(id);
                affichetab();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        viderchamps();


    }

    @FXML
    void select(ActionEvent event) {
        if (tableprofil.getSelectionModel().getSelectedIndex() == -1) {
            Utils.selectionElementsup();

        } else {
            Profil profil = tableprofil.getSelectionModel().getSelectedItem();

            nomtfd.setText(profil.getNom());
            System.out.println(profil.getNom().toLowerCase());


        }

    }

    @FXML
    void update(ActionEvent event) {
        String nom = nomtfd.getText().toLowerCase();
        if(nom.trim().isEmpty()||cbbadmin.getSelectionModel().isEmpty()){
            Utils.remplirechamp();
        }else {
            try {
                   long id =tableprofil.getSelectionModel().getSelectedItem().getId();



                    Admin admin = cbbadmin.getSelectionModel().getSelectedItem();
                   Profil profil = new Profil();
                     profil.setNom(nomtfd.getText().trim());
                     profil.setAdmin(cbbadmin.getValue());

                profil.setId(id);


                    Fabrique.getiProfil().updateprofil(profil);
                    Utils.modificationvalider();





                affichetab();


            } catch (Exception e) {
                Utils.showMessage("Projet", "bichri informatique", "Erreur : " + e.getMessage());
            }
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
       try {
           Admin admin =Fabrique.getIAdmin().verifiermail(LoginController.email);
           List<Admin>list = new ArrayList<>();
           list.add(admin);
          // List<Admin> listeadmin= Fabrique.getIAdmin().findAll();
           cbbadmin.setItems(FXCollections.observableArrayList(list));

        affichetab();
       }catch (Exception e){
           e.printStackTrace();
       }


    }
    public void Profile(ActionEvent actionEvent) {

    }

    public void loadProfil(ActionEvent actionEvent) {
    }

    public void loadUser(ActionEvent actionEvent) {
    }

    public void loadProduit(ActionEvent actionEvent) {
    }
    public void viderchamps () {

        nomtfd.setText(" ");
        //datetfd.setValue(LocalDate.parse(" "));
    }
    public boolean verifiermail(String mail){


        int index = mail.indexOf("@gmail.com");

        if(index == - 1) {
           return false;
        } else {
            return true;
        }

    }

    }

