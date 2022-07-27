package jana60.controller;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jana60.model.Book;
import jana60.model.Borrowing;
import jana60.repository.BookRepository;
import jana60.repository.BorrowingRepository;

@Controller
@RequestMapping("/borrowing")
public class BorrowingController {

  @Autowired
  private BookRepository bookRepo;

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
    return "/borrowing/edit";
  }

}
