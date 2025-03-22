package poly.edu.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "USERS")
public class Users {

    @Id
    @Column(name = "id_user", unique = true, nullable = false, length = 50)
    private String idUser;

    @Column(name = "sdt", nullable = false, length = 10)
    private String sdt;

    @Column(name = "hinh")
    private String hinh;

    @Column(name = "hoten", nullable = false)
    private String hoten;

    @Column(name = "matkhau", nullable = false, length = 50)
    private String matkhau;

    @Column(name = "vaitro", nullable = false)
    private boolean vaitro;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.ALL)
    private List<GioHang> gioHangs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.ALL)
    private List<HoaDon> hoaDons;

    // Constructors
    public Users() {
    }

    public Users(String idUser, String sdt, String hoten, String matkhau, boolean vaitro) {
        this.idUser = idUser;
        this.sdt = sdt;
        this.hoten = hoten;
        this.matkhau = matkhau;
        this.vaitro = vaitro;
    }

    public Users(String idUser, String sdt, String hinh, String hoten, String matkhau, boolean vaitro,
                 List<GioHang> gioHangs, List<HoaDon> hoaDons) {
        this.idUser = idUser;
        this.sdt = sdt;
        this.hinh = hinh;
        this.hoten = hoten;
        this.matkhau = matkhau;
        this.vaitro = vaitro;
        this.gioHangs = gioHangs;
        this.hoaDons = hoaDons;
    }
}
