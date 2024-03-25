package entities;
import java.util.ArrayList;

public class Player {
    String name;
    ArrayList<Card> hand;
    boolean banBan;
    boolean banLuck;
    int sum;
    int aceCount;
    int pictureCount;
    int money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player(){
        hand = new ArrayList<Card>();
        // banBan = false;
        // banLuck = false;
        sum = 0;
        aceCount =0;
        // pictureCount = 0;
        money = 5000;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public boolean isBanBan() {
        return banBan;
    }

    public boolean isBanLuck() {
        return banLuck;
    }

    public int getSum() {
        return sum;
    }

    public int getAceCount() {
        return aceCount;
    }

    public int getPictureCount() {
        return pictureCount;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setAceCount(int aceCount) {
        this.aceCount = aceCount;
    }

    public void setPictureCount(int pictureCount) {
        this.pictureCount = pictureCount;
    }

    public void setFreshHand(){
        this.hand = new ArrayList<Card>();
        aceCount = 0;
        sum = 0;
    }
  
    public int getMoney(){
        return this.money;
    }

    public void setMoney(int money){
        this.money = money;
    }
}
