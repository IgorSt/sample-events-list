# Listining Events


## Aplicativo que consome API e mostra uma listagem de eventos, possibilitando também o checkin e compartilhamento desse evento.


## Tecnologias utlizadas:


 1. **Android Navigation**: Parte da biblioteca Jetpack, nos auxilia entre a
    navegação das telas do aplicativo, inclusive permite a passagem
    segura de informações entre telas.
 2. **Retrofit**: Um completo cliente HTTP para android, nesse caso foi
    utilizado somente para requisições GET e POST
 3. **HILT**: Utilizado como Injetor de Dependência, nos poupa trabalho
    quando precisamos referencias objetos que precisam de parâmetros
 4. **Timber**: É uma classe de log que não gera logs em produção
 5. **Android LiveData**: Também faz parte da biblioteca Jetpack, é uma
    classe que observa dados
 6. **Android ViewModel**:  ------------------------------------------,
    cuida da armazenagem e gerenciamento de dados relacionado à UI, como
    por exemplo, manter o estado de um objeto após o usuário rotacionar
    a tela do device, porém nesse caso, foi utilizada para manter o
    princípio de separação de regras de negócio da view como no Design
    Pattern MVVW
 7. **GLIDE**: Utilizado para renderizar imagens vindas na API
 8. **MVVW**: É um modelo de padrão de projeto recomendado pela Google, que
    tem como objetivo a separação de responsabilidades em camadas
    (model, view, data ...)
