package br.com.poli.campoMinado.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import br.com.poli.campoMinado.Resolvedor;
import br.com.poli.campoMinado.jogo.Dificuldade;
import br.com.poli.campoMinado.mapa.Celula;
import br.com.poli.campoMinado.mapa.Mapa;
import br.com.poli.campoMinado.mapa.MapaDificil;
import br.com.poli.campoMinado.mapa.MapaFacil;
import br.com.poli.campoMinado.mapa.MapaMedio;
import br.com.poli.campoMinado.rank.Ranking;
import br.com.poli.campoMinado.gui.BotaoPlay;
import br.com.poli.campoMinado.rank.*;

import javax.swing.LayoutStyle.ComponentPlacement;

public class Jogo extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private int numeroBombas;
	private JPanel painelJogo;
	private JPanel painelCelulas;
	private Mapa mapa;
	private Menu menu;
	private Dificuldade dificuldade;
	private BotaoPlay[][] botoes;
	private JButton botaoSair;
	private ImageIcon iconBandeira;
	private JLabel[][] lblNumeros;
	private JLabel lblNumeroBombas;
	private Contador timer;
	private ImageIcon iconBomba;
	private ImageIcon iconBotao;
	private ImageIcon iconContador;
	private JLayeredPane layeredPane;
	private String nomeJogador;
	private Ranking rank;
	private JLabel lblIconContador;
	private boolean primeiraJogada;
	private JButton btnSolucao;
	private Resolvedor resolver;

	public Jogo(Dificuldade dificuldade, String nomeJogador) {
		this.primeiraJogada = true;
		this.nomeJogador = nomeJogador;
		this.dificuldade = dificuldade;
		iniciarJogo(this.dificuldade);
		mapa.imprimeTela(true);
	}

	private void configuraFonteNumeros(JLabel lblNumeros) {

		switch (dificuldade) {
		case FACIL:
			lblNumeros.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
			break;
		case MEDIO:
			lblNumeros.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
			break;
		case DIFICIL:
			lblNumeros.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			break;
		}

	}
	//Confidura cor da fonte do jogo
	private void configurarNumeros(int i, int j) {
		lblNumeros[i][j].setText(Integer.toString(this.mapa.getCelula(i, j).getQtdBombasVizinhas()));

		switch (this.mapa.getCelula(i, j).getQtdBombasVizinhas()) {
		case 1:
			lblNumeros[i][j].setForeground(Color.BLUE.brighter().brighter());
			break;
		case 2:
			lblNumeros[i][j].setForeground(Color.GRAY.darker());
			break;
		case 3:
			lblNumeros[i][j].setForeground(Color.RED);
			break;
		case 4:
			lblNumeros[i][j].setForeground(Color.BLUE.darker().darker());
			break;
		case 5:
			lblNumeros[i][j].setForeground(Color.RED.darker());
			break;
		case 6:
			lblNumeros[i][j].setForeground(Color.GRAY);
			break;
		case 7:
			lblNumeros[i][j].setForeground(Color.YELLOW);
			break;
		case 8:
			lblNumeros[i][j].setForeground(Color.CYAN);
			break;
		}
	}

	private void configurarIcones() {
		switch (dificuldade) {
		case FACIL:
			iconBomba = new ImageIcon("./resources/images/iconBombaFaciL.jpg");
			iconBandeira = new ImageIcon("./resources/images/iconBandeiraFacil.jpg");
			break;
		case MEDIO:
			iconBomba = new ImageIcon("./resources/images/iconBombaMedio.jpg");
			iconBandeira = new ImageIcon("./resources/images/iconBandeiraMedio.jpg");
			break;
		case DIFICIL:
			iconBomba = new ImageIcon("./resources/images/iconBombaDificil.jpg");
			iconBandeira = new ImageIcon("./resources/images/iconBandeiraDificil.jpg");
			break;
		}
		iconContador = new ImageIcon("./resources/images/iconBombaMedio.jpg");
		this.setIconImage(iconBomba.getImage());
	}
	//Mostra a quantidade de bombas vizinhas
	private void montarLabelNumeros() {

		lblNumeros = new JLabel[this.dificuldade.getValor()][this.dificuldade.getValor()];

		Border borda = BorderFactory.createLineBorder(Color.black.brighter().brighter(), 2);

		for (int i = 0; i < lblNumeros.length; i++) {
			for (int j = 0; j < lblNumeros.length; j++) {
				lblNumeros[i][j] = new JLabel("", SwingConstants.CENTER);
				lblNumeros[i][j].setVisible(true);
				//Configura a fonte a partir da fonte usada
				configuraFonteNumeros(lblNumeros[i][j]);
				//Adiciona painel
				this.painelCelulas.add(lblNumeros[i][j]);
				if (this.mapa.getCelula(i, j).isBomba() == false) {
					if (this.mapa.getCelula(i, j).isEmBranco() == false) {
						configurarNumeros(i, j);
					} else {
						this.lblNumeros[i][j].setOpaque(true);
						this.lblNumeros[i][j].setBackground(Color.GRAY.brighter());
					}
				} else {
					//Se for bomba deixa opaco,fundo branco e adiciona o icone da bomba
					lblNumeros[i][j].setOpaque(true);
					lblNumeros[i][j].setBackground(Color.WHITE);
					lblNumeros[i][j].setIcon(iconBomba);
				}
				lblNumeros[i][j].setBorder(borda);
				lblNumeros[i][j].setVisible(false);
			}
		}
	}

	private void montarTela() {

		this.setTitle("Campo Minado");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);
		this.setBounds(0, 0, 935, 935);
		this.configurarIcones();
		this.montarLayeredPane();
		this.PainelSuperior();
		this.PainelJogo(this.dificuldade);
		this.BotoesJogo();
		this.atualizarNumeroBombas();
		pack();
		this.setLocationRelativeTo(null);

	}
	//Monta um painel com outros paineis
	private void montarLayeredPane() {
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane, BorderLayout.NORTH);
		layeredPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));

		layeredPane.setOpaque(true);
		layeredPane.setBackground(Color.WHITE.darker());
	}
	//Monta o painel superios com o tempo,bombas,botão de sair e o IA
	public void PainelSuperior() {
		timer = new Contador();
		JPanel painelSuperior = new JPanel();

		painelSuperior.setBounds(6, 11, 922, 69);
		layeredPane.add(painelSuperior);
		botaoSair = new JButton("Sair");
		botaoSair.addActionListener(this);
		lblNumeroBombas = new JLabel();
		lblNumeroBombas.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumeroBombas.setFont(timer.getFont());
		lblIconContador = new JLabel();
		lblIconContador.setIcon(iconContador);

		btnSolucao = new JButton("Desistir");
		btnSolucao.addActionListener(this);

		GroupLayout gl_painelSuperior = new GroupLayout(painelSuperior);
		gl_painelSuperior.setHorizontalGroup(gl_painelSuperior.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_painelSuperior.createSequentialGroup().addContainerGap()
						.addComponent(botaoSair).addPreferredGap(ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
						.addComponent(btnSolucao).addPreferredGap(ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
						.addComponent(timer, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE).addGap(278)
						.addComponent(lblIconContador, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNumeroBombas, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
						.addGap(38)));
		gl_painelSuperior.setVerticalGroup(gl_painelSuperior.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelSuperior.createSequentialGroup().addGroup(gl_painelSuperior
						.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_painelSuperior.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING,
										gl_painelSuperior.createSequentialGroup().addGap(10).addComponent(
												lblIconContador, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
								.addGroup(Alignment.LEADING,
										gl_painelSuperior.createSequentialGroup().addContainerGap()
												.addGroup(gl_painelSuperior.createParallelGroup(Alignment.LEADING)
														.addComponent(botaoSair, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(btnSolucao).addComponent(timer,
																GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE))))
						.addGroup(gl_painelSuperior.createSequentialGroup().addGap(27).addComponent(lblNumeroBombas)))
						.addContainerGap()));
		painelSuperior.setLayout(gl_painelSuperior);
	}
	//Painel do jogo
	private void PainelJogo(Dificuldade dificuldade) {

		// Painel do jogo
		this.painelJogo = new JPanel(new GridLayout(dificuldade.getValor(), dificuldade.getValor()));
		painelJogo.setBounds(12, 91, 911, 731);
		painelJogo.setOpaque(false);
		layeredPane.add(painelJogo);

		// Painel dos numeros de casas
		this.painelCelulas = new JPanel(new GridLayout(dificuldade.getValor(), dificuldade.getValor()));
		painelCelulas.setBounds(12, 91, 911, 731);
		painelCelulas.setOpaque(false);
		layeredPane.add(painelCelulas);

	}
	//Botões selecionaveis do campo minado
	private void BotoesJogo() {

		botoes = new BotaoPlay[dificuldade.getValor()][dificuldade.getValor()];

		for (int i = 0; i < botoes.length; i++) {
			for (int j = 0; j < botoes.length; j++) {
				botoes[i][j] = new BotaoPlay(i, j);

				botoes[i][j].addActionListener(this);
				botoes[i][j].addMouseListener(this);
				botoes[i][j].setIcon(iconBotao);
				this.painelJogo.add(botoes[i][j]);

			}
		}

	}
	//Inicializa o mapa baseado na dificuldade escolhida pelo jogador
	private void iniciarJogo(Dificuldade dificuldade) {

		switch (dificuldade) {
		case FACIL:
			this.mapa = new MapaFacil();
			break;
		case MEDIO:
			this.mapa = new MapaMedio();
			break;
		case DIFICIL:
			this.mapa = new MapaDificil();
			break;
		}
		this.numeroBombas = mapa.getBombas();
		this.montarTela();

	}
	//Atualiza o número de bombas no contador
	private void atualizarNumeroBombas() {
		lblNumeroBombas.setText(Integer.toString(this.numeroBombas));
	}

	private void botaoPlayActionPerformed(ActionEvent e) {
		BotaoPlay botao = (BotaoPlay) e.getSource();
		System.out.println(botao.getLinha() + " " + botao.getColuna());

		mapa.escolherPosicao(botao.getLinha(), botao.getColuna()); // ESCOLHE A POSICAO DO BOTAO APERTADO

		atualizarTela();

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	// coloca e tira a bandeira do campo
	public void mouseReleased(MouseEvent e) {

		BotaoPlay botao = (BotaoPlay) e.getSource();

		if (SwingUtilities.isRightMouseButton(e)) {

			if (celulaEscolhida(botao).isBandeira() == false) {
				if (this.numeroBombas > 0) {
					celulaEscolhida(botao).setBandeira(true);
					botao.setIcon(iconBandeira);
					botao.removeActionListener(this);
					this.numeroBombas--;
				}

			} else {
				celulaEscolhida(botao).setBandeira(false);
				botao.addActionListener(this);
				botao.setIcon(iconBotao);
				this.numeroBombas++;
			}
			this.atualizarNumeroBombas();

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof BotaoPlay) {
			botaoJogoActionPerformed(e);
		} else if (e.getSource() == botaoSair) {
			this.voltarMenu();
		} else if (e.getSource() == btnSolucao) {
			resolver = new Resolvedor(this.mapa);
			resolver.resolver();
			atualizarNumeroBombas();

			atualizarTela();

		}

	}

	private void atualizarTela() {
		if (this.primeiraJogada == true) {
			this.primeiraJogada = false;
			this.montarLabelNumeros();
		}

		this.esconderBotao();

		this.acabarJogo();
	}

	private void voltarMenu() {
		menu = new Menu();
		menu.setVisible(true);
		this.dispose();
	}

	private void esconderBotao() {
		// For para olhar todos os botoes
		for (int i = 0; i < dificuldade.getValor(); i++) {
			for (int j = 0; j < dificuldade.getValor(); j++) {
				// Se a celula e o botao forem visiveis, esconde o botao 
				if (celulaEscolhida(botoes[i][j]).isVisivel() && botoes[i][j].isVisible()) {

					botoes[i][j].setVisible(false);
					lblNumeros[i][j].setVisible(true);
				}
			}
		}
	}
	//Ao final do jogo, abre um arquivo ranking específico para a dificuldade escolhida e salva os dados nele
	private void iniciarRanking() {
		switch (this.dificuldade) {
		case FACIL:
			rank = new RankingFacil(new Jogador(this.nomeJogador, timer.getTempo(), timer.getTempoTotal()));
			break;
		case MEDIO:
			rank = new RankingMedio(new Jogador(this.nomeJogador, timer.getTempo(), timer.getTempoTotal()));
			break;
		case DIFICIL:
			rank = new RankingDificil(new Jogador(this.nomeJogador, timer.getTempo(), timer.getTempoTotal()));
			break;
		}
		rank.iniciarRanking();
	}
	//Verifica a condição para o usuário ganhar ou perder o jogo
	private void acabarJogo() {
		if (mapa.isFimDeJogo() || mapa.isGanhouJogo()) {

			this.timer.paraRelogio();

			if (mapa.isFimDeJogo()) {
				JOptionPane.showMessageDialog(this, "VOCÊ PERDEU", "FIM DE JOGO", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, this.nomeJogador + "! ", "VOCÊ GANHOU",
						JOptionPane.INFORMATION_MESSAGE);
				iniciarRanking();
			}
			this.voltarMenu();

		}
	}

	private void botaoJogoActionPerformed(ActionEvent e) {
		BotaoPlay botao = (BotaoPlay) e.getSource();
		System.out.println(botao.getLinha() + " " + botao.getColuna());
		// Escolhe a posicao do botao selecionado
		mapa.escolherPosicao(botao.getLinha(), botao.getColuna());

		if (this.primeiraJogada == true) {
			this.primeiraJogada = false;
			this.montarLabelNumeros();
		}

		this.esconderBotao();
//		mapa.ganhar();
		this.acabarJogo();

	}

	private Celula celulaEscolhida(BotaoPlay botao) {
		// Retorna a celula da mesma posicao do botao
		return mapa.getCelula(botao.getLinha(), botao.getColuna());
	}
}
