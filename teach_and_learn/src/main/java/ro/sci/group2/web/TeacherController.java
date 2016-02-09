package ro.sci.group2.web;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.sci.group2.annotation.CurrentUser;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Role;
import ro.sci.group2.domain.User;
import ro.sci.group2.service.CourseService;
import ro.sci.group2.service.UserService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	UserService userService;
	@Autowired
	CourseService courseService;
	
	@RequestMapping("")
	public ModelAndView list(Long id,@CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("teacher_index");
		view.addObject("user", userService.findByUserName(u.getUsername()));
		return view;
	}
	
	@RequestMapping("/teacher_profile")
	public ModelAndView onEdit(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView result;

		if (u.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))) {
			result = new ModelAndView("teacher_profile");
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
	
	@RequestMapping("/teacher_meetings")
	public ModelAndView onMeetingEdit(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView result;

		if (u.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))) {
			result = new ModelAndView("teacher_meetings");
		} else {
			result = null;
		}
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		result.addObject("user", user);
		return result;
	}
	
	@RequestMapping(value="" , method = RequestMethod.POST)
	public ModelAndView saveuser(User user, @CurrentUser org.springframework.security.core.userdetails.User u) {
		user.setUsername(u.getUsername());
		Collection<Role> roles = new LinkedList<>();
		for(GrantedAuthority auth:u.getAuthorities()){
			roles.add(Role.valueOf(auth.getAuthority()));
		}
		user.setRoles(roles);
		userService.save(user);
		ModelAndView view = new ModelAndView("teacher_index");
		view.addObject(user);
		return view;
	}
	
	@RequestMapping("/teacher_courses")
	public ModelAndView listCourses(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("teacher_courses");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		view.addObject("user", user);
		view.addObject("teacherCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}
	
	@RequestMapping(value="/teacher_courses_add")
	public ModelAndView addCourse(Long id, Course course, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("teacher_courses");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
			userService.addCourse(user.getId(), course);
		}
		view.addObject("user", user);
		view.addObject("teacherCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}
	
	@RequestMapping(value="/teacher_course_create")
	public ModelAndView onCreate(Long id) {
		
		ModelAndView view = new ModelAndView("new_course");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		view.addObject("user", user);
		return view;
	}
	
	@RequestMapping(value="/teacher_course_create", method=RequestMethod.POST)
	public ModelAndView saveCourse(Course course, @CurrentUser org.springframework.security.core.userdetails.User u) {
		courseService.save(course);
		ModelAndView view = new ModelAndView("teacher_courses");
		User user = new User();
		user = userService.findByUserName(u.getUsername());
		view.addObject("user", user);
		view.addObject("teacherCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}
	
	@RequestMapping(value="/teacher_course_remove")
	public ModelAndView removeCourse(Long id, Course course, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("teacher_courses");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
			userService.removeCourse(user.getId(), course);
		}
		view.addObject("user", user);
		view.addObject("teacherCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}
	
}
