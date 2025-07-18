---
title: Galaxy Blitz
description: Um shooter espacial em Java com design limpo, jogabilidade intensa e estrutura modular.
authors:
  - name: João Lucas Melo
    url: https://github.com/joaolucasmmelo
---

# 🚀 Galaxy Blitz

**Galaxy Blitz** é um jogo de **space shooter** desenvolvido em **Java puro**, com foco em desempenho, jogabilidade fluida e estrutura de código modular. Ideal para quem quer entender como fazer um game 2D do zero usando apenas a linguagem Java.

---

## 🎮 Jogabilidade

- Mova a nave com: `W`, `A`, `S`, `D`
- Boost com: `SHIFT`
- Atire com: `P` e `ESPAÇO`
- Enfrente ondas de inimigos, desvie de tiros, colete power-ups e marque pontos

---

## 🧰 Tecnologias Utilizadas

- **Java AWT/Swing** — para renderização gráfica
- **Threads** — controle de loop de jogo em tempo real
- **KeyListener** — detecção de eventos de teclado
- **Canvas & Graphics2D** — renderização 2D
- **Gestão de colisão** — via `Rectangle.intersects(...)`
- **Recursos visuais e sonoros** — carregados com `getResourceAsStream()`

---

## 📁 Estrutura do Projeto

```
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── meujogo.Modelo/
│ │ │ ├── Container.java
│ │ │ ├── Enemy1.java
│ │ │ ├── Heart.java
│ │ │ ├── Phase.java
│ │ │ ├── Player.java
│ │ │ ├── Shot.java
│ │ │ ├── SoundPlayer.java
│ │ │ └── SpecialShot.java
│ │ └── resources/
│ │   └── Media/
├── GalaxyBlitz.jar
├── README.md
├── pom.xml
└── start.bat
```

---

## ⚙️ Como Rodar
### Execução no terminal
Essa execução roda o jogo completo com som.

---

### Executar o start.bat
essa execução é a mais simples porém não foi completamente desenvolvida, 
fazendo o jogo rodar sem som.

---

### Compilação Manual

```bash
javac -d bin src/meujogo/**/*.java
java -cp bin meujogo.principalmain.Main
```

---

## 📦 Criando o `.jar` Executável

### 1. Crie o Manifest

Arquivo `manifest.txt`:

```
Main-Class: meujogo.principalmain.Main
Class-Path: .
```

(*linha em branco no final é obrigatória*)

### 2. Gere o JAR

```bash
jar cfm GalaxyBlitz.jar manifest.txt -C bin .
```

### 3. Rode o Jogo

```bash
java -jar GalaxyBlitz.jar
```

---

## 🌠 Recursos Planejados

- Música de fundo
- Efeitos sonoros
- Múltiplas fases
- Ataque dos inimigos
- Sistema de score

---

## 👤 Autor

**João Lucas Melo**  
GitHub: [@joaolucasmmelo](https://github.com/joaolucasmmelo)

---

## 📜 Licença

Distribuído sob licença MIT. Veja `LICENSE` para mais detalhes.

---

## ⭐ Contribua

Se você curtiu o projeto:

- Deixe uma ⭐ no repositório!
- Reporte bugs e envie melhorias via Pull Request
- Compartilhe com amigos desenvolvedores 🚀

---

**Boas batalhas no espaço!** 🛸👾
