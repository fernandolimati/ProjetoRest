package br.com.projetoRest.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
    private static Connection conexao;
    
    public static Connection getConexao(){
        
        try {
            if(conexao == null || conexao.isClosed())
            	conexao = conectar();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return conexao;
    }
    
    private static Connection conectar() throws Exception{
        try {
            Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/sistemataxi","postgres","F#240924s");
    
        }catch (ClassNotFoundException e) {
        	throw new Exception("Nao foi encontrado a biblioteca postgres.");
        }catch (SQLException e){
        	throw new Exception("Banco/Usuario/Senha estaão erradas.");
        }
    }
}