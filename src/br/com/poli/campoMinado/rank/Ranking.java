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
		
		if(listaJogadores.size() > 5) {//SE TIVER MAIS DO QUE 5 ELEMENTOS NA LISTA ORDENADA, ELE EXCLUI OS ULTIMOS
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
			System.out.println("O Jogador foi salvo no arquivo");

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

		}catch(FileNotFoundException f) { //SE NAO TIVER ARQUIVO CRIADO ELE SAI DO MÉTODO
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
