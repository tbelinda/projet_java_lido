package Projet_java_caisse;

public class Drink extends Product {

    double volume;

    public Drink(String name, double price, double volume){
        super(name, price);
        this.volume = volume;

    }
    public void afficher_details(){
        IO.println(name + " - " + price + "€ - Volume: "+ volume +"cl");
    }
}
