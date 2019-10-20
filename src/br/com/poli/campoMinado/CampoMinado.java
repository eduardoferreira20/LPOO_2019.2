package br.com.poli.campoMinado;
import br.com.poli.campoMinado.mapa.*;
public class CampoMinado{

	// Atributos
	private Jogador jogador;
	private Mapa mapa;
	private Dificuldade dificuldade;

	// Consturtor recebe o nome do jogador e a dificuldade
	public CampoMinado(String nome, Dificuldade dificuldade) {
		
		this.jogador = new Jogador(nome);
		this.dificuldade = dificuldade;
		
		if(dificuldade.getValor() == 9) {
			this.mapa = new MapaFacil();
		}else if(dificuldade.getValor() == 16) {
			this.mapa = new MapaMedio();
		}else {
			this.mapa = new MapaDificil();
		}
		
	}

	// Getters e Setters
	public Jogador getJogador() {
		return jogador;
	}
	
	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
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
