package br.com.trabalho.util;

public class ErroMessenger {
	String mensagem;
	RuntimeException erro;
	
	public ErroMessenger(String mensagem, RuntimeException erro) {
		super();
		this.mensagem = mensagem;
		this.erro = erro;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public RuntimeException getErro() {
		return erro;
	}
	public void setErro(RuntimeException erro) {
		this.erro = erro;
	}
}
