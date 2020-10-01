package com.matchlivros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.matchlivros.models.Image;
import com.matchlivros.models.Livros;
import com.matchlivros.repository.ImageRepository;
import com.matchlivros.repository.LivrosRepository;

@Service
public class ImageService {
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private LivrosRepository lr;
	
	public Image saveFile(MultipartFile file, Livros livro) {
		String docname = file.getOriginalFilename();
		System.out.println(docname);
		try {
			Image image = new Image(docname, file.getContentType(), file.getBytes());
			image.setLivro(livro);
			//lr.save(livro);
			return imageRepository.save(image);
		}
		catch(Exception e){
				e.printStackTrace();
			}
		return null;
	}
	
	public Optional<Image> getFile(Integer fileId) {
		return imageRepository.findById(fileId);
	}
	public List<Image> getFiles(){
		return imageRepository.findAll();
	}
	
	public byte[] writeImageToResponse(Integer fileId)  {
        //store image in browser cache
      

        //obtaining bytes from DB
        byte[] imageData = imageRepository.findById(fileId).get().getData();
return imageData;
        //Some conversion
        //Maybe to base64 string or something else
        //Pay attention to encoding (UTF-8, etc)
       
        //write result to http response
       
	}
}

