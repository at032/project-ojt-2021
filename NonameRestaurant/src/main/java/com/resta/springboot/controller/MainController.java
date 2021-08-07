package com.resta.springboot.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.resta.springboot.model.AppUser;
import com.resta.springboot.model.Customer;
import com.resta.springboot.model.MyUserDetails;
import com.resta.springboot.service.CustomerService;
import com.resta.springboot.service.EmployeeService;
import com.resta.springboot.service.UserService;

@Controller
public class MainController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		return "welcomePage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {

//    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		for (GrantedAuthority list : authentication.getAuthorities()) {
//			if (list.getAuthority().equalsIgnoreCase("ROLE_ADMIN")) {
//				return "manageEmployee";
//			}
//			else if (list.getAuthority().equalsIgnoreCase("ROLE_USER")) {
//				return "welcomePage";
//			}
//		}

		return "loginPage";
	}

//    @GetMapping("/login")
//	public String login(Model model) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//			 
//			return "login";
//		}
//		
//		for (GrantedAuthority list : authentication.getAuthorities()) {
//			if (list.getAuthority().equalsIgnoreCase("ROLE_ADMIN")) {
//				return "manageEmployee";
//			}
//			else if (list.getAuthority().equalsIgnoreCase("ROLE_USER")) {
//				return "/";
//			}
//		}
//		
//		return "/";
//	}

//    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
//    public String logoutSuccessfulPage(Model model) {
//        model.addAttribute("title", "Logout");
//        return "logoutSuccessfulPage";
//    }
	@RequestMapping(value = "/ingredientListK", method = RequestMethod.GET)
	public String ingredientListK(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "Chef - Manage Stock");
		return "IngredientListK";
	}

	@RequestMapping(value = "/bookingListW", method = RequestMethod.GET)
	public String bookingListW(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "Waiter - Manage Booking");
		return "bookingListW";
	}

	@RequestMapping(value = "/invoiceListC", method = RequestMethod.GET)
	public String invoiceListC(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "Cashier - Manage Payment");
		return "invoiceListC";
	}

	@RequestMapping(value = "/manageEmployee", method = RequestMethod.GET)
	public String manageEmployee(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		model.addAttribute("title", "Admin - Manage Employee");
		return "manageEmployee";
	}

	@RequestMapping(value = "/manageCustomerAd", method = RequestMethod.GET)
	public String manageCustomerAd(Model model, Principal princi) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		List<Customer> listCustomer = customerService.getAllCustomer();
		List<AppUser> listUser = userService.getAllUser();

		model.addAttribute("listCustomer", listCustomer);
		model.addAttribute("listUser", listUser);
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "Admin - Manage Customer");
		return "manageCustomerAd";
	}

	@RequestMapping(value = "/addnewCus", method = RequestMethod.POST)
	public RedirectView addCustomer(@ModelAttribute("customer") Customer cus, RedirectAttributes ra) {
		if (userService.checkEmailExist(cus.getLoginId().getEmail())
				|| userService.checkPhoneExist(cus.getLoginId().getPhone())) {
			RedirectView rv = new RedirectView("/manageCustomerAd", true);
			if (userService.checkEmailExist(cus.getLoginId().getEmail())) {
				ra.addFlashAttribute("failcreatenew", "emailExist");
				System.err.println("error: email exist");
			} else {
				ra.addFlashAttribute("failcreatenew", "phoneExist");
				System.err.println("error: phone exist");
			}
			return rv;
		} else {
			RedirectView rv = new RedirectView("/manageCustomerAd", true);
			Customer customer = cus;
			AppUser user = new AppUser(null, cus.getLoginId().getEmail(), cus.getLoginId().getPhone(),
					encoder.encode("123456")// mk mac dinh.
					, "ROLE_USER", true);
			userService.saveUser(user);

			customer.setClassCus("Standard");
			customer.setPoints(0);
			customer.setLoginId(user);
			customerService.saveCustomer(customer);

			System.out.println(customer);
			ra.addFlashAttribute("registerSuccess", "ok");
			return rv;
		}

	}

	@RequestMapping(value = "/editCus", method = RequestMethod.POST)
	public RedirectView editCus(@ModelAttribute("customter") Customer cus, RedirectAttributes ra) {
		RedirectView rv = new RedirectView("/manageCustomerAd", true);

			Customer customer = customerService.findById(cus.getCustomerID());
			customer.setCusName(cus.getCusName());
			customer.getLoginId().setEmail(cus.getLoginId().getEmail());
			customer.getLoginId().setPhone(cus.getLoginId().getPhone());

			if (userService.checkPasswordChange(customer.getLoginId().getUserId(),
					encoder.encode(cus.getLoginId().getEncrytedPassword()))) {
				customer.getLoginId().setEncrytedPassword(cus.getLoginId().getEncrytedPassword());
			}
			customer.getLoginId().setEncrytedPassword(encoder.encode(cus.getLoginId().getEncrytedPassword()));
			customer.setGender(cus.getGender());
			customer.setBirthday(cus.getBirthday());
			customerService.saveCustomer(customer);
			ra.addFlashAttribute("UpdateCusSuccess", "phoneExist");
			return rv;
		

	}

