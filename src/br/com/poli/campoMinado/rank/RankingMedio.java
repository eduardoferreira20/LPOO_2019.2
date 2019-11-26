package br.com.poli.campoMinado.rank;

import java.io.File;

public class RankingMedio extends Ranking {

	public static final File RANK = new File("rankingMedio.data");
	
	public RankingMedio(Jogador novoJogador) {
		super(novoJogador, RANK);
		// TODO Auto-generated constructor stub
	}

	public File getRank() {
		return RANK;
	}

}
