package br.com.poli.campoMinado;

public class Main {

	public static void main(String[] args) {
		
		CampoMinado campo = new CampoMinado("Eduardo", Dificuldade.FACIL);
		campo.getMapa().imprimeTela(true);
		campo.iniciarJogo();
	}

}
