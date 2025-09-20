package com.problems.design.vechileFactor;

interface Vechile {
    void drive();
}

class Car implements Vechile {
    @Override
    public void drive() {
        System.out.println("Driving Car !");
    }
}

class Bike implements Vechile {
    @Override
    public void drive() {
        System.out.println("Driving Bike !");
    }
}

class Truck implements Vechile {
    @Override
    public void drive() {
        System.out.println("Driving Truck !");
    }
}

class VechileFactory {
    public static Vechile getVechile(String type){
        if(type == null) return null;

        if(type.equalsIgnoreCase("Car")){
            return new Car();
        }else if(type.equalsIgnoreCase("Bike")){
            return new Bike();
        }else if(type.equalsIgnoreCase("Truck")){
            return new Truck();
        }else{
            return null;
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Vechile vechile = VechileFactory.getVechile("Car");
        vechile.drive();
    }
}
