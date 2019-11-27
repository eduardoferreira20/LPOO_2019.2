package br.com.poli.campoMinado;

import java.util.Random;

import javax.swing.JOptionPane;

import br.com.poli.campoMinado.mapa.*;
import br.com.poli.campoMinado.mapa.Celula;

import java.util.ArrayList;
import java.util.List;

public class Resolvedor {

	private Mapa mapa;
	private int tamanho;
	private boolean verificar;
	
	//Construtor
	public Resolvedor(Mapa mapa) {
		this.mapa = mapa;
		this.tamanho = this.mapa.getCampo().length;
		this.verificar = true;
	}
	
	//Roda todo o laço para resolver o tabuleiro
	public void resolver() {
		
		int linha = new Random().nextInt(this.tamanho);
		int coluna = new Random().nextInt(this.tamanho);
		
		this.mapa.escolherPosicao(linha, coluna);
		this.removerBandeiras();
		
		while (!this.mapa.isGanhouJogo()) {
			
			if (this.verificar) {
				
				this.verificar = false;
				this.vasculhar();
				
			} else {
				
				this.selecionarCasoEspcifico();
				
				if(!this.verificar) {
					
				System.out.println("Error");
				break;
				}
			}
		}

		System.out.println();
		this.mapa.imprimeTela(false);

	}
	//Limpa as bandeiras do tabuleiro, caso o jogador tenha colocado antes de usar o resolvedor
	private void removerBandeiras() {
		for (int i = 0; i < mapa.getCampo().length; i++) {
			for(int j = 0; j< mapa.getCampo().length; j++) {
				if(mapa.getCelula(i, j).isBandeira())
					mapa.getCelula(i, j).setBandeira(false);
			}
		}
	}
	//Usado para calcular a diferença de bombas e bandeiras vizinhas
	public int diferenca(int x, int y) {
		if (x > y) {
			return x - y;
		} else {
			return y - x;
		}
	}

