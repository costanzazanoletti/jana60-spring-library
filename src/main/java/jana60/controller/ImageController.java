package jana60.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import jana60.model.Image;
import jana60.model.ImageForm;
import jana60.service.ImageService;

@Controller
@RequestMapping("/image")
public class ImageController {
  /*
   * Visto che c'è molta logica invece di usare direttamente i repository nel controller
   * creiamo una classe service che espone i metodi che ci servono e si occupa della logica
   */
  @Autowired
  private ImageService service;


  /*
   * Crea la view con la lista delle immagini collegate a un book e la form per
   * aggiungerne una
   */
  @GetMapping("/{bookId}")
  public String bookImages(@PathVariable("bookId") Integer bookId, Model model) {
    // chiedo al service la lista delle immagini legate a quel bookId
    List<Image> images = service.getImagesByBookId(bookId);
    // chiedo al service di istanziare una ImageForm inizializzata con quel Book
    ImageForm imageForm = service.createImageForm(bookId);

    model.addAttribute("imageList", images);
    model.addAttribute("imageForm", imageForm);
    return "/images/list";
  }

  /*
   * Controller che riceve la post della form e salva su database l'immagine
   */
  @PostMapping("/save")
  public String saveImage(@ModelAttribute("imageForm") ImageForm imageForm) {
    // devo salvare l'immagine su database
    try {
      Image savedImage = service.createImage(imageForm);
      return "redirect:/image/" + savedImage.getBook().getId();
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to save image");
    }
  }

  /*
   * Controller che in base all'id dell'Image restituisce il contenuto
   */
  @RequestMapping(value = "/{imageId}/content", produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<byte[]> getImageContent(@PathVariable("imageId") Integer imageId) {
    // recupero il content dal database
    byte[] content = service.getImageContent(imageId);
    // preparo gli headers della response con il tipo di contenuto
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);
    // ritorno il contenuto, gli headers e lo status http
    return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
  }
}
