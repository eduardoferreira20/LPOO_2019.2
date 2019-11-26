package br.com.poli.campoMinado.rank;

import java.util.Comparator;

public class CompararJogadores implements Comparator<Jogador>{

	
	@Override
	public int compare(Jogador a, Jogador b) {
		return a.getTempoTotal() - b.getTempoTotal(); //
	}
	
}

