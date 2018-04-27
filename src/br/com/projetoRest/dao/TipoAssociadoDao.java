package br.com.projetoRest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.projetoRest.entidade.EntidadeTipoAssociado;
import br.com.projetoRest.util.Conexao;




public class TipoAssociadoDao implements TipoAssociadoDaoInterface{

	@Override
    public int incluir(EntidadeTipoAssociado tipo) throws SQLException {
    	int idGerado = 0;
        String sql = "INSERT INTO tipoassociado (descricao, valormensalidade)"
                + " VALUES(?,?)";
        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        prd.setString(1, tipo.getDescricao());
        prd.setDouble(2, tipo.getValorMensalidade());
        prd.execute();
        
        ResultSet rs = prd.getGeneratedKeys();
		if (rs.next())idGerado = rs.getInt(1);
		
		cnn.close();
		
		return idGerado;
    }
	@Override
    public int atualizar(EntidadeTipoAssociado tipo) throws SQLException {

        String sql = "UPDATE tipoassociado SET"
                + " descricao=?, valormensalidade=?"
                + " WHERE codigo=?";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);

        prd.setString(1, tipo.getDescricao());
        prd.setDouble(2, tipo.getValorMensalidade());
        prd.setInt(3, tipo.getCodigo());

        int saida = prd.executeUpdate();
        cnn.close();
        
        return saida;

    }
	@Override
    public int excluir(int codigo) throws SQLException {
        String sql = "DELETE FROM tipoassociado "
                + "WHERE codigo=?;";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);

        prd.setInt(1, codigo);

        int saida = prd.executeUpdate();
        cnn.close();
        
        return saida;

    }
	
	@Override
	public EntidadeTipoAssociado consultar(int codigo) throws SQLException {
        String sql = "SELECT tb.codigo, tb.descricao, tb.valorMensalidade "
                + " FROM tipoassociado tb "
                + " WHERE tb.codigo=?";

        Connection cnn = Conexao.getConexao();
        PreparedStatement prd = cnn.prepareStatement(sql);
        prd.setInt(1, codigo);
        ResultSet rs = prd.executeQuery();

        EntidadeTipoAssociado tipo = new EntidadeTipoAssociado();

        if (rs.next()) {
            tipo.setCodigo(rs.getInt("codigo"));
            tipo.setDescricao(rs.getString("descricao"));
            tipo.setValorMensalidade(rs.getDouble("valorMensalidade"));
        }
        cnn.close();
        return tipo;

    }
	@Override
    public ArrayList<EntidadeTipoAssociado> listar() throws SQLException {
        String sql = "SELECT tb.codigo, tb.descricao, tb.valorMensalidade "
                + " FROM tipoassociado tb ORDER BY tb.codigo";

        Connection cnn = Conexao.getConexao();
        Statement prd = cnn.createStatement();
        ResultSet rs = prd.executeQuery(sql);
        ArrayList<EntidadeTipoAssociado> lista = new ArrayList<EntidadeTipoAssociado>();

        while (rs.next()) {
            EntidadeTipoAssociado tipo = new EntidadeTipoAssociado();
            tipo.setCodigo(rs.getInt("codigo"));
            tipo.setDescricao(rs.getString("descricao"));
            tipo.setValorMensalidade(rs.getDouble("valormensalidade"));
            lista.add(tipo);
        }
        cnn.close();
        return lista;

    }

}
