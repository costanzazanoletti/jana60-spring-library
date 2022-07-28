package jana60.model;

import org.springframework.web.multipart.MultipartFile;

/*
 * Data Transfer Object: una classe che serve a trasportare i dati che
 * servono a creare/modificare oggetti di un'altra classe
 * In questo caso fa da tramite tra Image e la form html, in cui
 * il campo di tipo file può essere mappato solo su un attributo MultipartFile
 */
public class ImageForm {

  private Integer id;

  private Book book;

  private MultipartFile contentMultipart;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public MultipartFile getContentMultipart() {
    return contentMultipart;
  }

  public void setContentMultipart(MultipartFile contentMultipart) {
    this.contentMultipart = contentMultipart;
  }


}
