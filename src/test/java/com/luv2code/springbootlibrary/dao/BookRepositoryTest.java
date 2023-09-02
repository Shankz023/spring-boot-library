package com.luv2code.springbootlibrary.dao;

import com.luv2code.springbootlibrary.dao.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void createShouldSave() {
        final var book = bookRepository.save(createBook());
        assertThat(book).isNotNull();
    }

    private Book createBook() {
        return Book.builder()
                .author("Test Author")
                .category("Test Cat")
                .copies(99)
                .copiesAvailable(99)
                .img("Test Img")
                .title("Test Title")
                .description("Test Description")
                .build();
    }

    ;
}
