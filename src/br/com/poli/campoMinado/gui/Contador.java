package br.com.poli.campoMinado.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Contador extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int s, m, tempoTotal;
	private String tempo = "00:00";
	private Timer timer;
	private Font fonteTempo;
	
	public Contador() {
		setHorizontalAlignment(SwingConstants.CENTER);
		
		criarFonte();
		setFont(fonteTempo.deriveFont(30f).deriveFont(Font.BOLD));
		setForeground(Color.BLACK);
		setText(tempo);
		setVisible(true);
		iniciaRelogio();

		
	}
	
	
	public String getTempo(){
		return tempo;
	}
	
	public int getTempoTotal() {
		return tempoTotal;
	}
	
	public String formato() {
		if(s==60) {
			s=0;
			m++;
		}
		tempo = m+":"+s;
		return tempo;
	}
	
	public void iniciaRelogio() {
		
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tempoTotal++;
				s++;
				setText(formato());
			}
		};
		timer = new Timer(1000, action);
		timer.setInitialDelay(1000);
		timer.setRepeats(true);
		timer.start();
	}
	public void paraRelogio() {
		timer.stop();
	}
	
	public void criarFonte() {
		try {
			fonteTempo = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/04B_21__.TTF"))
					.deriveFont(54f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/04B_21__.TTF")));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
	
}
