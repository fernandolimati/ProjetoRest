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
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<EntidadeTipoAssociado> listar() {
		List<EntidadeTipoAssociado> lista = null;
		try {
			lista = tipoAssociadoDAO.listar();
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return obj;
	}
	
	@Seguro({NivelPermissao.NIVEL_1})
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public int adicionar(EntidadeTipoAssociado obj) {
		int idGerado = 0;
		try {
			idGerado = tipoAssociadoDAO.incluir(obj);
		} catch (Exception e) {
		}
		return idGerado;
	}
	
}
