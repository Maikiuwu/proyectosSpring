package com.example.prueba_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Prueba2Application {

	public static void main(String[] args) {
		SpringApplication.run(Prueba2Application.class, args);
	}

}
/* git init (inicializar git (si no no hace nada))
 * git add . = mala practica ^ git add = nombre archivo (buena practica)
 * git status (saber modificaciones archivos)
 * git status -s (muestra lo mismo que el status en log pero como si lo viera en visual)
 * git commit -m "texto" (texto = nombre del commit/comentario)
 * git pull (actualiza todos los datos de la nube)
 * git push origin main (push = subir todo al repositorio ^ origin = desde donde hace el cambio)
 * git log (mostrar historial de commits)
 * git log --oneline (mostrar historial de commits resumido)
 * git branch "nombre" (crear una rama del repositorio)
 */