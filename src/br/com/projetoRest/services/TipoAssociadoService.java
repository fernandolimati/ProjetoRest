package br.com.projetoRest.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.projetoRest.dao.TipoAssociadoDao;
import br.com.projetoRest.entidade.EntidadeTipoAssociado;
import br.com.projetoRest.model.NivelPermissao;
import br.com.projetoRest.seguranca.Seguro;

@Path("/tipoassociado")
public class TipoAssociadoService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	private TipoAssociadoDao tipoAssociadoDAO;
	
	@PostConstruct
	private void init(){tipoAssociadoDAO = new TipoAssociadoDao();}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<EntidadeTipoAssociado> listar() {
		List<EntidadeTipoAssociado> lista = null;
		try {
			lista = tipoAssociadoDAO.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@GET
	@Path("/buscar/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public EntidadeTipoAssociado buscarPorId(@PathParam("id") int idNota) {
		EntidadeTipoAssociado obj = null;
		try {
			obj = tipoAssociadoDAO.consultar(idNota);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return obj;
	}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@POST
	@Path("/adicionar")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public int adicionar(EntidadeTipoAssociado obj) {
		int idGerado = 0;
		try {
			idGerado = tipoAssociadoDAO.incluir(obj);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return idGerado;
	}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@POST
	@Path("/atualizar")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public int atualizar(EntidadeTipoAssociado obj) {
		int res = 0;
		try {
			res = tipoAssociadoDAO.atualizar(obj);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@GET
	@Path("/remover/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public int removerPorId(@PathParam("id") int idNota) {
		int obj = 0;
		try {
			obj = tipoAssociadoDAO.excluir(idNota);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return obj;
	}
	
	
	
}
