package projet.utils;

import projet.java.service.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Fabrique {
   private static IPofil iPofil;
    private static IAdmin iAdmin;
    private static IUser iUser;
    private static IProduit iProduit;
    private static IEntres iEntres;
    private static IClient iClient;
    private static ICommande iCommande;
    private static IFacture iFacture;
    private static void init() throws Exception {
        try {
            Registry registry = LocateRegistry.getRegistry(5003);
            iPofil = (IPofil ) registry.lookup("profilRemote");
            iUser = (IUser) registry.lookup("userRemote");
           iAdmin = (IAdmin) registry.lookup("adminRemote");
           iProduit = (IProduit) registry.lookup("produitRemote");
           iEntres = (IEntres) registry.lookup("entresRemote");
           iClient = (IClient) registry.lookup("clientRemote");
           iCommande = (ICommande) registry.lookup("commandeRemote");
           iFacture = (IFacture) registry.lookup("factureRemote");

        }
        catch(Exception e){
            throw e;
        }
    }

    public static IPofil getiProfil() throws Exception {

        try {
            if(iPofil  == null) {
                init();
            }
            return iPofil;
        }
        catch(Exception e){
            throw e;
        }

    }

    public static IAdmin getIAdmin() throws Exception {
        try {
            if(iAdmin == null) {
                init();
            }
            return iAdmin;
        }
        catch(Exception e){
            throw e;
        }

    }
    public static IUser getiUser() throws Exception {
        try {
            if(iUser == null) {
                init();
            }
            return iUser;
        }
        catch(Exception e){
            throw e;
        }

    }
    public static IProduit getiProduit() throws Exception {
        try {
            if(iProduit == null) {
                init();
            }
            return iProduit;
        }
        catch(Exception e){
            throw e;
        }

    }
    public static IEntres getiEntres() throws Exception {
        try {
            if(iEntres == null) {
                init();
            }
            return iEntres;
        }
        catch(Exception e){
            throw e;
        }

    }

    public static IClient getiClient() throws Exception {
        try {
            if(iClient == null) {
                init();
            }
            return iClient;
        }
        catch(Exception e){
            throw e;
        }

    }
    public static ICommande getiCommande() throws Exception {
        try {
            if(iCommande == null) {
                init();
            }
            return iCommande;
        }
        catch(Exception e){
            throw e;
        }

    }
    public static IFacture getiFacture() throws Exception {
        try {
            if(iFacture == null) {
                init();
            }
            return iFacture;
        }
        catch(Exception e){
            throw e;
        }

    }

}

