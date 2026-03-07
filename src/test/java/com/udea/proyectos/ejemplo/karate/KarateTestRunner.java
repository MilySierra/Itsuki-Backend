package com.udea.proyectos.ejemplo.karate;

import com.intuit.karate.junit5.Karate;


public class KarateTestRunner {

    @Karate.Test
    Karate testProducto() {
        return Karate.run("classpath:karate/producto/producto.feature");
    }
}
