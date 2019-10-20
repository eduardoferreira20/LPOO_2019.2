package br.com.poli.campoMinado.mapa;

import br.com.poli.campoMinado.Dificuldade;

public class MapaMedio extends Mapa{
	
	public static final Dificuldade TAMANHO = Dificuldade.MEDIO;
	public static final int BOMBAS = 40;

	public MapaMedio() {
		super(TAMANHO.getValor(),BOMBAS);
	}

	public static Dificuldade getTamanho() {
		return TAMANHO;
	}

	public static int getBombas() {
		return BOMBAS;
	}
}
