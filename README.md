# Global Solution · OOP · Tema CitizenTech

## Integrantes

| Nome | RM |
|------|----|
| Leticia | 564028 |
| Giovanna | 562018 |


Repositório: https://github.com/AriArisu/gs-java.git

## Público beneficiado e decisão apoiada

**Quem sofre o problema:** moradores e comércios de pontos baixos perto de córregos. O território é descrito por região e ponto monitorado, nunca por pessoas.

**Quem usa o sistema:** a associação de bairro, a Defesa Civil ou a prefeitura que acompanha o ponto.

**Decisão que o sistema apoia:** "o que fazer hoje, antes da chuva chegar?" (avisar moradores, limpar bueiros, posicionar equipe de campo, preparar um abrigo). O sistema lê pluviômetro, nível do córrego e umidade do solo, prevê os próximos valores e emite alertas **ATENÇÃO, ALERTA ou CRÍTICO** explicando cada um. Ele não decide pela equipe.

## Como rodar

Requisitos: JDK 17 ou superior. Não precisa instalar banco (usamos H2) nem o Maven (o projeto traz o `mvnw`).

```bash
./mvnw spring-boot:run    
./mvnw test              
```

No IntelliJ: abra o `pom.xml` como projeto, aguarde o Maven baixar as dependências e execute `TerritoryApplication`. O menu aparece na janela Run.

**Banco:** H2 em arquivo (`./data/territory.mv.db`, criado sozinho). 

### Roteiro rápido para ver tudo funcionando

Nos números decimais digitados no menu, use ponto (ex.: `2.5`).

1. Menu **1 → 1** pelo menos 5 vezes, para formar histórico dos 3 sensores.
2. Menu **2** para rodar as duas previsões (e gerar alertas, se os valores atingirem os limiares).
3. Para forçar um alerta: Menu **1 → 2**, sensor `PLV-01`, valores altos como `90`, `95` e `99`, e depois Menu **2**.
4. Para ver a exceção customizada: Menu **1 → 2**, sensor `PLV-01`, valor `-5` ou `500`.
5. Menu **3** para ver medições e alertas emitidos.
6. Feche com **0**, rode de novo e confira que o histórico continua lá.

## Menu

| Opção | O que faz |
|-------|-----------|
| 1 Verificar sensores | Lê todos os sensores (grava no banco) ou registra uma leitura manual, validada |
| 2 Rodar previsões | Média móvel e comparação com o período anterior sobre o histórico do banco; grava os alertas |
| 3 Ver histórico | Medições por sensor e alertas já emitidos |
| 4 Gerenciar dados do território | Ver dados, alterar população e alterar limiares (validados) |
| 5 Status completo | Território, limiares, status dos sensores, medições gravadas e total de alertas |
| 0 Encerrar | Sai do programa (os dados continuam no banco) |

## Estrutura

Camadas: model, repository, service, mais a pasta `excecao` pedida na GS.

```
src/main/java/br/com/territory/
├── TerritoryApplication.java     classe principal (Spring Boot)
├── SistemaMonitoramento.java     menu de console (Scanner)
├── model/                        ComponenteTerritorial, Sensor (+ 3 sensores), DadosTerritorio, Limiares,
│                                 EstrategiaPrevisao (+ 2 filhas), Medicao, Alerta, NivelAlerta
├── excecao/                      DadosInvalidosException, FonteIndisponivelException
├── repository/                   MedicaoRepository, AlertaRepository (Spring Data JPA)
└── service/                      ServicoMonitoramento, ConfiguracaoSistema
src/test/java/br/com/territory/   MedicaoTest, PrevisaoTest, ServicoMonitoramentoTest
```

## Decisões da equipe

- **Recorte: clima local.** Três sensores coerentes entre si: pluviômetro (mm/h), nível do córrego (m) e umidade do solo (%).
- **Spring Boot + JPA.** Escolhemos o caminho JPA: as entidades `Medicao` e `Alerta` e os repositórios `MedicaoRepository` e `AlertaRepository`. O Spring gerencia as conexões, então não há SQL escrito à mão.
- **Previsão só com histórico suficiente.** Com menos leituras que o mínimo (padrão 5), o sistema avisa que não há previsão.
- **Dados simulados.** Os sensores sorteiam valores dentro da faixa (valores baixos são mais comuns) e falham cerca de 10% das vezes, para exercitar `FonteIndisponivelException`.
- **Limiares de exemplo, não oficiais.** Pluviômetro 25/50/80 mm/h, nível 1,5/2,5/3,5 m, umidade 60/80/95%. Devem ser calibrados com a Defesa Civil local.

## Testes

`./mvnw test`. Testes unitários em padrão AAA (Arrange, Act, Assert), sem banco e sem console:

- `MedicaoTest`: validação da medição, com `assertThrows` de `DadosInvalidosException`.
- `PrevisaoTest`: o cálculo das duas estratégias e o histórico curto (se a conta errar, o teste fica vermelho).
- `ServicoMonitoramentoTest`: o **Mockito** simula `MedicaoRepository` e `AlertaRepository` e testa a lógica de leitura, previsão e alerta sem banco.


## Uso de IA

> **A equipe deve revisar e completar esta seção com o que realmente aconteceu.**

- Ferramenta usada: Claude (Anthropic).
- Onde foi usada: _[descrever: ex. estrutura das camadas, rascunho de classes, testes, README]_.
- Como revisamos: _[descrever: quem leu cada parte, o que foi alterado, o que foi testado à mão]_.
- Todos os integrantes devem conseguir explicar qualquer trecho do código na conversa de correção.
