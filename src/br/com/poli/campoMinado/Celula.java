package br.com.poli.campoMinado;

import java.util.List;
import java.util.ArrayList;

public class Celula {

	private boolean bandeira;
	private boolean bomba;
	private int qtdBombasVizinhas;
	private boolean visivel;
	private List<Celula> vizinhas;
	private int linha;
	private int coluna;

	public Celula(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
		this.vizinhas = new ArrayList<Celula>();
	}

	public boolean isEmBranco() {
		if (this.qtdBombasVizinhas == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void buscarVizinhos(Celula[][] campo) {
		
		// Conjunto de FOR que percorre a matriz campo e adiciona as vizinhas na lista
		for (int linha = 0; linha < campo.length; linha++) {
			for (int coluna = 0; coluna < campo.length; coluna++) {
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						
						//Verifica se está dentro da matriz campo
						if (!(linha + i < 0 ||coluna + j > campo.length - 1 || coluna + j < 0|| linha + i > campo.length - 1)) {

							campo[linha][coluna].vizinhas.add(campo[linha + i][coluna + j]);

						}
					}
				}
			}
		}
	}

	public boolean isBandeira() {
		return bandeira;
	}

	public void setBandeira(boolean bandeira) {
		this.bandeira = bandeira;
	}

	public boolean isBomba() {
		return bomba;
	}

	public void setBomba(boolean bomba) {
		this.bomba = bomba;
	}

	public int getQtdBombasVizinhas() {
		return qtdBombasVizinhas;
	}

	public void setQtdBombasVizinhas(int qtdBombasVizinhas) {
		this.qtdBombasVizinhas = qtdBombasVizinhas;
	}

	public boolean isVisivel() {
		return visivel;
	}

	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}
	
	public List<Celula> getVizinhas() {
		return vizinhas;
	}


}
