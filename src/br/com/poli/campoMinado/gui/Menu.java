package br.com.poli.campoMinado.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.com.poli.campoMinado.jogo.*;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Jogo tela1;
	private Font fonte;
	private ImageIcon play;
	private JTextField txtNomeJogador;
	private JPanel panel;
	private JComboBox<String> comboBoxDificuldade;
	private JButton btnEntrar;
	private JLabel lblCampoMinado;
	private ImageIcon tela;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Menu() {

		configurarTela();

	}

	private void configurarTextField() {
		txtNomeJogador = new JTextField();
		txtNomeJogador.setBounds(389, 284, 140, 35);
		panel.add(txtNomeJogador);
		txtNomeJogador.setColumns(10);
		
		JLabel lblIndicarNome = new JLabel("Apelido:");
		lblIndicarNome.setForeground(Color.BLACK);
		lblIndicarNome.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblIndicarNome.setBounds(287, 281, 218, 35);
		panel.add(lblIndicarNome);
	}

	private void configurarLabel() {
		lblCampoMinado = new JLabel("<html>Campo Minado</html>");
		lblCampoMinado.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 54));
		lblCampoMinado.setFont(fonte);
		lblCampoMinado.setHorizontalAlignment(SwingConstants.CENTER);
		lblCampoMinado.setForeground(new Color(0, 10, 0));
		lblCampoMinado.setBounds(51, 52, 837, 151);
		panel.add(lblCampoMinado);
		
		
	}

	private void configurarBotaoEntrar() {

		btnEntrar = new JButton(play);
		btnEntrar.setOpaque(false);
		btnEntrar.setContentAreaFilled(false);
		btnEntrar.setBorderPainted(false);

		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nomeJogador = (txtNomeJogador.getText());
				if (!txtNomeJogador.getText().isEmpty() && txtNomeJogador.getText().length()<=10) {
						if (comboBoxDificuldade.getSelectedIndex() == 0) {
							tela1 = new Jogo(Dificuldade.FACIL, nomeJogador);
						} else if (comboBoxDificuldade.getSelectedIndex() == 1) {
							tela1 = new Jogo(Dificuldade.MEDIO, nomeJogador);
						} else if (comboBoxDificuldade.getSelectedIndex() == 2) {
							tela1 = new Jogo(Dificuldade.DIFICIL, nomeJogador);
						}
						tela1.setVisible(true);
						dispose();
				}
			}
		});

		btnEntrar.setBounds(376, 400, 167, 156);
		panel.add(btnEntrar);

	}

	private void configurarPanelPrincipal() {

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, "Campo Minado");
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));

	}

	private void configurarTela() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 935, 835);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY.darker());
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		this.setResizable(false);

		play = new ImageIcon("./resources/images/iconPlay.jpg");
		tela = new ImageIcon("./resources/images/iconBombaFacil.jpg");
		this.setIconImage(tela.getImage());
		this.setTitle("Campo Minado");
		
		criarFonte();

		configurarPanelPrincipal();

		criarComboBox();

		configurarBotaoEntrar();

		configurarLabel();

		configurarTextField();

		this.setLocationRelativeTo(null);

	}

	private void criarComboBox() {
		comboBoxDificuldade = new JComboBox<String>();
		comboBoxDificuldade.setModel(
				new DefaultComboBoxModel<String>(new String[] {"F\u00E1cil", "M\u00E9dio", "Dif\u00EDcil"}));
		comboBoxDificuldade.setBounds(389, 354, 140, 35);
		panel.add(comboBoxDificuldade);
	}

	private void criarFonte() {
		try {
			fonte = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/04B_31__.TTF"))
					.deriveFont(54f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/04B_31__.TTF")));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
}
