package entities;

public class Ace extends Card{
        public Ace(String number, String suits){
            super(number, suits);
        }

        public static int getHigherValue(){
            return 11;
        }

        public static int getLowerValue(){
            return 1;
        }
}
