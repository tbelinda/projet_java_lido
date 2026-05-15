package Projet_java_caisse;

public class Dish extends Product {
    String type;

    public Dish(String name, double price, String type){
        super(name, price);
        this.type = type;
    }
    public void afficher_details(){
        IO.println(name + " - " + price + "€ - Type: " + type);
    }
}


