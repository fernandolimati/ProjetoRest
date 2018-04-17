package br.com.projetoRest.services;

import java.util.ArrayList;
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

import br.com.projetoRest.dao.PedidoDao;
import br.com.projetoRest.entidade.EntidadePedido;
import br.com.projetoRest.model.NivelPermissao;
import br.com.projetoRest.seguranca.Seguro;

@Path("/pedido")
public class PedidoService {
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	private PedidoDao pedidoDao;
	
	@PostConstruct
	private void init(){pedidoDao = new PedidoDao();}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response listar() {
		List<EntidadePedido> lista = null;
		Gson teste = new Gson();
		String saida="";
		try {
			lista = pedidoDao.listar();
			saida = teste.toJson(lista);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Response.status(201).entity(saida).build();
	}
		
	@Seguro({NivelPermissao.NIVEL_1})
	@GET
	@Path("/buscar/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public EntidadePedido buscarPorId(@PathParam("id") int id) {
		EntidadePedido obj = null;
		try {
			obj = pedidoDao.consultar(id);
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
	public int adicionar(EntidadePedido obj) {
		int idGerado = 0;
		try {
			idGerado = pedidoDao.incluir(obj);
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
	public int atualizar(EntidadePedido obj) {
		int res = 0;
		try {
			res = pedidoDao.atualizar(obj);
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
	public int removerPorId(@PathParam("id") int id) {
		int obj = 0;
		try {
			obj = pedidoDao.excluir(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return obj;
	}
}
