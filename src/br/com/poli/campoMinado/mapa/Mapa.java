package br.com.poli.campoMinado.mapa;

//Import da Bbiblioteca Random usada para gerar posições aleatorias
import java.util.Random;

public abstract class Mapa {

	// Atributos
	private Celula[][] campo;

	private boolean fimDeJogo;
	private boolean ganhouJogo;
	private int celulasVisiveis;
	private int bombas;
	private boolean primeiraJogada;

	// Constutor
	public Mapa(int tamanho, int bombas) {

		this.fimDeJogo = false;
		this.ganhouJogo = false;
		this.bombas = bombas;
		this.primeiraJogada = true;
		this.campo = new Celula[tamanho][tamanho]; // inicializa a matriz campo com
													// o tamanho escolhido
		this.inicializarCelula();
		Celula.buscarVizinhos(campo);

	}

	public Celula[][] getCampo() {
		return campo;
	}

	public int getBombas() {
		return bombas;
	}

	public void setBombas(int bombas) {
		this.bombas = bombas;
	}

	public void setCampo(Celula[][] campo) {
		this.campo = campo;
	}

	public Celula getCelula(int linha, int coluna) {
		return campo[linha][coluna];
	}

	public boolean isFimDeJogo() {
		return this.fimDeJogo;
	}

	public boolean isGanhouJogo() {
		return this.ganhouJogo;
	}

	public void inicializarCelula() {

		for (int i = 0; i < campo.length; i++) {
			for (int j = 0; j < campo.length; j++) {
				this.campo[i][j] = new Celula(false,false,false,i, j);// inicializa a celula
			}
		}
	}

	// Distribui as bombas do mapa
	public void distribuirBombas(int bombas) {
		int contagem = 0;
		int posicaoX;
		int posicaoY;
		// Uso da biblioteca Random
		Random random = new Random();

		/*
		 * enquanto o numero de bombas for menor que o numero de bombas, ele vai rodar o
		 * while
		 */
		while (contagem < bombas) {

			// gera posições aleatorias para a posição das bombas
			posicaoX = random.nextInt(this.campo.length);
			posicaoY = random.nextInt(this.campo.length);

			/*
			 * caso a posição X e Y já tenha uma bomba, ele não entra no if e gera outra
			 * posição aleatória
			 */
			if (this.campo[posicaoX][posicaoY].isBomba() == false) {

				this.campo[posicaoX][posicaoY].setBomba(true);
				this.campo[posicaoX][posicaoY].setQtdBombasVizinhas(-1);
				contagem++;
			}
		}
	}
	

