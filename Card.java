import java.util.ArrayList;

public class Card {
    String number;
    String suits;

    Card(String number, String suits) {
        this.number = number;
        this.suits = suits;
    }

    // Overriding toString, makes the deck print the card in the format of
    // number|suits
    public String toString() {
        return number + "" + suits;
    }

    // Method to get the value of current card
    public int getValue() {
        if ("ajqk".contains(number)) {
            if (number == "a") {
                return 11;
            }
            return 10;
        }
        return Integer.parseInt(number);
    }

    // Method to know if the compared card is an ace
    public boolean isAce() {
        return number == "a";
    }
    //Method to get the current image's path
    public String getImagePath(){
        return "./cards/" + toString() + ".gif";
    }


}