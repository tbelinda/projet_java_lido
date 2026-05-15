package Projet_java_caisse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class TestPayment {
    public static void main(String[] args) {
        try {
            int numeroTable = 1;
            String cheminCommande = "Data/" + numeroTable + ".json";
            String cheminCatalogue = "Data/products.json";

            // Lire la commande
            File commandeFile = new File(cheminCommande);
            if (!commandeFile.exists()) {
                System.out.println("Fichier commande introuvable : " + cheminCommande);
                return;
            }
            FileReader reader = new FileReader(commandeFile);
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) sb.append((char) c);
            reader.close();
            JSONObject commandeJson = new JSONObject(sb.toString());

            // Étape 3 : récupérer les noms des produits
            JSONArray produitsArray = commandeJson.getJSONArray("products");
            List<String> produitsCommandes = new ArrayList<>();
            for (int i = 0; i < produitsArray.length(); i++) {
                produitsCommandes.add(produitsArray.getString(i));
            }
            System.out.println("Produits commandés : " + produitsCommandes);

            // Lecture products.json
            File catalogueFile = new File(cheminCatalogue);
            if (!catalogueFile.exists()) {
                System.out.println("Catalogue introuvable : " + cheminCatalogue);
                return;
            }
            FileReader catReader = new FileReader(catalogueFile);
            StringBuilder catSb = new StringBuilder();
            while ((c = catReader.read()) != -1) catSb.append((char) c);
            catReader.close();
            JSONObject catalogue = new JSONObject(catSb.toString());

            // Index nom -> prix
            Map<String, Double> prixParProduit = new HashMap<>();

            JSONArray plats = catalogue.getJSONArray("dishes");
            for (int i = 0; i < plats.length(); i++) {
                JSONObject item = plats.getJSONObject(i);
                prixParProduit.put(item.getString("name"), item.getDouble("price"));
            }
            JSONArray desserts = catalogue.getJSONArray("desserts");
            for (int i = 0; i < desserts.length(); i++) {
                JSONObject item = desserts.getJSONObject(i);
                prixParProduit.put(item.getString("name"), item.getDouble("price"));
            }
            JSONArray boissons = catalogue.getJSONArray("drinks");
            for (int i = 0; i < boissons.length(); i++) {
                JSONObject item = boissons.getJSONObject(i);
                prixParProduit.put(item.getString("name"), item.getDouble("price"));
            }

            // Affichage des prix et calcul du total
            System.out.println("\nDétail des prix :");
            double total = 0.0;
            for (String produit : produitsCommandes) {
                Double prix = prixParProduit.get(produit);
                if (prix == null) {
                    System.out.println(" Produit non trouvé : " + produit);
                } else {
                    System.out.printf("%s : %.2f €\n", produit, prix);
                    total += prix;
                }
            }
            System.out.printf("\nTotal à payer : %.2f €\n", total);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}