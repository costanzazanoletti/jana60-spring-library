package jana60.controller;

import java.time.LocalDate;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jana60.model.Book;
import jana60.model.Borrowing;
import jana60.repository.BookRepository;
import jana60.repository.BorrowingRepository;
import jana60.repository.UserRepository;

@Controller
@RequestMapping("/borrowing")
public class BorrowingController {

  @Autowired
  private BookRepository bookRepo;

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private BorrowingRepository repo;

  @GetMapping("/{bookId}")
  public String borrowBook(@PathVariable("bookId") Integer bookId, Model model) {
    // preparo un Borrowing per precaricare la form
    Borrowing formBorrowing = new Borrowing();
    Book bookToBorrow = bookRepo.findById(bookId).get();
    formBorrowing.setBook(bookToBorrow);
    formBorrowing.setStartDate(LocalDate.now());
    // passo al model il Borrowing con i dati di book e startDate inizializzati
    model.addAttribute("borrowing", formBorrowing);
    // passo al model la lista degli utenti
    model.addAttribute("userList", userRepo.findAll());
    return "/borrowing/edit";
  }

  @PostMapping("/save")
  public String doSave(@Valid @ModelAttribute Borrowing formBorrowing, BindingResult br,
      Model model) {
    // se ho errori torno alla form
    if (br.hasErrors()) {
      // passo al model la lista degli utenti
      model.addAttribute("userList", userRepo.findAll());
      return "/borrowing/edit";
    } else { // se non ho errori salvo il borrowing
      repo.save(formBorrowing);
      return "redirect:/detail/" + formBorrowing.getBook().getId();
    }

  }

}
