package br.com.projetoRest.entidade;

public class EntidadeTipoAssociado {

    private int codigo;
    private String descricao;
    private Double valorMensalidade;

    public EntidadeTipoAssociado() {}
    
    public EntidadeTipoAssociado(int codigo, String descricao, Double valorMensalidade) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.valorMensalidade = valorMensalidade;
	}

	public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorMensalidade() {
        return valorMensalidade;
    }

    public void setValorMensalidade(Double valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }
}
