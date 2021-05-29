package cz.czechitas.java2webapps.ukol8.service;

import cz.czechitas.java2webapps.ukol8.entity.Post;
import cz.czechitas.java2webapps.ukol8.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PostService {
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Vrací seznam všech příspěvků
     *
     * @return
     */
    public Page<Post> list(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /**
     * Vrací příspěvek dle zadaného slugu
     *
     * @param slug
     * @param pageable
     * @return
     */
    public Page<Post> findBySlug(String slug, Pageable pageable) {
        return postRepository.findBySlug(slug, Pageable.unpaged());
    }

    /**
     * Vrací seznam všech příspěvků v db, které nemají datum publikování zadané v budoucnosti nebo jej nemají zadané
     *
     * @return
     */
    public Page<Post> findByPublishedBeforeAndPublishedNotNull() {
        LocalDate dateBefore = LocalDate.now();
        return postRepository.findByPublishedBeforeAndPublishedNotNull(dateBefore, PostRepository.page);
    }
}
