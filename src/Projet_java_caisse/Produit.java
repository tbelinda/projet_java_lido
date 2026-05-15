package Projet_java_caisse;
import org.json.JSONObject;

public abstract class Produit {
    protected String nom;
    protected double prix;

    public Produit(JSONObject json) {
        this.nom = json.getString("name");
        this.prix = json.getDouble("price");
    }

    public String getNom() {
        return nom;
    }

    public abstract void afficher_detail();

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("name", nom);
        obj.put("price", prix);
        return obj;
    }
}