package Projet_java_caisse;

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Payment {

    public static void realiserPaiement() {
        Scanner scanner = new Scanner(System.in);

        // 1. Demander la table à payer
        System.out.print("Quelle table souhaite payer ? ");
        int numeroTable = scanner.nextInt();

        // 2. Construire le nom du fichier
        String nomFichier = "Data/" + numeroTable + ".json";

        // 3. Créer un objet File
        File commandeFile = new File(nomFichier);

        // 4. Vérifier si le fichier existe
        if (commandeFile.exists()) {
            System.out.println("Commande trouvée !");

            try {
                // 5. Ouvrir le fichier
                FileReader reader = new FileReader(commandeFile);

                // 6. Lire tout le contenu
                StringBuilder contenu = new StringBuilder();
                int caractere;

                while ((caractere = reader.read()) != -1) {
                    contenu.append((char) caractere);
                }

                // 7. Fermer le fichier
                reader.close();

                // 8. Transformer le texte en JSONObject
                JSONObject commandeJson = new JSONObject(contenu.toString());

                // 9. Afficher les informations principales
                System.out.println("Table : " + commandeJson.getInt("tabNum"));
                System.out.println("Date : " + commandeJson.getString("date"));
                System.out.println("Nombre de personnes : " + commandeJson.getInt("nbOfPeople"));

            } catch (Exception e) {
                System.out.println("Erreur lors de la lecture du fichier JSON.");
            }

        } else {
            System.out.println("Aucune commande trouvée pour la table " + numeroTable);
        }
    }
}
