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

import br.com.projetoRest.dao.ProdutoDao;
import br.com.projetoRest.entidade.EntidadeProduto;
import br.com.projetoRest.model.NivelPermissao;
import br.com.projetoRest.seguranca.Seguro;

@Path("/produto")
public class ProdutoService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	private ProdutoDao produtoDao;
	
	@PostConstruct
	private void init(){produtoDao = new ProdutoDao();}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<EntidadeProduto> listar() {
		List<EntidadeProduto> lista = null;
		try {
			lista = produtoDao.listar();
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
	public EntidadeProduto buscarPorId(@PathParam("id") int id) {
		EntidadeProduto obj = null;
		try {
			obj = produtoDao.consultar(id);
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
	public int adicionar(EntidadeProduto obj) {
		int idGerado = 0;
		try {
			idGerado = produtoDao.incluir(obj);
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
	public int atualizar(EntidadeProduto obj) {
		int res = 0;
		try {
			res = produtoDao.atualizar(obj);
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
			obj = produtoDao.excluir(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return obj;
	}
}
