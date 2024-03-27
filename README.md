Welcome to our Blackjack card game! You might be wondering, why did we choose Blackjack? Initially, we were unsure what game we should create. So we went to YouTube for some inspiration. After some research, we found a Blackjack project done by Kenny Yip Coding in Youtube. Inspired by the simplicity of his project's UI and his straightforward logic, we decided to create our own. We implemented a similar UI design with his project, but we opted for a more thorough and complete logic, as Blackjack actually has a lot of rules!

Here are the rules and rewards for our game!

The objective of the game is to get a higher hand sum than the dealer while not exceeding 21. The chances of winning are the best when you get exactly 21, as it is a rare value!

Below we describe our rules:
1) Player and dealer both start with 2 cards, and the dealer will have one hidden card.
3) STAY represents keeping the current sum of values of the cards. You would do this if you are confident you can win over the dealer, or you think drawing another card is too risky.
4) HIT represents drawing a card to the current hand to increase your hand value. Do this if you feel that your current hand's value is too low.
5) The values of each card are straightforward. Number cards follow the number written in them, while picture cards have a value of 10.
6) Aces have a special value. They are either 1 or 11, depending if your value exceeds 21 or not. By default, ace will have a value of 11. But if your value is over 21 and you have an ace, the ace's value will be reduced to 1. 
7) There are 3 conditions either a tie, win or lose.
8) You WIN if your hand sum does not exceed 21 and is higher than the dealer hand sum or the dealer exceeds 21 while you did not.
9) In the event that a player draws 5 cards and did not exceed 21 they win.
10) Its a TIE if both sides have the same value or both sides exceed 21.
11) You LOSE if your hand sum is smaller than the dealer or you exceed 21 while the dealer did not.

Here are the rewards below:
1) If you win, you receive 2 times your bet money.
2) If you lose, you lose your bet money.
3) If its a tie, you receive back your bet money.

The player has a starting hand sum of $5000. So bet wisely and try to become the next billionaire in this game of blackjack or become broke and try again!


References:

Source for card back: https://opengameart.org/content/colorful-poker-card-back

Source for all other card images: https://www.waste.org/~oxymoron/files/cards/

Inspired from: https://github.com/ImKennyYip/blackjack-java