	public void distribuirBombas() {

		for (int linha = 0; linha < this.campo.length; linha++) { // procura por bombas no mapa
			for (int coluna = 0; coluna < this.campo.length; coluna++) {
				// se ele achar bomba no mapa, ele entra nessa condição
				if (this.campo[linha][coluna].isBomba()) {

					for (int i = -1; i <= 1; i++) {
						for (int j = -1; j <= 1; j++) {

							// verifica se está na borda
							if (!(linha + i < 0 || linha + i > this.campo.length - 1 || coluna + j < 0 || coluna + j > this.campo.length - 1)) {
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

	private void revelarEspacos(Celula celulaEscolhida) {
		
		// Só entra se for invisivel a célula
		if (celulaEscolhida.isVisivel() == false && celulaEscolhida.isBandeira() == false) {

			celulaEscolhida.setVisivel(true);
			this.celulasVisiveis++;
			
			//Se for uma célula em branco, ele entra na condição
			if (celulaEscolhida.isEmBranco()) {
				
				//Percorre as vizinhas da célula escolhida									 
				for (int i = 0; i < celulaEscolhida.getVizinhas().size(); i++) {
					this.revelarEspacos(celulaEscolhida.getVizinhas().get(i));
				}
			}
		}
	}
	
	//Verifica se o jogador ganhou o jogo
	private boolean verificarGanhouJogo() {
		if (this.celulasVisiveis >= (this.campo.length * this.campo.length) - this.bombas) {
			System.out.println("Você ganhou o jogo!!");
			this.fimDeJogo=true;
			return true;
		} else
			return false;
	}

	public void escolherPosicao(int linha, int coluna) {

		this.jogadaInicial(linha, coluna);

		// OLHA SE A POSICAO ESCOLHIDA TA DENTRO DA MATRIZ
		if (linha >= 0 && linha < this.campo.length && coluna >= 0 && coluna < this.campo.length) {

			if (this.campo[linha][coluna].isVisivel() == false) { // SO VAI PODER ESCOLHERPOSICAO SE A CELULA NAO FOR
																	// VISIVEL
				if (this.campo[linha][coluna].isBomba()) {// SE FOR BOMBA
					this.campo[linha][coluna].setVisivel(true);
					this.celulasVisiveis++;
					this.fimDeJogo = true; // CASO SELECIONE UMA BOMBA, O JOGO ACABA

					for (int i = 0; i < this.campo.length; i++)
						for (int j = 0; j < this.campo.length; j++)
							this.campo[i][j].setVisivel(true);

					System.out.println("Fim de jogo. Você perdeu!!");
				}

				else if (this.campo[linha][coluna].isEmBranco() == false) {// SE NAO FOR BOMBA NEM 0
					this.campo[linha][coluna].setVisivel(true);
					this.celulasVisiveis++;

				}

				else {// SE FOR EM BRANCO
					this.revelarEspacos(this.campo[linha][coluna]);
				}

				this.imprimeTela(false);// DPS QUE ESCOLHE A POSICAO IMPRIME A TELA DE COMO FICOU
				this.ganhouJogo = this.verificarGanhouJogo();
			}
		}

	}

	// mostra o campo minado pronto
	public void imprimeTela(boolean teste) {
		System.out.println("CAMPO DE TAMANHO: "+this.campo.length+"x"+this.campo.length);
		for (int i = 0; i < this.campo.length; i++) {
			for (int j = 0; j < this.campo.length; j++) {

				System.out.print(" ");
				// caso seja "true", ele mostra todo o mapa com a localizações de cada bomba
				if (teste == true) {

					if (!this.campo[i][j].isBomba())
						System.out.print(" ");
					System.out.print(this.campo[i][j].getQtdBombasVizinhas());
				}

				else {

					if (this.campo[i][j].isBandeira()) {
						System.out.print(" b");
					} else if (this.campo[i][j].isVisivel()) {
						if (!this.campo[i][j].isBomba())
							System.out.print(" ");
						System.out.print(this.campo[i][j].getQtdBombasVizinhas());
					} else
						System.out.print(" *");
				}
			}
			System.out.println();
		}

	}
	
	private void jogadaInicial(int linha, int coluna) {
		if (this.primeiraJogada == true) {
			this.primeiraJogada = false;
			this.campo[linha][coluna].setCelulaInicial(true);
			for (int i = 0; i < this.campo[linha][coluna].getVizinhas().size(); i++)
				this.campo[linha][coluna].getVizinhas().get(i).setCelulaInicial(true);
			
			this.distribuirBombas(this.bombas);
			this.contarBombas();
		}
	}
	
	public void contarBombas() {

		for (int linha = 0; linha < this.campo.length; linha++) { // PROCURANDO BOMBAS
			for (int coluna = 0; coluna < this.campo.length; coluna++) {
				if (this.campo[linha][coluna].isBomba()) { // ACHOU

					for (int i = -1; i <= 1; i++) {// OLHA AO REDOR DA BOMBA
						for (int j = -1; j <= 1; j++) {

							if (!(linha + i < 0 || linha + i > this.campo.length - 1 || coluna + j < 0
									|| coluna + j > this.campo.length - 1)) { // VERIFICA SE ESTA DENTRO DA MATRIZ
								if (this.campo[linha + i][coluna + j].isBomba() == false) {// SE NAO FOR BOMBA, SOMA +1
																							// NA QNTDD DE BOMBAS
																							// VIZINHAS
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

}
