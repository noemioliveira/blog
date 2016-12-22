package br.com.noemi.blog.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.noemi.blog.entity.Usuario;
import br.com.noemi.blog.entity.UsuarioLogado;

@Service
public class UsuarioLogadoDetailService implements UserDetailsService {
	
	private static final Logger LOG = Logger.getLogger(UsuarioLogadoDetailService.class);

	@Autowired
	private UsuarioService service;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario;
		try {
			usuario = service.findByEmail(username);
			LOG.info("Usuario encontrado: {"+ username + "}.");
		} catch (Exception ex) {
			LOG.error("Usuario não encontrado: {"+ username + "}.");
			throw new UsernameNotFoundException(
					"Usuário {" + username + "{ não encontrado no sistema!");
		}
		
		return new UsuarioLogado(usuario);
	}

}
