package Projet_java_caisse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class Paiement {

    public static void realiserPaiement() {
        Scanner scanner = new Scanner(System.in);

        // 1. Demander la table à payer
        System.out.print("Quelle table souhaite payer ? ");
        int numeroTable = scanner.nextInt();

        // 2. Construire le nom du fichier de commande
        String nomFichierCommande = "Data/" + numeroTable + ".json";
        File commandeFile = new File(nomFichierCommande);

        // 3. Vérifier si le fichier existe
        if (!commandeFile.exists()) {
            System.out.println("Aucune commande trouvée pour la table " + numeroTable);
            return;
        }

        System.out.println("Commande trouvée !");

        try {
            // 4. Lire le fichier de commande
            FileReader reader = new FileReader(commandeFile);
            StringBuilder contenu = new StringBuilder();
            int caractere;
            while ((caractere = reader.read()) != -1) {
                contenu.append((char) caractere);
            }
            reader.close();

            // 5. Parser le JSON de la commande
            JSONObject commandeJson = new JSONObject(contenu.toString());

            int tabNum       = commandeJson.getInt("tabNum");
            String date      = commandeJson.getString("date");
            int nbPersonnes  = commandeJson.getInt("nbOfPeople");
            JSONArray nomsProduitsCommandes = commandeJson.getJSONArray("products");

            // 6. Lire le fichier products.json pour récupérer les prix
            File productsFile = new File("Data/products.json");
            FileReader readerProducts = new FileReader(productsFile);
            StringBuilder contenuProducts = new StringBuilder();
            int c;
            while ((c = readerProducts.read()) != -1) {
                contenuProducts.append((char) c);
            }
            readerProducts.close();

            JSONArray tousProduits = new JSONArray(contenuProducts.toString());

            // 7. Calculer l'addition et afficher le détail
            System.out.println("\n--- Détail de la commande ---");
            System.out.println("Table : " + tabNum);
            System.out.println("Date : " + date);
            System.out.println("Nombre de personnes : " + nbPersonnes);
            System.out.println("----------------------------");

            double total = 0;
            JSONArray produitsAvecInfos = new JSONArray(); // pour l'archive

            for (int i = 0; i < nomsProduitsCommandes.length(); i++) {
                String nomProduitCommande = nomsProduitsCommandes.getString(i);

                // Chercher le produit dans products.json
                JSONObject produitTrouve = null;
                for (int j = 0; j < tousProduits.length(); j++) {
                    JSONObject produit = tousProduits.getJSONObject(j);
                    if (produit.getString("nom").equals(nomProduitCommande)) {
                        produitTrouve = produit;
                        break;
                    }
                }

                if (produitTrouve != null) {
                    double prix = produitTrouve.getDouble("prix");
                    total += prix;
                    System.out.println(nomProduitCommande + " : " + prix + "€");
                    produitsAvecInfos.put(produitTrouve); // on garde toutes les infos
                } else {
                    System.out.println(nomProduitCommande + " : produit introuvable !");
                }
            }

            System.out.println("----------------------------");
            System.out.println("TOTAL : " + total + "€");

            // 8. Stocker la commande dans archive.json
            // On construit l'objet à archiver avec toutes les infos
            JSONObject commandeArchive = new JSONObject();
            commandeArchive.put("tabNum", tabNum);
            commandeArchive.put("date", date);
            commandeArchive.put("nbOfPeople", nbPersonnes);
            commandeArchive.put("total", total);
            commandeArchive.put("products", produitsAvecInfos);

            // Lire l'archive existante (ou créer un tableau vide)
            File archiveFile = new File("Data/archive.json");
            JSONArray archive;

            if (archiveFile.exists()) {
                FileReader readerArchive = new FileReader(archiveFile);
                StringBuilder contenuArchive = new StringBuilder();
                int a;
                while ((a = readerArchive.read()) != -1) {
                    contenuArchive.append((char) a);
                }
                readerArchive.close();
                archive = new JSONArray(contenuArchive.toString());
            } else {
                archive = new JSONArray();
            }

            // Ajouter la commande et réécrire le fichier
            archive.put(commandeArchive);

            FileWriter writer = new FileWriter(archiveFile);
            writer.write(archive.toString(2)); // toString(2) = indentation de 2
            writer.close();

            System.out.println("Paiement enregistré dans archive.json !");

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
