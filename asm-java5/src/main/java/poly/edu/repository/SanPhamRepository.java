package poly.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import poly.edu.entity.SanPham;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
	Page<SanPham> findByTenSanphamContainingIgnoreCase(String keyword, Pageable pageable);

	Page<SanPham> findByLoai_IdLoai(Integer id, Pageable pageable);

	@Query("SELECT COALESCE(SUM(hdct.soluong), 0) "
			+ "FROM HoaDonChiTiet hdct "
			+ "JOIN hdct.hoaDon hd "
			+ "WHERE hd.trangthai = 'received' " + "AND hdct.sanPham.idSanpham = :idSanpham")
	int tongSoLuongSanPhamDaBanTheoId(@Param("idSanpham") Integer idSanpham);
}
