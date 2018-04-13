package br.com.projetoRest.entidade;

import java.util.ArrayList;
import java.util.Date;

public class EntidadePedido {
	
	private int id;
	private double valorTotalPedido;
	private Date dataHoraPedido;
	ArrayList<EntidadeItemPedido> listaItemPedido;
	
	public EntidadePedido(int id, double valorTotalPedido, Date dataHoraPedido) {
		super();
		this.id = id;
		this.valorTotalPedido = valorTotalPedido;
		this.dataHoraPedido = dataHoraPedido;
	}
	public EntidadePedido(double valorTotalPedido, Date dataHoraPedido) {
		super();
		this.valorTotalPedido = valorTotalPedido;
		this.dataHoraPedido = dataHoraPedido;
	}
	public EntidadePedido() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getValorTotalPedido() {
		return valorTotalPedido;
	}
	public void setValorTotalPedido(double valorTotalPedido) {
		this.valorTotalPedido = valorTotalPedido;
	}
	public Date getDataHoraPedido() {
		return dataHoraPedido;
	}
	public void setDataHoraPedido(Date dataHoraPedido) {
		this.dataHoraPedido = dataHoraPedido;
	};
	
}
