package ro.sci.group2.service;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ro.sci.group2.dao.UserDAO;
import ro.sci.group2.domain.Role;


/**
 * <p>
 * Custom implementation of the
 * {@link org.springframework.security.core.userdetails.UserDetailsService
 * UserDetailsService};
 * </p>
 * <p>The class implements the loadUserByUsername method, which receives a username
 * and returns a spring security
 * {@link org.springframework.security.core.userdetails.User User} build from
 * the user in that Db that matches this username</p>
 * 
 * @author Oltean Andrei
 *
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserDAO dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ro.sci.group2.domain.User u;
		try {
			u = dao.findByUsername(username);
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException("User not found!");
		}
		return buildUserFromUserEntity(u);
	}

	private User buildUserFromUserEntity(ro.sci.group2.domain.User userEntity) {
		// convert model user to spring security user
		String username = userEntity.getUsername();
		String password = userEntity.getPassword();
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		Collection<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
		for (Role role : userEntity.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		}

		User springUser = new User(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		return springUser;
	}

}
