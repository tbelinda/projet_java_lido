package Projet_java_caisse;
import org.json.JSONObject;

public class Drink extends Product {
    private int volume;

    public Drink(JSONObject json) {
        super(json);
        this.volume = json.getInt("volume");
    }

    @Override
    public void afficher_detail() {
        System.out.println("Boisson (" + volume + "cl) : " + nom + " | " + prix + "€");
    }

    public JSONObject toJSONObject() {
        JSONObject obj = super.toJSONObject();
        obj.put("volume", volume);
        return obj;
    }
}