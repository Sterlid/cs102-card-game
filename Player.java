import java.util.ArrayList;

// dealer extends player
// dealer has a hiddenCard attribute
public class Player {
    ArrayList<Card> hand;
    boolean banBan;
    boolean banLuck;
    int sum;
    int aceCount;
    int pictureCount;

    public Player(){
        hand = new ArrayList<Card>();
        banBan = false;
        banLuck = false;
        sum = 0;
        aceCount =0;
        pictureCount = 0;
    }

    // public Player(ArrayList <Card> hand){
    //     this.hand = hand;
    //     for (Card c: hand){
    //         if (c.isAce()){
    //             aceCount++;
    //         } else if (c.isPicture()){
    //             pictureCount++;
    //         }
    //     }
    //     if (hand.size()==2 && aceCount==2){
    //         banBan = true;
    //     } else if (hand.size()==2 && aceCount==1 && pictureCount ==1){
    //         System.out.println("Ban Luck!!!");
    //         banLuck = true;
    //     } 
    // }

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
        banBan = false;
        banLuck = false;
        sum = 0;
        aceCount =0;
        pictureCount = 0;
    }
  
}
