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
import br.com.projetoRest.entidade.EntidadeProduto;
import br.com.projetoRest.entidade.EntidadeTipoAssociado;
import br.com.projetoRest.util.Conexao;
import br.com.projetoRest.util.DataHelper;

public class ItemPedidoDao implements ItemPedidoInterface {

	@Override
	public int incluir(EntidadeItemPedido tipo) throws SQLException {
		int idGerado = 0;
        String sql = "INSERT INTO public.itempedido(precomomento, quantidade, idproduto, idpedido) VALUES (?, ?, ?, ?);";
        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        prd.setDouble(1, tipo.getPrecoMomento());
        prd.setInt(2, tipo.getQuantidade());
        prd.setInt(3, tipo.getProduto().getId());
        prd.setInt(4, tipo.getPedido().getId());
        prd.execute();
        
        ResultSet rs = prd.getGeneratedKeys();
		if (rs.next())idGerado = rs.getInt(1);
		
		return idGerado;
	}

	@Override
	public int atualizar(EntidadeItemPedido tipo) throws SQLException {
		String sql = "UPDATE public.itempedido SET precomomento=?, quantidade=?, idproduto=?, idpedido=? WHERE id=?;";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);

        prd.setDouble(1, tipo.getPrecoMomento());
        prd.setInt(2, tipo.getQuantidade());
        prd.setInt(3, tipo.getProduto().getId());
        prd.setInt(4, tipo.getPedido().getId());
        prd.setInt(4, tipo.getId());
        
        return prd.executeUpdate();
	}

	@Override
	public int excluir(int codigo) throws SQLException {
		String sql = "DELETE FROM itempedido WHERE codigo=?;";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);

        prd.setInt(1, codigo);

        return prd.executeUpdate();
	}

	@Override
	public ArrayList<EntidadeItemPedido> consultar(int codigo) throws SQLException {
		String sql = "SELECT * FROM view_itempedido WHERE pedido_id = ?;";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);
        prd.setInt(1, codigo);
        ResultSet rs = prd.executeQuery();
        

        EntidadeItemPedido tipo = new EntidadeItemPedido();
        ArrayList<EntidadeItemPedido> lista = new ArrayList<EntidadeItemPedido>();

        while (rs.next()) {
            tipo.setId(rs.getInt("itempedido_id"));
            tipo.setPrecoMomento(rs.getDouble("precomomento"));
            tipo.setQuantidade(rs.getInt("quantidade"));
            EntidadeTipoAssociado tpAssociado = new EntidadeTipoAssociado(rs.getInt("tipoassociado_id"), rs.getString("descricao"), rs.getDouble("valormensalidade"));
            EntidadeAssociado associado = new EntidadeAssociado(rs.getInt("associado_id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("endereco"), rs.getString("telefone"), tpAssociado);
            tipo.setPedido(new EntidadePedido(associado, rs.getDouble("valortotalpedido"), new DataHelper(rs.getString("datahorapedido"))));
            tipo.setProduto(new EntidadeProduto(rs.getInt("produto_id"), rs.getString("nomeproduto"), rs.getDouble("valorvenda")));
            lista.add(tipo);
        }
        System.out.println(lista.size());
        return lista;
	}

	@Override
	public ArrayList<EntidadeItemPedido> listar() throws SQLException {
		String sql = "SELECT * FROM view_itempedido ORDER BY pedido_id;";

        Connection cnn = Conexao.getConexao();
        Statement prd = cnn.createStatement();
        ResultSet rs = prd.executeQuery(sql);
        ArrayList<EntidadeItemPedido> lista = new ArrayList<EntidadeItemPedido>();

        while (rs.next()) {
        	EntidadeItemPedido tipo = new EntidadeItemPedido();
        	tipo.setId(rs.getInt("itempedido_id"));
            tipo.setPrecoMomento(rs.getDouble("precomomento"));
            tipo.setQuantidade(rs.getInt("quantidade"));
            EntidadeTipoAssociado tpAssociado = new EntidadeTipoAssociado(rs.getInt("tipoassociado_id"), rs.getString("descricao"), rs.getDouble("valormensalidade"));
            EntidadeAssociado associado = new EntidadeAssociado(rs.getInt("associado_id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("endereco"), rs.getString("telefone"), tpAssociado);
            tipo.setPedido(new EntidadePedido(associado, rs.getDouble("valortotalpedido"), new DataHelper(rs.getString("datahorapedido"))));
            tipo.setProduto(new EntidadeProduto(rs.getInt("produto_id"), rs.getString("nomeproduto"), rs.getDouble("valorvenda")));
            lista.add(tipo);
        }
        return lista;
	}

}
