package com.alura.literalura.principal;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.dto.LivroDTO;
import com.alura.literalura.dto.ResultadoDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoApiService;
import com.alura.literalura.service.ConverteDadosService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApiService consumoApi = new ConsumoApiService();
    private final ConverteDadosService conversor = new ConverteDadosService();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private LivroRepository livroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                \n LiterAlura - Escolha uma opção:
                1- Buscar livro pelo título
                2- Listar livros registrados
                3- Listar autores registrados
                4- Listar autores vivos em um determinado ano
                5- Listar livros em um determinado idioma
                0- Sair
                """;
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo do LiterAlura...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o nome do livro que você deseja buscar:");
        var nomeLivro = leitura.nextLine();
        var json = consumoApi.obterDados((ENDERECO + nomeLivro.replace(" ", "%20")));

        ResultadoDTO dadosBusca = conversor.obterDados(json, ResultadoDTO.class);

//        Optional<Livro> livroEncontrado = dadosNusca.resultados().stream()
//                .map(l -> new Livro(l))
//                .findFirst();

//        if (livroEncontrado.isPresent()) {
//            Livro livro = livroEncontrado.get();
//            // Lógica para verificar se o autor já existe e salvar o livro
//            // (Verificar repositórios antes de salvar para não duplicar)
//            System.out.println("Livro encontrado e salvo no banco de dados!");
//            System.out.println(livro);
//            // Salvar no repositório...
//            // livroRepositorio.save(livro);
//        } else {
//            System.out.println("Livro não encontrado.");
//        }

        // Verifica se a busca retornou algum resultado
        if (dadosBusca == null || dadosBusca.resultados().isEmpty()) {
            System.out.println("Livro não encontrado na API Gutendex.");
            return;
        }

        LivroDTO livroDTO = dadosBusca.resultados().get(0);

        Optional<Livro> livroExistente = livroRepositorio.findByTituloContainsIgnoreCase(livroDTO.titulo());

        if (livroExistente.isPresent()) {
            System.out.println("O livro '" + livroExistente.get().getTitulo() + "' Já está registrado no banco de dados.");
            return;
        }

        Autor autor;
        if (livroDTO.autores() == null || livroDTO.autores().isEmpty()) {
            System.out.println("Livro encontrado, mas sem autor associado. Não será salvo.");
            return;
        }

        AutorDTO autorDTO = livroDTO.autores().get(0);

        Optional<Autor> autorExistente = autorRepositorio.findByNomeContainingIgnoreCase(autorDTO.nome());

        if (autorExistente.isPresent()) {
            autor = autorExistente.get();
            System.out.println("Autor '" + autor.getNome() + "' já existe no banco de dados.");
        } else {
            autor = new Autor(autorDTO);
            autorRepositorio.save(autor);
            System.out.println("Novo autor '" + autor.getNome() + "' salvo no banco de dados.");
        }

        Livro novoLivro = new Livro(livroDTO);
        novoLivro.setAutor(autor);
        livroRepositorio.save(novoLivro);

        System.out.println("Livro salvo com sucesso!");
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepositorio.findAll();
        if (livros.isEmpty()) {
            System.out.println("\nNenhum livro registrado.\n");
        } else {
            System.out.println("\nLIVROS REGISTRADOS!");
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepositorio.findAll();
        if (autores.isEmpty()) {
            System.out.println("\nNenhum autor registrado no banco de dados.\n");
        } else {
            System.out.println("\nAUTORES REGISTRADOS!");
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Digite o ano para buscar autores vivos:");
        try {
            var ano = leitura.nextInt();
            leitura.nextLine();
            List<Autor> autoresVivos = autorRepositorio.findAutoresVivosEmAno(ano);
            if (autoresVivos.isEmpty()) {
                System.out.println("\nNenhum autor vivo encontrado para o ano de " + ano + ".\n");
            } else {
                System.out.println("\n--- AUTORES VIVOS EM " + ano + " ---");
                autoresVivos.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Ano inválido. Por favor, digite um número.");
            leitura.nextLine();
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Digite o idioma para a busca (ex: pt, en, es, fr):");
        var idioma = leitura.nextLine().toLowerCase();

        List<Livro> livrosPorIdioma = livroRepositorio.findByIdioma(idioma);

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("\nNenhum livro encontrado para o idioma '" + idioma + "'.\n");
        } else {
            System.out.println("\nLIVROS NO IDIOMA '" + idioma + "' ");
            livrosPorIdioma.forEach(System.out::println);
        }
    }
}