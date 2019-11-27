package br.com.poli.campoMinado;

import java.util.Random;

import javax.swing.JOptionPane;

import br.com.poli.campoMinado.mapa.*;


import java.util.ArrayList;
import java.util.List;

public class Resolvedor {

	private Mapa mapa;
	private int tamanho;
	private boolean teste;

	public Resolvedor(Mapa mapa) {
		this.mapa = mapa;
		this.tamanho = this.mapa.getCampo().length;
		this.teste = true;
	}

	public void escolhePosicaoResolvedor() {
		int linha = new Random().nextInt(this.tamanho);
		int coluna = new Random().nextInt(this.tamanho);

		this.mapa.escolherPosicao(linha, coluna);
		while (!this.mapa.isGanhouJogo()) {
			if (this.teste) {
				this.teste = false;
			} else {
				System.out.println("Error");
				break;
			}
		}
		System.out.println();
		this.mapa.imprimeTela(false);
	}

	public int comparar(int x, int y) {
		if (x > y) {
			return x - y;
		} else {
			return y - x;
		}
	}

	public void escolher(int x, int y) {
		ArrayList<Celula> vizinhasNaoVisiveis = this.mapa.getCampo()[x][y].getNaoVisivel();

		for (int i = 0; i < this.mapa.getCampo()[x][y].getVizinhas().size(); i++) {
			if (!this.mapa.getCampo()[x][y].getVizinhas().get(i).isVisivel()) {
				vizinhasNaoVisiveis.add(this.mapa.getCampo()[x][y].getVizinhas().get(i));
			}
		}
		if (vizinhasNaoVisiveis.size() == 3) {

			if (vizinhasNaoVisiveis.get(0).getLinha() == vizinhasNaoVisiveis.get(1).getLinha()
					&& vizinhasNaoVisiveis.get(1).getLinha() == vizinhasNaoVisiveis.get(2).getLinha()) {

				this.mapa.getCampo()[x][y].setQualificada(true);

			} else if (vizinhasNaoVisiveis.get(0).getColuna() == vizinhasNaoVisiveis.get(1).getColuna()
					&& vizinhasNaoVisiveis.get(1).getColuna() == vizinhasNaoVisiveis.get(2).getColuna()) {

				this.mapa.getCampo()[x][y].setQualificada(true);
			}
		}

	}

	public void vasculhar(int i, int j) {
		int bandeirasUsaveis = 0;

		for (int o = 0; o < this.mapa.getCampo()[i][j].getVizinhas().size(); o++) {
			if (this.mapa.getCampo()[i][j].getVizinhas().get(o).isBandeira()) {
				bandeirasUsaveis++;
			}
		}
		if (bandeirasUsaveis == this.mapa.getCampo()[i][j].getQtdBombasVizinhas()) {
			for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
				if (!this.mapa.getCampo()[i][j].getVizinhas().get(k).isBandeira()
						&& !this.mapa.getCampo()[i][j].getVizinhas().get(k).isVisivel()) {
					int linha = this.mapa.getCampo()[i][j].getVizinhas().get(k).getLinha();
					int coluna = this.mapa.getCampo()[i][j].getVizinhas().get(k).getColuna();
					this.mapa.escolherPosicao(linha, coluna);
				}
			}
			this.teste = true;
		} else {
			this.escolher(i, j);
			if (this.mapa.getCampo()[i][j].isQualificada()) {
				for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
					if (this.mapa.getCampo()[i][j].getVizinhas().get(k).isQualificada()
							&& this.mapa.getCampo()[i][j].getVizinhas().get(k)
									.getQtdBombasVizinhas() != this.mapa.getCampo()[i][j].getQtdBombasVizinhas()) {
							Celula x = this.mapa.getCampo()[i][j];
							Celula y = this.mapa.getCampo()[i][j].getVizinhas().get(k);
//						if(x.getNaoVisivel().get(1) == y.getNaoVisivel().get(0) || x.getNaoVisivel().get(0) == y.getNaoVisivel().get(1))
//							this.casoEspecial1(this.mapa.getCampo()[i][j], this.mapa.getCampo()[i][j].getVizinhas().get(k));
					}
				}
			}
		}
	}

	public void procurar() {
		int i = 0, j = 0;

		for (i = 0; i < this.tamanho; i++) {
			for (j = 0; j < this.tamanho; j++) {
				int bandeira = 0;
				int celulaNaoVisivel = 0;
				int bombasProximas = this.mapa.getCampo()[i][j].getQtdBombasVizinhas();

				if (this.mapa.getCampo()[i][j].isVisivel() && this.mapa.getCampo()[i][j].getQtdBombasVizinhas() != 0) {
					ArrayList<Celula> proximasNaoVisiveis = new ArrayList<Celula>();

					for (int y = 0; y < this.mapa.getCampo()[i][j].getVizinhas().size(); y++) {
						if (!this.mapa.getCampo()[i][j].getVizinhas().get(y).isVisivel()) {
							if (this.mapa.getCampo()[i][j].getVizinhas().get(y).isBandeira()) {
								bandeira++;
							} else {
								proximasNaoVisiveis.add(this.mapa.getCampo()[i][j].getVizinhas().get(y));
								celulaNaoVisivel++;
							}
						}
					}
					if (this.comparar(bombasProximas, bandeira) == bombasProximas) {
						for (int x = 0; x < proximasNaoVisiveis.size(); x++) {
							proximasNaoVisiveis.get(x).setBandeira(true);
							this.teste = true;
						}
					} else {
						this.vasculhar(i, j);
					}
				}
			}
		}
	}

	

}
