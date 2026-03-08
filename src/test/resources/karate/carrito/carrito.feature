Feature: Pruebas de aceptacion para /carrito

    Background:
        * url baseUrl

    Scenario: Flujo completo 1. agregar - agregar carrito, decrementar y eliminar
        Given path '/carrito/1/1'
        When method POST
        Then status 201
        And match response.id == '#number'
        * def carritoId = response.id

        #Flujo completo 2. Obtener carrito por el id del usuario
        Given path '/carrito/1'
        When method GET
        Then status 200
        And match response == '#[]'

        #Flujo completo 3. Actualizar decrementar cantidad producto del carrito
        Given path '/carrito/' + carritoId
        When method PUT
        Then status 200

        #Flujo completo 4. Volver agregar carrito para poder eliminar
        Given path '/carrito/1/1'
        When method POST
        Then status 201
        And match response.id == '#number'
        * def carritoId2 = response.id

        #Flujo completo 5. Eliminar
        Given path '/carrito/' + carritoId2
        When method DELETE
        Then status 200
        And match response == 'true'