package springajax.service;

import org.springframework.data.jpa.repository.JpaRepository;
import springajax.domain.Book;

public interface BookRepository extends JpaRepository<Book,Integer> {


}
