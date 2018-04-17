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

import br.com.projetoRest.dao.ItemPedidoDao;
import br.com.projetoRest.entidade.EntidadeItemPedido;
import br.com.projetoRest.model.NivelPermissao;
import br.com.projetoRest.seguranca.Seguro;

@Path("/itempedido")
public class ItemPedidoService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	private ItemPedidoDao itemPedidoDao;
	
	@PostConstruct
	private void init(){itemPedidoDao = new ItemPedidoDao();}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<EntidadeItemPedido> listar() {
		List<EntidadeItemPedido> lista = null;
		try {
			lista = itemPedidoDao.listar();
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
	public ArrayList<EntidadeItemPedido> buscarPorId(@PathParam("id") int id) {
		ArrayList<EntidadeItemPedido> obj = new ArrayList<EntidadeItemPedido>();
		try {
			obj = itemPedidoDao.consultar(id);
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
	public int adicionar(EntidadeItemPedido obj) {
		int idGerado = 0;
		try {
			idGerado = itemPedidoDao.incluir(obj);
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
	public int atualizar(EntidadeItemPedido obj) {
		int res = 0;
		try {
			res = itemPedidoDao.atualizar(obj);
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
			obj = itemPedidoDao.excluir(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return obj;
	}
}
