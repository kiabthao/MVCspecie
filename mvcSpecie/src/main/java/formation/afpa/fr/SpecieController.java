package formation.afpa.fr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import formation.afpa.fr.exception.SpecieNotAvailableException;
import formation.afpa.fr.service.SpecieServiceImpl;

@Controller
public class SpecieController {

	@Autowired
	private SpecieServiceImpl service;

	/*
	 * @InitBinder("specie") public void initSpeciesBinder(WebDataBinder dataBinder)
	 * { dataBinder.setValidator(new SpecieValider()); }
	 * 
	 * @GetMapping("/") public String initCreationForms(Model model) { List<Specie>
	 * listSpecie; try { listSpecie = service.list(); } catch
	 * (SpecieNotAvailableException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } model.addAttribute("species", listSpecie); return
	 * "debut"; }
	 */
	@GetMapping("/")
	public String debut() {
		return "redirect:/species";
	}

	@GetMapping("/species")
	public String start(Model model) throws SpecieNotAvailableException {

		List<Specie> listSpecie = service.findAll();
		model.addAttribute("listSpecie", listSpecie);

		return "species";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
		} catch (Exception ex) {
		}
		return "redirect:/species";
	}

	@GetMapping("/species/create")
	public String create(Model model) {
		model.addAttribute("specie", new Specie());
		return "addSpecies";
	}

	@PostMapping("/species/create")
	public String save(Specie sp, Model model) throws Exception {
		service.save(sp);
		return "redirect:/species";
	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable("id") String id, Model model) throws Exception {
		Specie s = service.findById(Long.parseLong(id));
		model.addAttribute("specie", s);
		return "addSpecies";

	}

	@PostMapping("list/{id}/update")
	public String doUpdate(Specie sp, Model model) throws Exception {
		service.save(sp);
		model.asMap().replace("specie", sp);
		return "redirect:/species";

	}

}
