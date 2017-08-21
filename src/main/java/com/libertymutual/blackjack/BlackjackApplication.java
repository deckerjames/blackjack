package com.libertymutual.blackjack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
//import com.libertymutual.blackjack.models.AceCard;
//import com.libertymutual.blackjack.models.Deck;
//import com.libertymutual.blackjack.models.Hand;
//import com.libertymutual.blackjack.models.NumberCard;

@SpringBootApplication
public class BlackjackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlackjackApplication.class, args);
		
//		Hand handle = new Hand();
//		handle.addCard(new AceCard("Hearts"));
//		handle.addCard(new NumberCard(3, "Diamonds"));
//		
//		Deck deck = new Deck();
//		deck.shuffle();
//		deck.printThis();
//		
	}
}
