package projet.controller;

import projet.java.model.Admin;
import projet.java.model.User;
import projet.utils.Fabrique;
import projet.utils.LoadView;
import projet.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.crypto.*;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.*;
import java.util.ResourceBundle;

public class
LoginController implements Initializable {



        @FXML
        private TextField emailtdf;
       public static String email;

        @FXML
        private PasswordField pwdtfd;

        @FXML
        private Button loginbtn;

        @FXML
        void connxion(ActionEvent event) {

        }

        @FXML
        public void login(javafx.event.ActionEvent actionEvent) {
                Admin admin = null;
                User user =null;


                try {
                      user =   Fabrique.getiUser().findByUser(emailtdf.getText(),pwdtfd.getText());

                      admin =  Fabrique.getIAdmin().find(emailtdf.getText().trim(),pwdtfd.getText().trim());
                }catch (Exception e){
                     e.printStackTrace();

                }
                if(emailtdf.getText().trim().isEmpty()||pwdtfd.getText().trim().isEmpty()){
                        Utils.remplirechamp();
                }else if(user == null && admin == null){
                    Utils.showMessage("","projet java","Email OU mot de passe incorrect !!!");

                }
                else
                {
                    email = emailtdf.getText();
                    LoadView.showView("menu pricipale","Menu.fxml",1);

                }

        }



    public void viderchamps(){
            emailtdf.setText(" ");
            pwdtfd.setText(" ");
    }

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }

        public boolean verifiermail(String mail){


                int index = mail.indexOf(" "+"@gmail.com");

                if(index == - 1) {
                        return false;
                } else {
                        return true;
                }

        }
        public String crypterpwd(String pwd){

            byte[] plainText = pwd.getBytes();
            //
            // get a DES private key
            System.out.println("\nStart generating DES key");
            KeyGenerator keyGen = null;
            try {
                keyGen = KeyGenerator.getInstance("DES");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            keyGen.init(56);
            Key key = keyGen.generateKey();
            System.out.println("Finish generating DES key");
            //
            // get a DES cipher object and print the provider
            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
            System.out.println("\n" + cipher.getProvider().getInfo());
            //
            // encrypt using the key and the plaintext
            System.out.println("\nStart encryption");
            try {
                cipher.init(Cipher.ENCRYPT_MODE, key);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            byte[] cipherText = new byte[0];
            try {
                cipherText = cipher.doFinal(plainText);
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            System.out.println("Finish encryption: ");
            System.out.println(new String(cipherText));

            //
            // decrypt the ciphertext using the same key
            System.out.println("\nStart decryption");
            try {
                cipher.init(Cipher.DECRYPT_MODE, key);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            byte[] newPlainText = new byte[0];
            try {
                newPlainText = cipher.doFinal(cipherText);
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            System.out.println("Finish decryption: ");

            System.out.println(new String(newPlainText));
            return   new String(cipherText);
        }
}

