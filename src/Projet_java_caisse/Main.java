package Projet_java_caisse;

import org.json.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Product> catalogue = new ArrayList<>();

        // Chargement du catalogue au démarrage
        String contenu = Files.readString(Paths.get("products.json"));
        JSONObject base = new JSONObject(contenu);
        JSONArray plats = base.getJSONArray("dishes");
        for (int i = 0; i < plats.length(); i++) catalogue.add(new Dish(plats.getJSONObject(i)));
        JSONArray desserts = base.getJSONArray("desserts");
        for (int i = 0; i < desserts.length(); i++) catalogue.add(new Dessert(desserts.getJSONObject(i)));
        JSONArray boissons = base.getJSONArray("drinks");
        for (int i = 0; i < boissons.length(); i++) catalogue.add(new Drink(boissons.getJSONObject(i)));

        Scanner scan = new Scanner(System.in);
        String menu = "1 - Afficher les détails d'un produit\n2 - Réaliser le paiement d'une table\n3 - Utiliser la caisse de secours\n4 - Quitter";
        IO.println(menu);
        IO.println("Votre choix");
        int choix = scan.nextInt();

        while (choix != 4) {
            if (choix == 1) {
                Catalogue.afficherProduit(catalogue, scan);
            }
            if (choix == 2) {
                Paiement.realiserPaiement(catalogue, scan);
            }
            if (choix == 3) {
                Caisse caisse = new Caisse(catalogue, scan);
                caisse.caisse_secours();
            }
            IO.println(menu);
            IO.println("Votre choix");
            choix = scan.nextInt();
        }

        IO.println("Au revoir !");
    }
}
