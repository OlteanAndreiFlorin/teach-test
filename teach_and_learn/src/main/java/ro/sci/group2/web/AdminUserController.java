package ro.sci.group2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.sci.group2.annotation.CurrentUser;
import ro.sci.group2.domain.User;
import ro.sci.group2.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
	@Autowired
	UserService userService;

	@RequestMapping("")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView("user_list");
		view.addObject("users", userService.listAll());
		return view;
	}

	@RequestMapping(value = "/sorted", method = RequestMethod.POST)
	public ModelAndView list(String order) {
		ModelAndView view = list();
		view.addObject("users", userService.listAll(order));
		return view;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView listByName(String query) {
		ModelAndView view = list();
		view.addObject("users", userService.findByName(query));
		return view;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveuser(User user) {
		userService.save(user);
		return list();
	}

	@RequestMapping("/user_edit")
	public ModelAndView onEdit(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView result;

		if (u.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			result = new ModelAndView("admin_user_edit");
		} else {
			result = new ModelAndView("index");
		}
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		result.addObject("user", user);
		return result;
	}

	@RequestMapping("/user_delete")
	public ModelAndView onDelete(long id) {
		if (!userService.delete(id)) {
			throw new IllegalStateException("Non existing user");
		} else {
			return list();
		}

	}

}
