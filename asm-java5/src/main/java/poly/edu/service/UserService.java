package poly.edu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import poly.edu.entity.GioHang;
import poly.edu.entity.Users;
import poly.edu.repository.GioHangChiTietRepository;
import poly.edu.repository.GioHangRepository;
import poly.edu.repository.HoaDonChiTietRepository;
import poly.edu.repository.HoaDonRepository;
import poly.edu.repository.UsersRepository;

@Service
public class UserService {
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	private HttpSession session;
	@Autowired
	GioHangRepository gioHangRepository;
	@Autowired
	GioHangChiTietRepository gioHangChiTietRepository;
	@Autowired
	HoaDonChiTietRepository hoaDonChiTietRepository;
	@Autowired
	HoaDonRepository hoaDonRepository;

	public Users register(Users user) {
		Optional<Users> existingUserById = usersRepository.findById(user.getIdUser());
		if (existingUserById.isPresent()) {
			throw new IllegalArgumentException("Email người dùng đã tồn tại!");
		}

		Optional<Users> existingUserByPhone = usersRepository.findBySdt(user.getSdt());
		if (existingUserByPhone.isPresent()) {
			throw new IllegalArgumentException("Số điện thoại đã được sử dụng!");
		}
		user.setVaitro(false);
		usersRepository.save(user);

		GioHang gioHang = new GioHang();
		gioHang.setUsers(user);
		gioHangRepository.save(gioHang);

		return user;
	}

	public Users updateUser(String id, Users updatedUser) {
		Optional<Users> optionalUser = usersRepository.findById(id);
		if (optionalUser.isPresent()) {
			Users user = optionalUser.get();
			user.setHoten(updatedUser.getHoten());
			user.setSdt(updatedUser.getSdt());
			user.setHinh(updatedUser.getHinh());
			user.setVaitro(updatedUser.isVaitro());

			return usersRepository.save(user);
		} else {
			throw new RuntimeException("Không tìm thấy người dùng!");
		}
	}

	public Users login(Users users) {
		Optional<Users> userOptional = usersRepository.findById(users.getIdUser());
		if (userOptional.isEmpty()) {
			throw new IllegalArgumentException("Tài khoản không tồn tại!");
		}

		Users user = userOptional.get();
		if (!user.getMatkhau().equals(users.getMatkhau())) {
			throw new IllegalArgumentException("Tài khoản hoặc mật khẩu không chính xác!");
		}
		session.setAttribute("currentUser", user);
		return user;
	}

	public void logout() {
		session.invalidate();
	}

	public Page<Users> getAllUsers(int pageNumber, int limit) {
		PageRequest pageable = PageRequest.of(pageNumber, limit);
		return usersRepository.findAll(pageable);
	}

	public Users getUserById(String email) {
		return usersRepository.findById(email)
				.orElseThrow(() -> new IllegalArgumentException("Tài khoản không tồn tại!"));
	}

	public void deleteUser(String id) {
		if (!usersRepository.existsById(id)) {
			throw new RuntimeException("Người dùng không tồn tại!");
		}
		hoaDonChiTietRepository.deleteByUserId(id);
		hoaDonRepository.deleteByUserId(id);
		gioHangChiTietRepository.deleteByUserId(id);
		gioHangRepository.deleteByUserId(id);
		usersRepository.deleteById(id);
	}
}
