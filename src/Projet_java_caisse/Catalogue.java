package Projet_java_caisse;

import java.util.*;

public class Catalogue {
    public static void afficherProduit(List<Product> catalogue, Scanner scan) {
        int i = 1;
        for (Product p : catalogue) {
            IO.println(i + " - " + p.getNom());
            i++;
        }
        IO.println("Entrez le numéro du produit : ");
        int numProduit = scan.nextInt();
        if (numProduit > 0 && numProduit <= catalogue.size()) {
            catalogue.get(numProduit - 1).afficher_detail();
        } else {
            IO.println("Numéro invalide.");
        }
    }
}

