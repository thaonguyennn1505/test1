package poly.edu.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "GIOHANG")
public class GioHang {

	private Integer idGiohang;
	private Users users;
	private List<GioHangChiTiet> gioHangChiTiets;

	public GioHang() {
	}

	public GioHang(Users users, List<GioHangChiTiet> gioHangChiTiets) {
		this.users = users;
		this.gioHangChiTiets = gioHangChiTiets;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id_giohang", unique = true, nullable = false)
	public Integer getIdGiohang() {
		return this.idGiohang;
	}

	public void setIdGiohang(Integer idGiohang) {
		this.idGiohang = idGiohang;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gioHang")
	public List<GioHangChiTiet> getGioHangChiTiets() {
		return this.gioHangChiTiets;
	}

	public void setGioHangChiTiets(List<GioHangChiTiet> gioHangChiTiets) {
		this.gioHangChiTiets = gioHangChiTiets;
	}

}
