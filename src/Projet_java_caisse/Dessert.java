package Projet_java_caisse;

import org.json.JSONObject;

public class Dessert extends Produit {
    int calories;

    public Dessert(JSONObject json) {
        super(json);
        this.calories = json.getInt("calories");
    }

    @Override
    public void afficher_detail() {
        System.out.println("Dessert (" + calories + " kcal) : " + nom + " | " + prix + "€");
    }

    public JSONObject toJSONObject() {
        JSONObject obj = super.toJSONObject();
        obj.put("calories", calories);
        return obj;
    }
}