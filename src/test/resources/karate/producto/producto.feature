Feature: Pruebas de aceptacion para /producto

  Background:
    * url baseUrl
    # baseUrl viene de karate-config.js → http://localhost:8080
    # Background se ejecuta antes de CADA scenario

  Scenario: Obtener todos los productos - debe retornar lista
  # Esto es como hacer GET http://localhost:8080/producto en Postman

    Given path '/producto'
    # ↑ define el endpoint

    When method GET
    # ↑ ejecuta la petición

    Then status 200
    # ↑ verifica que el servidor respondió 200 OK

    And match response == '#[]'
    # ↑ verifica que la respuesta sea un array JSON
    # '#[]' es la forma de Karate de decir "esto debe ser un array"


  Scenario: Obtener productos por tipo - debe retornar solo ese tipo

    Given path '/producto/electronica'
    # ↑ llama a GET /producto/electronica

    When method GET

    Then status 200

    And match each response contains { tipo: 'electronica' }
    # ↑ verifica que CADA elemento del array tenga tipo "electronica"
    # 'match each' recorre toda la lista


  Scenario: Obtener producto por ID existente - debe retornar el producto

    Given path '/1'
    # ↑ llama a GET /1

    When method GET

    Then status 200

    And match response.id == '#number'
    # ↑ verifica que el campo id sea un número

    And match response.nombre == '#string'
    # ↑ verifica que nombre sea un string

    And match response.precio == '#number'
    # ↑ verifica que precio sea un número


  Scenario: Obtener producto por ID inexistente - debe retornar error

    Given path '/9999'
    When method GET
    Then status 500
    # ↑ tu API lanza RuntimeException cuando no existe
    #   eso genera un 500 por defecto en Spring Boot