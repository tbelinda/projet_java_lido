package Projet_java_caisse;

import org.json.*;
import java.nio.file.*;
import java.util.*;

public class Paiement {
    public static void realiserPaiement(List<Product> catalogue, Scanner scan) {
        try {
            IO.println("Saisir le numéro de la table : ");
            int numTable = scan.nextInt();

            // Vérification que la table existe
            Path fichierTable = Path.of(numTable + ".json");
            if (!Files.exists(fichierTable)) {
                IO.println("Aucune commande trouvée pour la table " + numTable);
                return;
            }

            // Lecture de la commande
            JSONObject jsonCommande = new JSONObject(Files.readString(fichierTable));
            int tabNum = jsonCommande.getInt("tabNum");
            String dateCommande = jsonCommande.getString("date");
            int nbPeople = jsonCommande.getInt("nbOfPeople");
            JSONArray produitsNoms = jsonCommande.getJSONArray("products");

            // Recherche des produits dans le catalogue
            ArrayList<Product> produitsCommande = new ArrayList<>();
            for (int i = 0; i < produitsNoms.length(); i++) {
                String nomProduit = produitsNoms.getString(i);
                for (Product p : catalogue) {
                    if (p.nom.equals(nomProduit)) {
                        produitsCommande.add(p);
                    }
                }
            }

            // Calcul et affichage du total
            Commande commande = new Commande(tabNum, dateCommande, nbPeople, produitsCommande);
            double total = commande.calculerTotal();
            IO.println("Total : " + total + "€");

            // Ajout dans l'archive sans écraser
            JSONObject archiveObj = commande.toJSONObject();
            Path fichierArchive = Path.of("archive.json");
            JSONArray archives;
            if (Files.exists(fichierArchive)) {
                String contenuArchive = Files.readString(fichierArchive).trim();
                if (contenuArchive.startsWith("[")) {
                    archives = new JSONArray(contenuArchive);
                } else {
                    archives = new JSONArray();
                }
            } else {
                archives = new JSONArray();
            }
            archives.put(archiveObj);
            Files.writeString(fichierArchive, archives.toString(2));
            IO.println("Paiement enregistré dans archive.json !");

        } catch (Exception e) {
            IO.println("Erreur : " + e.getMessage());
        }
    }
}