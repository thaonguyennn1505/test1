package poly.edu.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.edu.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findBySdt(String sdt); // Đảm bảo phương thức này tồn tại
}

