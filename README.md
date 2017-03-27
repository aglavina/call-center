# Call Center - Threads Exercise

### prerequisites

 * Java 7 or superior is needed
 * Maven for packaging the jar 


### Documentation

 * A class diagram and a sequence diagram are included

### Extras

 * Cuando no hay empleado disponible, se podría: 
  	- Reintentar en el consumidor del servicio cada ciertos intervalos de tiempo hasta que se libere
  	- Encolar en el servidor y reintentar cada ciertos intervalos de tiempo hasta que se libere
 	- Encolar en el servidor y que al completarse la llamada se busque en esa cola las llamadas pendientes

* Cuando no hay threads disponibles se podría:  
	- Mismas soluciones que para cuando no hay empleado disponible
	- Agregar mas Threads 
	- Encolar en la cola del ExecutorService de Java
	- Agregar un servidor nuevo y dar una libreria cliente a los consumidores que balancee la carga
	- Agregar un servidor nuevo y tener un balanceador de carga que reciba las Calls y las distribuya
