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
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

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
	public Response listar() {
		List<EntidadeTipoAssociado> lista = null;
		String saida = "";
		try {
			lista = tipoAssociadoDAO.listar();
			saida = new Gson().toJson(lista);
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(201).entity(saida).build();
	}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@GET
	@Path("/buscar/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response buscarPorId(@PathParam("id") int id) {
		EntidadeTipoAssociado obj = null;
		String saida = "";
		try {
			obj = tipoAssociadoDAO.consultar(id);
			saida = new Gson().toJson(obj);
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(201).entity(saida).build();
	}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@POST
	@Path("/adicionar")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public Response adicionar(EntidadeTipoAssociado obj) {
		int idGerado = 0;
		try {
			idGerado = tipoAssociadoDAO.incluir(obj);
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(201).entity(idGerado).build();
	}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@POST
	@Path("/atualizar")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public Response atualizar(EntidadeTipoAssociado obj) {
		int res = 0;
		try {
			res = tipoAssociadoDAO.atualizar(obj);
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(201).entity(res).build();
	}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@GET
	@Path("/remover/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response removerPorId(@PathParam("id") int id) {
		int obj = 0;
		try {
			obj = tipoAssociadoDAO.excluir(id);
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(201).entity(obj).build();
	}
	
	
	
}
