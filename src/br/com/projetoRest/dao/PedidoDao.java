package br.com.projetoRest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.projetoRest.entidade.EntidadeAssociado;
import br.com.projetoRest.entidade.EntidadeItemPedido;
import br.com.projetoRest.entidade.EntidadePedido;
import br.com.projetoRest.entidade.EntidadeTipoAssociado;
import br.com.projetoRest.util.Conexao;
import br.com.projetoRest.util.DataHelper;

public class PedidoDao implements PedidoDaoInterface {
	
	@Override
	public boolean incluir(EntidadePedido tipo) throws Exception {
		
		int idGerado = 0;
        String sql = "INSERT INTO public.pedido(valortotalpedido, datahorapedido, codigoassociado) VALUES (?, now(), ?);";
        Connection cnn = Conexao.getConexao();
        
        cnn.setAutoCommit(false);
        try {
        	PreparedStatement prd = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            prd.setDouble(1, tipo.getValorTotalPedido());
            prd.setInt(2, tipo.getAssociado().getCodigo());
            
            prd.execute();
            
            ResultSet rs = prd.getGeneratedKeys();
    		if (rs.next())idGerado = rs.getInt(1);
    		
    		EntidadePedido pedido = new EntidadePedido(idGerado, tipo.getAssociado(), tipo.getValorTotalPedido(), tipo.getDataHoraPedido());
    		tipo.setId(idGerado);
    		
    		for(EntidadeItemPedido itemPedido: tipo.getListaItemPedido()) {
    			itemPedido.setPedido(pedido);
    			new ItemPedidoDao().incluir(itemPedido, cnn);
    		}
    		cnn.commit();
        }catch(Exception e){
        	cnn.rollback();
        	return false;
        }
        cnn.close();
        return true;
	}

	@Override
	public boolean atualizar(EntidadePedido tipo) throws SQLException {
		String sql = "UPDATE public.pedido SET valortotalpedido=?, datahorapedido=?, codigoassociado=? WHERE id=?;";

        Connection cnn = Conexao.getConexao();
        cnn.setAutoCommit(false);
        try {
        	 PreparedStatement prd = cnn.prepareStatement(sql);

             prd.setDouble(1, tipo.getValorTotalPedido());
             prd.setString(2, tipo.getDataHoraPedido().getDataString());
             prd.setInt(3, tipo.getAssociado().getCodigo());
             prd.setInt(4, tipo.getId());
             prd.execute();
                          
             for(EntidadeItemPedido itemPedido: tipo.getListaItemPedido()) {
            	 switch (itemPedido.getFlag()) {
				case DELETE:
					new ItemPedidoDao().excluirPorId(itemPedido.getIdItemPedido(),cnn);
					break;
				case INSERT:
					new ItemPedidoDao().incluir(itemPedido, cnn);
					break;
				case NORMAL:
					break;
				case UPDATE:
					new ItemPedidoDao().atualizar(itemPedido, cnn);
					break;
				}
             }
             
		} catch (Exception e) {
			cnn.rollback();
			cnn.close();
			return false;
		}
       
        cnn.close();
		return true;
	}

	@Override
	public boolean excluir(int codigo) throws SQLException {
		String sql = "DELETE FROM pedido WHERE codigo=?;";

        Connection cnn = Conexao.getConexao();
        cnn.setAutoCommit(false);
        try {
        	PreparedStatement prd = cnn.prepareStatement(sql);
            prd.setInt(1, codigo);
            prd.execute();
            
            new ItemPedidoDao().excluirPorPedido(codigo, cnn);
            
            cnn.commit();
		} catch (Exception e) {
			cnn.close();
			return false;
		}
        cnn.close();
        return true;
	}

	@Override
	public EntidadePedido consultar(int codigo) throws Exception {
		String sql = "SELECT * FROM view_pedido WHERE pedido_id = ?;";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);
        prd.setInt(1, codigo);
        ResultSet rs = prd.executeQuery();

        EntidadePedido tipo = new EntidadePedido();

        if (rs.next()) {
            tipo.setId(rs.getInt("pedido_id"));
            tipo.setValorTotalPedido(rs.getDouble("valortotalpedido"));
            tipo.setDataHoraPedido(new DataHelper(rs.getString("datahorapedido")));
            tipo.setAssociado(new EntidadeAssociado(rs.getInt("associado_codigo"), rs.getString("nome"), rs.getString("cpf"), rs.getString("endereco"), rs.getString("telefone"), new EntidadeTipoAssociado(rs.getInt("tipoassociado_codigo"), rs.getString("descricao"), rs.getDouble("valormensalidade"))));
            tipo.setListaItemPedido(new ItemPedidoDao().consultar(rs.getInt("pedido_id")));
        }
        cnn.close();
        return tipo;
	}

	@Override
	public ArrayList<EntidadePedido> listar() throws Exception {
		String sql = "SELECT * FROM view_pedido;";

        Connection cnn = Conexao.getConexao();
        Statement prd = cnn.createStatement();
        ResultSet rs = prd.executeQuery(sql);
        ArrayList<EntidadePedido> lista = new ArrayList<>();

        while (rs.next()) {
        	EntidadePedido tipo = new EntidadePedido();
        	tipo.setId(rs.getInt("pedido_id"));
            tipo.setValorTotalPedido(rs.getDouble("valortotalpedido"));
            tipo.setDataHoraPedido(new DataHelper(rs.getString("datahorapedido")));
            tipo.setAssociado(new EntidadeAssociado(rs.getInt("associado_codigo"), rs.getString("nome"), rs.getString("cpf"), rs.getString("endereco"), rs.getString("telefone"), new EntidadeTipoAssociado(rs.getInt("tipoassociado_codigo"), rs.getString("descricao"), rs.getDouble("valormensalidade"))));
            tipo.setListaItemPedido(new ItemPedidoDao().consultar(rs.getInt("pedido_id")));
            lista.add(tipo);
            
        }
        cnn.close();
        return lista;
	}

}
