package br.com.trabalho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.trabalho.facade.ProdutoFacade;
import br.com.trabalho.model.Produto;
import br.com.trabalho.util.Retorno;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
	private ProdutoFacade facade = new ProdutoFacade();

	@RequestMapping(value = "", method = RequestMethod.GET)
	 public ModelAndView init(Model model) {
		Retorno retorno = facade.listProdutos();
		
		if(retorno.getErro() != null) {
			model.addAttribute("erro", retorno.getErro());
			return new ModelAndView("erro");
		}else {
			model.addAttribute("produtos", retorno.getObjeto());
			model.addAttribute("produto", new Produto());
			return new ModelAndView("produtos");
		}
	
	 }

	@RequestMapping(value = "/setProduto", method = RequestMethod.POST)
	public ModelAndView setProduto(@ModelAttribute("produto")Produto produto, Model model) {
		Retorno retorno = facade.setProduto(produto);
		if(retorno.getErro() != null) {
			model.addAttribute("erro", retorno.getErro());
			return new ModelAndView("erro");
		}else {
			model.addAttribute("page", "produtos");
			return new ModelAndView("success");
		}
	}

	@RequestMapping(value = "/editaProduto", method = RequestMethod.POST)
	public ModelAndView editaProduto(@ModelAttribute("produto")Produto produto, Model model) {
		Retorno retorno = facade.editProduto(produto);
		if(retorno.getErro() != null) {
			model.addAttribute("erro", retorno.getErro());
			return new ModelAndView("erro");
		}else {
			model.addAttribute("page", "produtos");
			return new ModelAndView("success");
		}
	}

	@RequestMapping(value = "/excluirProduto", method = RequestMethod.POST)
	public ModelAndView excluirProduto(@ModelAttribute("produto")Produto produto, Model model) {
		Retorno retorno = facade.excluirProduto(produto.getId());
		if(retorno.getErro() != null) {
			model.addAttribute("erro", retorno.getErro());
			return new ModelAndView("erro");
		}else {
			model.addAttribute("page", "produtos");
			return new ModelAndView("success");
		}
	}
}
