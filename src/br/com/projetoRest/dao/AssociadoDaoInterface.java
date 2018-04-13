package br.com.projetoRest.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.projetoRest.entidade.EntidadeAssociado;

public interface AssociadoDaoInterface {
	
	public int incluir(EntidadeAssociado tipo)throws SQLException;
    public int atualizar(EntidadeAssociado tipo)throws SQLException;
    public int excluir(int codigo)throws SQLException;
    public EntidadeAssociado consultar(int codigo)throws SQLException;
    public ArrayList<EntidadeAssociado> listar()throws SQLException;
    
}
