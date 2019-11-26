package br.com.poli.campoMinado.rank;

import java.io.File;

public class RankingDificil extends Ranking {
	
	public static final File RANK = new File("rankingDificil.data");

	public RankingDificil(Jogador novoJogador) {
		super(novoJogador, RANK);
		// TODO Auto-generated constructor stub
	}

	public File getRank() {
		return RANK;
	}

}
