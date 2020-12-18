package projet.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import projet.java.model.DetailFacture;
import projet.java.model.Entres;
import projet.utils.Fabrique;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Statistique  implements Initializable {

    @FXML
    private Pane paneView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loaddata();

    }
    private void loaddata(){
        int total1 = 0;
        double somm = 0;
        long total = 0;
        int i = 0;
        paneView.getChildren().clear();
        try {
            List<DetailFacture> list1 = new ArrayList<>();


            try {
                //Fabrique.getiEntres().findAll();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            List<Entres> list = new ArrayList<>();
            try {
                list = Fabrique.getiEntres().findAll();

                for (Entres entres : list) {
                    total += entres.getTotal();
                    i++;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            list1=Fabrique.getiFacture().findAllbydetaill();
            for (DetailFacture d:list1) {
                total1+= d.getQuentiteCommande() *d.getProduits().getPrixunitiare();

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        ObservableList<PieChart.Data>list = FXCollections.observableArrayList();
        list.add(new PieChart.Data("A C H A T",total));
        list.add(new PieChart.Data("V E N T E",total1));
        PieChart p = new PieChart(list);
        paneView.getChildren().add(p);



    }
}
