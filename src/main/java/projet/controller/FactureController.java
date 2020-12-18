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
import projet.java.model.*;
import projet.utils.Fabrique;
import projet.utils.Utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class FactureController implements Initializable {

        @FXML
        private DatePicker datfacture;

        @FXML
        private ComboBox<Commande> cbbcommande;

        @FXML
        private ComboBox<User> cbbgestionnaire;

        @FXML
        private TableView<Facture> tablefacture;

        @FXML
        private TableColumn<Facture, String> nucolonne;

        @FXML
        private TableColumn<Facture, Date> datecolonne;

        @FXML
        private TableColumn<Facture, String> commandecolonne;

        @FXML
        private TableColumn<Facture, String> qtecolonne;

        @FXML
        private TableColumn<Facture, String> totalcolonne;

        @FXML
        private Button btnenrigister;

        @FXML
        void enrigistrer(ActionEvent event) {
            long vente=0;
            List<DetailCommande>list = new ArrayList<>();
            Commande commande = null;
            Facture facture1 = null;;
            Produit produit = null;
            Produit produit1 = null;
            User use = null;
            Facture facture = new Facture();
            if (datfacture.getValue()==null ||
                    cbbcommande.getSelectionModel().isEmpty()||
                    cbbgestionnaire.getSelectionModel().isEmpty() ) {
                Utils.remplirechamp();

            }else {
                try {
                    java.sql.Date sqlDate = java.sql.Date.valueOf(datfacture.getValue());
                    String sqlDate1 = String.valueOf(java.sql.Date.valueOf(datfacture.getValue()));
                    if(!comparDate(sqlDate)){
                        System.out.println("non");
                    }else {
                        commande = cbbcommande.getSelectionModel().getSelectedItem();
                        use = cbbgestionnaire.getSelectionModel().getSelectedItem();
                        list = Fabrique.getiCommande().findbydetail(commande.getId());
                        if (commande.getClient().getCni() != null) {

                            facture.setClient(commande.getClient());
                            facture.setDatefacture(sqlDate);
                            facture.setUser(use);
                            facture.setCommande(commande);
                            facture.setNumero(formatcode(commande.getNumero()));
                            int jour = nombrejour(commande.getDateLivraision(), sqlDate);
                            System.out.println(jour);
                            double montant = montanttotal(commande.getId(), jour);
                            System.out.println(montant);
                            facture.setMontant((int) montant);
                            facture1 = Fabrique.getiFacture().add(facture);
                            for (DetailCommande d : list) {
                                DetailFacture detailFacture = new DetailFacture();
                                detailFacture.setProduit(String.valueOf(d.getProduit()));
                                detailFacture.setFacture(facture1);
                                detailFacture.setQuentiteCommande(d.getQuentiteCommande());
                                detailFacture.setProduit(d.getProduit().getLibelle() + d.getProduit().getCode());
                                Fabrique.getiFacture().ajout(detailFacture);
                                Utils.insertionvalider();
                                produit = Fabrique.getiProduit().findbyid(d.getProduit().getId());
                                if (produit != null) {
                                    long stock = produit.getStock();
                                    long rest = d.getQuentiteCommande();
                                    long stockacuel = stock -rest;
                                    Fabrique.getiProduit().updatebystock(stockacuel,produit.getId());

                                }

                            }

                            commande = cbbcommande.getSelectionModel().getSelectedItem();
                            int id = (int) commande.getId();
                            commande.setId(id);
                            commande.setEtat("ok");
                            Document document = new Document();
                            Document document1 = new Document();
                            document1 = ourire(document, commande.getNumero() + facture1.getNumero());
                            document1.add(titre());
                            document1.add(table1(sqlDate, facture.getNumero()));
                            document1.add(premierTableau2(facture1.getClient().getNom(), facture1.getClient().getTelephone(), facture1.getClient().getAdresse()));
                            document1.add(tableCommande(commande.getId()));
                            document1.add(pdf1(commande.getId(), 0));
                            fermer(document);
                            Fabrique.getiCommande().update(commande);
                            Utils.insertionvalider();
                        }else {
                            facture.setClient(commande.getClient());
                            facture.setDatefacture(sqlDate);
                            facture.setUser(use);
                            facture.setCommande(commande);
                            facture.setNumero(formatcode(commande.getNumero()));
                            int jour = nombrejour(commande.getDateLivraision(), sqlDate);
                            System.out.println(jour);
                            double montant = montanttotal(commande.getId(), jour);
                            System.out.println(montant);
                            facture.setMontant((int) montant);
                            facture1 = Fabrique.getiFacture().add(facture);
                            for (DetailCommande d : list) {
                                DetailFacture detailFacture = new DetailFacture();
                                detailFacture.setProduit(String.valueOf(d.getProduit()));
                                detailFacture.setFacture(facture1);
                                detailFacture.setQuentiteCommande(d.getQuentiteCommande());
                                detailFacture.setProduit(d.getProduit().getLibelle() + d.getProduit().getCode());
                                Fabrique.getiFacture().ajout(detailFacture);
                                Utils.insertionvalider();
                                produit = Fabrique.getiProduit().findbyid(d.getProduit().getId());
                                if (produit != null) {
                                    long stock = produit.getStock();
                                    long rest = d.getQuentiteCommande();
                                    long stockacuel = stock -rest;
                                         Fabrique.getiProduit().updatebystock(stockacuel,produit.getId());

                                }

                            }

                            commande = cbbcommande.getSelectionModel().getSelectedItem();
                            int id = (int) commande.getId();
                            commande.setId(id);
                            commande.setEtat("ok");
                            Document document = new Document();
                            Document document1 = new Document();
                            document1 = ourire(document, commande.getNumero() + facture1.getNumero());
                            document1.add(titre());
                            document1.add(table1(sqlDate, facture.getNumero()));
                            document1.add(premierTableau2(facture1.getClient().getNom(), facture1.getClient().getTelephone(), facture1.getClient().getAdresse()));
                            document1.add(tableCommande(commande.getId()));
                            document1.add(pdf(commande.getId(),jour));
                            fermer(document);
                            Fabrique.getiCommande().update(commande);
                            Utils.insertionvalider();

                        }


                    }

                 /*
                    Document document1 = new Document();
                    /*Document document = new Document();
                    document = ourire(document1, String.valueOf(sqlDate));
                    document.add(titre());
                    document.add(table1(sqlDate,"2"));
                    document.add(premierTableau2("ffff", "fggg", ""));
                    document.add(tableCommande(1));
                    document.add(pdf());
                    fermer(document);*/

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            affichetab();
        }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            User user = Fabrique.getiUser().find(LoginController.email);
            List<User> list = new ArrayList<>();
            list.add(user);
            cbbgestionnaire.setItems(FXCollections.observableArrayList(list));
            List<Commande>  liste= Fabrique.getiCommande().findAll();
            cbbcommande.setItems(FXCollections.observableArrayList(liste));
        }catch (Exception e){
            e.printStackTrace();
        }
        affichetab();
    }
    public void affichetab () {
        nucolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumero()));
        commandecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCommande().getNumero()));
        datecolonne.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDatefacture()));

        try {
            tablefacture.setItems(FXCollections.
                    observableArrayList(Fabrique.getiFacture().findAll()));
        } catch (Exception e) {
            Utils.showMessage("projet java", "bichri infomatique", "Erreur : " + e.getMessage());
        }


    }
    public PdfPTable table1(java.sql.Date date, String num) {

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
            cell = new PdfPCell(new Phrase("Facture"));
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
    public Document ourire(Document document,String nomfichier){
       try {
           PdfWriter.getInstance(document, new FileOutputStream("C:/facture/" + nomfichier + ".pdf"));
           document.open();
           titre();
       }catch (Exception e){
           e.printStackTrace();
       }
            return document;
    }
    public Document fermer(Document document){
        document.close();
        return document;
    }
    public PdfPTable pdf(long id,int remise) {

        PdfPTable table = new PdfPTable(2);
           int montant = 0;
           int rediction=0;
           int tva1 = 0;
           int tva = 0;
           int montant1= 0;
        try {
            List<DetailCommande> list = new ArrayList<>();
            list = Fabrique.getiCommande().findbydetail(id);
            for (DetailCommande d:list) {
                String designation = d.getProduit().getLibelle();
                int qte = (int) d.getQuentiteCommande();
                int pu = Integer.parseInt(String.valueOf(d.getProduit().getPrixunitiare()));
                 montant = pu * qte;
                montant1 += montant;
            }
            for (int i=1;i<=remise;i++){
               rediction += montant * 0.1;
            }
             tva1 = montant - rediction;
              tva = (int) (tva1 * 0.18);
            int acompte  = 0;
            acompte += montant * 0.30;
            int netpayer = montant -rediction;
            netpayer  += tva;
            netpayer-= acompte;

            PdfPCell c1 = new PdfPCell(new Phrase("Total H.T"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(String.valueOf(montant1)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Remise 1% "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(rediction)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("TVA 18% "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(tva)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("A compte 30%"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(acompte)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Montant  Net a Payer "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(netpayer)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

        } catch (Exception e) {
            e.printStackTrace();
        }
            return table;
    }
    public PdfPTable pdf1(long id,int remise) {

        PdfPTable table = new PdfPTable(2);
        int montant = 0;
        int rediction=0;
        int tva1 = 0;
        int tva = 0;
        int montant1 =0;
        try {
            List<DetailCommande> list = new ArrayList<>();
            list = Fabrique.getiCommande().findbydetail(id);
            for (DetailCommande d:list) {
                String designation = d.getProduit().getLibelle();
                long qte =d.getQuentiteCommande();
                int pu = Integer.parseInt(String.valueOf(d.getProduit().getPrixunitiare()));
                montant = (int) (pu * qte);
                montant1 += montant;
            }
            for (int i=1;i<=remise;i++){
                rediction += montant * 0.1;
            }
            tva1 = montant - rediction;
            tva = (int) (tva1 * 0.18);
            int acompte  = 0;
            acompte += montant * 0.30;
            int netpayer = montant -rediction;
            netpayer  += tva;


            PdfPCell c1 = new PdfPCell(new Phrase("Total H.T"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(String.valueOf(montant1)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Remise 1% "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(0)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("TVA 18% "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(tva)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("A compte 30%"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(0));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Montant  Net a Payer "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(netpayer)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    public PdfPTable pdf2(long id,int remise) {

        PdfPTable table = new PdfPTable(2);
        int montant = 0;
        int rediction=0;
        int tva1 = 0;
        int tva = 0;
        int montant1 =0;
        try {
            List<DetailCommande> list = new ArrayList<>();
            list = Fabrique.getiCommande().findbydetail(id);
            for (DetailCommande d:list) {
                String designation = d.getProduit().getLibelle();
                long qte =d.getQuentiteCommande();
                int pu = Integer.parseInt(String.valueOf(d.getProduit().getPrixunitiare()));
                montant = (int) (pu * qte);
                montant1 += montant;
            }
            for (int i=1;i<=remise;i++){
                rediction += montant * 0.1;
            }
            tva1 = montant - rediction;
            tva = (int) (tva1 * 0.18);
            int acompte  = 0;
            acompte += montant * 0.30;
            int netpayer = montant -rediction;
            netpayer  += tva;


            PdfPCell c1 = new PdfPCell(new Phrase("Total H.T"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(String.valueOf(montant1)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Remise 1% "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(rediction)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("TVA 18% "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(tva)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("A compte 30%"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(0)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Montant  Net a Payer "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(netpayer)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    public int nombrejour(Date date, Date date2) {
        int res = 0;
        double result = Double.POSITIVE_INFINITY;
        if (date != null && date2 != null) {
            final long MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24;
            Calendar aCal = Calendar.getInstance();
            aCal.setTime(date);
            long aFromOffset = aCal.get(Calendar.DST_OFFSET);
            aCal.setTime(date2);
            long aToOffset = aCal.get(Calendar.DST_OFFSET);
            long aDayDiffInMili = (date2.getTime() + aToOffset) - (date.getTime() + aFromOffset);
            result = ((int) aDayDiffInMili / MILLISECONDS_PER_DAY);
        }
        return (int) result;
        }

    public PdfPTable tableCommande(long id){
        PdfPTable table = new PdfPTable(4);

        try {




            PdfPCell c1 = new PdfPCell(new Phrase("Designation"));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Quentite "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Prix De ventre "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Montant "));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            List<DetailCommande> list = new ArrayList<>();
            list = Fabrique.getiCommande().findbydetail(id);
            for (DetailCommande d:list) {
                String designation = d.getProduit().getLibelle();
                long qte = d.getQuentiteCommande();
                int pu = Integer.parseInt(String.valueOf(d.getProduit().getPrixunitiare()));
                int montant = (int) (pu * qte);
                table.addCell(designation);
                table.addCell(String.valueOf(qte));
                table.addCell(String.valueOf(pu));
                table.addCell(String.valueOf(montant));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    public String formatcode(String numero) {

        String nom = "701";
        String nom1 = numero;
        String code = nom + nom1;

        return code;
    }
    public boolean comparDate(java.util.Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date1 = sdf.parse(date.toString());
            java.util.Date date2 = sdf.parse("2020-07-10");
            if(date2.compareTo(date1)< 0 || date1.compareTo(date2)> 0){
                return true;
            }else
                return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    public double montanttotal(long id,int jour){
        double montant = 0;
        double montantnet = 0;
        try {


            List<DetailCommande> list = new ArrayList<>();
            list = Fabrique.getiCommande().findbydetail(id);

            for (DetailCommande d:list) {
                double qte = d.getQuentiteCommande();
                double pu = d.getProduit().getPrixunitiare();
                montant+= qte * pu;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i=1;i<=jour;i++){
            montant = montant - (montant * 0.1);
        }
        montant += montant * 0.18;
        montant -= montant * 0.30;

            return montant;
    }
    public double montanttotal1(long id){
        double montant = 0;
        double montantnet = 0;
        try {


            List<DetailCommande> list = new ArrayList<>();
            list = Fabrique.getiCommande().findbydetail(id);

            for (DetailCommande d:list) {
                double qte = d.getQuentiteCommande();
                double pu = d.getProduit().getPrixunitiare();
                montant+= qte * pu;

            }
            montant += montant *0.18;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return montant;
    }

}
