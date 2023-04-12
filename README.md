# GitHub Challenge
## _An endless list of github repository sorted by star with room database for caching_

## Features

- Request to github public API to show a list of repository
- Endless scroll with recycleview
- Room database for caching and single source of truth
- Fragment navigation
- Databinding

## Tech

- Kotlin: programming language
- MVVM: Model-View-ViewModel
- Multimodule Application
- StateFlow
- Dagger-Hilt: Dependency injection
- Android Extensions (KTX)
- Architecture Components
- Constraintlayout
- RecyclerView
- Coroutines
- Glide
- Retrofit2 (including adapters and converters)
- Room
- JUnit
- Mockk
- Gradle dependency catalog(TOML file)
    - [Artigo próprio sobre a estratégia](https://medium.com/@tiagoMissiato/gradle-dependency-catalog-on-android-a005a0f6039c)

## Quality
- Teste unitario das principais classes e funcionalidades
    - View Model
    - Repositorio
    - Mapper
    - Room
- Não existe teste para o Interactor pois ele atua neste projeto como um proxy, não tem regras especificas, as bordas foram testadas então não vi necessidade de testa-lo

## Melhorias
- Criar alguns testes instrumentados
- Criar outras ordenações para o usuários escolher a que melhor atende
- Criar uma tela de detalhe com algumas informações extras sobre o repositorio
- Criar um modulo :core-testing para centralizar as implementações comuns para os testes
- Criar um modulo :core-ui para se comportar como um Design System
- Criar uma CI simples para executar os testes
- Criar uma linha de loading no adapter para o carregamento da pagina

## Known issue
- Todas as requests são cacheadas no room e quando existe mais de uma pagina cachead e o app perde a conexão na primeira request, ele se perde nas paginas, é precisa executar o pull to refresh e zerar o cache para funcionar normalmente