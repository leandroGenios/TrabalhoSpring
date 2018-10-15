package br.com.trabalho.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.com.trabalho.facade.ClienteFacade;
import br.com.trabalho.model.Cliente;
import br.com.trabalho.util.Retorno;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	private ClienteFacade facade = new ClienteFacade();

	@RequestMapping(value = "", method = RequestMethod.GET)
	 public ModelAndView init(Model model) {
		Retorno retorno = facade.listClientes();
		
		if(retorno.getErro() != null) {
			
			
			Client c = Client.create();
		    WebResource wr = c.resource("http://localhost:8080/ServidorRest/rest/pessoas");
		    String json = wr.get(String.class);
		    Gson gson = new Gson();
		    Pessoa p = gson.fromJson(json, new TypeToken<Pessoa>(){}.getType());
			
			System.out.println(p.getNome());
			System.out.println(p.getEmail());
			
			model.addAttribute("erro", retorno.getErro());
			return new ModelAndView("erro");
		}else {
			model.addAttribute("clientes", retorno.getObjeto());
			model.addAttribute("cliente", new Cliente());
			return new ModelAndView("clientes");
		}
	 }

	@RequestMapping(value = "/setCliente", method = RequestMethod.POST)
	public ModelAndView setCliente(@ModelAttribute("cliente")Cliente cliente, Model model) {
		Retorno retorno = facade.setCliente(cliente);
		if(retorno.getErro() != null) {
			model.addAttribute("erro", retorno.getErro());
			return new ModelAndView("erro");
		}else {
			model.addAttribute("page", "clientes");
			return new ModelAndView("success");
		}
	}

	@RequestMapping(value = "/editaCliente", method = RequestMethod.POST)
	public ModelAndView editaCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
		Retorno retorno = facade.verificaCpf(cliente);
		
		if(retorno.getErro() != null) {
			model.addAttribute("erro", retorno.getErro());
			return new ModelAndView("erro");
		}else {
			retorno = facade.editaCliente(cliente);
			if(retorno.getErro() != null) {
				model.addAttribute("erro", retorno.getErro());
				return new ModelAndView("erro");
			}else {
				model.addAttribute("page", "clientes");
				return new ModelAndView("success");
			}
		}
	}

	@RequestMapping(value = "/excluirCliente", method = RequestMethod.POST)
	public ModelAndView excluirCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
		Retorno retorno = facade.excluirCliente(cliente.getId());
		if(retorno.getErro() != null) {
			model.addAttribute("erro", retorno.getErro());
			return new ModelAndView("erro");
		}else {
			model.addAttribute("page", "clientes");
			return new ModelAndView("success");
		}
	}
}
