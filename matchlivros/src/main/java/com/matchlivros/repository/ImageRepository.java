package com.matchlivros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matchlivros.models.Image;

public interface ImageRepository  extends JpaRepository<Image, Integer>{
	@Query("SELECT t FROM Image t WHERE t.livro.id = :idLivro")
	List<Image> carregarImages(@Param("idLivro") Long codigo);
}
