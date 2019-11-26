package br.com.poli.campoMinado.jogo;

public enum Dificuldade {

	// Atributos da classe
	FACIL(9), MEDIO(16), DIFICIL(32);

	private final int valor;

	// Dar o valor da dificuldade escolhida pelo jogador
	private Dificuldade(int valor) {
		this.valor = valor;
	}

	// Getter
	public int getValor() {
		return this.valor;
	}
}
