package jana60.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    return "/book/list"; // -> il nome o path di un template che si trova in /resources/templates
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

  /*
   * controller che riceve la post con i dati della
   * form, li valida e salva il book
   */
  @PostMapping("/add")
  public String save(@Valid @ModelAttribute("book") Book formBook, BindingResult br) {
    // testo se ci sono errori di validazione
    if (br.hasErrors()) {
      // se ci sono errori non salvo il book su database ma ritorno alla form precaricata
      return "/book/edit";
    } else {
      // se non ci sono errori salvo il book che arriva dalla form
      repo.save(formBook);
      return "redirect:/"; // non cercare un template, ma fai la HTTP redirect a quel path
    }
  }
}
