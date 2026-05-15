package Projet_java_caisse;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import org.json.*;


public class Caisse {
    List<Product> prod;
    Scanner scan;

    public Caisse(List<Product> prod, Scanner scan) {
        this.prod = prod;
        this.scan = scan;
    }
    public void caisse_secours() throws IOException {
        IO.println("Saisir le numérode la table : ");
        int num = scan.nextInt();
        IO.println("Saisir le nombre de personne : ");
        int nbr = scan.nextInt();
        int i = 1;
        for (Product produ : prod) {
            IO.println(i + " - " + produ.nom);
            i++;
        }
        ArrayList<Product> produitsCommandes = new ArrayList<>();
        int nprod;
        String date = LocalDateTime.now().toString();
        do {
            IO.println("Saisir le numéro du produit (0 pour arrêter) : ");
            nprod = scan.nextInt();
            if (nprod >= 0 && nprod <= prod.size()){
                if (nprod != 0) {
                    produitsCommandes.add(prod.get(nprod - 1));
                }
            }
            else {
                IO.println("Numéro invalide veuillez recommencer : ");
                nprod = scan.nextInt();
            }
        } while(nprod != 0);
        Commande com = new Commande(num,date, nbr, produitsCommandes);
        double res = com.calculerTotal();
        IO.println(res);
        JSONObject archive = com.toJSONObject();
        String contenuArchive = Files.readString(Path.of("archive.json"));
        JSONArray archives = new JSONArray(contenuArchive);
        archives.put(archive);
        Files.writeString(Path.of("archive.json"), archives.toString(2));
    }
}
