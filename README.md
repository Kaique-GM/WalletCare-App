# WalletCare

## 📝 Descrição
O **WalletCare** é uma aplicação de **gestão financeira** 💰 intuitiva e eficaz, desenvolvida utilizando **Java** ☕, **JavaFX** 🎨 e **MySQL** 🗃️. O principal objetivo do projeto é proporcionar aos usuários uma maneira prática e visual de controlar suas **finanças pessoais**. Com recursos como **gráficos interativos** 📈, controle detalhado de **entradas** e **saídas financeiras** 💸, e a possibilidade de gerenciar múltiplos **anos**  e **meses** , o **WalletCare** torna o **planejamento financeiro** mais acessível e organizado . Através de uma interface amigável 👨‍💻, o usuário pode monitorar seus **gastos**, **receitas** e gerenciar suas finanças de maneira eficiente ⚙️.


## Funcionalidades 📱

- **Tela de Login** 🔐: Permite que o usuário faça login em sua conta. Caso não tenha uma conta, há uma opção para **cadastro** 📝.
  
- **Tela Home** 🏠: 
  - Exibe um **gráfico de pizza** 🍕 e três **gráficos de barras** 📊 para visualizar as **entradas**, **saídas fixas** e **saídas variáveis** 💸.
  - Permite que o usuário faça **logout** 🚪 e altere entre diferentes **meses** ou **anos** 🗓️.
  - Atalhos para adicionar ➕ novas **entradas** ou **saídas fixas** e **variáveis**.
  - Opção para **criar um novo ano financeiro** 📆.

- **Tela de Tabelas** 📋:
  - Exibe uma tabela detalhada para cada **entrada** e **tipo de saída** (fixa ou variável 💸).
  - Permite **adicionar**, **editar** ✏️ ou **remover** ❌ dados das tabelas.
  - Opção para alterar o **mês** ou o **ano** 🗓️ dos registros.


## 🌟 Tecnologias Utilizadas
 - Java ☕: Linguagem de programação principal utilizada no desenvolvimento da aplicação.
 - JavaFX 🎨: Framework utilizado para criar a interface gráfica.
 - SceneBuilder 🛠️: Ferramenta para criação de interfaces gráficas de forma visual.
 - MySQL 🗄️: Banco de dados utilizado para armazenar informações dos usuários, entradas e saídas financeiras.

## 🚀 Instalação

### 🛠️ Pré-requisitos

Antes de rodar o projeto, certifique-se de ter os seguintes programas e dependências instalados:

- **Java 8 ou superior** ☕: Para compilar e executar o código.  
- **JavaFX** 🎨: Framework necessário para criar e executar a interface gráfica da aplicação.  
- **MySQL** 🗄️: Para a base de dados. O projeto foi configurado para se conectar a um banco de dados MySQL.  

### 🏁 Passos para Rodar o Projeto

1. **Clone o repositório** 🛠️: 
   ```bash
   git clone https://github.com/Kaique-GM/WalletCare-App.git
   ```
2. **Configure o banco de dados** 🗄️:

- Crie um banco de dados MySQL utilizando o script fornecido no projeto.
- No arquivo db.properties, configure as credenciais do banco de dados
   ```bash
    db.url=jdbc:mysql://localhost:3306/nome_do_banco
    db.user=seu_usuario
    db.password=sua_senha
   ```
3. **Configure o JavaFX** 🎨:

 - Certifique-se de que o caminho do SDK do JavaFX esteja configurado corretamente no IDE.
 - Inclua as dependências do JavaFX no projeto ou configure-as no classpath.

4. **Compile e execute o projeto** ▶️:

- Abra o projeto no seu IDE preferido.
- Execute a aplicação a partir do arquivo principal (App.java).

5. **Login e uso** 🔐:

- Acesse a aplicação resgistrando um novo usuário.
- Explore as funcionalidades como gráficos e tabelas.

    `Agora você está pronto para gerenciar suas finanças com o WalletCare! 🎉`

## 🛠️ Como Usar

### 🔐 Login:
- Abra a aplicação e faça login com suas credenciais.  
- Caso ainda não tenha uma conta, clique na opção de cadastro para criar um novo usuário.

### 🏠 Tela Home:
- Visualize seus **gráficos financeiros** de **entradas**, **saídas fixas** e **saídas variáveis** 📊.  
- Utilize os botões para:  
  - **Mudar o mês ou o ano** 🗓️.  
  - **Adicionar novas entradas ou saídas** ➕.  
  - **Fazer logout** e sair da sua conta 🔓.  

### 📋 Tela de Tabelas:
- Visualize os **detalhes de cada entrada ou tipo de saída** (fixa ou variável).  
- Adicione, edite ou remova entradas e saídas conforme necessário ✏️.  
- Mude o mês ou ano dos registros para ver informações de outros períodos 🗂️.

## 📸 Imagens

### 🏠 Tela Home
Na tela Home, você pode visualizar os gráficos de **entradas**, **saídas fixas** e **saídas variáveis**, bem como mudar de mês/ano ou adicionar novas entradas e saídas.

![Tela Home](/demo/src/main/resources/img/home.png)

### 📋 Tela de Tabelas
Na tela de Tabelas, você pode visualizar, adicionar, editar ou remover entradas e saídas financeiras. Além disso, é possível mudar o mês ou ano dos registros.

![Tela de Tabelas](/demo/src/main/resources/img/tables.png)

 ## 💬 Feedback e Contribuições

Se você encontrou um bug ou gostaria de sugerir melhorias, sinta-se à vontade para abrir uma `Issue` ou enviar um `Pull Request`. Agradeço seu feedback!