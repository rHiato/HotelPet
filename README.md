
```markdown
# 🏨 HotelPet API

Bem-vindo ao **HotelPet**, uma API REST desenvolvida em Java com Spring Boot para gerenciar um hotelzinho para animais de estimação. O sistema permite o cadastro de tutores, pets (com características específicas para cães e gatos) e gerenciamento inteligente de reservas com cálculo automático de diárias.

## 🚀 Tecnologias Utilizadas

* **Java 21** - Linguagem moderna e robusta.
* **Spring Boot 3** - Framework para desenvolvimento ágil.
* **Spring Data JPA** - Para persistência de dados e repositórios.
* **H2 Database** - Banco de dados em memória para testes rápidos.
* **Maven** - Gerenciador de dependências.
* **Lombok** (Opcional) - Para redução de código boilerplate.

## ⚙️ Funcionalidades

### 👥 Tutores (Clientes)
* Cadastro completo com validação.
* Listagem de tutores mostrando a quantidade de pets vinculados.
* Atualização de dados cadastrais.

### 🐾 Pets (Animais)
* Sistema de herança para **Cachorros** e **Gatos**.
* **Polimorfismo:** Campos específicos para cada tipo (Raça/Sociável para cães, Uso de Caixa de Areia para gatos).
* Validação para impedir duplicidade de pets com o mesmo nome para o mesmo tutor.
* Vínculo automático com o Tutor (Dono).

### 📅 Reservas (Hospedagem)
* Cálculo automático do valor total baseado no período (Data de Entrada vs. Data de Saída).
* Duas opções de acomodação com preços diferentes:
    * **Suíte de Luxo:** R$ 100,00 / dia.
    * **Canil Standard:** R$ 50,00 / dia.
* Validação de datas (Saída não pode ser anterior à Entrada).
* Atualização inteligente: Recalcula o valor total automaticamente se o cliente mudar as datas ou o tipo de quarto.

### 🖥️ Interface (Console)
* Menu interativo no terminal para testar todas as funcionalidades sem necessidade de ferramentas externas.

---

## 🛠️ Como Rodar o Projeto

### Pré-requisitos
* Java JDK 21 instalado.
* Maven instalado (ou usar o wrapper incluso no projeto).
* Uma IDE (IntelliJ IDEA, Eclipse ou VS Code).

### Passo a Passo
1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/SEU-USUARIO/HotelPet.git](https://github.com/SEU-USUARIO/HotelPet.git)
    ```
2.  **Entre na pasta:**
    ```bash
    cd HotelPet
    ```
3.  **Execute o projeto:**
    * Pela IDE: Abra a classe `HotelPetApplication.java` e clique em Run.
    * Pelo Terminal: `mvn spring-boot:run`

4.  **Acesse o Sistema:**
    * O menu interativo aparecerá no **Console** da sua IDE.
    * Para testar via API (Postman/Insomnia), as rotas estão disponíveis em `http://localhost:8080/api/...`.

---

## 🔗 Endpoints da API (Exemplos JSON)

Se preferir testar via Postman, aqui estão os modelos de requisição:

### 1. Cadastrar Tutor
`POST /api/tutores`
```json
{
  "nome": "Ana Maria",
  "cpf": "123.456.789-00",
  "telefone": "(11) 99999-9999"
}

```

### 2. Cadastrar Pet (Cachorro)

`POST /api/pets`

```json
{
  "tipo": "CACHORRO",
  "nome": "Rex",
  "tutor-Id": 1,
  "raca": "Labrador",
  "peso": 25.5,
  "idade": 5,
  "sociavel": true
}

```

### 3. Criar Reserva

`POST /api/reservas`

```json
{
  "tutor-Id": 1,
  "pet-Id": 1,
  "tipoAcomodacao": "SUITE_LUXO",
  "dataDeEntrada": "2024-12-20",
  "dataDeSaida": "2024-12-25"
}

```

---

## 🗄️ Banco de Dados (H2 Console)

Para visualizar as tabelas e dados criados em memória:

1. Com o projeto rodando, acesse: `http://localhost:8080/h2-console`
2. **JDBC URL:** `jdbc:h2:mem:test`
3. **User:** `sa`
4. **Password:** `sa`

---

## 📝 Aprendizados

Este projeto foi desenvolvido para praticar conceitos fundamentais de Orientação a Objetos e Desenvolvimento Web, incluindo:

* Mapeamento Objeto-Relacional (ORM) com Hibernate.
* Injeção de Dependência e Inversão de Controle.
* Tratamento de Exceções Personalizadas (`@ControllerAdvice`).
* Arquitetura em Camadas (Controller, Service, Repository, Model, DTO).

---

Desenvolvido por Davi Da Silva Sinfronio

```
