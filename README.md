# API de IMC com Java

Projeto iniciante para praticar back-end com Java usando apenas recursos da linguagem. A primeira versão era uma calculadora de IMC em Python no terminal; agora o repositório também mostra uma evolução para uma API HTTP simples em Java.

## O que este projeto demonstra

- Criação de servidor HTTP em Java
- Endpoint `POST` para cálculo de IMC
- Separação entre entrada, regra de negócio e resposta
- Validação básica de dados
- Resposta em JSON
- Evolução de lógica de terminal para uma pequena API back-end

## Como executar a versão Java

Requisito: Java 17 ou superior.

```bash
javac src/ImcApi.java
java -cp src ImcApi
```

A API sobe em:

```text
http://localhost:8080
```

## Rotas

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/health` | Verifica se a API está online |
| `POST` | `/imc` | Calcula o IMC a partir de peso e altura |

## Exemplo

```bash
curl -X POST http://localhost:8080/imc \
  -H "Content-Type: application/json" \
  -d '{"peso":72,"altura":1.75}'
```

Resposta esperada:

```json
{
  "imc": 23.51,
  "classificacao": "Peso normal"
}
```

## Versão anterior em Python

O arquivo `imc.py` foi mantido como registro da primeira etapa do projeto, praticando entrada de dados, funções e condicionais.

```bash
python imc.py
```

## Próximos passos

- Criar classes separadas para service e controller
- Melhorar o parser de JSON usando uma biblioteca
- Criar testes automatizados
- Evoluir para Spring Boot quando a base de Java estiver mais firme
