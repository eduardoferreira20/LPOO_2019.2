package br.com.poli.campoMinado;

import br.com.poli.campoMinado.jogo.CampoMinado;
import br.com.poli.campoMinado.jogo.Dificuldade;

public class Main {

	public static void main(String[] args) {
		
		CampoMinado campo = new CampoMinado("Eduardo", Dificuldade.DIFICIL);
		Resolvedor r = new Resolvedor(campo.getMapa());
		r.resolver();
//		campo.getMapa().imprimeTela(false);
//		campo.iniciarJogo();
	}

}
