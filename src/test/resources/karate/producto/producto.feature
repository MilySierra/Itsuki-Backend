Feature: Pruebas de aceptacion para /producto

  Background:
    * url baseUrl
    

  Scenario: Obtener todos los productos - debe retornar lista


    Given path '/producto'


    When method GET
    

    Then status 200
    

    And match response == '#[]'
    



  Scenario: Obtener productos por tipo - debe retornar solo ese tipo

    Given path '/producto/electronica'


    When method GET

    Then status 200

    And match each response contains { tipo: 'electronica' }




  Scenario: Obtener producto por ID existente - debe retornar el producto

    Given path '/1'


    When method GET

    Then status 200

    And match response.id == '#number'


    And match response.nombre == '#string'


    And match response.precio == '#number'



  Scenario: Obtener producto por ID inexistente - debe retornar error

    Given path '/9999'
    When method GET
    Then status 500

