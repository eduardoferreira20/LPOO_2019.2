package br.com.poli.campoMinado;

import br.com.poli.campoMinado.jogo.CampoMinado;
import br.com.poli.campoMinado.jogo.Dificuldade;

public class Main {

	public static void main(String[] args) {
		
		CampoMinado campo = new CampoMinado("Eduardo", Dificuldade.FACIL);
		campo.getMapa().imprimeTela(false);
		campo.iniciarJogo();
	}

}
