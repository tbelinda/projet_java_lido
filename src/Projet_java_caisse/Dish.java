package Projet_java_caisse;
import org.json.JSONObject;

public class Dish extends Product {
    private String type;

    public Dish(JSONObject json) {
        super(json);
        this.type = json.getString("type");
    }

    @Override
    public void afficher_detail() {
        System.out.println("Plat (" + type + ") : " + nom + " | " + prix + "€");
    }
}

