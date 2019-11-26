package br.com.poli.campoMinado.rank;

import java.io.File;

public class RankingFacil extends Ranking {

	public static final File RANK = new File("rankingFacil.data");
	
	public RankingFacil(Jogador novoJogador) {
		super(novoJogador, RANK);
		// TODO Auto-generated constructor stub
	}

	public File getRank() {
		return RANK;
	}

}
