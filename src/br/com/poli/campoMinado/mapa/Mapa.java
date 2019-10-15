package br.com.poli.campoMinado.mapa;

//Import da Bbiblioteca Random usada para gerar posições aleatorias
import java.util.Random;

import br.com.poli.campoMinado.Celula;
import br.com.poli.campoMinado.Dificuldade;

abstract class Mapa {
	// Atributos da classe Mapa
	private Celula[][] campo;

	// Constutor
	public Mapa(int bombas, int tamanho) {

		inicializarCelula();
		distribuirBombas(bombas);
		contarBombas();

	}

	/*
	 * Getters e Setters
	 */

	public void inicializarCelula() {

		for (int i = 0; i < campo.length; i++) {
			for (int j = 0; j < campo.length; j++) {
				this.campo[i][j].setQtdBombasVizinhas(0);
				this.campo[i][j].setBomba(false);
				this.campo[i][j].setBandeira(false);
				this.campo[i][j].setVisivel(false);
			}
		}
	}

	// Distribui as bombas do mapa
	public void distribuirBombas(int bombas) {
		int bomba = 0;
		// Uso da biblioteca Random
		Random random = new Random();

		/*
		 * enquanto o numero de bombas for menor que o numero de bombas, ele vai rodar o
		 * while
		 */
		while (bomba < bombas) {

			// gera posições aleatorias para a posição das bombas
			int posicaoX = random.nextInt(this.campo.length);
			int posicaoY = random.nextInt(this.campo.length);

			/*
			 * caso a posição X e Y já tenha uma bomba, ele não entra no if e gera outra
			 * posição aleatória
			 */
			if (this.campo[posicaoX][posicaoY].isBomba() == false) {

				this.campo[posicaoX][posicaoY].setBomba(true);
				bomba++;
			}
		}
	}
	//precisa retornar um int
	public void contarBombas() {
		for(int i = 0; i < campo.length;i++) {
			for(int j = 0; j < campo.length; j++) {
				if(this.campo[i+1][j].isBomba()==true && this.campo[i][j+1].isBomba()==true) {
					this.campo[i][j].setQtdBombasVizinhas(2);
				}
			}
		}
	}
	public void escolherPosicao(int linha, int coluna) {
		
		if(this.campo[linha][coluna].isBomba() == true) {
			
			System.out.println("Fim de Jogo. Você perdeu!");
			
		}else if(this.campo[linha][coluna].isBomba() == false) {
			
			this.campo[linha][coluna].setVisivel(true);
		}
	}

	// mostra o campo minado pronto
	public void imprimeTela(boolean teste) {
		
		if (teste == false) {
			for (int i = 0; i < campo.length; i++) {
				for (int j = 0; j < campo.length; j++) {
					
					if (this.campo[i][j].isVisivel() == true) {
						
						System.out.print("  " + this.campo[i][j] + "  ");
					}
				}
				System.out.println();
			}
		} else {
			for (int i = 0; i < campo.length; i++) {
				for (int j = 0; j < campo.length; j++) {

					System.out.print("  " + this.campo[i][j] + "  ");
				}
				System.out.println();
			}
		}

	}

}
