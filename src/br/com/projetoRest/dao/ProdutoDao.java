package br.com.projetoRest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.projetoRest.entidade.EntidadeProduto;
import br.com.projetoRest.util.Conexao;

public class ProdutoDao implements ProdutoDaoInterface {

	@Override
	public int incluir(EntidadeProduto tipo) throws SQLException {
		int idGerado = 0;
        String sql = "INSERT INTO public.produto(nomeproduto, valorvenda) VALUES (?, ?);";
        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        prd.setString(1, tipo.getNomeProduto());
        prd.setDouble(2, tipo.getValorVenda());

        prd.execute();
        
        ResultSet rs = prd.getGeneratedKeys();
		if (rs.next())idGerado = rs.getInt(1);
		
		return idGerado;
	}

	@Override
	public int atualizar(EntidadeProduto tipo) throws SQLException {
		String sql = "UPDATE public.produto SET nomeproduto=?, valorvenda=?	WHERE id=?;";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);

        prd.setString(1, tipo.getNomeProduto());
        prd.setDouble(2, tipo.getValorVenda());
        prd.setInt(3, tipo.getId());
        
        return prd.executeUpdate();
	}

	@Override
	public int excluir(int codigo) throws SQLException {
		String sql = "DELETE FROM produto WHERE codigo=?;";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);

        prd.setInt(1, codigo);

        return prd.executeUpdate();
	}

	@Override
	public EntidadeProduto consultar(int codigo) throws SQLException {
		String sql = "SELECT * FROM produto WHERE codigo=?";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);
        prd.setInt(1, codigo);
        ResultSet rs = prd.executeQuery();

        EntidadeProduto tipo = new EntidadeProduto();

        if (rs.next()) {
            tipo.setId(rs.getInt("codigo"));
            tipo.setNomeProduto(rs.getString("nomeproduto"));
            tipo.setValorVenda(rs.getDouble("valorvenda"));
        }
        return tipo;
	}

	@Override
	public ArrayList<EntidadeProduto> listar() throws SQLException {
		String sql = "SELECT * FROM produto;";

        Connection cnn = Conexao.getConexao();
        Statement prd = cnn.createStatement();
        ResultSet rs = prd.executeQuery(sql);
        ArrayList<EntidadeProduto> lista = new ArrayList<EntidadeProduto>();

        while (rs.next()) {
        	EntidadeProduto tipo = new EntidadeProduto();
        	tipo.setId(rs.getInt("id"));
            tipo.setNomeProduto(rs.getString("nomeproduto"));
            tipo.setValorVenda(rs.getDouble("valorvenda"));
            lista.add(tipo);
        }
        return lista;
	}

}
