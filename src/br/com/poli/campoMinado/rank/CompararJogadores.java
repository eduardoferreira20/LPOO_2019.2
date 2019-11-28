package br.com.poli.campoMinado.rank;

import java.util.Comparator;

public class CompararJogadores implements Comparator<Jogador>{
//Compara os jogadores baseado no tempo total deles
	@Override
	public int compare(Jogador a, Jogador b) {
		return a.getTempoTotal() - b.getTempoTotal(); //
	}
	
}

