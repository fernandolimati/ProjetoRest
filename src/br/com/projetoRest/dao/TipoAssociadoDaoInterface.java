package br.com.projetoRest.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.projetoRest.entidade.EntidadeTipoAssociado;

public interface TipoAssociadoDaoInterface {
	 	
		public int incluir(EntidadeTipoAssociado tipo)throws SQLException;
	    public void atualizar(EntidadeTipoAssociado tipo)throws SQLException;
	    public void excluir(int codigo)throws SQLException;
	    public EntidadeTipoAssociado consultar(int codigo)throws SQLException;
	    public ArrayList<EntidadeTipoAssociado> listar()throws SQLException;
	    
}
