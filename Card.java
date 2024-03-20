import java.util.ArrayList;

public class Card {
    String value;
    String suits;

    Card(String value, String suits) {
        this.value = value;
        this.suits = suits;
    }

    // Overriding toString, makes the deck print the card in the format of
    // value|suits
    public String toString() {
        return value + "" + suits;
    }

    // Method to get the value of current card
    public int getValue() {
        if ("ajqk".contains(value)) {
            if (value == "a") {
                return 11;
            }
            return 10;
        }
        return Integer.parseInt(value);
    }

    // Method to know if the compared card is an ace
    public boolean isAce() {
        return value == "a";
    }
    //Method to get the current image's path
    public String getImagePath(){
        return "./cards/" + toString() + ".gif";
    }


}