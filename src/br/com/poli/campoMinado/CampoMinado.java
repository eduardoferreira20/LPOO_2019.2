package br.com.poli.campoMinado;

public class CampoMinado{

	// Atributos
	private Jogador jogador;
	private Dificuldade dificuldade;

	// Consturtor recebe o nome do jogador e a dificuldade
	public CampoMinado(String nome, Dificuldade dificuldade) {
		/*
		 * Cria um novo jogardo a partir da string nome e um Mapa a partir da
		 * Dificuldade
		 */
		this.jogador = new Jogador(nome);
		this.dificuldade = dificuldade;
	}

	// Getters e Setters
	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public Dificuldade getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(Dificuldade dificuldade) {
		this.dificuldade = dificuldade;
	}

}
