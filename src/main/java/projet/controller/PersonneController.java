package projet.controller;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import projet.java.model.*;
import projet.utils.Achat;
import projet.utils.Fabrique;
import projet.utils.Utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PersonneController implements Initializable {

    @FXML
    private TableView<Commande> tablecommande;

    @FXML
    private TableColumn<Commande, String> numcommandecolonne;

    @FXML
    private TableColumn<Commande, String> usercolonne;

    @FXML
    private TableColumn<Commande, java.util.Date> datecolonne;

    @FXML
    private TableColumn<?, ?> qtecolonne;

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
    private ComboBox<Produit> cbbproduit;

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
    private ComboBox<User> cbbgestionnaire;

    @FXML
    private TableView<Achat1> tableachat;

    @FXML
    private TableColumn<Achat1, String> libcolonneach;

    @FXML
    private TableColumn<Achat1, Integer> qtecolonnacha;

    @FXML
    private Button btnajout;

    @FXML
    private TextField codepostaltfd;
    @FXML
    private DatePicker dateliv;

    @FXML
    void ajouer(ActionEvent event) {
        if (cbbproduit.getSelectionModel().isEmpty() || qtetfd.getText().trim().isEmpty()) {
            Utils.remplirechamp();

        } else {
            Produit produit = cbbproduit.getSelectionModel().getSelectedItem();
            Achat1 achat = new Achat1();
            achat.setLibelle(produit.getLibelle());
            achat.setQte(Integer.parseInt(qtetfd.getText()));
            try {
                Fabrique.getIAdmin().addAchat(achat);
                affich();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public void affich() {
        libcolonneach.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        qtecolonnacha.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQte()));
        try {
            tableachat.setItems(FXCollections.
                    observableArrayList(Fabrique.getIAdmin().findAll()));
        } catch (Exception e) {
            Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
        }

        // affichetab();
    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void rechercher(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {
        Document document = new Document();
        String code = "non";
        if (verfierchamps(nomtfd.getText()
                ,adressetfd.getText(),
                teltfd.getText())==1 && verfierchamps1(codepostaltfd.getText())==0) {
            Utils.remplirechamp();

        }else {
            String code1 = frmatcode(nomtfd.getText(),teltfd.getText());
            Client client = new Client();
            Client client1 = new Client();
            Client client2 = new Client();
            User user = cbbgestionnaire.getSelectionModel().getSelectedItem();
;
            try {
                client2= Fabrique.getiClient().findbytel(teltfd.getText());
                if(client2 ==null){

                    client.setNom(nomtfd.getText());
                    client.setAdresse(adressetfd.getText());
                    client.setTelephone(teltfd.getText());
                    client.setUser(user);
                    client.setCodePostal(codepostaltfd.getText());
                    client.setNumero(frmatcode(nomtfd.getText(),teltfd.getText()));
                    client.setPrenom(prenomtfd.getText());
                    client.setCni(cnitfd.getText());
                    client1= Fabrique.getiClient().add(client);
                    Commande commande = new Commande();
                    Commande commande1 = new Commande();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(dateliv.getValue());
                    String numero = frmatcode(String.valueOf(sqlDate), client1.getNom());
                    commande.setNumero(numero);
                    commande.setDateLivraision(sqlDate);
                    commande.setEtat(code);
                    commande.setUser(user);
                    commande.setClient(client1);
                    commande1 = Fabrique.getiCommande().add(commande);
                    List<Achat1> produit = tableachat.getItems();
                    List<DetailCommande> list = new ArrayList<>();
                    for (Achat1 p : produit) {
                        DetailCommande detailCommande = new DetailCommande();
                        DetailCommande detailCommande1 = new DetailCommande();
                        String cod = commande1.getNumero() + p.getLibelle();
                        detailCommande.setProduit(Fabrique.getiProduit().find(p.getLibelle()));
                        detailCommande.setQuentiteCommande(p.getQte());
                        detailCommande.setCommande(commande1);
                        detailCommande.setCommande_produit(cod);
                        detailCommande1 = Fabrique.getiCommande().ajout(detailCommande);
                        list.add(detailCommande);
                        String nom = client1.getNom() ;
                        String s = String.valueOf(commande1.getId());

                        try {


                            //PdfWriter.getInstance(document, new FileOutputStream("C:/facture/"+nom+"+"+s+".pdf"));

                            PdfWriter.getInstance(document, new FileOutputStream("C:/commande/" + nom + ".pdf"));


                            document.open();
                            document.add(titre());
                            document.add(table1(sqlDate, commande1.getNumero()));
                            document.add(premierTableau2(client1.getNom(), client1  .getTelephone(), client1.getAdresse()));
                            document.add(tableCommande(commande1.getId()));

                        } catch (DocumentException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        Fabrique.getIAdmin().delate();
                        affich();
                    }
                    document.close();
                }else {
                    Commande commande = new Commande();
                    Commande commande1 = new Commande();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(dateliv.getValue());
                    String numero = frmatcode(String.valueOf(sqlDate), client2.getNom());
                    commande.setNumero(numero);
                    commande.setDateLivraision(sqlDate);
                    commande.setEtat(code);
                    commande.setUser(user);
                    commande.setClient(client2);
                    commande1 = Fabrique.getiCommande().add(commande);
                    List<Achat1> produit = tableachat.getItems();
                    List<DetailCommande> list = new ArrayList<>();
                    for (Achat1 p : produit) {
                        DetailCommande detailCommande = new DetailCommande();
                        DetailCommande detailCommande1 = new DetailCommande();
                        String cod = commande1.getNumero() + p.getLibelle();
                        detailCommande.setProduit(Fabrique.getiProduit().find(p.getLibelle()));
                        detailCommande.setQuentiteCommande(p.getQte());
                        detailCommande.setCommande(commande1);
                        detailCommande.setCommande_produit(cod);
                        detailCommande1 = Fabrique.getiCommande().ajout(detailCommande);
                        list.add(detailCommande);
                        String nom = client2.getNom() ;
                        String s = String.valueOf(commande1.getId());

                        try {


                            //PdfWriter.getInstance(document, new FileOutputStream("C:/facture/"+nom+"+"+s+".pdf"));

                            PdfWriter.getInstance(document, new FileOutputStream("C:/commande/" + nom + ".pdf"));


                            document.open();
                            document.add(titre());
                            document.add(table1(sqlDate, commande1.getNumero()));
                            document.add(premierTableau2(client1.getNom(), client2  .getTelephone(), client2.getAdresse()));
                            document.add(tableCommande(commande1.getId()));

                        } catch (DocumentException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        Fabrique.getIAdmin().delate();
                        affich();
                    }
                    document.close();

                }



                // Utils.insertionvalider();
               // viderchamps();
                //affichtabe();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        viderchamps();
        affichetab();
    }

        @FXML
        void select (ActionEvent event){

        }

        @FXML
        void update (ActionEvent event){

        }

        @Override
        public void initialize (URL location, ResourceBundle resources){
        affichetab();
        affich();
           try {
               List<User> users = new ArrayList<>();
               User user = Fabrique.getiUser().find(LoginController.email);
               List<User> list = new ArrayList<>();
               list.add(user);
               cbbgestionnaire.setItems(FXCollections.observableArrayList(list));
               cbbproduit.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().findAll()));
                cbbproduit.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().findAll()));




           }catch (Exception e){
               e.printStackTrace();
           }

            teltfd.focusedProperty().addListener((obs,outfocus,infocus)->{
                if (outfocus){
                    //ILocataire loc=new LocataireDao();
                    Client client=new Client();

                    try {
                        client= Fabrique.getiClient().findbytel(teltfd.getText());
                        //System.out.println(client);
                        if (client != null)

                        {
                            teltfd.setText(client.getTelephone());
                            nomtfd.setText(client.getNom());
                            prenomtfd.setText(client.getPrenom());
                            adressetfd.setText(client.getAdresse());
                            cnitfd.setText(client.getCni());
                            codepostaltfd.setText(client.getCodePostal());
                            adressetfd.setDisable(true);
                            nomtfd.setDisable(true);
                            prenomtfd.setDisable(true);
                            teltfd.setDisable(true);
                            codepostaltfd.setDisable(true);
                            cnitfd.setDisable(true);


                        } else  {
                           // Notification.NotifError(" Client non existant","Veuiller ajouter un nouveaux client !");
                            adressetfd.setDisable(false);
                            nomtfd.setDisable(false);
                            prenomtfd.setDisable(false);
                            teltfd.setDisable(false);
                            codepostaltfd.setDisable(false);
                            cnitfd.setDisable(false);
                            //locataireexist.set(false);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }else if (infocus){
                    //Utils.showMessage("location voiture","verification numero piece","le numero nexiste pas");
                    //Notification.NotifSucces("Gestion ","Veuiller ajouter un nouveaux client !");


                }
            });
           /* putdf.setDisable(true);
            try {
                cnitfd.focusedProperty().addListener((obs, outFocus, inFocus) -> {
                    String cni = cnitfd.getText();

                    Personne personne = new Personne();
                    Entreprise entreprise = new Entreprise();

                    try {
                        personne = Fabrique.getIPersonne().findnyCni(cni);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    if (outFocus) {
                        setdisable();

                        nomtfd.setText(personne.getNom());
                        prenomtfd.setText(personne.getPrenom());
                        // telephonetfd.setText(l.getTel());

                        //dateniassetfd.setText(l.getDateNaisse());
                    } else if (inFocus) {

                        // vider1();
                    }

                });
                User user = Fabrique.getiUser().find(LoginController.email);
                List<User> list = new ArrayList<>();
                list.add(user);
                List<Produit> liste = Fabrique.getiProduit().findAll();
                cbbproduit.setItems(FXCollections.observableArrayList(liste));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void setdisable () {
            nomtfd.setDisable(true);


            prenomtfd.setDisable(true);


            teltfd.setDisable(true);
            adressetfd.setDisable(true);*/

        }
    public int verfierchamps(String nom,String adresse,String tel){
        if(nom.trim().isEmpty() ||
                adresse.trim().isEmpty()||

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
    public String frmatcode(String name,String tel) {

        String nom = "411";
        String nom1 =name;
        String nom2 = nom1.substring(0, 1);
        String nom3 = tel.substring(0, 4);
        String code = nom + nom2+nom3;

        return code;
    }

    public String frmatcode1(LocalDate date, String nomcli) {

        String nom = "601";
        String nom1 = String.valueOf(date);
        String nom2 = nom1.substring(0, 2);
        String nom3 = nomcli.substring(0, 4);
        String code = nom + nom2+nom3;

        return code;
    }
    public void pdf(Document document, String nomcommande, List<DetailCommande> list) {


        try {
            PdfWriter.getInstance(document, new FileOutputStream("C:/commande/"+nomcommande+".pdf"));
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
            cell = new PdfPCell(new Phrase("B O N  D E   C O M M AN D"));
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
    public void affichetab () {
        numcommandecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumero()));
        usercolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getClient().getNom()));
        datecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDateLivraision()));
        datecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDateLivraision()));

        try {
            tablecommande.setItems(FXCollections.
                    observableArrayList(Fabrique.getiCommande().findAll()));
        } catch (Exception e) {
            Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
        }


    }
    public void viderchamps() {

        nomtfd.setText(" ");
        prenomtfd.setText(" ");
        cnitfd.setText(" ");
        adressetfd.setText(" ");
        teltfd.setText(" ");
        codepostaltfd.setText(" ");
       // dateliv.setValue(LocalDate.ofEpochDay(0));

        //datetfd.setValue(LocalDate.parse(" "));
    }

}





