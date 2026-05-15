package Projet_java_caisse;

import org.json.*;
import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String contenu = Files.readString(Path.of("products (2).json"));
        JSONObject json = new JSONObject(contenu);
        JSONArray dishes = json.getJSONArray("dishes");
        JSONArray drinks = json.getJSONArray("drinks");
        JSONArray desserts = json.getJSONArray("desserts");
        ArrayList<Product> prod = new ArrayList<>();
        for (int i = 0; i < dishes.length(); i++) {
            JSONObject dish = dishes.getJSONObject(i);
            String name = dish.getString("name");
            Double price = dish.getDouble("price");
            String type = dish.getString("type");
            Dish d = new Dish(name, price, type);
            prod.add(d);
        }
        for (int i = 0; i < desserts.length(); i++) {
            JSONObject dessert = desserts.getJSONObject(i);
            String name = dessert.getString("name");
            Double price = dessert.getDouble("price");
            int calories = dessert.getInt("calories");
            Dessert de = new Dessert(name, price, calories);
            prod.add(de);
        }
        for (int i = 0; i < drinks.length(); i++) {
            JSONObject drink = drinks.getJSONObject(i);
            String name = drink.getString("name");
            Double price = drink.getDouble("price");
            Double volume = drink.getDouble("volume");
            Drink dr = new Drink(name, price, volume);
            prod.add(dr);
        }
        Scanner scan = new Scanner(System.in);
        String menu = "1 - Afficher les détails d'un produit\n2 - Réaliser le paiement d'une table\n3 - Utiliser la caisser de secours\n4 - Quitter";
        IO.println(menu);
        IO.println("Choix");
        int choix = scan.nextInt();

        while(choix != 4){
            if (choix == 1){
                int i = 1;
                for (Product produ : prod) {
                    IO.println(i + " - " + produ.name);
                    i++;
                }
            }
            if (choix == 2){
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
                        for (Product p : prod) {
                            if (p.name.equals(nomProduit)) {
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

            }
            if (choix == 3){
                IO.println("Saisir le numérode la table : ");
                int num = scan.nextInt();
                IO.println("Saisir le nombre de personne : ");
                int nbr = scan.nextInt();
                int i = 1;
                for (Product produ : prod) {
                    IO.println(i + " - " + produ.name);
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
            IO.println(menu);
            IO.println("Choix");
            choix = scan.nextInt();
        }
    }
}











