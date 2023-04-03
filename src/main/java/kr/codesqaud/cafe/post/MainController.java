package kr.codesqaud.cafe.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.codesqaud.cafe.post.form.SimplePostForm;

@Controller
public class MainController {

	private final PostRepository postRepository;
	private final PostService postService;

	public MainController(PostRepository postRepository, PostService postService) {
		this.postRepository = postRepository;
		this.postService = postService;
	}

	@GetMapping("/")
	public String showMainPage(Model model) {
		List<Post> posts = postRepository.getAllPosts();
		List<SimplePostForm> simpleForms = postService.mappingSimpleForm(posts);
		model.addAttribute("simpleForms", simpleForms);
		return "index";
	}

}
