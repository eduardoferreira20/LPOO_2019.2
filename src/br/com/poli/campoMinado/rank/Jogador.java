package br.com.poli.campoMinado.rank;

import java.io.Serializable;

public class Jogador implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String tempo;
	private int tempoTotal;
	
	public Jogador(String nome) {
		this.nome = nome;
	}
	
	public Jogador(String nome, String tempo, int tempoTotal) {
		this.nome = nome;
		this.tempo = tempo;
		this.tempoTotal = tempoTotal;
	}

	//Metodos
	@Override
	public String toString() {
		return this.nome + "   " + this.tempo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public int getTempoTotal() {
		return tempoTotal;
	}

	public void setTempoTotal(int tempoTotal) {
		this.tempoTotal = tempoTotal;
	}


}
