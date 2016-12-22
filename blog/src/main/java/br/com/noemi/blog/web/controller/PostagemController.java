package br.com.noemi.blog.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.noemi.blog.entity.Postagem;
import br.com.noemi.blog.entity.UsuarioLogado;
import br.com.noemi.blog.service.AutorService;
import br.com.noemi.blog.service.CategoriaService;
import br.com.noemi.blog.service.PostagemService;
import br.com.noemi.blog.web.editor.CategoriaEditorSupport;
import br.com.noemi.blog.web.validator.PostagemAjaxValidator;

@Controller
@RequestMapping("postagem")
public class PostagemController {

	@Autowired
	private PostagemService postagemService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private AutorService autorService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(
				List.class, 
				new CategoriaEditorSupport(List.class, categoriaService)
		);
	}
	
	@RequestMapping(value = "/ajax/autor/{id}/titulo/{titulo}/page/{page}", method = RequestMethod.GET) 
	public ModelAndView searchByAjaxByAutor(@PathVariable("id") Long id,
			                                @PathVariable("titulo") String titulo,
									        @PathVariable("page") Integer pagina) {
		
		ModelAndView view = new ModelAndView("postagem/table-rows");
		
		Page<Postagem> page = postagemService.findByTituloAndAutor(pagina - 1, 5, titulo, id);
		
		view.addObject("page", page);
		
		return view;
	}
	
	
	@RequestMapping(value = "/ajax/autor/{id}/page/{page}", method = RequestMethod.GET)
	public ModelAndView pagePostagens(@PathVariable("id") Long id,
									  @PathVariable("page") Integer pagina) {
		
		ModelAndView view = new ModelAndView("postagem/table-rows"); 
		
		Page<Postagem> page = postagemService.findByPaginationByAutor(pagina - 1, 5, id);
		
		view.addObject("page", page);
		
		return view;
	}
	
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public ModelAndView listPostagensByAutor(@PathVariable("id") Long id, ModelMap model) {
		
		Long autorId = autorService.findByUsuario(id).getId();		

		Page<Postagem> page = postagemService.findByPaginationByAutor(0, 5, autorId);		
		model.addAttribute("page", page);
		model.addAttribute("autorId", autorId);
		
		return new ModelAndView("postagem/list", model);
	}
	
	@RequestMapping(value = "/ajax/save", method = RequestMethod.POST)
	public @ResponseBody PostagemAjaxValidator saveAjax(@Validated Postagem postagem, 
			                                            BindingResult result, 
			                                            @AuthenticationPrincipal UsuarioLogado logado) {
		
		PostagemAjaxValidator validator = new PostagemAjaxValidator();
		
		if ( result.hasErrors() ) {
			
			validator.setStatus("FAIL");
			
			validator.validar(result);
			
			return validator;
		}
		
		postagem.setAutor(autorService.findByUsuario(logado.getId()));
		
		postagemService.saveOrUpdate(postagem);
		
		validator.setPostagem(postagem);
		
		return validator;
	}
	
	@RequestMapping(value = "/ajax/add", method = RequestMethod.GET)
	public ModelAndView cadastroAjax() {
		ModelAndView view = new ModelAndView("postagem/cadastro-ajax");
		view.addObject("categorias", categoriaService.findAll());
		return view;
	}
	
	@RequestMapping(value = "/ajax/titulo/{titulo}/page/{page}", method = RequestMethod.GET) 
	public ModelAndView searchByAjax(@PathVariable("titulo") String titulo,
									 @PathVariable("page") Integer pagina) {
		
		ModelAndView view = new ModelAndView("postagem/table-rows");
		
		Page<Postagem> page = postagemService.findByTitulo(pagina - 1, 5, titulo);
		
		view.addObject("page", page);
		
		return view;
	}
	
	
	@RequestMapping(value = "/ajax/page/{page}", method = RequestMethod.GET)
	public ModelAndView pagePostagens(@PathVariable("page") Integer pagina) {
		
		ModelAndView view = new ModelAndView("postagem/table-rows"); 
		
		Page<Postagem> page = postagemService.findByPagination(pagina - 1, 5);
		
		view.addObject("page", page);
		//view.addObject("urlPagination", "/postagem/page");
		
		return view;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView preUpdate(@PathVariable("id") Long id, ModelMap model) {
		
		Postagem postagem = postagemService.findById(id);
		
		model.addAttribute("postagem", postagem);
		model.addAttribute("categorias", categoriaService.findAll());
		
		return new ModelAndView("postagem/cadastro", model);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		
		postagemService.delete(id);
		
		return "redirect:/postagem/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPostagens(ModelMap model) {
		
		//model.addAttribute("postagens", postagemService.findAll());
		Page<Postagem> page = postagemService.findByPagination(0, 5);
		
		model.addAttribute("page", page);
		//model.addAttribute("urlPagination", "/postagem/page");
		
		return new ModelAndView("postagem/list", model);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("postagem") @Validated Postagem postagem,
			           BindingResult result, @AuthenticationPrincipal UsuarioLogado logado) {
		
		if ( result.hasErrors() ) {
			
			return new ModelAndView("postagem/cadastro", "categorias", categoriaService.findAll());
		}
		
		postagem.setAutor(autorService.findByUsuario(logado.getId()));
		
		postagemService.saveOrUpdate(postagem);
		
		return new ModelAndView("redirect:/postagem/list/" + logado.getId());
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView cadastro(@ModelAttribute("postagem") Postagem postagem) {
		ModelAndView view = new ModelAndView("postagem/cadastro");
		view.addObject("categorias", categoriaService.findAll());
		return view;
	}
}
