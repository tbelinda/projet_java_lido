package Projet_java_caisse;

public abstract class Product {
    protected String name;
    protected double price;

    public Product(String name, double price){
        this.name = name;
        this.price = price;
    }
    public abstract void afficher_details();
}
