package br.com.trabalho.util;

public class Retorno {
	Object objeto;
	ErroMessenger erro;
	
	public Object getObjeto() {
		return objeto;
	}
	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}
	public ErroMessenger getErro() {
		return erro;
	}
	public void setErro(ErroMessenger erro) {
		this.erro = erro;
	}
}
