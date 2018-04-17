package br.com.projetoRest.util;

public class Teste2 {

	public static void main(String[] args) {
		String teste = "2018-05-16 21:56:54";
		DataHelper data = new DataHelper(teste);
		
		System.out.println(data.getAsString());

	}

}
