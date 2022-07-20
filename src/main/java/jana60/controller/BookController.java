package jana60.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jana60.repository.BookRepository;

@Controller
@RequestMapping("/")
public class BookController {

  @Autowired
  private BookRepository repo;

  @GetMapping
  public String bookList(Model model) {
    model.addAttribute("books", repo.findAll());
    return "/book/list";
  }
}
