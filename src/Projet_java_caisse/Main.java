package Projet_java_caisse;

import org.json.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Product> catalogue = new ArrayList<>();

        try {
            String contenu = Files.readString(Paths.get("products.json"));
            JSONObject base = new JSONObject(contenu);

            JSONArray plats = base.getJSONArray("dishes");
            for (int i = 0; i < plats.length(); i++) catalogue.add(new Dish(plats.getJSONObject(i)));

            JSONArray desserts = base.getJSONArray("desserts");
            for (int i = 0; i < desserts.length(); i++) catalogue.add(new Dessert(desserts.getJSONObject(i)));

            JSONArray boissons = base.getJSONArray("drinks");
            for (int i = 0; i < boissons.length(); i++) catalogue.add(new Drink(boissons.getJSONObject(i)));

            System.out.println("=== MENU DU RESTAURANT ===");
            for (int i = 0; i < catalogue.size(); i++) {
                System.out.println((i + 1) + " - " + catalogue.get(i).getNom());
            }

            Scanner sc = new Scanner(System.in);
            System.out.print("\nEntrez le numéro du produit : ");
            int choix = sc.nextInt();

            if (choix > 0 && choix <= catalogue.size()) {
                catalogue.get(choix - 1).afficher_detail();
            } else {
                System.out.println("Numéro invalide.");
            }

        } catch (Exception e) {
            System.out.println("Erreur de chargement : " + e.getMessage());
        }
        Scanner scan = new Scanner(System.in);
        String menu = "1 - Afficher les détails d'un produit\n2 - Réaliser le paiement d'une table\n3 - Utiliser la caisser de secours\n4 - Quitter";
        IO.println(menu);
        IO.println("Choix");
        int choix = scan.nextInt();

        while(choix != 4){
            if (choix == 1){
                int i = 1;
                for (Product produ : catalogue) {
                    IO.println(i + " - " + produ.nom);
                    i++;
                }
            }
//realiser le paiement
            if (choix == 2){
                IO.println("Saisir le numéro de la table : ");
                int numTable = scan.nextInt();
                String contenuCommande = Files.readString(Path.of(numTable + ".json"));
                JSONObject jsonCommande = new JSONObject(contenuCommande);
                int tabNum = jsonCommande.getInt("tabNum");
                String dateCommande = jsonCommande.getString("date");
                int nbPeople = jsonCommande.getInt("nbOfPeople");
                JSONArray produitsNoms = jsonCommande.getJSONArray("products");
                ArrayList<Product> produitsCommande = new ArrayList<>();
                for (int i = 0; i < produitsNoms.length(); i++) {
                    String nomProduit = produitsNoms.getString(i);
                    for (Product p : catalogue) {
                        if (p.nom.equals(nomProduit)) {
                            produitsCommande.add(p);
                        }
                    }
                }
                Commande commande = new Commande(tabNum, dateCommande, nbPeople, produitsCommande);
                double total = commande.calculerTotal();
                IO.println("Total : " + total + "€");
                JSONObject archiveObj = commande.toJSONObject();
                Files.writeString(Path.of("archive.json"), archiveObj.toString());

            }
            if (choix == 3){
                Caisse caisse = new Caisse(catalogue, scan);
                caisse.caisse_secours();
            }
            IO.println(menu);
            IO.println("Choix");
            choix = scan.nextInt();
        }
    }
}











