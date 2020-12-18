package projet.controller;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import projet.java.model.*;
import projet.utils.Fabrique;
import projet.utils.Utils;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StockController implements Initializable {

        @FXML
        private TableView<Entres> tablestock;

        @FXML
        private TableColumn<Entres, Long> idcolonne;

        @FXML
        private TableColumn<Entres, String> libcolonne;

        @FXML
        private TableColumn<Entres, Date> datecolonne;

        @FXML
        private TableColumn<Entres, Long> pucolonne;

        @FXML
        private TableColumn<Entres, Long> qtecolonne;

        @FXML
        private TableColumn<Entres, Long> totalcolonne;

        @FXML
        private Button btnajouter;

        @FXML
        private Button btnupdate;

        @FXML
        private Button btnSupprimer;

        @FXML
        private Button btnchoisir;

        @FXML
        private ComboBox<Produit> cbbproduit;

        @FXML
        private TextField qtetfd;

        @FXML
        private DatePicker datetfd;

        @FXML
        private TextField putfd;
        @FXML
        private TextField totlaachatfd;
        @FXML
        private TextField nombretfd;

        @FXML
        private ComboBox<User> cbbuser;

        @FXML
        private  DatePicker dtfd;

        @FXML
        private Button btnrechercher;


        @FXML
        void add(ActionEvent event) {
                int stock =0;
                if(putfd.getText().trim().isEmpty()||
                        qtetfd.getText().trim().isEmpty()||datetfd.getValue()==null){
                        Utils.remplirechamp();
                }else {
                        try {
                                Entres entres = new Entres();
                                User user = cbbuser.getSelectionModel().getSelectedItem();
                                Produit produit = cbbproduit.getSelectionModel().getSelectedItem();
                                entres.setPrixunitiare(Long.parseLong(putfd.getText().trim()));
                                entres.setQuantite(Long.parseLong(qtetfd.getText().trim()));
                                java.sql.Date sqlDate = java.sql.Date.valueOf(datetfd.getValue());
                                if (!comparDate(sqlDate)){
                                        Utils.showMessage("","Bichri informatique","Date incorrect!!");
                                }else {
                                        entres.setDateentre(sqlDate);
                                        entres.setProduit(produit);
                                        String code = frmatcode(produit.getLibelle());
                                        entres.setCode(code);
                                        entres.setUser(user);
                                        long pu = Long.parseLong(putfd.getText());
                                        long qte = Long.parseLong(qtetfd.getText());
                                       if(pu < produit.getPrixunitiare()){
                                               long total = pu* qte;
                                               entres.setTotal(total);

                                               Fabrique.getiEntres().add(entres);
                                              stock+= produit.getStock()+qte;
                                              Produit produit1 = new Produit();
                                              produit1.setId(produit.getId());
                                              produit1.setLibelle(produit.getLibelle());
                                              produit1.setAdmin(produit.getAdmin());
                                              produit1.setCode(produit.getCode());
                                              produit1.setStock(stock);
                                              produit1.setPrixunitiare(produit.getPrixunitiare());
                                              Fabrique.getiProduit().update(produit1);
                                               Utils.insertionvalider();
                                               viderchamps();
                                       }else {
                                               Utils.showMessage("","","verifier le prix svp !!!");

                                       }

                                }
                                affichetab();

                        } catch (Exception e) {
                                Utils.showMessage("Projet java", "Bichri infomatique", "Erreur : " + e.getMessage());
                        }
                }


        }

        @FXML
        void delete(ActionEvent event) {
                try {
                        if (tablestock.getSelectionModel().getSelectedIndex() == -1) {
                                Utils.selectionElementsup();
                        } else {


                                Entres entres = new Entres();
                                entres = tablestock.getSelectionModel().getSelectedItem();
                                int id = (int) entres.getId();

                                Fabrique.getiEntres().delate(id);
                                affichetab();
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }

               viderchamps();

        }
        @FXML
        void click(MouseEvent event) {
                Entres entres=tablestock.getSelectionModel().getSelectedItem();
                putfd.setText(String.valueOf(entres.getPrixunitiare()));
                qtetfd.setText(String.valueOf(entres.getQuantite()));


        }
        @FXML
        void select(ActionEvent event) {
                totlaachatfd.setText( " ");
                nombretfd.setText( " ");
                affichetab();

        }

        @FXML
        void update(ActionEvent event) {

                if(putfd.getText().trim().isEmpty()||
                        qtetfd.getText().trim().isEmpty()){
                        Utils.remplirechamp();
                }else {
                        try {
                                long id =tablestock.getSelectionModel().getSelectedItem().getId();

                                long pu = Long.parseLong(putfd.getText());
                                long qte = Long.parseLong(qtetfd.getText());
                                long total = pu* qte;
                                Entres entres = new Entres();
                                User user = cbbuser.getSelectionModel().getSelectedItem();
                                Produit produit = cbbproduit.getSelectionModel().getSelectedItem();
                                entres.setPrixunitiare(Long.parseLong(putfd.getText().trim()));
                                entres.setQuantite(Long.parseLong(qtetfd.getText().trim()));
                                java.sql.Date sqlDate = java.sql.Date.valueOf(datetfd.getValue());
                                entres.setDateentre(sqlDate);
                                entres.setProduit(produit);
                                String code = frmatcode(produit.getLibelle());
                                entres.setCode(code);
                                entres.setUser(user);
                                entres.setTotal(total);
                                entres.setId(id);

                                Fabrique.getiEntres().update(entres);
                                Utils.insertionvalider();
                                viderchamps();

                                affichetab();

                        } catch (Exception e) {
                                Utils.showMessage("Projet java", "Bichri infomatique", "Prix ou qentite saisi incorrect  : ");
                        }
                }



        }
        @FXML
        void rechercher(ActionEvent event) {

                if (dtfd.getValue()== null) {
                        Utils.showMessage("","bichri informaique","merci de selectioner une date !!!");
                }else {


                        java.sql.Date sqlDate = java.sql.Date.valueOf(dtfd.getValue());
                        Date sqlDate1 = java.sql.Date.valueOf(dtfd.getValue());
                        rechercher(sqlDate);
                        double somm = 0;
                        long total = 0;
                        int i = 0;
                        try {
                                // somm = Fabrique.getiEntres().sommetotal();
                        } catch (Exception exception) {
                                exception.printStackTrace();
                        }
                        List<Entres> list = new ArrayList<>();
                        try {
                                list = Fabrique.getiEntres().findbyDate(sqlDate1);

                                for (Entres entres : list) {
                                        total += entres.getTotal();
                                        i++;
                                }
                        } catch (Exception exception) {
                                exception.printStackTrace();
                        }
                        totlaachatfd.setText(String.valueOf(total));
                        nombretfd.setText(String.valueOf(i));
                }
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                totlaachatfd.setDisable(true);
               nombretfd.setDisable(true);

                try {
                        User user = Fabrique.getiUser().find(LoginController.email);
                        List<User> list = new ArrayList<>();
                        list.add(user);
                        cbbuser.setItems(FXCollections.observableArrayList(list));
                        List<Produit>  liste= Fabrique.getiProduit().findAll();
                        cbbproduit.setItems(FXCollections.observableArrayList(liste));


                        affichetab();
                }catch (Exception e){
                        e.printStackTrace();
                }
        }
        public void affichetab () {
                libcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProduit().getLibelle()));
                qtecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantite()));
                pucolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrixunitiare()));
                idcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
                datecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDateentre()));
                totalcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTotal()));

                try {
                        tablestock.setItems(FXCollections.
                                observableArrayList(Fabrique.getiEntres().findAll()));
                } catch (Exception e) {
                        Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
                }


        }
        public String frmatcode(String libelle){

                String nom = "BI";
                String nom1 = libelle;
                String nom2 = nom1.substring(0,2);
                String code = nom+nom2;

                return code;
        }
        public void viderchamps () {

                putfd.setText(" ");
                qtetfd.setText(" ");
                datetfd.setValue(null);

                //datetfd.setValue(LocalDate.parse(" "));
        }
        public boolean comparDate(Date date){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                        Date date1 = sdf.parse(date.toString());
                        Date date2 = sdf.parse("2020-07-10");
                        if(date2.compareTo(date1)> 0 || date1.compareTo(date2)> 0){
                                return true;
                        }else
                                return false;
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                return false;
        }
        public boolean isnumerique(String libelle){


                if (libelle.matches("\\d+")){
                 return true;
                }else
                      return false;
        }
        public boolean alpha(String libelle){
               if( libelle.matches("[^a-zA-Z]*")){
                       return true;
               }else
                       return false;
        }

        public boolean alphanumerique(String libelle){
                if (libelle.indexOf("[A-Za-z]")!=-1)
                       return true;
                else
                      return false;
        }
        public void rechercher(Date date) {
                pucolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrixunitiare()));
                idcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
                datecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDateentre()));
                totalcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTotal()));
                try {
                        tablestock.setItems(FXCollections.observableArrayList(Fabrique.getiEntres().findbyDate(date)));
                } catch (Exception e) {
                        Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
                }
        }
        public int totalentre(long id){
                int somm= 0;
                List<DetailCommande>list1 = new ArrayList<>();
                try {
                        list1 = Fabrique.getiCommande().findbydetail(id);
                            List<Entres>entres = new ArrayList<>();
                                entres = Fabrique.getiEntres().findAllbystock(id);
                                for (Entres e:entres) {
                                        somm+= e.getQuantite();
                                }

                } catch (Exception exception) {
                        exception.printStackTrace();
                }

                return somm;
        }
        public int totalsortie(long id){
                int somm= 0;
                try { ;
                        List<DetailFacture>factures = new ArrayList<>();
                        factures = Fabrique.getiFacture().findbydetail(id);
                        for (DetailFacture d:factures) {

                        }

                } catch (Exception exception) {
                        exception.printStackTrace();
                }

                return somm;
        }
}


