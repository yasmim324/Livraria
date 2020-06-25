package livraria.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import livraria.model.Autor;
import livraria.model.AutorRepo;
import livraria.model.Editora;
import livraria.model.EditoraRepo;
import livraria.model.Genero;
import livraria.model.GeneroRepo;
import livraria.model.Livro;
import livraria.model.LivroRepo;


@Controller
public class LivroController {
	
	@Autowired
	private LivroRepo repo;
	
	@Autowired
	private GeneroRepo generoRepo;
	
	@Autowired
	private EditoraRepo editoraRepo;
	
	@Autowired
	private AutorRepo autorRepo;
	

	@GetMapping("/createList")
	public String create(Model model) {
	model.addAttribute("livro", new Livro());
	model.addAttribute("generos", getGeneros());
	model.addAttribute("editoras", getEditoras());
	model.addAttribute("autor", getAutor());
	
	List<Livro> lista = repo.findAll();
	
	model.addAttribute("lista", lista);
	
	return "livroformList";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute Livro livro) {
		
		if (livro.getId() != 0) {
			Livro livroEditada = livro;
			livro= repo.findById(livroEditada.getId()).get();
			
			livro.setTitulo(livroEditada.getTitulo());
			livro.setDescricao(livroEditada.getDescricao());
			livro.setAno(livroEditada.getAno());
			livro.setPreco(livroEditada.getPreco());
			livro.setGenero(livroEditada.getGenero());
			livro.setEditora(livroEditada.getEditora());
			livro.setAutor(livroEditada.getAutor());
			
		}
		
	repo.save(livro);
	return "redirect:/list";
	
	}
	
	@GetMapping("/list")
	public String listAll(Model model, @ModelAttribute Livro livro) {
		List<Livro> lista = repo.findAll();
		
		model.addAttribute("lista", lista);
		model.addAttribute("generos", getGeneros());
		model.addAttribute("editoras", getEditoras());
		model.addAttribute("autor", getAutor());
		
		
		return "livroformList";
	}
	
	@GetMapping("/update/{id}")
	public String update(@PathVariable Integer id, Model model) {
		Livro livro = repo.findById(id).get();
		
		model.addAttribute("livro", livro);
		List<Livro> lista = repo.findAll();
		
		model.addAttribute("lista", lista);
		model.addAttribute("generos", getGeneros());
		model.addAttribute("editoras", getEditoras());
		model.addAttribute("autor", getAutor());
		
		
		return "livroformList";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		repo.deleteById(id);
		
		return "redirect:/list";
		

	}
	
	
	private List<Genero> getGeneros(){
		List<Genero> generos = generoRepo.findAll();
		
		return generos;
	}
	
	private List<Editora> getEditoras(){
		List<Editora> editoras = editoraRepo.findAll();
		
		return editoras;
		
	} 
	
	private  List<Autor> getAutor(){
		List<Autor> autor = autorRepo.findAll();
	     
		return autor;
	}
	
	
	
	

}
	
	
	
	
	



