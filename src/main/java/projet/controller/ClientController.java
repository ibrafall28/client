package projet.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import projet.java.model.*;
import projet.utils.Clien;
import projet.utils.Fabrique;
import projet.utils.Utils;

public class ClientController implements Initializable {

    @FXML
    private TableView<Clien> tableclient;

    @FXML
    private TableColumn<Clien, String> numfaccolonne;

    @FXML
    private TableColumn<Clien, String> nomclientcolonne;

    @FXML
    private TableColumn<Clien, String> adressecolonne;

    @FXML
    private TableColumn<Clien, String> telcolonne;

    @FXML
    private Button btnenrigisrer;

    @FXML
    private Button btnmodifier;

    @FXML
    private Button btndelate;

    @FXML
    private Button btnchoix;

    @FXML
    private TextField recherchertdf;

    @FXML
    private Button btnrechercher;

    @FXML
    private TextField cnitfd;

    @FXML
    private TextField nomtfd;

    @FXML
    private TextField prenomtfd;

    @FXML
    private TextField adressetfd;

    @FXML
    private TextField teltfd;

    @FXML
    private ComboBox<User> cbbgestionnaire;
    @FXML
    private ComboBox<String> cbbtype;

    @FXML
    private TextField codetfd;
    private String type = "personne";
    private String type1 = "entreprise";

    @FXML
    void delete(ActionEvent event) {
       /* try {
            if (tableclient.getSelectionModel().getSelectedIndex() == -1) {
                Utils.selectionElementsup();
            } else {
                Clien clien = new Clien();
                clien = tableclient.getSelectionModel().getSelectedItem();
                Client client = new Client();
                client = Fabrique.getiClient().find(client.getNom());

                if (client != null) {
                    int id = (int) client.getId();
                    Fabrique.getiClient().delate(id);
                    viderchamps();
                    affichtabe();

                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    @FXML
    void rechercher(ActionEvent event) {
      /*  if (recherchertdf.getText().trim().isEmpty()) {
            Utils.remplirechamp();
        } else {
            String numero = recherchertdf.getText();
            rechercher(numero);


        }*/

    }

    @FXML
    void save(ActionEvent event) {

                    /* if(verfierchamps(cnitfd.getText(),nomtfd.getText(),prenomtfd.getText(),adressetfd.getText(),teltfd.getText())==0 && verfierchamps1(codetfd.getText())==1)
                     {
                             String code = frmatcode(nomtfd.getText(),teltfd.getText());
                         Client client = new Client();
                         User user = cbbgestionnaire.getSelectionModel().getSelectedItem();

                         client.setNom(nomtfd.getText());
                         client.setAdresse(adressetfd.getText());
                         client.setTelephone(teltfd.getText());
                         client.setUser(user);
                         client.setCodePostal(codetfd.getText());
                         client.setNumero(code);
                         client.setPrenom(prenomtfd.getText());
                         try {
                             Fabrique.getiClient().add(client);
                             Utils.insertionvalider();
                             viderchamps();
                             affichtabe();
                         }catch (Exception e){
                             e.printStackTrace();
                         }


                     }else if (verfierchamps2(cnitfd.getText(),prenomtfd.getText())==1 && verfierchamps1(codetfd.getText())==0){




                     }else {
                             Utils.remplirechamp();
                     }*/


    }


    @FXML
    void click(MouseEvent event) {
             /* try {
                      Clien clien = tableclient.getSelectionModel().getSelectedItem();
                      Entreprise entreprise = Fabrique.getiEntreprise().find(clien.getNom());
                      Personne personne = Fabrique.getIPersonne().find(clien.getNom());
                      if(personne != null){
                              nomtfd.setText(personne.getNom());
                              adressetfd.setText(personne.getAdresse());
                              teltfd.setText(personne.getTelephone());
                              prenomtfd.setText(personne.getPrenom());
                              cnitfd.setText(personne.getCni());
                              codetfd.setText("");

                              System.out.println(1);
                      } else if(entreprise !=null){
                              nomtfd.setText(entreprise.getNom());
                              adressetfd.setText(entreprise.getAdresse());
                              teltfd.setText(entreprise.getTelephone());
                              codetfd.setText(entreprise.getCodePostal());
                              cnitfd.setText("");
                              prenomtfd.setText("");
                              System.out.println(0);

                      }

              }catch (Exception e){
                      e.printStackTrace();
              }*/

    }

