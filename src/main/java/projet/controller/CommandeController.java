package projet.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import projet.java.model.*;
import projet.utils.Achat;
import projet.utils.Clien;
import projet.utils.Fabrique;
import projet.utils.Utils;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
   /* @FXML
    private ComboBox<?> cbbproduit;

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
    private TextField qtetfd;

    @FXML
    private TextField putdf;

    @FXML
    private ComboBox<?> cbbgestionnaire;

    @FXML
    private TableView<Achat1> tableachat;

    @FXML
    private TableColumn<Achat1, String> libcolonneach;

    @FXML
    private TableColumn<Achat1, Long> qtecolonnacha;

    @FXML
    private Button btnajout;

    @FXML
    private TextField codepostaltfd;

    @FXML
    private Button btnmodifier;

    @FXML
    private Button btnsuprimer;

    @FXML
    private Button btnchoisir;

    @FXML
    private TextField descriptiontfd;

    @FXML
    private DatePicker datetfd;

    @FXML
    private ComboBox<User> cbbgesinnaire;

    @FXML
    private ComboBox<Clien> client;

    @FXML
    private TextField qteacheter;

    @FXML
    private TableView<Commande> tablecommande;

    @FXML
    private TableColumn<Commande, String> numcolonne;

    @FXML
    private TableColumn<Commande, String> nomcolonne;

    @FXML
    private TableColumn<Commande, java.util.Date> datecolonne;

    @FXML
    private TableColumn<Commande, String> etatcolonne;
    @FXML
    private Button btnchoix;
    @FXML
    private Button btnrechercher;

    @FXML
    private TableColumn<Achat1, String> libachacolonne;

    @FXML
    private TableColumn<Achat1, String> qteachatcolonnz;

    @FXML
    void produitselect(ActionEvent event) {
         if(cbbproduit.getSelectionModel().isEmpty() || qteacheter.getText().trim().isEmpty()nom.trim().isEmpty() ||
                .trim().isEmpty() ||
                adresse.trim().isEmpty()||
                cni.trim().isEmpty()||
                tel.trim().isEmpty()){
             Utils.remplirechamp();
         }else {
             Produit produit = cbbproduit.getSelectionModel().getSelectedItem();
             Achat1 achat = new Achat1();
             achat.setLibelle(produit.getLibelle());
             achat.setQte(qteacheter.getText());
               try {
                   Fabrique.getIAdmin().addAchat(achat);
                   affich();
               }catch (Exception e){
                   e.printStackTrace();
               }



         }





    }
    @FXML
    void rechercher(ActionEvent event) {

    }
     public void vider(){
        qteacheter.setText("");
     }
    @FXML
    void delate(ActionEvent event) {
        try {
            if (tablecommande.getSelectionModel().getSelectedIndex() == -1) {
                Utils.selectionElementsup();
            } else {


                Commande commande = new Commande();
                commande = tablecommande.getSelectionModel().getSelectedItem();
                int id = (int) commande.getId();

                Fabrique.getiCommande().delate(id);
                if(Fabrique.getiCommande().Supprimer(id)){
                    System.out.println(1);
                }else {
                    System.out.println(0);
                }
                affichetab();

            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        //    viderchamps();



    }

    @FXML
    void save(ActionEvent event) {
        Document document = new Document();
       // pdf(document);
        Commande commande = new Commande();
        Commande commande1 = new Commande();
        String code = "non";
        if (datetfd.getValue()==null ||
                cbbgesinnaire.getSelectionModel().isEmpty()||
                client.getSelectionModel().isEmpty() ) {
            Utils.remplirechamp();

        }else {
            List<Achat1> produit = tableachat.getItems();
              User  user = cbbgesinnaire.getSelectionModel().getSelectedItem();
           Clien  clien = client.getSelectionModel().getSelectedItem();
           Personne personne = null;
           Entreprise entreprise = null;
            java.sql.Date sqlDate = java.sql.Date.valueOf(datetfd.getValue());
           try {
               personne = Fabrique.getIPersonne().find(clien.getNom());
               entreprise = Fabrique.getiEntreprise().find(clien.getNom());
               if (!produit.isEmpty()) {


                   if (personne != null) {

                       String numero = frmatcode(sqlDate, clien.getNom());
                       commande.setNumero(numero);
                       commande.setDateLivraision(sqlDate);
                       commande.setEtat(code);
                       commande.setUser(user);
                       commande.setPersonne(personne);
                       commande1 = Fabrique.getiCommande().add(commande);
                       affichetab();
                       List<DetailCommande> list = new ArrayList<>();
                       for (Achat1 p : produit) {
                           DetailCommande detailCommande = new DetailCommande();
                           DetailCommande detailCommande1 = new DetailCommande();
                           String cod = commande1.getNumero() + p.getLibelle();
                           detailCommande.setProduit(Fabrique.getiProduit().find(p.getLibelle()));
                           detailCommande.setQuentiteCommande(Long.parseLong(p.getQte()));
                           detailCommande.setCommande(commande1);
                           detailCommande.setCommande_produit(cod);
                           detailCommande1 = Fabrique.getiCommande().ajout(detailCommande);
                           list.add(detailCommande);
                           String nom = personne.getNom() + personne.getPrenom();
                           String s = String.valueOf(commande1.getId());

                           try {


                               //PdfWriter.getInstance(document, new FileOutputStream("C:/facture/"+nom+"+"+s+".pdf"));

                               PdfWriter.getInstance(document, new FileOutputStream("C:/facture/" + nom + ".pdf"));


                               document.open();
                               document.add(titre());
                               document.add(table1(sqlDate, commande1.getNumero()));
                               document.add(premierTableau2(clien.getNom(), clien.getTelephone(), clien.getAdresse()));
                               document.add(tableCommande(commande1.getId()));

                           } catch (DocumentException e) {
                               e.printStackTrace();
                           } catch (FileNotFoundException e) {
                               e.printStackTrace();
                           }

                           Fabrique.getIAdmin().delate();
                       }
                       document.close();

                   } else {
                       String numero = frmatcode(sqlDate, clien.getNom());
                       commande.setNumero(numero);
                       commande.setDateLivraision(sqlDate);
                       commande.setEtat(code);
                       commande.setUser(user);
                       commande.setEntreprise(entreprise);
                       commande1 = Fabrique.getiCommande().add(commande);
                       List<DetailCommande> list = new ArrayList<>();
                       for (Achat1 p : produit) {
                           DetailCommande detailCommande = new DetailCommande();
                           String cod = commande1.getNumero() + p.getLibelle();
                           detailCommande.setProduit(Fabrique.getiProduit().find(p.getLibelle()));
                           detailCommande.setQuentiteCommande(Long.parseLong(p.getQte()));
                           detailCommande.setCommande(commande1);
                           detailCommande.setCommande_produit(cod);
                           Fabrique.getiCommande().ajout(detailCommande);
                           String nom = entreprise.getNom();
                           try {
                               PdfWriter.getInstance(document, new FileOutputStream("C:/facture/" + nom + ".pdf"));
                               document.open();
                               document.add(titre());
                               document.add(table1(sqlDate, commande1.getNumero()));
                               document.add(premierTableau2(clien.getNom(), clien.getTelephone(), clien.getAdresse()));
                               document.add(tableCommande(commande1.getId()));
                           } catch (DocumentException e) {
                               e.printStackTrace();
                           } catch (FileNotFoundException e) {
                               e.printStackTrace();
                           }


                           Fabrique.getIAdmin().delate();

                       }
                       document.close();
                   }
                   }else {
                   Utils.remplirechamp();
               }
               }catch(Exception e){
                   e.printStackTrace();
               }

               affich();



        }

    }

    @FXML
    void select(ActionEvent event) {
        if (tablecommande.getSelectionModel().getSelectedIndex() == -1) {
            Utils.selectionElementsup();

        } else {
            Commande commande = tablecommande.getSelectionModel().getSelectedItem();

        }
    }



    @FXML
    void update(ActionEvent event) {

    }
    public void pdf(Document document,String nomcommande,List<DetailCommande> list) {


        try {
            PdfWriter.getInstance(document, new FileOutputStream("C:/facture/"+nomcommande+".pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();

        PdfPTable table = new PdfPTable(3);

        try {




            PdfPCell c1 = new PdfPCell(new Phrase("Designation"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Quentite Commander"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Date de livraision"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            for (DetailCommande d:list) {
                String nom = d.getCommande_produit();
                int  de = (int) d.getQuentiteCommande();
                String pe = String.valueOf(d.getCommande_produit());
                table.addCell(nom);
                table.addCell(String.valueOf(de));
                table.addCell(pe);


            }
              // document.add(premierTableau2());
            document.add(table);
             document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public PdfPTable table1(Date date,String num) {

        PdfPTable table = new PdfPTable(2);

        try {

            PdfPCell c1 = new PdfPCell(new Phrase(String.valueOf(date)));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(num));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

        } catch (Exception e) {
            e.printStackTrace();
        }
       return table ;
    }
    public PdfPTable titre() {

        PdfPTable table = new PdfPTable(1);

        try {
            PdfPCell cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Bon De Commande"));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return table ;
    }
    public static PdfPTable premierTableau2(String nom,String tel,String adresse) {

        //On créer un objet table dans lequel on intialise ça taille.
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        float[] columnWidths = new float[]{25, 50, 30,30, 30, 30,30};
        try {
            table.setWidths(columnWidths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table.setSpacingAfter(200F);
        //On créer l'objet cellule.
        PdfPCell cell = new PdfPCell();


        cell = new PdfPCell(new Phrase("Nom:"));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("bichri informatique"));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(""));
         cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(""));
         cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

            cell = new PdfPCell(new Phrase("Nom:"));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(nom));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Adresse:"));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("touba"));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Adresse:"));
         cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(adresse));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Tel:"));
         cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("782967755"));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Tel:"));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(tel));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);


       table.completeRow();
        return table;
    }
    public PdfPTable tableCommande(long id){
        PdfPTable table = new PdfPTable(2);

        try {




            PdfPCell c1 = new PdfPCell(new Phrase("Designation"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Quentite Commander"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);


            List<DetailCommande> list = new ArrayList<>();
            list = Fabrique.getiCommande().findbydetail(id);
            for (DetailCommande d:list) {
                String nom = d.getProduit().getLibelle();
                int de = (int) d.getQuentiteCommande();
                table.addCell(nom);
                table.addCell(String.valueOf(de));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      try {
          List<Personne> personneList = new ArrayList<>();
          personneList= Fabrique.getIPersonne().findAll();
          List<Entreprise> entrepriseList = new ArrayList<>();
          entrepriseList= Fabrique.getiEntreprise().findAll();
          List<Clien> cliens = new ArrayList<>();
          List<User> users = new ArrayList<>();
          User user = Fabrique.getiUser().find(LoginController.email);
          List<User> list = new ArrayList<>();
          list.add(user);
          cbbgesinnaire.setItems(FXCollections.observableArrayList(list));


          cliens = affichage(personneList,entrepriseList);
          client.setItems(FXCollections.observableArrayList(cliens));
          cbbproduit.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().findAll()));
        //  cbbproduit.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().findAll()));



      }catch (Exception e){
          e.printStackTrace();
      }
        affich();
    }
    public void affich() {
        libachacolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        qteachatcolonnz.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQte()));
        try {
            tableachat.setItems(FXCollections.
                    observableArrayList(Fabrique.getIAdmin().findAll()));
        } catch (Exception e) {
            Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
        }

        affichetab();
    }
        public List<Clien> affichage(List<Personne >personneList,List< Entreprise >entreprises){
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





    public String frmatcode(Date date,String nomcli) {

        String nom = "601";
        String nom1 = String.valueOf(date);
        String nom2 = nom1.substring(0, 2);
        String nom3 = nomcli.substring(0, 4);
        String code = nom + nom2+nom3;

        return code;
    }
    public void affichetab () {
        numcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumero()));
       nomcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getLogin()));
        datecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDateLivraision()));
        etatcolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEtat()));

        try {
            tablecommande.setItems(FXCollections.
                    observableArrayList(Fabrique.getiCommande().findAll()));
        } catch (Exception e) {
            Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
        }


    }
    public boolean comparDate(java.util.Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date1 = sdf.parse(date.toString());
            java.util.Date date2 = sdf.parse("2020-07-10");
            if(date2.compareTo(date1)> 0 || date1.compareTo(date2)> 0){
                return true;
            }else
                return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }*/
}
