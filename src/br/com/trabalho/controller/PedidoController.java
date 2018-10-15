package br.com.trabalho.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.trabalho.facade.ClienteFacade;
import br.com.trabalho.facade.PedidoFacade;
import br.com.trabalho.facade.ProdutoFacade;
import br.com.trabalho.model.Cliente;
import br.com.trabalho.model.ItemDoPedido;
import br.com.trabalho.model.Pedido;
import br.com.trabalho.model.Produto;
import br.com.trabalho.util.ErroMessenger;
import br.com.trabalho.util.Retorno;

@Controller
public class PedidoController {
	private PedidoFacade facade = new PedidoFacade();
	private List<ItemDoPedido> itens = new ArrayList<>();
	
	@RequestMapping(value = "/pedidos")
	public ModelAndView init(@RequestParam(value="cpf", required=false) String cpf,
							 @RequestParam(value="item", required=false) String item,
							 Model model) {
		itens.clear();
		if(cpf != null) {
			model.addAttribute("cpf", cpf);
			Retorno retorno = facade.listPedidos(cpf);
			if(retorno.getErro() != null) {
				model.addAttribute("erro", retorno.getErro());
				return new ModelAndView("erro");
			}else {
				model.addAttribute("pedidos", retorno.getObjeto());
				model.addAttribute("pedido", new Pedido());
				
				if(item != null) {
					for (Pedido pedido : (List<Pedido>)retorno.getObjeto()) {
						if(pedido.getId() == Integer.parseInt(item)) {
							model.addAttribute("itens", pedido.getItens());							
						}
					}
				}
				return new ModelAndView("pedidos");
			}			
		}else {
			model.addAttribute("pedidos", new ArrayList<>());
			model.addAttribute("pedido", new Pedido());
			return new ModelAndView("pedidos");			
		}
	}

	@RequestMapping("/novopedido")
	public ModelAndView novo(@RequestParam(value="cpf", required=false) String cpf,
							 @RequestParam(value="produto", required=false) String produto,
							 @RequestParam(value="quantidade", required=false) String quantidade, 
							 Model model) {
		
		ProdutoFacade prodFac = new ProdutoFacade();
		if(cpf != null && produto != null && quantidade != null) {
			Retorno retorno = prodFac.getProduto(Integer.parseInt(produto));
			for (ItemDoPedido itemDoPedido : itens) {
				if(itemDoPedido.getProduto().getId() == ((Produto)retorno.getObjeto()).getId()) {
					retorno.setErro(new ErroMessenger("O produto selecionado ja foi adicionado ao pedido.", null));
					model.addAttribute("erro", retorno.getErro());
					return new ModelAndView("erro");
				}
			}
			itens.add(new ItemDoPedido(Integer.parseInt(quantidade), (Produto)retorno.getObjeto()));
			model.addAttribute("cpf", cpf);
			model.addAttribute("itens", itens);				
		}else {
			Retorno retorno = new Retorno();
			retorno.setErro(new ErroMessenger("Todos os campos devem ser preenchidos", null));
			model.addAttribute("erro", retorno);
		}
		model.addAttribute("produtos", prodFac.listProdutos());
		return new ModelAndView("novopedido");			
	}

	
	@RequestMapping("/novopedido/salvar")
	public ModelAndView novo(@RequestParam(value="cpf", required=false) String cpf, Model model) {
		Retorno retorno = null;
	
		ClienteFacade clifac = new ClienteFacade();
		retorno = clifac.getCliente(cpf);
		
		if(retorno.getErro() != null) {
			model.addAttribute("erro", retorno.getErro());
			return new ModelAndView("erro");
		}else {
			Pedido pedido = new Pedido();
			pedido.setCliente((Cliente)retorno.getObjeto());
			retorno = facade.setPedido(pedido);
			if(retorno.getErro() != null) {
				model.addAttribute("erro", retorno.getErro());
				return new ModelAndView("erro");
			}else {
				pedido = (Pedido)retorno.getObjeto();
				pedido.setItens(itens);
				retorno = facade.setItemPedido(pedido);
				
				if(retorno.getErro() != null) {
					model.addAttribute("erro", retorno.getErro());
					return new ModelAndView("erro");
				}
				itens.clear();
			}
		}
		model.addAttribute("page", "novopedido");
		return new ModelAndView("success");
	}
}
