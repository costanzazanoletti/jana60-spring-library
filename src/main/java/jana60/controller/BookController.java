package jana60.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jana60.model.Book;
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

  /*
   * controller che ritorna la view con la form
   * vuota per aggiungere un nuovo book
   */
  @GetMapping("/add")
  public String bookForm(Model model) {
    model.addAttribute("book", new Book());
    return "/book/edit";
  }
}
