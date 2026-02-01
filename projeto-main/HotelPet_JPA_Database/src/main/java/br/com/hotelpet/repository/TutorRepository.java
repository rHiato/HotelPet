package br.com.hotelpet.repository;
import br.com.hotelpet.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Mágica do Spring Data JPA: Não precisa implementar nada!
@Repository
public interface TutorRepository extends JpaRepository<Tutor, Integer> {
}