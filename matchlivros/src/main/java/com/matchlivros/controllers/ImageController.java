package com.matchlivros.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.matchlivros.models.Image;
import com.matchlivros.models.Livros;
import com.matchlivros.repository.LivrosRepository;
import com.matchlivros.service.ImageService;



@Controller
public class ImageController {
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private LivrosRepository lr;
	
	List<MultipartFile> files = new ArrayList<MultipartFile>();
	
	/*@GetMapping("/")
	public String get(Model model) {
		List<Image> images = imageService.getFiles();
		model.addAttribute("images", images);
		return "image";
	}*/
	@RequestMapping(value="/uploadFiles", method=RequestMethod.GET)
	public String form() {
		return "livros/formLivro";
	}
	
	@GetMapping("/listar")
	public ModelAndView listar(HttpServletRequest request ) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("livros/listLivros");
		//String emailUsuario = request.getUserPrincipal().getName();
		mv.addObject("livros", lr.findAll());
		return mv;
	}
	
	
	@PostMapping("/uploadFiles")
	public String uploadMultipleFiles(Livros livro) {
		//Map<String, MultipartFile> fileMap = request.getFileMap();
		if(livro != null) {
			System.out.println("dentro do null");
			lr.save(livro);
		}
		//lr.save(livro);
		//uploadImages(request);
		//Map<String, MultipartFile> fileMap = request.getFileMap();
		System.out.println("OOOOOI");
		//System.out.println("primeiro"+files.size());
		System.out.println("livro"+livro.getNome());
		
		//for(MultipartFile file: fileMap.values()) {
			//System.out.println("nome"+file.getOriginalFilename());
			//System.out.println(file);
			//System.out.println(file.getName());
			//System.out.println(file.getBytes());
			//imageService.saveFile(file, livro);
		//}
		
		/*System.out.println("oi");
		System.out.println(files.length);
		for(MultipartFile file: files) {
			System.out.println(file);
			imageService.saveFile(file, livro);
		}*/
		
		return "redirect:/continuar/" + livro.getCodigo();
	}
	
	@GetMapping("/continuar/{id}")
	public ModelAndView continuarCadastro(@PathVariable("id") Long id ) {
		ModelAndView mv = new ModelAndView();
		Optional<Livros> livro = lr.findById(id);
		mv.setViewName("livros/addImagem");
		mv.addObject("livro", livro.get());
		System.out.println(livro.get().getNome());
		//mv.addObject("categorias", tarefa.getCategorias());
		return mv;
	}
	
	@PostMapping("/uploadImages")
	public ResponseEntity<String> uploadImages( MultipartHttpServletRequest request, Long idLivro) throws IOException {
		String message = "";
		System.out.println(request.getParameterNames());
		System.out.println(idLivro);
		Optional<Livros> livro = lr.findById(idLivro);
		 Map<String, MultipartFile> fileMap = request.getFileMap();
		/*System.out.println(files.length);
		String message = "";
	    try {
	      List<String> fileNames = new ArrayList<>();

	      Arrays.asList(files).stream().forEach(file -> {    
	        fileNames.add(file.getOriginalFilename());
	      });

	      message = "Uploaded the files successfully: " + fileNames;
	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	    } catch (Exception e) {
	      message = "Fail to upload files!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	    }*/
		//lr.save(livro);
		System.out.println("INHAI");
		System.out.println("segundo"+fileMap.size());
		//System.out.println(file.getOriginalFilename());
		//List<Image> images = null; 
		//System.out.println(files.length);
		for(MultipartFile file: fileMap.values()) {
			//files.add(file);
			System.out.println(file);
			System.out.println(file.getBytes());
			//System.out.println(file.getOriginalFilename());
			//Image image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());
			//images.add(image);
			//System.out.println(file.getName());
			//System.out.println(file.getContentType());
			imageService.saveFile(file, livro.get());
		}
		/*System.out.println("oi");
		System.out.println(files.length);
		for(MultipartFile file: files) {
			System.out.println(file);
			imageService.saveFile(file, livro);
		}*/
		//System.out.println(images.toArray());
		return ResponseEntity.ok("aqui");
		//return files;
	}
	
	@GetMapping("downloadFile/{fileId}")
		public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
			Image image = imageService.getFile(fileId).get();
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(image.getDocType()))
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+image.getDocName()+"\"")
					.body(new ByteArrayResource(image.getData()));
		}
	
	@GetMapping("/convertImage/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Integer id) throws IOException{

		byte[] imageContent = imageService.writeImageToResponse(id);
		final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}
}