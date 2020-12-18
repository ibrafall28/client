package projet.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserControlller implements Initializable {

        @FXML
        private TextField nomtfd;

        @FXML
        private TableView<User> tableuser;

        @FXML
        private TableColumn<User, Long> idcolonne;

        @FXML
        private TableColumn<User, String> nomcolonne;

        @FXML
        private TableColumn<User, String> prenomcolonn;

        @FXML
        private TableColumn<User, String> logincolonne;

        @FXML
        private TableColumn<User, String> profilcolonne;

        @FXML
        private Button btnAjout;

        @FXML
        private Button btnModifier;

        @FXML
        private Button btnbloque;

        @FXML
        private Button btnchoix;

        @FXML
        private TextField prenomtfd;

        @FXML
        private TextField adressetfd;

        @FXML
        private PasswordField pwdtfd;

        @FXML
        private ComboBox<Profil> cbbprofil;
        @FXML
        private ComboBox<Admin> cbbadmin;

        @FXML
        private TextField tfdrecherche;

        @FXML
        private Button btnrechercher;

        @FXML
        void add(ActionEvent event) {
                if (nomtfd.getText().trim().isEmpty() ||
                        prenomtfd.getText().trim().isEmpty() ||
                        adressetfd.getText().trim().isEmpty() ||
                        pwdtfd.getText().trim().isEmpty() ||
                        cbbprofil.getSelectionModel().isEmpty() ||
                        cbbadmin.getSelectionModel().isEmpty()) {
                        Utils.remplirechamp();
                } else {
                        try {
                                User user = new User();
                                Admin admin = cbbadmin.getSelectionModel().getSelectedItem();
                                Profil profil = cbbprofil.getSelectionModel().getSelectedItem();
                                user.setNom(nomtfd.getText().trim());
                                if (!nomberespace(adressetfd.getText())|| verifiermail(adressetfd.getText())) {
                                        Utils.showMessage("", "", "email incorrect !!!!");
                                } else {


                                        user.setLogin(adressetfd.getText().trim());
                                        user.setPassword(pwdtfd.getText().trim());
                                        user.setPrenom(prenomtfd.getText().trim());
                                        user.setProfil(profil);
                                        user.setAdmin(admin);


                                        User user1 = Fabrique.getiUser().find(adressetfd.getText());
                                        if (user1 == null) {
                                                Fabrique.getiUser().add(user);
                                                Utils.insertionvalider();
                                                viderchamps();
                                        } else {
                                                Utils.existe();
                                        }

                                         viderchamps();
                                        affichetab();
                                }
                                } catch(Exception e){
                                        Utils.showMessage("Projet java", "Bichri infomatique", "Erreur : " + e.getMessage());
                                }

                }


        }

        @FXML
        void boloque(ActionEvent event) {
                try {
                        if (tableuser.getSelectionModel().getSelectedIndex() == -1) {
                                Utils.selectionElementsup();
                        } else {


                                User user = new User();
                                user = tableuser.getSelectionModel().getSelectedItem();
                                int id = (int) user.getId();
                                user.setId(id);
                                user.setEtat(1);

                                Fabrique.getiUser().updateUser(user);
                                viderchamps();
                                affichetab();

                        }


                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        @FXML
        void rechercher(ActionEvent event) {

        }
        @FXML
        void click(MouseEvent event) {
                User user=tableuser.getSelectionModel().getSelectedItem();
                nomtfd.setText(user.getNom());
                prenomtfd.setText(user.getPrenom());
                pwdtfd.setText(user.getPassword());
                adressetfd.setText(user.getLogin());

        }

        @FXML
        void select(javafx.event.ActionEvent actionEvent) {
                if (tableuser.getSelectionModel().getSelectedIndex() == -1) {
                        Utils.selectionElementsup();

                } else {
                        User user = tableuser.getSelectionModel().getSelectedItem();

                        nomtfd.setText(user.getNom());
                        prenomtfd.setText(user.getPrenom());
                        adressetfd.setText(user.getLogin());
                        pwdtfd.setText(user.getPassword());


                }
        }

        @FXML
        void update(javafx.event.ActionEvent actionEvent) {
                if (nomtfd.getText().trim().isEmpty() ||
                        prenomtfd.getText().trim().isEmpty() ||
                        adressetfd.getText().trim().isEmpty() ||
                        pwdtfd.getText().trim().isEmpty() ||
                        cbbprofil.getSelectionModel().isEmpty() ||
                        cbbadmin.getSelectionModel().isEmpty()) {
                        Utils.remplirechamp();
                } else {
                        try {
                                User user = new User();
                                long id =tableuser.getSelectionModel().getSelectedItem().getId();
                                Admin admin = cbbadmin.getSelectionModel().getSelectedItem();
                                Profil profil = cbbprofil.getSelectionModel().getSelectedItem();
                                user.setNom(nomtfd.getText().trim());
                                user.setLogin(adressetfd.getText().trim());
                                if(!nomberespace(adressetfd.getText())){
                                                Utils.showMessage("", "", "email incorrect !!!!");

                                } else {


                                        user.setPassword(pwdtfd.getText().trim());
                                        user.setPrenom(prenomtfd.getText().trim());
                                        user.setProfil(profil);
                                        user.setAdmin(admin);
                                        user.setId(id);
                                        Fabrique.getiUser().updateUser(user);

                                       viderchamps();
                                        affichetab();
                                }

                        } catch (Exception e) {
                                Utils.showMessage("Projet java", "Bichri infomatique", "Erreur : " + e.getMessage());
                        }
                }


        }

        public void viderchamps() {

                nomtfd.setText(" ");
                prenomtfd.setText(" ");
                adressetfd.setText(" ");
                pwdtfd.setText(" ");
                //datetfd.setValue(LocalDate.parse(" "));
        }

        public void affichetab() {
                nomcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNom()));
                prenomcolonn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrenom()));
                logincolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLogin()));
                profilcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProfil().getNom()));
                idcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
                try {
                        tableuser.setItems(FXCollections.
                                observableArrayList(Fabrique.getiUser().findAll()));
                } catch (Exception e) {
                        Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
                }


        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                try {
                        Admin admin = Fabrique.getIAdmin().verifiermail(LoginController.email);
                        List<Admin> list = new ArrayList<>();
                        list.add(admin);
                        cbbadmin.setItems(FXCollections.observableArrayList(list));
                        List<Profil> listprofil = Fabrique.getiProfil().findAll();
                        cbbprofil.setItems(FXCollections.observableArrayList(listprofil));

                        affichetab();
                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        public boolean nomberespace(String mail) {
                int spaceCount = 0;
                for (char c : mail.toCharArray()) {
                        if (c == ' ') {
                                spaceCount++;
                        }
                }
                if(spaceCount== 0){
                        return true;
                }
          return false;
        }
        public boolean verifiermail(String mail){


                int index = mail.indexOf("@gmail.com");

                if(index == - 1) {
                        return true;
                }

                return false;

        }
}


