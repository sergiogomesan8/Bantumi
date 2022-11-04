# Bantumi


## Opciones del menu superior:

Reiniciar partida: Reinicia la partida, desde 0.

Guardar partida: Guarda el estado de la partida actual, a través de la memoria interna.
Recogeremos y realizaremos la serielización de la partida en juegoBantumi para poder guardarla en esa memoria interna. 
> Se ha creado un gestor de memoria interna, para realizar este guardado de información llamado InternalMemoryManager. Solo se puede guardar una partida.

## Opciones del menu lateral:

Recuperar partida partida: Recarga el juego cuando pulsas el botón de recuperar partidas en el estado del último guardado realizado en el apartado anterior.
> A través del InternalMemoryManager realizaremos la carga o lectura de ese archivo para recuperar la partida.

Mejores resultados: Mostrará el listado de resultado que se ha guardado en la base de datos.
Mostrará el nombre del usuario, la puntuación del usuario y del rival, la fecha y la hora de comienzo de la partida.

Ajustes: Podrás cambiar el nombre del usuario, si no rellenas el nombre de usuario, éste no se verá reflejado en los resultados.
> Para este opción se ha utilizado una función deprecated llamada **startActivityForResult** pero es la que vi que hemos utilizado en las prácticas, 

Preferences: Podrá cambiar el número inicial de semillas y el turno inicial. 
Los cambios se verán reflejados en la siguiente partida, ya sea finalizando la actual o reiniciando.
> Para esta opción se ha utilizado **sharedPreference** **https://www.youtube.com/watch?v=9Ye3j1c7pS4&t=398s**

## Al finalizar las partidas:

Guardar puntuación: Guarda toda la información del resultado final de la partida: 
Nombre del usuario, la puntuación del usuario y del rival, la fecha y la hora de comienzo de la partida.
Para poder guardar el nombre, el usuario tendrá que ir a la opción ajustes en el menú y rellenar el nombre.
Una vez finalizada la partida, se guarda en la base de datos Room.
> Para ello se ha utilizado el método de las diapositivas y de las prácticas vistas en clase. Creando el DAO, Repository, Entity, Database, ViewModel, Adapater...
