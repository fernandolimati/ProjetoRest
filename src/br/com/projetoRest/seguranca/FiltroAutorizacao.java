package br.com.projetoRest.seguranca;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.com.projetoRest.model.NivelPermissao;
import br.com.projetoRest.services.LoginService;

import javax.ws.rs.Priorities;

@Seguro
@Provider
@Priority(Priorities.AUTHORIZATION)
public class FiltroAutorizacao implements ContainerRequestFilter {
	
	
	@Context
	private ResourceInfo resourceInfo;
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Class<?> classe = resourceInfo.getResourceClass();
		List<NivelPermissao> nivelPermissaoClasse = extrairNivelPermissao(classe);

		Method metodo = resourceInfo.getResourceMethod();
		List<NivelPermissao> nivelPermisaoMetodo = extrairNivelPermissao(metodo);

		try {
			String login = requestContext.getSecurityContext().getUserPrincipal().getName();
			if (nivelPermisaoMetodo.isEmpty()) {
				checarPermissoes(nivelPermissaoClasse,login);
			} else {
				checarPermissoes(nivelPermisaoMetodo,login);
			}

		} catch (Exception e) {
			requestContext.abortWith(
					Response.status(Response.Status.FORBIDDEN).build());
		}
	}
	private List<NivelPermissao> extrairNivelPermissao(AnnotatedElement annotatedElement) {
		if (annotatedElement == null) {
			return new ArrayList<NivelPermissao>();
		} else {
			Seguro secured = annotatedElement.getAnnotation(Seguro.class);
			if (secured == null) {
				return new ArrayList<NivelPermissao>();
			} else {
				NivelPermissao[] allowedRoles = secured.value();
				return Arrays.asList(allowedRoles);
			}
		}
	}
	private void checarPermissoes(List<NivelPermissao> nivelPermissaoPermitidos,String login) throws Exception {
		try {
			if(nivelPermissaoPermitidos.isEmpty())
				return;
			
			boolean temPermissao = false;
			NivelPermissao nivelPermissaoUsuario = new LoginService().buscarNivelPermissao(login);
			
			for (NivelPermissao nivelPermissao : nivelPermissaoPermitidos) {
				if(nivelPermissao.equals(nivelPermissaoUsuario))
				{
					temPermissao = true;
					break;
				}
			}
			
			if(!temPermissao)
				throw new Exception("Cliente nÃo possui o nivel de permissão para consumir esse serviço.");
			
		} catch (Exception e) {
			throw e;
		}
	}
}


