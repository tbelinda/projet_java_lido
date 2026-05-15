package Projet_java_caisse;

import org.json.*;
import java.util.ArrayList;

public class Commande {
    int tabNum;
    String date;
    int nbOfPeople;
    ArrayList<Product> product = new ArrayList<>();

    public Commande(int tabNum, String date, int nbofPeople, ArrayList<Product> product){
        this.tabNum = tabNum;
        this.date = date;
        this.nbOfPeople = nbOfPeople;
        this.product = product;
    }
    public double calculerTotal(){
        double total = 0;
        for (Product jsp : product){
            total += jsp.price;
        }
    return total;
    }
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("tabNum", tabNum);
        obj.put("date", date);
        obj.put("nbOfPeople", nbOfPeople);
        JSONArray produits = new JSONArray();
        for (Product p : product) {
            JSONObject pro = new JSONObject();
            pro.put("name", p.name);
            pro.put("price", p.price);
            produits.put(pro);
        }
        obj.put("products", produits);
        return obj;
    }

}
