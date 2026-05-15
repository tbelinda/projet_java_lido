package Projet_java_caisse;

public class Dessert extends Product {
    int calories;
    public Dessert(String name, double price, int calories){
        super(name, price);
        this.calories = calories;

    }

    public void afficher_details(){
        IO.println(name + " - " + price + "€ - Calories: " + calories + "kcal");
    }
}
