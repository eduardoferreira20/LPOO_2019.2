package br.com.poli.campoMinado.mapa;

public class MapaDificil extends Mapa {
	
	public static final int TAMANHO = 32;
	public static final int BOMBAS = 99;

	public MapaDificil() {
		super(TAMANHO, BOMBAS);
	}

	public static int getTamanho() {
		return TAMANHO;
	}

	public static int getBombas() {
		return BOMBAS;
	}
}
