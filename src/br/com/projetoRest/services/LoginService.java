package br.com.projetoRest.services;

import java.util.Calendar;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import br.com.projetoRest.model.Credencial;
import br.com.projetoRest.model.NivelPermissao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/login")
public class LoginService {
	private final static String FRASE_SEGREDO =  "aplicacaoClubeFaculdade";

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response fazerLogin(String crendenciaisJson){
		try {
			Gson gson = new Gson();
			Credencial crendencial = gson.fromJson(crendenciaisJson, Credencial.class);
			validarCrendenciais(crendencial);
			String token = gerarToken(crendencial.getLogin(),1);
			return Response.ok(token).build();
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	private void validarCrendenciais(Credencial crendencial) throws Exception {
		try {
			if(!crendencial.getLogin().equals("fernando") || !crendencial.getSenha().equals("123"))
				throw new Exception("Crendencias não validas!");
		} catch (Exception e) {
			throw e;
		}

	}
	private String gerarToken(String login,Integer expiraEmDias){
		SignatureAlgorithm algoritimoAssinatura = SignatureAlgorithm.HS512;
		Date agora = new Date();
		Calendar expira = Calendar.getInstance();
		expira.add(Calendar.DAY_OF_MONTH, expiraEmDias);
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(FRASE_SEGREDO);
		SecretKeySpec key = new SecretKeySpec(apiKeySecretBytes, algoritimoAssinatura.getJcaName());
		JwtBuilder construtor = Jwts.builder()
				.setIssuedAt(agora)
				.setIssuer(login)
				.signWith(algoritimoAssinatura, key)
				.setExpiration(expira.getTime());

		return construtor.compact();
	}

	public  Claims validaToken(String token) {
		try{
			 Claims claims = Jwts.parser()         
					.setSigningKey(DatatypeConverter.parseBase64Binary(FRASE_SEGREDO))
					.parseClaimsJws(token).getBody();
			 return claims;
		}catch(Exception ex){
			throw ex;
		}
	}
	public NivelPermissao buscarNivelPermissao(String login) {return NivelPermissao.NIVEL_1;}
}
