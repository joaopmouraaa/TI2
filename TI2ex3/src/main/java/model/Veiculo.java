package model;

public class Veiculo {
	private int codigo;
	private String modelo;
	private String placa;
	private int ano;
	private String cor;
	
	public Veiculo() {
		this.codigo = -1;
		this.modelo = "";
		this.placa = "";
		this.ano = 0;
		this.cor = "";
	}
	
	public Veiculo(int codigo, String modelo, String placa, int ano, String cor) {
		this.codigo = codigo;
		this.modelo = modelo;
		this.placa = placa;
		this.ano = ano;
		this.cor = cor;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}

	@Override
	public String toString() {
		return "Veiculo [Codigo=" + codigo + ", Modelo=" + modelo + ", Placa=" + placa + ", Ano=" + ano + ", Cor=" + cor + "]";
	}	
}
