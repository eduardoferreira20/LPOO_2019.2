package br.com.poli.campoMinado.rank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Ranking {

	private Jogador novoJogador;
	private List<Jogador> listaJogadores;
	private File rank;

	public Ranking(Jogador novoJogador, File rank) {
		this.rank = rank;
		this.listaJogadores = new ArrayList<Jogador>();
		this.novoJogador = novoJogador;
	}
	
	public void iniciarRanking() {
		this.lerArquivo();
		this.criarLista();
		this.escreverNoArquivo();
		System.out.println(listaJogadores.toString());
	}

	private void criarLista() {
		listaJogadores.add(novoJogador);
		Collections.sort(listaJogadores, new CompararJogadores());
		//Se tiver mais de 5 itens na lista, ele exclui o ultimo
		if(listaJogadores.size() > 5) {
			for(int i = 5; i<listaJogadores.size();i++) {
				listaJogadores.remove(i);
			}
		}
		
	}

	private void escreverNoArquivo() {

		try {
			
			FileOutputStream fileOutput = new FileOutputStream(rank);
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(listaJogadores);
			objectOutput.close();
			System.out.println("Os dados foram salvos com sucesso");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private void lerArquivo() {
		try {
			FileInputStream fileInput = new FileInputStream(rank);
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			listaJogadores = (ArrayList<Jogador>) objectInput.readObject();
			objectInput.close();
			System.out.println(listaJogadores.toString());
			// se não tiver arquivo criado, ele sai do método
		}catch(FileNotFoundException f) {
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
