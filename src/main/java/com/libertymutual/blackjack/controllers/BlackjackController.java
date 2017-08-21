package com.libertymutual.blackjack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.libertymutual.blackjack.models.Dealer;
import com.libertymutual.blackjack.models.Deck;
import com.libertymutual.blackjack.models.Player;

@Controller
@RequestMapping({ "/", "/blackjack" })
public class BlackjackController {

	private Deck deck = new Deck();
	private Player player;
	private Dealer dealer;

	public BlackjackController() {
		this.player = new Player();
		this.dealer = new Dealer();
		this.deck = new Deck();
		deck.populateDeck();
		deck.shuffle();
		player.getWallet();
	}

	@GetMapping("")
	public String showHomePage(Model model) {
		model.addAttribute("wallet", player.getWallet());
		return "/blackjack/home";
	}

	@GetMapping("/home")
	public String updateWallet(Model model) {
		model.addAttribute("wallet", player.getWallet());
		return "/blackjack/home";
	}

	@PostMapping("/addCash")
	public String addCashToWallet(double cash) {
		player.addCash(cash);
		return "redirect:/blackjack/home";
	}

	@GetMapping("/bet")
	public String showBetPage(Model model) {
		return "/blackjack/bet";
	}

	@PostMapping("/startGame")
	public String startGame(double betAmount, Model model) {
		player.placeBet(betAmount);
		player.hit(deck);
		player.hit(deck);
		dealer.hit(deck);
		dealer.hit(deck);
		return "redirect:/blackjack/playerTurn";
	}

	@GetMapping("/playerTurn")
	public String showPlayerTurnPage(Model model) {
		model.addAttribute("canShowHitAndStandButtons", (player.calculateHandValue() < 21) && (deck.getDeckSize() > 0) && (player.getWallet() >= 0));
		model.addAttribute("deckSize", deck.getDeckSize());
		model.addAttribute("playerHand", player.showHand());
		model.addAttribute("dealerHand", dealer.showHandWithHoleCard());
		model.addAttribute("playerHandValue", player.calculateHandValue());
		model.addAttribute("dealerHandValue", dealer.calculateHandValue());
		model.addAttribute("bet", player.getBet());
		model.addAttribute("wallet", player.getWallet());
		if (player.getWallet() < 0) {
			model.addAttribute("noMoney", player.getWallet() < 0);
			return "/blackjack/playerTurn";
		}
		if ((player.calculateHandValue() >= 21) || (deck.getDeckSize() == 0)) {
			return "redirect:/blackjack/dealerTurn";
		} else {
			return "/blackjack/playerTurn";
		}
	}

	@GetMapping("/playerHit")
	public String hitPlayer() {
		player.hit(deck);
		if (player.calculateHandValue() >= 21) {
			return "redirect:/blackjack/dealerTurn";
		} else {
			return "redirect:/blackjack/playerTurn";
		}
	}

	@GetMapping("/dealerTurn")
	public String showDealerTurnPage(Model model) {

		// player busted
		if (player.calculateHandValue() > 21) {
			model.addAttribute("dealerWon", player.calculateHandValue() > 21);
			model.addAttribute("wallet", player.losesBet());
		}

		// player has blackjack, dealer not
		if ((player.calculateHandValue() == 21) && (dealer.calculateHandValue() != 21)) {
			model.addAttribute("playerWon",
					((player.calculateHandValue() == 21) && (dealer.calculateHandValue() != 21)));
			model.addAttribute("wallet", player.getsTripleAmount());
		}

		// blackjack tie
		if ((player.calculateHandValue() == 21) && (dealer.calculateHandValue() == 21)) {
			model.addAttribute("blackjackTie",
					(player.calculateHandValue() == 21) && (dealer.calculateHandValue() == 21));
			model.addAttribute("wallet", player.justGetsBetBack());
		}

		// player stood
		if (player.calculateHandValue() < 21) {
			while (dealer.calculateHandValue() < 17) {
				dealer.hit(deck);
			}
			// dealer bust
			if (dealer.calculateHandValue() > 21) {
				model.addAttribute("playerWon", dealer.calculateHandValue() > 21);
				model.addAttribute("wallet", player.getsDoubleAmount());
				// player hand greater than dealer hand
			} else if (player.calculateHandValue() > dealer.calculateHandValue()) {
				model.addAttribute("playerWon", (player.calculateHandValue() > dealer.calculateHandValue()));
				model.addAttribute("wallet", player.getsDoubleAmount());
				// dealer hand greater than player hand
			} else if ((dealer.calculateHandValue() < 21)
					&& (dealer.calculateHandValue() > player.calculateHandValue())) {
				model.addAttribute("dealerWon", (dealer.calculateHandValue() > player.calculateHandValue()));
				model.addAttribute("wallet", player.losesBet());				
				// player hand and dealer hand are equal
			} else if (player.calculateHandValue() == dealer.calculateHandValue()) {
				model.addAttribute("tie", (player.calculateHandValue() == dealer.calculateHandValue()));
				model.addAttribute("wallet", player.justGetsBetBack());
			}
		}
		
		model.addAttribute("dealerHandValue", dealer.calculateHandValue());
		model.addAttribute("playerHandValue", player.calculateHandValue());
		model.addAttribute("playerHand", player.showHand());
		model.addAttribute("dealerHand", dealer.showHand());
		model.addAttribute("bet", player.getBet());
		model.addAttribute("deckSize", deck.getDeckSize());
		model.addAttribute("canShowPlayAnotherRoundButton", (deck.getDeckSize() > 3) && (player.getWallet() > 0));
		model.addAttribute("notEnoughResourcesToContinue", (deck.getDeckSize() <= 3) || (player.getWallet() <= 0));

		return "/blackjack/dealerTurn";
	}

	@PostMapping("/playAnotherRound")
	public String popCardsOffHands() {
		while (player.getNumberOfCardsInHand() > 0) {
			player.getHand().pop();
		}
		while (dealer.getNumberOfCardsInHand() > 0) {
			dealer.getHand().pop();
		}
		return "redirect:/blackjack/bet";
	}
}