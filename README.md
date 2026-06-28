# 🐾 IdotPet — Plataforma de Adoção de Animais

Aplicação web para cadastro e divulgação de animais disponíveis para adoção.

O projeto permite que usuários anunciem animais com descrição, localização e imagem, facilitando o processo de adoção responsável.

---

## 🚀 Tecnologias utilizadas

### Backend

* Java 21
* Quarkus
* Hibernate ORM + Panache
* PostgreSQL
* RESTEasy Reactive

### Frontend (em desenvolvimento)

* Angular

### Infraestrutura

* Docker
* Docker Compose

---

## 📦 Funcionalidades

* ✅ Cadastro de animais para adoção
* ✅ Listagem de animais
* ✅ Busca por ID
* ✅ Exclusão de animais
* ✅ Upload de imagem
* ✅ Servir imagens via endpoint

---

## 🗂️ Estrutura do projeto

```
idotpet/
├── idotpet-backend/
│   ├── src/
│   ├── uploads/          # imagens enviadas
│   ├── Dockerfile
│   └── pom.xml
├── docker-compose.yml
└── README.md
```

---

## 🧭 Documentação arquitetural

As decisões públicas de arquitetura, evolução futura e módulos planejados estão em:

* [docs/arquitetura.md](docs/arquitetura.md)

---

## ⚙️ Como rodar o projeto

### 🔧 Pré-requisitos

* Docker
* Docker Compose
* Java 21 (opcional para rodar sem Docker)

---

### ▶️ Rodando com Docker

```bash
# build do backend
cd idotpet-backend
./mvnw clean package -DskipTests

# subir containers
cd ..
docker compose up --build
```

---

### 🌐 A aplicação estará disponível em:

* Backend: http://localhost:8080

---

## 📚 Swagger / OpenAPI

A documentação interativa da API fica disponível em:

* Swagger UI: http://localhost:8080/swagger-ui
* Especificação OpenAPI: http://localhost:8080/openapi

Use o Swagger UI para visualizar endpoints, contratos de request/response e testar chamadas da API durante o desenvolvimento.

---

## 🧪 Endpoints principais

### 📌 Animais

| Método | Endpoint      | Descrição             |
| ------ | ------------- | --------------------- |
| POST   | /animais      | Cadastrar novo animal |
| GET    | /animais      | Listar todos          |
| GET    | /animais/{id} | Buscar por ID         |
| DELETE | /animais/{id} | Remover               |

#### Exemplo de cadastro com imagens:

```json
{
  "nome": "Mel",
  "descricao": "Cadela dócil para adoção",
  "idade": 2,
  "porte": "MEDIO",
  "cidade": "São Paulo",
  "estado": "SP",
  "imagemUrls": [
    "/uploads/foto-1.jpg",
    "/uploads/foto-2.jpg"
  ]
}
```

Cada animal pode ter no máximo `5` imagens. O campo antigo `imagemUrl` ainda funciona para cadastro com uma única imagem, mas `imagemUrls` é o formato recomendado para novas telas.

---

### 🖼️ Upload de imagem

**POST** `/upload`

* Content-Type: `multipart/form-data`
* Campo: `file`

#### Exemplo (curl):

```bash
curl -X POST http://localhost:8080/upload \
  -F "file=@imagem.jpg"
```

#### Resposta:

```json
{
  "fileName": "arquivo-gerado.jpg",
  "url": "/uploads/arquivo-gerado.jpg",
  "contentType": "image/jpeg",
  "size": 123456
}
```

O campo `url` é relativo para funcionar melhor com frontend, proxy reverso e ambientes de produção.

Tipos aceitos:

* `image/jpeg`
* `image/png`
* `image/webp`

Tamanho máximo padrão: `5 MB`.

---

### 📸 Acesso às imagens

As imagens são servidas via endpoint:

```
GET /uploads/{nome-do-arquivo}
```

Exemplo:

```
http://localhost:8080/uploads/arquivo.jpg
```

---

## 🐳 Docker

O projeto utiliza dois serviços:

* `backend` → aplicação Quarkus
* `db` → PostgreSQL

As imagens enviadas são persistidas via volume:

```
./idotpet-backend/uploads → /deployments/uploads
```

---

## 🔐 Variáveis de ambiente

Crie um arquivo `.env` na raiz:

```
DB_USER=postgres
DB_PASSWORD=postgres
DB_NAME=idotpet
```

Configurações de upload:

* No Docker, o backend usa `/deployments/uploads`, persistido em `./idotpet-backend/uploads`.
* Em modo local, o padrão é `uploads` dentro de `idotpet-backend`.
* Para sobrescrever o diretório, defina a variável `UPLOAD_DIR`.

---

## 📌 Observações

* O projeto está em fase inicial (MVP)
* Frontend em Angular ainda em desenvolvimento
* Upload de imagens armazenado localmente (futuro: cloud storage)

---