//	@RequestMapping(value = "/deleteCus", method = RequestMethod.POST)
//	public RedirectView deleteCus(@RequestParam int id, RedirectAttributes ra) {
//		RedirectView rv = new RedirectView("/manageCustomerAd", true);
//		try {
//			customerService.deleteCustomer(id);
//			ra.addFlashAttribute("ok", "ok");
//		} catch (Exception e) {
//			// TODO: handle exception
//			ra.addFlashAttribute("faildelete", e.getMessage());
//		}
//		return rv;
//
//	}

	@RequestMapping(value = "/manageStockAd", method = RequestMethod.GET)
	public String manageStockAd(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		model.addAttribute("title", "Admin - Manage Stock");
		return "manageStockAd";
	}

	@RequestMapping(value = "/manageBookingAd", method = RequestMethod.GET)
	public String manageBookingAd(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "Admin - Manage Booking");
		return "manageBookingAd";
	}

	@RequestMapping(value = "/managePaymentAd", method = RequestMethod.GET)
	public String managePaymentAd(Model model, Principal princi) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		model.addAttribute("title", "Admin - Manage Payment");
		return "managePaymentAd";
	}

	@RequestMapping(value = "/manageMenu", method = RequestMethod.GET)
	public String manageMenu(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		model.addAttribute("title", "Admin - Manage Menu");

		return "manageMenuDish";
	}

	@RequestMapping(value = "/manageStatiticsAd", method = RequestMethod.GET)
	public String manageStatiticsAd(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "Admin - Manage Statitics");
		return "manageStatiticsAd";
	}

	@RequestMapping(value = "/manageFeedback", method = RequestMethod.GET)
	public String manageFeedback(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "Admin - Manage Feedback");
		return "manageFeedback";
	}

	@RequestMapping(value = "/manageOffer", method = RequestMethod.GET)
	public String manageOffer(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "Admin - Manage Offer");
		return "manageOffer";
	}

	@RequestMapping(value = "/viewProfileAd", method = RequestMethod.GET)
	public String viewProfileAd(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "Admin - View profile");
		return "viewProfileAd";
	}

	@RequestMapping(value = "/viewProfile", method = RequestMethod.GET)
	public String viewProfile(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "View profile");
		return "viewProfileCus";
	}

	@RequestMapping(value = "/transactionHistory", method = RequestMethod.GET)
	public String transactionHistory(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		model.addAttribute("title", "View profile");
		return "transactionHistory";
	}

	@RequestMapping(value = "/newBookingCus", method = RequestMethod.GET)
	public String newBookingCus(Model model, Principal princi) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		model.addAttribute("title", "Booking Table");
		return "newBookingCus";
	}

	@RequestMapping(value = "/viewEmptyTable", method = RequestMethod.GET)
	public String viewEmptyTable(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "View empty table");
//        model.addAttribute("classActiveSettings","active");
		return "viewEmptyTable";
	}

	@RequestMapping(value = "/orderMenu", method = RequestMethod.GET)
	public String orderMenu(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "Booking Table");
		return "orderMenu";
	}

	@RequestMapping(value = "/bookingPayment", method = RequestMethod.GET)
	public String bookingPayment(Model model, Principal princi) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		model.addAttribute("title", "Booking Payment");
		return "bookingPayment";
	}

	@RequestMapping(value = "/invoicePayment", method = RequestMethod.GET)
	public String invoicePayment(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		model.addAttribute("title", "Invoice Payment");
		return "invoicePayment";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessful(Model model, Principal princi) {

		return "welcomePage";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model, Principal principal) {

		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public RedirectView registerSuccess(@ModelAttribute("customer") Customer cus, RedirectAttributes ra,
			@RequestParam("formFile") MultipartFile multipartFile) throws IOException {

		if (userService.checkEmailExist(cus.getLoginId().getEmail())) {
			ra.addFlashAttribute("failRegisterCus", "emailExist");
			System.err.println("error: email exist");
			RedirectView rv = new RedirectView("/register", true);
			return rv;
		} else {
			RedirectView rv = new RedirectView("/login", true);
			Customer customer = cus;
			AppUser user = new AppUser(null, cus.getLoginId().getEmail(), cus.getLoginId().getPhone(),
					encoder.encode(cus.getLoginId().getEncrytedPassword()), "ROLE_USER", true);
			userService.saveUser(user);
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			customer.setAvatar(fileName);

			customer.setClassCus("Standard");
			customer.setPoints(0);
			customer.setLoginId(user);
			customerService.saveCustomer(customer);
			String uploadDir = "./src/main/resources/static/images/avatarC/" + customer.getCustomerID();
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputStream = multipartFile.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				System.out.print(filePath.toFile().getAbsolutePath());
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
				throw new IOException("Could not save upload file" + fileName + e.getMessage());

			}
			System.out.println(customer.getLoginId());
			ra.addFlashAttribute("registerSuccess", "ok");
			return rv;
		}

	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public String forgotPassword(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		return "forgotPassword";
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String aboutPage(Model model, Principal princi) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));

		model.addAttribute("title", "About");
		return "aboutPage";
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menuPage(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		model.addAttribute("title", "Menu");
		return "menuPage";
	}

	@RequestMapping(value = "/photos", method = RequestMethod.GET)
	public String photos(Model model, Principal princi) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		model.addAttribute("title", "Photos");
		return "photosPage";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contactPage(Model model, Principal princi) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof MyUserDetails) {
			username = ((MyUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("message", "This is welcome page!");
		model.addAttribute("title", "Welcome");
		model.addAttribute("Customer", customerService.findCusByEmail(username));
		model.addAttribute("Employee", employeeService.findEmpByEmail(username));
		model.addAttribute("User", userService.findByEmail(username));
		model.addAttribute("title", "Contact");
		return "contactPage";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

//        if (principal != null) {
//            User loginedUser = (User) ((Authentication) principal).getPrincipal();
// 
//            String userInfo = WebUtils.toString(loginedUser);
// 
//            model.addAttribute("userInfo", userInfo);
// 
//            String message = "Hi " + principal.getName() //
//                    + "<br> You do not have permission to access this page!";
//            model.addAttribute("message", message);
// 
//        }

		return "403";
	}

}
