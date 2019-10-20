package br.com.poli.campoMinado.mapa;

//Import da Bbiblioteca Random usada para gerar posições aleatorias
import java.util.Random;

import br.com.poli.campoMinado.Celula;
import br.com.poli.campoMinado.Dificuldade;

public abstract class Mapa {
	// Atributos da classe Mapa
	private Celula[][] campo;

	// Constutor
	public Mapa(int bombas, int tamanho) {

		this.campo = new Celula[tamanho][tamanho];

		inicializarCelula();
		distribuirBombas(bombas);
		contarBombas();

	}

	public void inicializarCelula() {

		for (int i = 0; i < campo.length; i++) {
			for (int j = 0; j < campo.length; j++) {
				this.campo[i][j] = new Celula(false, false, false, 0);// inicializa a celula
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
				this.campo[posicaoX][posicaoY].setQtdBombasVizinhas(-1);
				bomba++;
			}
		}
	}

	public void contarBombas() {

		for (int linha = 0; linha < this.campo.length; linha++) { // procura por bombas no mapa
			for (int coluna = 0; coluna < this.campo.length; coluna++) {
				// se ele achar bomba no mapa, ele entra nessa condição
				if (this.campo[linha][coluna].isBomba()) {

					for (int i = -1; i <= 1; i++) {
						for (int j = -1; j <= 1; j++) {

							// verifica se está na borda
							if (!(linha + i < 0 || linha + i > this.campo.length - 1 || coluna + j < 0
									|| coluna + j > this.campo.length - 1)) {
								/*
								 * se não for bomba, soma +1 na quantidade de bombas vizinhas
								 */
								if (this.campo[linha + i][coluna + j].isBomba() == false) {//
									this.campo[linha + i][coluna + j].setQtdBombasVizinhas(
											this.campo[linha + i][coluna + j].getQtdBombasVizinhas() + 1);
								}
							}

						}

					}

				}
			}
		}
	}

	public void escolherPosicao(int linha, int coluna) {

		if (this.campo[linha][coluna].isVisivel() == false) {
			if (this.campo[linha][coluna].isBomba()) {
				this.campo[linha][coluna].setVisivel(true);
				System.out.println("Fim de jogo. Você perdeu!!");
			}

			else if (this.campo[linha][coluna].getQtdBombasVizinhas() != 0) {
				this.campo[linha][coluna].setVisivel(true);

			}


			this.imprimeTela(false);
		}
	}

	// mostra o campo minado pronto
	public void imprimeTela(boolean teste) {

		for (int i = 0; i < this.campo.length; i++) {
			for (int j = 0; j < this.campo.length; j++) {

				System.out.print(" ");

				if (teste == true) {

					if (!this.campo[i][j].isBomba())
						System.out.print(" ");
					System.out.print(this.campo[i][j].getQtdBombasVizinhas());
				}

				else {

					if (this.campo[i][j].isVisivel()) {
						if (!this.campo[i][j].isBomba())
							System.out.print(" ");
						System.out.print(this.campo[i][j].getQtdBombasVizinhas());
					} else
						System.out.print(" -");
				}
			}
			System.out.println();
		}

	}

}
