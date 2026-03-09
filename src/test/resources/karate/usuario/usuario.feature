Feature: Pruebas de aceptación /usuario

    Background: 
    * url baseUrl

    Scenario: Crear usuario - devuelve 201
        Given path '/usuario'
        And request {"nombre": "luisa", "email": "luisa@udea.edu.co", "contrasena": "luisa" }
        When method POST
        Then status 201
        And match response.nombre == '#string'

    Scenario: Iniciar sesión - devuelve 200
        Given path '/usuario/login'
        And request {"email": "mily@udea.edu.co", "contrasena": "luisa" }
        When method POST
        Then status 200
        And match response.email == '#string'
    
    Scenario: Crear usuario con correo existente - debe generar error
        Given path '/usuario'
        And request {"nombre": "luisa", "email": "luisa@udea.edu.co", "contrasena": "luisa" }
        When method POST
        Then status 500

    Scenario: Crear usuario con campos sin completar - debe generar error
        Given path '/usuario'
        And request {"email": "luisa@udea.edu.co", "contrasena": "" }
        When method POST
        Then status 500

    Scenario: Iniciar sesión con contraseña incorrecta - debe devolver 401
        Given path '/usuario/login'
        And request {"email": "mily@udea.edu.co", "contrasena": "mily" }
        When method POST
        Then status 401

    Scenario: Iniciar sesión con un usuario no existente - debe devolver 401
        Given path '/usuario/login'
        And request {"email": "camila@udea.edu.co", "contrasena": "mily" }
        When method POST
        Then status 404