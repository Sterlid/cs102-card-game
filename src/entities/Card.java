package entities;
public class Card {
    String number;
    String suits;

    public Card(String number, String suits) {
        this.number = number;
        this.suits = suits;
    }

    // Overriding toString, makes the deck print the card in the format of
    // number|suits
    public String toString() {
        return number + suits;
    }

    // Method to get the value of current card
    public int getValue() {
        if ("atjqk".contains(number)) {
            if (number == "a") {
                return 11;
            }
            return 10;
        }
        return Integer.parseInt(number);
    }

    // Method to know if the compared card is an ace
    public int isAce() {
        if(number.equals("a")){
            return 1;
        }
        return 0;
    }
    
    //Method to get the current image's path
    public String getImagePath(){
        String imagePath = "images/cards/" + toString() + ".gif";
        System.out.println("Image Path: " + imagePath);
        return imagePath;
    }


}
