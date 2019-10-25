package br.com.poli.campoMinado;

public class Main {

	public static void main(String[] args) {
		
		CampoMinado c = new CampoMinado("Eduardo", Dificuldade.DIFICIL);
		c.getMapa().imprimeTela(false);
		System.out.println("---------------------------------------------");
		c.getMapa().escolherPosicao(1, 3);
		System.out.println("---------------------------------------------");
		c.getMapa().imprimeTela(true);
	}

}
