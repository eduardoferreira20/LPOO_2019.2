package br.com.poli.campoMinado.mapa;

public class MapaFacil extends Mapa {

	private static final int TAMANHO = 9;
	private static final int BOMBAS = 10;
	
	public MapaFacil() {
		super(TAMANHO,BOMBAS);
		
	}

	public static int getTamanho() {
		return TAMANHO;
	}
	
}
