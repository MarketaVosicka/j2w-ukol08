package cz.czechitas.java2webapps.ukol8.controller;

import cz.czechitas.java2webapps.ukol8.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;


@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public ModelAndView list() {
        return new ModelAndView("index")
                .addObject("post", postService.findByPublishedBeforeAndPublishedNotNull());
    }

    @GetMapping(path = "/post/", params = "slug")
    public ModelAndView singlePost(@PathVariable String slug, Pageable pageable) {
        return new ModelAndView("post")
                .addObject("post", postService.findBySlug(slug, Pageable.unpaged()));
    }

    @ModelAttribute("currentURL")
    public String currentURL(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        return UriComponentsBuilder
                .newInstance()
                .path(helper.getOriginatingRequestUri(request))
                .query(helper.getOriginatingQueryString(request))
                .replaceQueryParam("size")
                .replaceQueryParam("page")
                .build()
                .toString();
    }
}
