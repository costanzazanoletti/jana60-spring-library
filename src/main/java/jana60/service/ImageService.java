package jana60.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jana60.model.Book;
import jana60.model.Image;
import jana60.model.ImageForm;
import jana60.repository.BookRepository;
import jana60.repository.ImageRepository;

@Service
public class ImageService {

  @Autowired
  private ImageRepository repo;

  @Autowired
  private BookRepository bookRepo;

  /*
   * A partire dal book id cerco tutte le immagini associate
   */
  public List<Image> getImagesByBookId(Integer bookId) {

    Book book = bookRepo.findById(bookId).get();
    return repo.findByBook(book);

  }

  /*
   * A partire dal book id creo un'istanza di ImageForm associata
   * a quel Book
   */
  public ImageForm createImageForm(Integer bookId) {
    Book book = bookRepo.findById(bookId).get();
    ImageForm img = new ImageForm();
    img.setBook(book);
    return img;
  }


  /*
   * A partire da un oggetto ImageForm deve creare un oggetto di tipo Image
   * e salvarlo su database
   */
  public Image createImage(ImageForm imageForm) throws IOException {
    // creo un oggetto Image vuoto
    Image imgToSave = new Image();
    // lo inizializzo coi dati di ImageForm
    imgToSave.setBook(imageForm.getBook());
    // trasformo il content MultipartFile in un byte[] e lo passo all'Image da salvare
    if (imageForm.getContentMultipart() != null) {
      byte[] contentSerialized = imageForm.getContentMultipart().getBytes();
      imgToSave.setContent(contentSerialized);
    }
    // salvo Image su database
    return repo.save(imgToSave);
  }

  /* a partire dall'id dell'Image restituisce il content */
  public byte[] getImageContent(Integer id) {
    Image img = repo.findById(id).get();
    return img.getContent();
  }
}
