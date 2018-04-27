package br.com.projetoRest.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.projetoRest.entidade.EntidadePedido;

public interface PedidoDaoInterface {
	public boolean incluir(EntidadePedido tipo)throws Exception;
    public boolean atualizar(EntidadePedido tipo)throws SQLException;
    public boolean excluir(int codigo)throws SQLException;
    public EntidadePedido consultar(int codigo)throws Exception;
    public ArrayList<EntidadePedido> listar()throws Exception;
}
