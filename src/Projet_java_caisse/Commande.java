package Projet_java_caisse;

import org.json.*;
import java.util.ArrayList;

public class Commande {
    int tabNum;
    String date;
    int nbOfPeople;
    ArrayList<Produit> produit = new ArrayList<>();

    public Commande(int tabNum, String date, int nbOfPeople, ArrayList<Produit> produit){
        this.tabNum = tabNum;
        this.date = date;
        this.nbOfPeople = nbOfPeople;
        this.produit = produit;
    }
    public double calculerTotal(){
        double total = 0;
        for (Produit jsp : produit){
            total += jsp.prix;
        }
    return total;
    }
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("tabNum", tabNum);
        obj.put("date", date);
        obj.put("nbOfPeople", nbOfPeople);
        JSONArray produits = new JSONArray();
        for (Produit p : produit) {
            produits.put(p.toJSONObject());
        }
        obj.put("products", produits);
        return obj;
    }

}
