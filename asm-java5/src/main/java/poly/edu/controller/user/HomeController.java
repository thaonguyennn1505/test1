package poly.edu.controller.user;

import java.io.File;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import poly.edu.entity.SanPham;
import poly.edu.entity.Users;
import poly.edu.service.CookieService;
import poly.edu.service.LoaiService;
import poly.edu.service.ParamService;
import poly.edu.service.SanPhamService;
import poly.edu.service.SessionService;
import poly.edu.service.UserService;

@Controller
public class HomeController {
	@Autowired
	UserService userService;
	@Autowired
	LoaiService loaiService;
	@Autowired
	SanPhamService sanPhamService;
	@Autowired
	private HttpSession session;
	  @Autowired
    ParamService paramService;

    @Autowired
    CookieService cookieService;

    @Autowired
    SessionService sessionService;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("loais", loaiService.getAllLoai(0, 5));
		model.addAttribute("sanphams", sanPhamService.getAllSanPham(0, 12));
		return "user/home";
	}

	@GetMapping("/signin")
	public String signin(@ModelAttribute("user") Users user, Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser != null) {
			return "redirect:/";
		}
		model.addAttribute("loais", loaiService.getAllLoai(0, 5));
		return "user/signin";
	}

	@PostMapping("/signin")
	public String login(@ModelAttribute("user") Users user, Model model) {
		try {
			userService.login(user);
			return "redirect:/";
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "user/signin";
		}
	}

	@GetMapping("/signup")
	public String signup(@ModelAttribute("user") Users user, Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser != null) {
			return "redirect:/";
		}
		model.addAttribute("loais", loaiService.getAllLoai(0, 5));
		return "user/signup";
	}

	@GetMapping("/signout")
	public String signout() {
		userService.logout();
		return "redirect:/";
	}

	@PostMapping("/signup")
	public String regiter(@ModelAttribute("user") Users user, Model model) {
		try {
			userService.register(user);
			model.addAttribute("successMessage", "Tạo tài khoản thành công");
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "user/signup";
	}

	@ResponseBody
	@GetMapping("/image/{filename:.+}")
	public ResponseEntity<Object> downloadFile(@PathVariable(name = "filename") String filename) {
		File file = new File("c:/var/java5/images/" + filename);
		if (!file.exists()) {
			throw new RuntimeException("File không tồn tại!");
		}

		UrlResource resource;
		try {
			resource = new UrlResource(file.toURI());
		} catch (MalformedURLException ex) {
			throw new RuntimeException("File không tồn tại!");
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
				.body(resource);
	}

	@GetMapping("/sanpham")
	public String sanpham(@RequestParam(name = "id") String id, Model model) {
		try {
			SanPham sanPham = sanPhamService.getSanPhamById(Integer.valueOf(id));
			model.addAttribute("sanpham", sanPham);
			model.addAttribute("sanphams", sanPhamService.getSanPhamByIdLoai(sanPham.getLoai().getIdLoai(), 0, 4)
					.filter(item -> item.getIdSanpham() != sanPham.getIdSanpham()));
			model.addAttribute("loais", loaiService.getAllLoai(0, 5));
			model.addAttribute("luotMua", sanPhamService.getLuotMuaById(sanPham.getIdSanpham()));
			return "user/productDetail";
		} catch (Exception e) {
			return "redirect:/";
		}
	}
}
