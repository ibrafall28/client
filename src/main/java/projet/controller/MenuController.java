package projet.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import projet.java.model.Admin;
import projet.java.model.User;
import projet.utils.Fabrique;
import projet.utils.LoadView;
import projet.utils.Outils;
import projet.utils.Utils;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
   private Menu admin;
    @FXML
    private MenuItem profile;
    @FXML
    private MenuItem produit;

    @FXML
    private MenuItem itmvente;

    @FXML
    private MenuItem itmstock;

    @FXML
    private MenuItem itmcommand;

    @FXML
    private MenuItem itmpersonne;

    @FXML
    private MenuItem itmentreorise;

    @FXML
    private MenuItem itmbilaninitial;

    @FXML
    private MenuItem itmbilanfinal;

    @FXML
    private MenuItem itemparametrage;
    @FXML
    private TextField datjourtfd;
    @FXML
    private Menu gestioncommande;
    @FXML
    private Menu prametrage;

    @FXML
    private Menu gestionfac;

    @FXML
    private Menu statistique;

    @FXML
    private Menu gestionvente;

    @FXML
    private MenuItem clientitem;
  ;
    @FXML
    private Button btnconncter;

    @FXML
    private TextField cletdf;
     public  String  tableprofil[] ={"commerciale","facturation","commande"};
    public enum tab {
        commerciale, facturation, commande
    }
    @FXML
    void logservice(ActionEvent event) {
        String cle = cletdf.getText().trim();
        if (cle.trim().isEmpty()) {
            Utils.showMessage("", " Bichri informatique", " identifient incorrecte");
        } else {



            }
        }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datjourtfd.setDisable(true);
        String date = Utils.dateDuJour();
        datjourtfd.setText(String.valueOf(date));

        /*admin.setDisable(true);
        gestionvente.setDisable(true);
        gestionfac.setDisable(true);
        gestioncommande.setDisable(true);
        statistique.setDisable(true);
        prametrage.setDisable(true);*/

        User user = null;
        Admin admin1 = null;
        System.out.println(tableprofil[0]);
        try {
            user = Fabrique.getiUser().find(LoginController.email);
            admin1 = Fabrique.getIAdmin().verifiermail(LoginController.email);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (user != null){
              if(user.getProfil().getId() == 1){
                  gestioncommande.setDisable(true);
                  gestionvente.setDisable(false);
                  admin.setDisable(true);
                  gestionfac.setDisable(true);
                  statistique.setDisable(true);
                  prametrage.setDisable(false);


              }else if(user.getProfil().getId() == 3){
                  gestioncommande.setDisable(false);
                  gestionvente.setDisable(true);
                  admin.setDisable(true);
                  gestionfac.setDisable(true);
                  statistique.setDisable(true);
                  prametrage.setDisable(false);


              }else if(user.getProfil().getId()== 2){
                  gestioncommande.setDisable(true);
                  gestionvente.setDisable(true);
                  admin.setDisable(true);
                  gestionfac.setDisable(false);
                  statistique.setDisable(false);
                  prametrage.setDisable(false);

              }



        }else if(admin1 != null){
            admin.setDisable(false);
            gestioncommande.setDisable(true);
            gestionvente.setDisable(true);
            admin.setDisable(true);
            admin.setDisable(false);
            gestionfac.setDisable(true);
            statistique.setDisable(true);
            prametrage.setDisable(false);
        }else {
            Utils.showMessage("","","votre compte est bloquer !!!!");
        }

    }



        @FXML
        private BorderPane btn;
    @FXML
    void loadclient(ActionEvent event) {
        try {
            Outils.loadSub(event,"/projet/view/Client.fxml","client");


        }catch (Exception exception){
            exception.printStackTrace();
        }


    }

        @FXML
        void loadProduit(ActionEvent event) {

            try {
                Outils.loadSub(event,"/projet/view/produit.fxml","produit");


            }catch (Exception exception){
                exception.printStackTrace();
            }

        }

        @FXML
        void loadProfil(ActionEvent event) {
            try {
                Outils.loadSub(event,"/projet/view/profil.fxml","profil");


            }catch (Exception exception){
                exception.printStackTrace();
            }

        }

        @FXML
        void loadUser(ActionEvent event) {
            try {
                Outils.loadSub(event,"/projet/view/user.fxml","user");


            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    @FXML
    void loadachat(ActionEvent event) {
        try {
            Outils.loadSub(event,"/projet/view/stock.fxml","stock");


        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @FXML
    void loadbilanfinal(ActionEvent event) {
        try {
            Outils.loadSub(event,"/projet/view/bilanFinal.fxml","bilant");


        }catch (Exception exception){
            exception.printStackTrace();
        }


    }

    @FXML
    void loadbilaninitial(ActionEvent event) {
        try {
            Outils.loadSub(event,"/projet/view/bilaninitial.fxml","bilantf");


        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    @FXML
    void loadcommand(ActionEvent event) {
        try {
            Outils.loadSub(event,"/projet/view/personne.fxml","commande");


        }catch (Exception exception){
            exception.printStackTrace();
        }

    }



    @FXML
    void loadfacturespersonne(ActionEvent event) {
        try {
            Outils.loadSub(event,"/projet/view/facturepersonne.fxml","produit");


        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    @FXML
    void loadparaetrages(ActionEvent event) {
        try {
            LoadView.showView("cv","login.fxml",1);

        }catch (Exception exception){
            exception.printStackTrace();
        }


    }

    @FXML
    void loadstock(ActionEvent event) {
        try {
            Outils.loadSub(event,"/projet/view/listeProdit.fxml","produit");


        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    }


