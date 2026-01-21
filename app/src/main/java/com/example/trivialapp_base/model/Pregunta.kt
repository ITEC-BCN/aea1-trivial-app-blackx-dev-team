package com.example.trivialapp_base.model

// Definición de la clase de datos Pregunta según requisitos
data class Pregunta(
    val pregunta: String,
    val categoria: String,
    val dificultad: String, // "Facil", "Medio", "Dificil"
    val respuesta1: String,
    val respuesta2: String,
    val respuesta3: String,
    val respuesta4: String,
    val respuestaCorrecta: String // Debe coincidir con una de las anteriores
)

// Objeto para simular la base de datos local (Hardcoded)
object ProveedorPreguntas {
    fun obtenerPreguntas(): MutableList<Pregunta> {
        return mutableListOf(

            // ==================== FÁCIL (30) ====================
            Pregunta("¿Capital de Francia?", "Geografía", "Facil", "Madrid", "París", "Roma", "Berlín", "París"),
            Pregunta("¿Fórmula del agua?", "Ciencia", "Facil", "H2O", "CO2", "O2", "H2O2", "H2O"),
            Pregunta("¿Autor del Quijote?", "Literatura", "Facil", "Cervantes", "Quevedo", "Lope", "Góngora", "Cervantes"),
            Pregunta("¿Planeta rojo?", "Ciencia", "Facil", "Marte", "Júpiter", "Venus", "Saturno", "Marte"),
            Pregunta("¿Rey de los dioses griegos?", "Mitología", "Facil", "Zeus", "Hades", "Ares", "Apolo", "Zeus"),
            Pregunta("¿Animal que ladra?", "Naturaleza", "Facil", "Gato", "Perro", "Vaca", "Caballo", "Perro"),
            Pregunta("¿Color del cielo?", "General", "Facil", "Verde", "Rojo", "Azul", "Amarillo", "Azul"),
            Pregunta("¿Cuántos días tiene una semana?", "General", "Facil", "5", "6", "7", "8", "7"),
            Pregunta("¿Lenguaje de Android?", "Tecnología", "Facil", "Swift", "Kotlin", "PHP", "Ruby", "Kotlin"),
            Pregunta("¿Mamífero más grande?", "Naturaleza", "Facil", "Elefante", "Ballena azul", "Rinoceronte", "Hipopótamo", "Ballena azul"),
            Pregunta("¿Resultado de 2+2?", "Matemáticas", "Facil", "3", "4", "5", "6", "4"),
            Pregunta("¿Mes con Navidad?", "General", "Facil", "Enero", "Febrero", "Diciembre", "Marzo", "Diciembre"),
            Pregunta("¿Deporte de Messi?", "Deportes", "Facil", "Tenis", "Fútbol", "Baloncesto", "Golf", "Fútbol"),
            Pregunta("¿Animal que maúlla?", "Naturaleza", "Facil", "Perro", "Gato", "León", "Tigre", "Gato"),
            Pregunta("¿Día después del lunes?", "General", "Facil", "Domingo", "Martes", "Viernes", "Sábado", "Martes"),
            Pregunta("¿Océano más grande?", "Geografía", "Facil", "Atlántico", "Índico", "Pacífico", "Ártico", "Pacífico"),
            Pregunta("¿Moneda de EE.UU.?", "Economía", "Facil", "Euro", "Dólar", "Yen", "Libra", "Dólar"),
            Pregunta("¿Forma de la Tierra?", "Ciencia", "Facil", "Plana", "Cuadrada", "Redonda", "Triangular", "Redonda"),
            Pregunta("¿Color de la sangre?", "Biología", "Facil", "Azul", "Rojo", "Verde", "Negro", "Rojo"),
            Pregunta("¿Héroe de Gotham?", "Cine", "Facil", "Superman", "Batman", "Spiderman", "Iron Man", "Batman"),
            Pregunta("¿Animal más rápido?", "Naturaleza", "Facil", "León", "Guepardo", "Tigre", "Caballo", "Guepardo"),
            Pregunta("¿Idioma de Brasil?", "Geografía", "Facil", "Español", "Portugués", "Inglés", "Francés", "Portugués"),
            Pregunta("¿Estación más fría?", "General", "Facil", "Verano", "Otoño", "Invierno", "Primavera", "Invierno"),
            Pregunta("¿Instrumento con teclas?", "Música", "Facil", "Guitarra", "Piano", "Violín", "Flauta", "Piano"),
            Pregunta("¿Número romano V?", "Historia", "Facil", "1", "5", "10", "50", "5"),
            Pregunta("¿Ingrediente principal de la cerveza?", "Cerveza", "Facil", "Uvas", "Lúpulo", "Arroz", "Trigo", "Lúpulo"),
            Pregunta("¿Bebida alcohólica fermentada de cebada?", "Cerveza", "Facil", "Vino", "Cerveza", "Sidra", "Ron", "Cerveza"),

            // ==================== MEDIO (30) ====================
            Pregunta("¿Planeta más grande?", "Ciencia", "Medio", "Tierra", "Marte", "Júpiter", "Saturno", "Júpiter"),
            Pregunta("¿Año descubrimiento América?", "Historia", "Medio", "1492", "1500", "1485", "1600", "1492"),
            Pregunta("¿Hueso más largo?", "Anatomía", "Medio", "Tibia", "Húmero", "Fémur", "Radio", "Fémur"),
            Pregunta("¿Moneda de Japón?", "Economía", "Medio", "Yuan", "Won", "Yen", "Dólar", "Yen"),
            Pregunta("¿Autor de Hamlet?", "Literatura", "Medio", "Shakespeare", "Cervantes", "Dante", "Molière", "Shakespeare"),
            Pregunta("¿Órgano que bombea sangre?", "Biología", "Medio", "Pulmón", "Cerebro", "Corazón", "Riñón", "Corazón"),
            Pregunta("¿Autor de La Odisea?", "Literatura", "Medio", "Homero", "Platón", "Sófocles", "Aristóteles", "Homero"),
            Pregunta("¿Metal líquido?", "Química", "Medio", "Plomo", "Mercurio", "Aluminio", "Zinc", "Mercurio"),
            Pregunta("¿Animal ovíparo?", "Biología", "Medio", "Perro", "Vaca", "Gallina", "Delfín", "Gallina"),
            Pregunta("¿Autor de 1984?", "Literatura", "Medio", "Orwell", "Huxley", "Bradbury", "Asimov", "Orwell"),
            Pregunta("¿Unidad de fuerza?", "Física", "Medio", "Watt", "Newton", "Joule", "Pascal", "Newton"),
            Pregunta("¿Órgano de la vista?", "Biología", "Medio", "Nariz", "Ojo", "Oído", "Lengua", "Ojo"),
            Pregunta("¿Principal gas atmósfera?", "Ciencia", "Medio", "Oxígeno", "Nitrógeno", "CO2", "Hidrógeno", "Nitrógeno"),
            Pregunta("¿Autor de El Principito?", "Literatura", "Medio", "Saint-Exupéry", "Verne", "Hugo", "Camus", "Saint-Exupéry"),
            Pregunta("¿País inventor de la cerveza Pilsner?", "Cerveza", "Medio", "Alemania", "República Checa", "Bélgica", "Irlanda", "República Checa"),
            Pregunta("¿Tipo de cerveza muy oscura y fuerte?", "Cerveza", "Medio", "Lager", "IPA", "Stout", "Pale Ale", "Stout"),
            Pregunta("¿Qué gas se utiliza para dar carbonatación a la cerveza?", "Cerveza", "Facil", "Oxígeno", "Nitrógeno", "Dióxido de carbono", "Helio", "Dióxido de carbono"),

            // ==================== DIFÍCIL (30) ====================
            Pregunta("¿Velocidad de la luz?", "Física", "Dificil", "150.000 km/s", "300.000 km/s", "1.000 km/s", "30.000 km/s", "300.000 km/s"),
            Pregunta("¿Número de huesos humanos?", "Anatomía", "Dificil", "200", "206", "210", "180", "206"),
            Pregunta("¿Constante de Planck?", "Física", "Dificil", "6.62×10⁻³⁴", "9.8", "3×10⁸", "1.6×10⁻¹⁹", "6.62×10⁻³⁴"),
            Pregunta("¿Lenguaje bajo nivel?", "Tecnología", "Dificil", "Python", "Java", "Assembly", "PHP", "Assembly"),
            Pregunta("¿Autor de La Divina Comedia?", "Literatura", "Dificil", "Petrarca", "Boccaccio", "Dante", "Virgilio", "Dante"),
            Pregunta("¿Metal más conductor?", "Física", "Dificil", "Cobre", "Plata", "Oro", "Aluminio", "Plata"),
            Pregunta("¿Teoría de Einstein?", "Física", "Dificil", "Evolución", "Relatividad", "Cuántica", "Gravedad", "Relatividad"),
            Pregunta("¿Número atómico del carbono?", "Química", "Dificil", "4", "6", "8", "12", "6"),
            Pregunta("¿Autor del Leviatán?", "Filosofía", "Dificil", "Locke", "Hobbes", "Rousseau", "Hume", "Hobbes"),
            Pregunta("¿Hueso del oído?", "Anatomía", "Dificil", "Estribo", "Fémur", "Radio", "Cúbito", "Estribo"),
            Pregunta("¿Autor de Crimen y Castigo?", "Literatura", "Dificil", "Tolstói", "Dostoyevski", "Pushkin", "Gogol", "Dostoyevski"),
            Pregunta("¿Gas noble?", "Química", "Dificil", "Nitrógeno", "Helio", "Oxígeno", "Hidrógeno", "Helio"),
            Pregunta("¿Año Revolución Francesa?", "Historia", "Dificil", "1789", "1776", "1804", "1750", "1789"),
            Pregunta("¿Fuerza que atrae masas?", "Física", "Dificil", "Magnética", "Gravitatoria", "Eléctrica", "Nuclear", "Gravitatoria"),
            Pregunta("¿Obra de Maquiavelo?", "Filosofía", "Dificil", "El Príncipe", "Utopía", "República", "Contrato Social", "El Príncipe"),
            Pregunta("¿Componente que da amargor a la cerveza?", "Cerveza", "Dificil", "Malta", "Lúpulo", "Levadura", "Agua", "Lúpulo"),
            Pregunta("¿Cerveza con fermentación mixta y sabor ácido?", "Cerveza", "Dificil", "Lager", "Sour", "Ale", "Stout", "Sour"),
            Pregunta("¿Proceso de calentar malta y agua para extraer azúcares?", "Cerveza", "Dificil", "Lúpulo", "Maceración", "Fermentación", "Filtrado", "Maceración"),
            Pregunta("¿Cómo se llama el proceso de calentar la malta para extraer azúcares?", "Cerveza", "Dificil", "Fermentación", "Maceración", "Destilación", "Filtración", "Maceración"),
        )
    }
}