package jana60.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jana60.model.Book;
import jana60.repository.BookRepository;
import jana60.repository.CategoryRepository;

@Controller
@RequestMapping("/")
public class BookController {

  @Autowired
  private BookRepository repo;

  @Autowired
  private CategoryRepository categoryRepo;

  @GetMapping
  public String bookList(Model model) {
    model.addAttribute("books", repo.findAll());
    return "/book/list"; // -> il nome o path di un template che si trova in /resources/templates
  }

  @GetMapping("/advanced_search")
  public String advancedSearch() {
	  return "/book/search";
  }
  
  @GetMapping("/search")
  public String search(@RequestParam(name = "queryTitle") String queryTitle, 
		  @RequestParam(name = "queryAuthor", required = false) String queryAuthor, Model model) {
	  
	  if (queryTitle != null && queryTitle.isEmpty()) {
		  queryTitle = null;
	  }
	  if (queryAuthor != null && queryAuthor.isEmpty()) {
		  queryAuthor = null;
	  }
	  
	  List<Book> books = repo.findByTitleContainingOrAuthorsContainingIgnoreCase(queryTitle, queryAuthor);
	  model.addAttribute("books", books);
	  return "/book/list";
  }
  
  /*
   * controller che ritorna la view con la form
   * vuota per aggiungere un nuovo book
   */
  @GetMapping("/add")
  public String bookForm(Model model) {
    model.addAttribute("book", new Book());
    model.addAttribute("categoryList", categoryRepo.findAllByOrderByName());
    return "/book/edit";
  }

  /*
   * controller che riceve la post con i dati della
   * form, li valida e salva il book
   */
  @PostMapping("/save")
  public String save(@Valid @ModelAttribute("book") Book formBook, BindingResult br, Model model) {
    // testo se ci sono errori di validazione
    boolean hasErrors = br.hasErrors();
    boolean validateIsbn = true;
    if (formBook.getId() != null) { // sono in edit non in create
      Book bookBeforeUpdate = repo.findById(formBook.getId()).get();
      if (bookBeforeUpdate.getIsbn().equals(formBook.getIsbn())) {
        validateIsbn = false;
      }
    }
    // testo se isbn è univoco
    if (validateIsbn && repo.countByIsbn(formBook.getIsbn()) > 0) {
      br.addError(new FieldError("book", "isbn", "ISBN must be unique"));
      hasErrors = true;
    }

    if (hasErrors) {
      // se ci sono errori non salvo il book su database ma ritorno alla form precaricata
      model.addAttribute("categoryList", categoryRepo.findAllByOrderByName());
      return "/book/edit";
    } else {
      // se non ci sono errori salvo il book che arriva dalla form
      try {
        repo.save(formBook);
      } catch (Exception e) { // gestisco eventuali eccezioni sql
        model.addAttribute("errorMessage", "Unable to save the Book");
        model.addAttribute("categoryList", categoryRepo.findAllByOrderByName());
        return "/book/edit";
      }
      return "redirect:/"; // non cercare un template, ma fai la HTTP redirect a quel path
    }
  }

  // request a http://localhost:8080/delete/2
  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") Integer bookId, RedirectAttributes ra) {
    Optional<Book> result = repo.findById(bookId);
    if (result.isPresent()) {
      // repo.deleteById(bookId);
      repo.delete(result.get());
      ra.addFlashAttribute("successMessage", "Book " + result.get().getTitle() + " deleted!");
      return "redirect:/";
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Book con id " + bookId + " not present");
    }


  }

  @GetMapping("/edit/{id}")
  public String edit(@PathVariable("id") Integer bookId, Model model) {
    Optional<Book> result = repo.findById(bookId);
    // controllo se il Book con quell'id è presente
    if (result.isPresent()) {
      // preparo il template con al form passandogli il book trovato su db

      model.addAttribute("book", result.get());
      model.addAttribute("categoryList", categoryRepo.findAllByOrderByName());
      return "/book/edit";
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Book con id " + bookId + " not present");
    }

  }
}
