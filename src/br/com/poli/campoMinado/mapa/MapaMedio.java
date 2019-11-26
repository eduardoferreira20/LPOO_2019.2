package br.com.poli.campoMinado.mapa;

public class MapaMedio extends Mapa {
	
	public static final int TAMANHO = 16;
	public static final int BOMBAS = 40;

	public MapaMedio() {
		super(TAMANHO,BOMBAS);
	}

	public static int getTamanho() {
		return TAMANHO;
	}
}
