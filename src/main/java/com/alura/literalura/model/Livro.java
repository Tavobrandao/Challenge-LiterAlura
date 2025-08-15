package com.alura.literalura.model;

import com.alura.literalura.dto.LivroDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private String idioma;

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    public Livro() {}

    public Livro(LivroDTO dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idioma = String.join(", ", dadosLivro.idiomas());
        if (dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
            this.autor = new Autor(dadosLivro.autores().get(0));
        } else {
            this.autor = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        String nomeAutor = (autor != null) ? autor.getNome() : "Autor Desconhecido";
        return """
               ------ LIVRO ------
               Título: %s
               Autor: %s
               Idioma: %s
               -------------------
               """.formatted(titulo, nomeAutor, idioma);
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
