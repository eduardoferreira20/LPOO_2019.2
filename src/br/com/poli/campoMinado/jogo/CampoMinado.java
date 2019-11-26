package br.com.poli.campoMinado.jogo;
import java.util.Scanner;

import br.com.poli.campoMinado.mapa.*;
import br.com.poli.campoMinado.rank.Jogador;
public class CampoMinado{

	// Atributos
	private Jogador jogador;
	private Mapa mapa;
	private Dificuldade dificuldade;

	// Consturtor recebe o nome do jogador e a dificuldade
	public CampoMinado(String nome, Dificuldade dificuldade) {
		
		this.jogador = new Jogador(nome);
		this.dificuldade = dificuldade;
		
		switch (dificuldade) {
		case FACIL:
			this.mapa = new MapaFacil();
			break;
		case MEDIO:
			this.mapa = new MapaMedio();
			break;
		case DIFICIL:
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
	//Laço que roda todo o jogo
	public void iniciarJogo() {
		Scanner posicao = new Scanner(System.in);
		int linha, coluna;
		while(this.mapa.isGanhouJogo() == false && this.mapa.isFimDeJogo()==false) {
			System.out.print("Informe um número para a Coluna:");
			coluna = posicao.nextInt();
			System.out.print("Informe um número para a Linha: ");
			linha = posicao.nextInt();
			this.mapa.escolherPosicao(linha,coluna);
			
		}
		posicao.close();// FECHA O SCANNER
		if (this.mapa.isFimDeJogo()) {// SERVE PARA MOSTRAR COMO É O MAPA COMPLETO CASO O JOGADOR PERCA
			System.out.println("Mapa Original:");
			this.mapa.imprimeTela(true);
		}
	}

}
