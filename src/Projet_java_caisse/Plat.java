package Projet_java_caisse;
import org.json.JSONObject;

public class Plat extends Produit {
    String type;

    public Plat(JSONObject json) {
        super(json);
        this.type = json.getString("type");
    }

    @Override
    public void afficher_detail() {
        System.out.println("Plat (" + type + ") : " + nom + " | " + prix + "€");
    }

    public JSONObject toJSONObject() {
        JSONObject obj = super.toJSONObject();
        obj.put("type", type);
        return obj;
    }
}

