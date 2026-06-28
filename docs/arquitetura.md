# Arquitetura Publica do IdotPet

Este documento registra a arquitetura publica do IdotPet. Ele deve ser atualizado sempre que uma decisao estrutural importante for tomada.

Este arquivo deve conter apenas informacoes adequadas para um repositorio publico. Detalhes sensiveis de seguranca, antifraude, moderacao, provedores de pagamento, webhooks, credenciais, regras internas de aprovacao e configuracoes reais de producao nao devem ser documentados aqui.

## Objetivo do Produto

O IdotPet é uma plataforma para cadastro e divulgação de animais disponíveis para adoção. A visão futura é evoluir para uma plataforma com:

- anúncios vinculados a usuários autenticados;
- perfis diferentes para pessoas físicas, abrigos e administradores;
- verificacao e moderacao de conteudo;
- doacoes direcionadas a organizacoes cadastradas;
- sustentabilidade financeira para custos de manutencao do projeto.

## Decisão Arquitetural Principal

O projeto deve evoluir como um monólito modular.

Microserviços não são recomendados neste momento porque aumentariam a complexidade operacional antes de existir uma necessidade real. O caminho atual é manter um backend único, mas organizado por domínios claros.

Dominios planejados:

```text
autenticacao
usuarios
animais
organizacoes
uploads
doacoes
pagamentos
moderacao
```

Essa organização permite evoluir com simplicidade agora e, se necessário no futuro, extrair módulos específicos para serviços separados.

## Stack Atual

- Java 21
- Quarkus
- Hibernate ORM com Panache
- PostgreSQL
- Docker e Docker Compose
- API REST com JSON
- Upload local de imagens com diretório configurável

## Estado Atual do Backend

O backend possui os seguintes recursos principais:

- cadastro de animais;
- listagem paginada de animais;
- busca de animal por ID;
- exclusão de animal;
- upload de imagens;
- suporte a até 5 imagens por animal;
- resposta de erro padronizada em JSON para erros conhecidos.

## Fluxo Atual de Cadastro de Animal

O cadastro de animal com imagens funciona em duas etapas.

1. O frontend envia cada imagem para:

```http
POST /upload
```

O backend retorna:

```json
{
  "fileName": "arquivo-gerado.jpg",
  "url": "/uploads/arquivo-gerado.jpg",
  "contentType": "image/jpeg",
  "size": 123456
}
```

2. O frontend cadastra o animal usando as URLs retornadas:

```http
POST /animais
```

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

Cada animal pode ter no máximo 5 imagens.

O campo `imagemUrl` continua existindo por compatibilidade e representa a primeira imagem do animal. O campo recomendado para novas telas é `imagemUrls`.

## Modelo Atual Simplificado

```text
Animal
  id
  nome
  descricao
  idade
  porte
  status
  cidade
  estado
  imagemUrl
  criadoEm
  imagens

AnimalImagem
  id
  url
  ordem
  animal
```

## Direcao Futura do Modelo

Para suportar usuarios, organizacoes e doacoes, o modelo deve evoluir de forma gradual. Em alto nivel, os principais conceitos planejados sao:

```text
Usuario
  id
  nome
  email
  tipoPerfil
  status
  criadoEm

Organizacao
  id
  responsavel
  nomePublico
  cidadeEstado
  status

Animal
  id
  publicadoPor
  organizacao opcional
  nome
  descricao
  status

AnimalImagem
  id
  animal
  url
  ordem

Doacao
  id
  doador opcional
  organizacao
  valor
  status
  criadoEm
```

Campos sensiveis, documentos, identificadores externos e detalhes de integracao devem ficar fora da documentacao publica.

## Perfis de Usuário Planejados

Perfis previstos em alto nivel:

- pessoa fisica;
- organizacao ou abrigo;
- administrador.

Regras esperadas:

- pessoa fisica pode publicar anuncios proprios;
- organizacao pode publicar anuncios vinculados ao seu perfil;
- organizacoes poderao passar por verificacao antes de acessar recursos sensiveis;
- administradores terao recursos internos de gestao e moderacao.

## Autenticação Planejada

A autenticacao deve usar tokens de sessao ou JWT, com expiracao e validacao no backend.

O backend deve identificar o usuario autenticado pelo token. O frontend nao deve enviar manualmente o ID do usuario ao criar um anuncio.

## Regras Futuras Para Anúncios

Quando autenticacao existir:

- todo anuncio deve ter um usuario publicador;
- usuario comum so pode gerenciar recursos proprios;
- organizacao so pode gerenciar recursos vinculados ao proprio perfil;
- administradores terao permissoes internas de moderacao.

## Doações e Pagamentos

Doacoes devem ser implementadas somente depois da base de usuarios e organizacoes estar pronta.

Fluxo publico em alto nivel:

```text
Frontend inicia a doacao
-> Backend cria o registro necessario
-> Provedor externo processa o pagamento
-> Backend atualiza o status da doacao
```

O backend deve centralizar as regras financeiras. O frontend nunca deve ser a fonte confiavel para calculos, estados de pagamento ou regras de repasse.

Detalhes de provedor, validacao de eventos, regras de repasse, antifraude e operacao financeira devem ficar em documentacao privada.

## Segurança

Diretrizes publicas para evolucao:

- senhas nunca devem ser armazenadas em texto puro;
- sessoes e tokens devem ter expiracao;
- validação forte nos DTOs;
- usuarios so podem alterar recursos permitidos;
- permissoes administrativas devem ser explicitas;
- organizacoes devem passar por verificacoes antes de acessar recursos sensiveis;
- upload de imagens deve manter limite de tipo e tamanho;
- integracoes externas devem ser validadas no backend;
- acoes sensiveis devem ter rastreabilidade interna.

Detalhes especificos de seguranca operacional nao devem ser publicados neste arquivo.

## Ordem Recomendada de Implementação

1. Usuarios, cadastro, login e autenticacao.
2. Vincular anuncios ao usuario autenticado.
3. Criar fluxo de perfil para organizacoes e abrigos.
4. Adicionar permissoes e moderacao basica.
5. Implementar doacoes em modo preparado para integracao externa.
6. Integrar provedor de pagamento real.
7. Criar relatorios administrativos de doacoes.

## Critério Para Pensar em Microserviços

Microserviços só devem ser considerados quando houver necessidade real, como:

- domínio de pagamento muito complexo;
- equipe separada cuidando de módulos diferentes;
- necessidade de escalar partes específicas de forma independente;
- deploys independentes trazendo benefício claro;
- observabilidade, filas e infraestrutura já maduras.

Até lá, o caminho recomendado é manter o monólito modular.