	public void vasculhar() {
		int i = 0, j = 0;
		//Verifica todo o tabuleiro
		for (i = 0; i < this.tamanho; i++) {
			for (j = 0; j < this.tamanho; j++) {
				int bombasVizinhas = this.mapa.getCampo()[i][j].getQtdBombasVizinhas();
				int casasProxNaoVisivel = 0;
				int bandeiraPerto = 0;
				
				//Caso ele encontre uma posição visível
				if (this.mapa.getCampo()[i][j].isVisivel() && this.mapa.getCampo()[i][j].getQtdBombasVizinhas() != 0) {

					ArrayList<Celula> vizinhasNaoVisiveis = new ArrayList<Celula>();
					//for que vasculha as casas vizinhas
					for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
						//Caso ele encontre uma casa vizinha não visivel, ele verifica se essa casa é uma bandeira, se for, ele adiciona ao contador
						if (!this.mapa.getCampo()[i][j].getVizinhas().get(k).isVisivel()) {
							
							if (this.mapa.getCampo()[i][j].getVizinhas().get(k).isBandeira()) {
								
								bandeiraPerto++;
							
							} else {
								//Caso não seja visivel e não seja uma bandeira,ele adiciona essa posição no ArrayList para colocar bandeira
								vizinhasNaoVisiveis.add(this.mapa.getCampo()[i][j].getVizinhas().get(k));
								
								casasProxNaoVisivel++;
							}
						}

					}
					//Caso o número de bombas e bandeiras próximas seja igual a casas não visiveis, quer dizer que são bombas
					if (this.diferenca(bombasVizinhas, bandeiraPerto) == casasProxNaoVisivel) {

						for (int k = 0; k < vizinhasNaoVisiveis.size(); k++) {
							
							vizinhasNaoVisiveis.get(k).setBandeira(true);
							
							this.mapa.getCampo()[i][j].getBandeirasVisinhas().add(vizinhasNaoVisiveis.get(k));
							
							this.verificar = true;
						}
					}else{
						this.limpar(i, j);
					}
				}
			}
		}
	}

	public void limpar(int i, int j) {
		int bandeiras = 0;
		//Verificar ao redor da posição selecionada, para procurar bandeiras.
		for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
			//Caso seja bandeira,adicionar ao contador
			if (this.mapa.getCampo()[i][j].getVizinhas().get(k).isBandeira()) {
				
				bandeiras++;
			}
		}//Se o número de bandeiras for igual ao de bombas ao redor
		if (bandeiras == this.mapa.getCampo()[i][j].getQtdBombasVizinhas()) {
			//Vasculha as casas ao redor
			for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
				//Se a posição não for uma bandeira e não for visivel, ele seleciona essa posição
				if (!this.mapa.getCampo()[i][j].getVizinhas().get(k).isBandeira()
						&& !this.mapa.getCampo()[i][j].getVizinhas().get(k).isVisivel()) {

					int linha = this.mapa.getCampo()[i][j].getVizinhas().get(k).getLinha();
					int coluna = this.mapa.getCampo()[i][j].getVizinhas().get(k).getColuna();
					
					this.mapa.escolherPosicao(linha, coluna);

				}
			}
			this.verificar = true;
		}
		else {
			this.selecionar(i, j);
		}
	}

	public void selecionar(int i, int j) {
		ArrayList<Celula> casasNaoVisiveis = this.mapa.getCampo()[i][j].getNaoVisivel();
		casasNaoVisiveis.clear();
		//verifica a casa ao redor, caso ela não seja visivei, ele adiciona ao ArrayList
		for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
			
			if (!this.mapa.getCampo()[i][j].getVizinhas().get(k).isVisivel()) {
				
				casasNaoVisiveis.add(this.mapa.getCampo()[i][j].getVizinhas().get(k));
			}
		//Quando o número de casas não visiveis chegar a três, ele vai verificar e confirmar se ele é um caso específico
		}if (casasNaoVisiveis.size() == 3) {
			//ele vai verificar tanto a linha como a coluna
			if (casasNaoVisiveis.get(0).getLinha() == casasNaoVisiveis.get(1).getLinha()
					&& casasNaoVisiveis.get(1).getLinha() == casasNaoVisiveis.get(2).getLinha()) {
				
				this.mapa.getCampo()[i][j].setQualificada(true);
				this.mapa.getCampo()[i][j].setCasoQualifiacao(1);
				
			} else if (casasNaoVisiveis.get(0).getColuna() == casasNaoVisiveis.get(1).getColuna()
					&& casasNaoVisiveis.get(1).getColuna() == casasNaoVisiveis.get(2).getColuna()) {
				
				this.mapa.getCampo()[i][j].setQualificada(true);
				this.mapa.getCampo()[i][j].setCasoQualifiacao(1);
			}
			//Mesmo caso de de quando for 3 casas
		} else if (casasNaoVisiveis.size() == 2) {

			if (casasNaoVisiveis.get(0).getLinha() == casasNaoVisiveis.get(1).getLinha()) {
				this.mapa.getCampo()[i][j].setQualificada(true);
				this.mapa.getCampo()[i][j].setCasoQualifiacao(2);

			} else if (casasNaoVisiveis.get(0).getColuna() == casasNaoVisiveis.get(1).getColuna()) {
				
				this.mapa.getCampo()[i][j].setQualificada(true);
				this.mapa.getCampo()[i][j].setCasoQualifiacao(2);
			}
		} else {
			
			this.mapa.getCampo()[i][j].setQualificada(false);
		}
	}
	//Após fazer a seleção, ele vai aplicar o metodo para cada tipo de caso
	public void selecionarCasoEspcifico() {
		
		for (int i = 0; i < this.tamanho; i++) {
			for (int j = 0; j < this.tamanho; j++) {
				
				if (this.mapa.getCampo()[i][j].isQualificada()) {
					
					if (this.mapa.getCampo()[i][j].getCasoQualifiacao() == 1) {
						
						this.verificarPrimeiroCasoEspecial(i, j);

					} else if (this.mapa.getCampo()[i][j].getCasoQualifiacao() == 2) {
						
						this.verificarSegundoCasoEspecial(i, j);
					}
				}
			}
		}
	}
	//Separa em dois tipos de Celula, uma com mais bom e a outra com menos bombas
	public void primeiroCasoEspecial(Celula a, Celula b) {
//Procura a celula com mais bomba e indica a sua posição
		Celula maisBomba = a;
		Celula menosBomba = b;
		
		if (b.getQtdBombasVizinhas() > a.getQtdBombasVizinhas()) {
			
			maisBomba = b;
			menosBomba = a;
		}
		if (maisBomba.getNaoVisivel().get(0) != menosBomba.getNaoVisivel().get(1)) {
			
			maisBomba.getNaoVisivel().get(0).setBandeira(true);
			
			int linha = menosBomba.getNaoVisivel().get(2).getLinha();
			int coluna = menosBomba.getNaoVisivel().get(2).getColuna();
			//Abre a casa que não tem bomba
			this.mapa.escolherPosicao(linha, coluna);

		} else {
			//Adiciona bandeira caso não seja visivel
			maisBomba.getNaoVisivel().get(2).setBandeira(true);
			
			int linha = menosBomba.getNaoVisivel().get(0).getLinha();
			int coluna = menosBomba.getNaoVisivel().get(0).getColuna();

			this.mapa.escolherPosicao(linha, coluna);
		
		}
		this.verificar = true;
	}

	public void verificarPrimeiroCasoEspecial(int i, int j) {
		
		for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
			
			int bombasFaltandoA = this.diferenca(this.mapa.getCampo()[i][j].getQtdBombasVizinhas(),
					this.mapa.getCampo()[i][j].getBandeirasVisinhas().size());
			
			int bombasFaltandoB = this.diferenca(this.mapa.getCampo()[i][j].getVizinhas().get(k).getQtdBombasVizinhas(),
					this.mapa.getCampo()[i][j].getVizinhas().get(k).getBandeirasVisinhas().size());

			if (this.mapa.getCampo()[i][j].getVizinhas().get(k).getCasoQualifiacao() == 1
					&& bombasFaltandoA != bombasFaltandoB) {
				
				Celula a = this.mapa.getCampo()[i][j];
				Celula b = this.mapa.getCampo()[i][j].getVizinhas().get(k);
				
				if (a.getNaoVisivel().get(1) == b.getNaoVisivel().get(0) || a.getNaoVisivel().get(0) == b.getNaoVisivel().get(1))
					
					this.primeiroCasoEspecial(a, b);
			}
		}
	}

	public void verificarSegundoCasoEspecial(int i, int j) {
		
		for (int k = 0; k < this.mapa.getCampo()[i][j].getVizinhas().size(); k++) {
			
			int bombasFaltandoA = this.diferenca(this.mapa.getCampo()[i][j].getQtdBombasVizinhas(),
					this.mapa.getCampo()[i][j].getBandeirasVisinhas().size());
			
			int bombasFaltandoB = this.diferenca(this.mapa.getCampo()[i][j].getVizinhas().get(k).getQtdBombasVizinhas(),
					this.mapa.getCampo()[i][j].getVizinhas().get(k).getBandeirasVisinhas().size());
			
			if (this.mapa.getCampo()[i][j].getVizinhas().get(k).getCasoQualifiacao() == 1) {
				
				Celula a = this.mapa.getCampo()[i][j];
				Celula b = this.mapa.getCampo()[i][j].getVizinhas().get(k);
				
				if (a.getNaoVisivel().get(1) == b.getNaoVisivel().get(0)) {
					
					if (bombasFaltandoA == bombasFaltandoB) {
						
						this.segundoCasoEspecial(a, b, true);
						
					} else {
						
						this.segundoCasoEspecial(a, b, false);
					}
				}
			}
		}
	}

	public void segundoCasoEspecial(Celula a, Celula b, boolean bombasfaltandoiguais) {
		// b TEM MENOS CASAS (DUAS)
		// a TEM MAIS CASAS (TRES)
		if (bombasfaltandoiguais) {
			if (b.getNaoVisivel().get(0) == a.getNaoVisivel().get(0)) {
				int linha = b.getNaoVisivel().get(2).getLinha();
				int coluna = b.getNaoVisivel().get(2).getColuna();
				this.mapa.escolherPosicao(linha, coluna);
			} else {
				int linha = b.getNaoVisivel().get(0).getLinha();
				int coluna = b.getNaoVisivel().get(0).getColuna();
				this.mapa.escolherPosicao(linha, coluna);
			}
		} else {
			if (b.getNaoVisivel().get(0) == a.getNaoVisivel().get(0)) {
				b.getNaoVisivel().get(2).setBandeira(true);

			} else {
				b.getNaoVisivel().get(0).setBandeira(true);
			}
		}

		this.verificar = true;

	}
}
