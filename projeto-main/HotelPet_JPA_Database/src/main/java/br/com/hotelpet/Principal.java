package br.com.hotelpet;

import br.com.hotelpet.dto.*;
import br.com.hotelpet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Configuration
public class Principal implements CommandLineRunner {

    @Autowired private TutorService tutorService;
    @Autowired private AnimalService animalService;
    @Autowired private ReservaService reservaService;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        limparTela();
        System.out.println("Sistema carregado com sucesso!");
        exibirMenu();
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("""
                    
                    =========================================
                    |           HOTEL PET - MENU            |
                    =========================================
                    | 1. Cadastrar Tutor                    |
                    | 2. Cadastrar Pet                      |
                    | 3. Fazer Reserva                      |
                    | ------------------------------------- |
                    | 4. Listar Tutores (Com Pets)          |
                    | 5. Listar Pets                        |
                    | 6. Listar Reservas                    |
                    | ------------------------------------- |
                    | 0. Sair                               |
                    =========================================
                    """);
            System.out.print("Digite a opção desejada: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                limparTela();

                switch (opcao) {
                    case 1 -> cadastrarTutor();
                    case 2 -> cadastrarPet();
                    case 3 -> fazerReserva();
                    case 4 -> listarTutores();
                    case 5 -> listarPets();
                    case 6 -> listarReservas();
                    case 0 -> System.out.println("Encerrando o sistema...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("ERRO INESPERADO: " + e.getMessage());
                scanner.nextLine();
            }

            if (opcao != 0) {
                pressioneEnterParaVoltar();
                limparTela();
            }
        }
    }



    private void limparTela() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private void pressioneEnterParaVoltar() {
        System.out.println("\n-----------------------------------------");
        System.out.println("Pressione ENTER para voltar ao menu...");
        scanner.nextLine();
    }



    private void cadastrarTutor() {
        System.out.println("=== CADASTRO DE TUTOR ===");
        System.out.print("Nome Completo: ");
        String nome = scanner.nextLine();

        System.out.print("CPF (xxx.xxx.xxx-xx): ");
        String cpf = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        TutorRequestDTO dto = new TutorRequestDTO(nome, cpf, telefone);
        TutorResponseDTO salvo = tutorService.cadastrar(dto);

        System.out.println("✅ Tutor cadastrado! ID: " + salvo.id());
    }

    private void cadastrarPet() {
        System.out.println("=== CADASTRO DE PET ===");
        System.out.print("Digite o ID do Tutor dono deste pet: ");
        int tutorId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nome do Pet: ");
        String nome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = scanner.nextInt();

        System.out.print("Peso (ex: 12,5): ");
        double peso = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Tipo (CACHORRO ou GATO): ");
        String tipo = scanner.nextLine().toUpperCase();

        String raca = null;
        Boolean sociavel = null;
        Boolean usaCaixa = null;

        if (tipo.equals("CACHORRO")) {
            System.out.print("Raça: ");
            raca = scanner.nextLine();
            System.out.print("É sociável? (true/false): ");
            sociavel = scanner.nextBoolean();
        } else if (tipo.equals("GATO")) {
            System.out.print("Usa caixa de areia? (true/false): ");
            usaCaixa = scanner.nextBoolean();
        }

        PetRequestDTO dto = new PetRequestDTO(tipo, nome, tutorId, raca, peso, idade, sociavel, usaCaixa);

        try {
            PetResponseDTO salvo = animalService.adicionarPet(dto);
            System.out.println("✅ Pet cadastrado! ID: " + salvo.id());
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void fazerReserva() {
        System.out.println("=== NOVA RESERVA ===");
        System.out.print("ID do Tutor: ");
        int tutorId = scanner.nextInt();

        System.out.print("ID do Pet: ");
        int petId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tipo (SUITE_LUXO ou CANIL_STANDARD): ");
        String acomodacao = scanner.nextLine().toUpperCase();

        System.out.print("Data Entrada (AAAA-MM-DD): ");
        LocalDate entrada = LocalDate.parse(scanner.nextLine());

        System.out.print("Data Saída (AAAA-MM-DD): ");
        LocalDate saida = LocalDate.parse(scanner.nextLine());

        ReservaRequestDTO dto = new ReservaRequestDTO(tutorId, petId, entrada, saida, acomodacao);

        try {
            ReservaResponseDTO reserva = reservaService.criarReserva(dto);
            System.out.printf("✅ Reserva Confirmada! Total: R$ %.2f\n", reserva.valorTotal());
        } catch (Exception e) {
            System.out.println("❌ Erro na reserva: " + e.getMessage());
        }
    }

    

    @Transactional
    public void listarTutores() {
        System.out.println("=== LISTA DE TUTORES ===");
        List<TutorResponseDTO> lista = tutorService.listar();

        if (lista.isEmpty()) {
            System.out.println("Nenhum tutor cadastrado.");
        } else {
            lista.forEach(t -> {
                System.out.println("ID: " + t.id() + " | Nome: " + t.nome() + " | Pets: " + t.quantidadePets());
            });
        }
    }

    private void listarPets() {
        System.out.println("=== LISTA DE PETS ===");
        List<PetResponseDTO> lista = animalService.listaTodos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
        } else {
            lista.forEach(p -> {
                System.out.printf("ID: %d | Nome: %s | Tipo: %s | Dono: %s\n",
                        p.id(), p.nome(), p.tipo(), p.nomeTutor());
            });
        }
    }

    private void listarReservas() {
        System.out.println("=== LISTA DE RESERVAS ===");
        List<ReservaResponseDTO> lista = reservaService.listarReservas();

        if (lista.isEmpty()) {
            System.out.println("Nenhuma reserva encontrada.");
        } else {
            lista.forEach(r -> {
                System.out.printf("Reserva #%d | %s (Pet: %s) | %s -> %s | R$ %.2f\n",
                        r.idReserva(), r.nomeTutor(), r.nomePet(),
                        r.dataDeEntrada(), r.DataDeSaida(), r.valorTotal());
            });
        }
    }
}