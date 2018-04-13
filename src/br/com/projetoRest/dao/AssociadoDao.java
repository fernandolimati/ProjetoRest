package br.com.projetoRest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.projetoRest.entidade.EntidadeAssociado;
import br.com.projetoRest.entidade.EntidadeTipoAssociado;
import br.com.projetoRest.util.Conexao;

public class AssociadoDao implements AssociadoDaoInterface{

	@Override
	public int incluir(EntidadeAssociado tipo) throws SQLException {
		int idGerado = 0;
        String sql = "INSERT INTO public.associado(nome, endereco, telefone, codigotipoassociado, cpf) VALUES (?, ?, ?, ?, ?);";
        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        prd.setString(1, tipo.getNome());
        prd.setString(2, tipo.getEndereco());
        prd.setString(3, tipo.getTelefone());
        prd.setInt   (4, tipo.getTipoAssociado().getCodigo());
        prd.setString(5, tipo.getCpf());
        prd.execute();
        
        ResultSet rs = prd.getGeneratedKeys();
		if (rs.next())idGerado = rs.getInt(1);
		
		return idGerado;
	}

	@Override
	public int atualizar(EntidadeAssociado tipo) throws SQLException {
		String sql = "UPDATE associado SET nome=?, endereco=?, telefone=?, codigotipoassociado=?, cpf=?"
                + " WHERE codigo=?";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);

        prd.setString(1, tipo.getNome());
        prd.setString(2, tipo.getEndereco());
        prd.setString(3, tipo.getTelefone());
        prd.setInt   (4, tipo.getTipoAssociado().getCodigo());
        prd.setString(5, tipo.getCpf());
        prd.setInt   (6, tipo.getCodigo());
        
        return prd.executeUpdate();
	}

	@Override
	public int excluir(int codigo) throws SQLException {
		String sql = "DELETE FROM associado WHERE codigo=?;";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);

        prd.setInt(1, codigo);

        return prd.executeUpdate();
	}

	@Override
	public EntidadeAssociado consultar(int codigo) throws SQLException {
		String sql = "SELECT * FROM associado"
                + " WHERE codigo=?";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);
        prd.setInt(1, codigo);
        ResultSet rs = prd.executeQuery();

        EntidadeAssociado tipo = new EntidadeAssociado();

        if (rs.next()) {
            tipo.setCodigo(rs.getInt("codigo"));
            tipo.setNome(rs.getString("nome"));
            tipo.setEndereco(rs.getString("endereco"));
            tipo.setTelefone(rs.getString("telefone"));
            tipo.setTipoAssociado(new TipoAssociadoDao().consultar(rs.getInt("codigotipoassociado")));
            tipo.setCpf(rs.getString("cpf"));
        }
        return tipo;
	}

	@Override
	public ArrayList<EntidadeAssociado> listar() throws SQLException {
		String sql = "SELECT * FROM view_associado;";

        Connection cnn = Conexao.getConexao();
        Statement prd = cnn.createStatement();
        ResultSet rs = prd.executeQuery(sql);
        ArrayList<EntidadeAssociado> lista = new ArrayList<EntidadeAssociado>();

        while (rs.next()) {
        	EntidadeAssociado tipo = new EntidadeAssociado();
        	tipo.setCodigo(rs.getInt("codigo"));
            tipo.setNome(rs.getString("nome"));
            tipo.setEndereco(rs.getString("endereco"));
            tipo.setTelefone(rs.getString("telefone"));
            tipo.setTipoAssociado(new EntidadeTipoAssociado(rs.getInt("codigotipoassociado"),rs.getString("descricao"),rs.getDouble("valormensalidade")));
            tipo.setCpf(rs.getString("cpf"));
            lista.add(tipo);
        }
        return lista;
	}

}
