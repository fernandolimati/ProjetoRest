package br.com.projetoRest.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.projetoRest.entidade.EntidadeItemPedido;

public interface ItemPedidoInterface {
	public void incluir(EntidadeItemPedido tipo,Connection cnn)throws SQLException;
	public int incluir(EntidadeItemPedido tipo)throws SQLException;
	
    public int atualizar(EntidadeItemPedido tipo)throws SQLException;
    public void atualizar(EntidadeItemPedido tipo, Connection cnn) throws SQLException;
    
    public int excluir(int codigo)throws SQLException;
    public void excluirPorId(int codigo, Connection cnn) throws SQLException;
    public void excluirPorPedido(int codigoPedido,Connection cnn)throws SQLException;
    
    public ArrayList<EntidadeItemPedido> consultar(int codigo)throws Exception;
    public ArrayList<EntidadeItemPedido> listar()throws Exception;
}
