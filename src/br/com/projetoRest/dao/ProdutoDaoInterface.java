package br.com.projetoRest.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.projetoRest.entidade.EntidadeProduto;

public interface ProdutoDaoInterface {
	public int incluir(EntidadeProduto tipo)throws SQLException;
    public int atualizar(EntidadeProduto tipo)throws SQLException;
    public int excluir(int codigo)throws SQLException;
    public EntidadeProduto consultar(int codigo)throws SQLException;
    public ArrayList<EntidadeProduto> listar()throws SQLException;
}
