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
import jana60.model.Category;
import jana60.repository.CategoryRepository;

@Controller
@RequestMapping("/category")
public class CategoryController {

  @Autowired
  private CategoryRepository repo;

  @GetMapping
  public String categoryList(Model model) {
    model.addAttribute("categories", repo.findAllByOrderByName());
    model.addAttribute("newCategory", new Category());
    return "/category/list";
  }

  @PostMapping("/save")
  public String saveCategory(@Valid @ModelAttribute("newCategory") Category formCategory,
      BindingResult br, Model model) {
    if (br.hasErrors()) {
      // ricarico la pagina
      model.addAttribute("categories", repo.findAllByOrderByName());
      return "category/list";

    } else {
      // salvo la category
      repo.save(formCategory);
      return "redirect:/category";
    }

  }

}