    @FXML
    void select(ActionEvent event) {
        //affichtabe();

    }

    @FXML
    void update(ActionEvent event) {
               /* Clien clien = tableclient.getSelectionModel().getSelectedItem();
                if(verfierchamps(cnitfd.getText(),nomtfd.getText(),prenomtfd.getText(),adressetfd.getText(),teltfd.getText())==0 && verfierchamps1(codetfd.getText())==1){
                        String code = frmatcode(nomtfd.getText(),teltfd.getText());
                        try {
                                        Personne personne1 = Fabrique.getIPersonne().find(clien.getNom());
                                        User user = cbbgestionnaire.getSelectionModel().getSelectedItem();
                                        Personne personne = new Personne();
                                        personne.setId(personne1.getId());
                                        personne.setCni(cnitfd.getText());
                                        personne.setPrenom(prenomtfd.getText());
                                        personne.setNom(nomtfd.getText());
                                        personne.setAdresse(adressetfd.getText());
                                        personne.setTelephone(teltfd.getText());
                                        personne.setUser(user);
                                        personne.setNumero(code);
                                Fabrique.getIPersonne().update(personne);
                                Utils.modificationvalider();
                                viderchamps();
                                affichtabe();
                        }catch (Exception e){
                                e.printStackTrace();
                        }

                }else if (verfierchamps2(cnitfd.getText(),prenomtfd.getText())==1 && verfierchamps1(codetfd.getText())==0){

                        try {
                                Entreprise entreprise1 = Fabrique.getiEntreprise().find(clien.getNom());
                                String code = frmatcode(nomtfd.getText(),teltfd.getText());
                                User user = cbbgestionnaire.getSelectionModel().getSelectedItem();
                               Entreprise entreprise = new Entreprise();
                               entreprise.setId(entreprise1.getId());
                                entreprise.setNom(nomtfd.getText());
                                entreprise.setAdresse(adressetfd.getText());
                                entreprise.setTelephone(teltfd.getText());
                                entreprise.setUser(user);
                                entreprise.setCodePostal(codetfd.getText());
                                entreprise.setNumero(code);
                                entreprise.setId(entreprise1.getId());
                                Fabrique.getiEntreprise().update(entreprise);
                                Utils.insertionvalider();
                                viderchamps();
                                affichtabe();
                        }catch (Exception e){
                                e.printStackTrace();
                        }


                }else {
                        Utils.remplirechamp();
                }



        }


        public void rechercher(String numero) {
                List<Clien> list = new ArrayList<>();
                List<Personne> list1 = new ArrayList<>();
                List<Entreprise> list2 = new ArrayList<>();
                try {
                        ;

                } catch (Exception exception) {
                        exception.printStackTrace();
                }

                nomclientcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNom()));
                numfaccolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumero()));
                adressecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAdresse()));
                telcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTelephone()));
                try {

                               tableclient.setItems(FXCollections.observableArrayList(affichagerechercher(list1,list2,numero)));



                } catch (Exception e) {
                        Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
                }
        }
    @Override
    public void initialize(URL location, ResourceBundle resources) {



            try {




                    User user = Fabrique.getiUser().find(LoginController.email);
                    List<User> list = new ArrayList<>();
                    list.add(user);
                    cbbgestionnaire.setItems(FXCollections.observableArrayList(list));
                    affichtabe();
            }catch (Exception e){
                    e.printStackTrace();
            }


    }
        public String frmatcode(String name,String tel) {

                String nom = "411";
                String nom1 =name;
                String nom2 = nom1.substring(0, 2);
                String nom3 = tel.substring(0, 4);
                String code = nom + nom2+nom3;

                return code;
        }
        public int verfierchamps(String nom,String prenom,String adresse,String tel,String cni){
                if(nom.trim().isEmpty() ||
                        prenom.trim().isEmpty() ||
                        adresse.trim().isEmpty()||
                        cni.trim().isEmpty()||
                        tel.trim().isEmpty() ){
                        return 1;
                }else
                return 0;

        }
        public int verfierchamps1(String code){
                if(code.trim().isEmpty() ){
                        return 1;
                }else
                        return 0;


        }
        public int verfierchamps2(String code,String prenom){
                if(code.trim().isEmpty() && prenom.trim().isEmpty() ){
                        return 1;
                }else
                        return 0;

        }

        public void viderchamps() {

                nomtfd.setText(" ");
                prenomtfd.setText(" ");
                adressetfd.setText(" ");
                cnitfd.setText(" ");
                teltfd.setText(" ");
                cnitfd.setText(" ");
                codetfd.setText("");

                //datetfd.setValue(LocalDate.parse(" "));
        }
        public List<Clien> affichage(List<Personne>personneList,List<Entreprise>entreprises){
                List<Clien> list = new ArrayList<>();
               try {
                       personneList =Fabrique.getIPersonne().findAll();
                       for (Personne personne:personneList) {
                            Clien clien = new Clien();
                            clien.setNom(personne.getNom());
                            clien.setAdresse(personne.getAdresse());
                            clien.setNumero(personne.getNumero());
                            clien.setTelephone(personne.getTelephone());
                            list.add(clien);

                       }
                       entreprises =Fabrique.getiEntreprise().findAll();
                       for (Entreprise entreprise:entreprises) {
                               Clien clien = new Clien();
                               clien.setNom(entreprise.getNom());
                               clien.setNumero(entreprise.getNumero());
                               clien.setAdresse(entreprise.getAdresse());
                               clien.setTelephone(entreprise.getTelephone());
                               list.add(clien);

                       }

               }catch (Exception e){
                       e.printStackTrace();
               }
                 return list;
        }
        public void affichtabe(){
                try {
                        List<Personne> personneList = new ArrayList<>();
                        personneList= Fabrique.getIPersonne().findAll();
                        List<Entreprise> entrepriseList = new ArrayList<>();
                        entrepriseList= Fabrique.getiEntreprise().findAll();
                        List<Clien> cliens = new ArrayList<>();
                        cliens = affichage(personneList,entrepriseList);
                        nomclientcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNom()));
                        numfaccolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumero()));
                        adressecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAdresse()));
                        telcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTelephone()));

                        try {
                                tableclient.setItems(FXCollections.
                                        observableArrayList(affichage(personneList,entrepriseList)));
                        } catch (Exception e) {
                                Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
                        }
                }catch (Exception e){
                        e.printStackTrace();
                }
        }
        public List<Clien> affichagerechercher(List<Personne>personneList,List<Entreprise>entreprises,String numero){
                List<Clien> list = new ArrayList<>();
                try {
                        entreprises =Fabrique.getiEntreprise().findbynumero(numero);
                        personneList =Fabrique.getIPersonne().findnynumero(numero);
                        if(personneList!=null){
                                for (Personne personne:personneList) {
                                        Clien clien = new Clien();
                                        clien.setNom(personne.getNom());
                                        clien.setAdresse(personne.getAdresse());
                                        clien.setNumero(personne.getNumero());
                                        clien.setTelephone(personne.getTelephone());
                                        list.add(clien);
                                        return list;

                                }

                        }else if(entreprises !=null){
                                for (Entreprise entreprise:entreprises) {
                                        Clien clien = new Clien();
                                        clien.setNom(entreprise.getNom());
                                        clien.setNumero(entreprise.getNumero());
                                        clien.setAdresse(entreprise.getAdresse());
                                        clien.setTelephone(entreprise.getTelephone());
                                        list.add(clien);
                                        return list;

                                }
                        }else {
                                Utils.erreur();
                        }




                }catch (Exception e){
                        e.printStackTrace();
                }
                return list;
        }*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
