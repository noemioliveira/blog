package br.com.noemi.blog.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.servlet.ModelAndView;

import br.com.noemi.blog.entity.Avatar;
import br.com.noemi.blog.entity.Perfil;
import br.com.noemi.blog.entity.Usuario;
import br.com.noemi.blog.service.AvatarService;
import br.com.noemi.blog.service.UsuarioService;
import br.com.noemi.blog.web.editor.PerfilEditorSupport;
import br.com.noemi.blog.web.validator.UsuarioValidator;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AvatarService avatarService;
	
	@InitBinder("usuario")
	public void initBinder(WebDataBinder binder) {
		
		binder.registerCustomEditor(Perfil.class, new PerfilEditorSupport());
		binder.setValidator(new UsuarioValidator());
	}
	
	@RequestMapping(value = "/update/perfil", method = RequestMethod.POST)
	public String updatePerfil(@ModelAttribute("usuario") @Validated Usuario usuario, 
			                   BindingResult result) {
		
		usuarioService.updatePerfil(usuario);
		
		return "redirect:/usuario/list";
	}
	
	@RequestMapping(value = "/sort/{order}/{field}/page/{page}", method = RequestMethod.GET)
	public ModelAndView pageUsuario(@PathVariable("page") Integer pagina, 
									@PathVariable("order") String order,
									@PathVariable("field") String field) {
		
		ModelAndView view = new ModelAndView("usuario/list");
		
		Page<Usuario> page = 
				usuarioService.findByPaginationOrderByField(pagina-1, 5, field, order);
		
		view.addObject("page", page);
		
		view.addObject("urlPagination", "/usuario/sort/"+ order +"/"+ field +"/page" );
		
		return view;
	}
	
	@RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
	public ModelAndView pageUsuarios(@PathVariable("page") Integer pagina) {
		
		ModelAndView view = new ModelAndView("usuario/list");
		
		Page<Usuario> page = usuarioService.findByPagination(pagina - 1, 5);
		
		view.addObject("page", page);
		
		view.addObject("urlPagination", "/usuario/page");
		
		return view;
	}
	
	@RequestMapping(value = {"/update/senha/{id}", "/update/senha"}, 
			        method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView updateSenha(@PathVariable("id") Optional<Long> id, 
									@ModelAttribute("usuario") @Validated Usuario usuario,
									BindingResult result) {
		
		ModelAndView view = new ModelAndView();
		
		if (id.isPresent()) {
			usuario = usuarioService.findById(id.get());
			view.addObject("usuario", usuario);
			view.setViewName("usuario/atualizar");
			return view;
		}
		
		if (result.hasFieldErrors("senha")) {
			usuario = usuarioService.findById(usuario.getId());
			view.addObject("nome", usuario.getNome());
			view.addObject("email", usuario.getEmail());
			view.setViewName("usuario/atualizar"); 
			return view;
		}
		
		usuarioService.updateSenha(usuario);
		
		view.setViewName("redirect:/usuario/perfil/" + usuario.getId());
		
		return view;
	}
	
	@RequestMapping(value = {"/update/{id}", "/update"}, 
					method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView update(@PathVariable("id") Optional<Long> id, 
			                   @ModelAttribute("usuario") @Validated Usuario usuario,
			                   BindingResult result) {
		
		ModelAndView view = new ModelAndView();
		
		if (id.isPresent()) {
			usuario = usuarioService.findById(id.get());
			view.addObject("usuario", usuario);
			view.setViewName("usuario/atualizar");
			return view;
		}
		
		if (result.hasErrors()) {
			view.setViewName("usuario/atualizar");
			return view; 
		}
		
		usuarioService.updateNomeAndEmail(usuario);
		
		view.setViewName("redirect:/usuario/perfil/" + usuario.getId());
		
		return view;
		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listUsuarios(ModelMap model) {
		
		//List<Usuario> usuarios = usuarioService.findAll();		
		//model.addAttribute("usuarios", usuarios);
		
		Page<Usuario> page = usuarioService.findByPagination(0, 5);
		model.addAttribute("page", page);
		
		model.addAttribute("urlPagination", "/usuario/page");
		
		return new ModelAndView("usuario/list", model);
	}
	
	@RequestMapping(value = "/perfil/{id}", method = RequestMethod.GET)
	public ModelAndView perfil(@PathVariable("id") Long id) {
		
		ModelAndView view = new ModelAndView();
		
		Usuario usuario = usuarioService.findById(id);
		
		view.addObject("usuario", usuario);
		
		view.setViewName("usuario/perfil");
		
		return view;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("usuario") @Validated Usuario usuario,
			           BindingResult result) {
		
		if (result.hasErrors()) {
			
			return "usuario/cadastro";
		}
		
		Avatar avatar = avatarService.getAvatarByUpload(usuario.getFile());
		
		usuario.setAvatar(avatar);
		
		usuarioService.save(usuario);
		
		return "redirect:/auth/form";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView showForm(@ModelAttribute("usuario") Usuario usuario) {
		return new ModelAndView("usuario/cadastro");
	}
}
