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

import br.com.projetoRest.dao.AssociadoDao;
import br.com.projetoRest.entidade.EntidadeAssociado;
import br.com.projetoRest.model.NivelPermissao;
import br.com.projetoRest.seguranca.Seguro;

@Path("/associado")
public class AssociadoService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	private AssociadoDao associadoDAO;
	
	@PostConstruct
	private void init(){associadoDAO = new AssociadoDao();}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<EntidadeAssociado> listar() {
		List<EntidadeAssociado> lista = null;
		try {
			lista = associadoDAO.listar();
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
	public EntidadeAssociado buscarPorId(@PathParam("id") int idAssociado) {
		EntidadeAssociado obj = null;
		try {
			obj = associadoDAO.consultar(idAssociado);
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
	public int adicionar(EntidadeAssociado obj) {
		int idGerado = 0;
		try {
			idGerado = associadoDAO.incluir(obj);
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
	public int atualizar(EntidadeAssociado obj) {
		int resultado = 0;
		if(obj.getCodigo() >0) {
			try {
				resultado = associadoDAO.atualizar(obj);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return resultado;
	}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@GET
	@Path("/remover/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public int removerPorId(@PathParam("id") int idAssociado) {
		int resp=-1;
		try {
			resp = associadoDAO.excluir(idAssociado);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resp;
	}
	
}
