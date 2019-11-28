package br.com.poli.campoMinado.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class BotaoPlay extends JButton {

	private static final long serialVersionUID = 1L;
	private int linha;
	private int coluna;

	public BotaoPlay(int linha, int coluna) {
		
		this.linha = linha;
		this.coluna = coluna;
		//Defini a fonte
		this.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		//O background
		this.setBackground(new Color(41, 10, 74));
		//Dimensão
		this.setPreferredSize(new Dimension(10, 10));

		

	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

}
