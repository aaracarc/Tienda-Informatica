package org.iesvegademijas.stream.test;

import org.iesvegademijas.hibernate.Fabricante;
import org.iesvegademijas.hibernate.FabricanteHome;
import org.iesvegademijas.hibernate.Producto;
import org.iesvegademijas.hibernate.ProductoHome;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class TiendaTest {
	
	@Test
	void testSkeletonFrabricante() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
		
			
			//TODO STREAMS
			
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	

	@Test
	void testSkeletonProducto() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
		
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	@Test
	void testAllFabricante() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
			assertEquals(9,listFab.size());
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	@Test
	void testAllProducto() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
			assertEquals(11,listProd.size());
		
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		

	
	}
	
	/**
	 * 1. Lista los nombres y los precios de todos los productos de la tabla producto
	 */
	@Test
	void test1() {

		// Instancia de ProductoHome, que se utiliza para acceder a la base de datos de productos
		ProductoHome prodHome = new ProductoHome();
		
		try {
			// Comienza una transacción en la base de datos
			prodHome.beginTransaction();

			// Se obtiene una lista de todos los productos de la base de datos
			List<Producto> listProd = prodHome.findAll();

			// Se utiliza Streams para procesar cada producto de la lista
			listProd.stream()
					.forEach(producto -> System.out.println("Nombre: " + producto.getNombre() + ", Precio: " + producto.getPrecio()));

			// Se confirma la transacción en la base de datos
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			// Si hay un error se realiza un rollback de la transacción y se lanza una excepción
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}

	}
	
	
	/**
	 * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares .
	 */
	@Test
	void test2() {
		
		ProductoHome prodHome = new ProductoHome();

		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			// Se utiliza Streams para crear una nueva lista con los precios en Dollar
			List<Producto> listProductosEnDolar = listProd.stream()
					.map(producto -> {
						Producto productoEnDolares = new Producto();
						productoEnDolares.setCodigo(producto.getCodigo());
						productoEnDolares.setNombre(producto.getNombre());
						productoEnDolares.setPrecio(producto.getPrecio() * 1.2);
						productoEnDolares.setFabricante(producto.getFabricante());
						return productoEnDolares;
					})
					.collect(Collectors.toList());

			listProductosEnDolar.forEach(producto -> System.out.println("Nombre: " + producto.getNombre() + ", Precio en Dollar: " + producto.getPrecio()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
	 */
	@Test
	void test3() {
		
		
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.forEach(producto -> {
						String nombreEnMayusculas = producto.getNombre().toUpperCase();
						System.out.println("Nombre: " + nombreEnMayusculas + ", Precio: " + producto.getPrecio());
					});

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
	 */
	@Test
	void test4() {
	
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();
			List<Fabricante> listFab = fabHome.findAll();

			listFab.stream()
					.forEach(fabricante -> {
						String nombreFabricante = fabricante.getNombre();
						String dosPrimerosCaracteresEnMayusculas = nombreFabricante.substring(0, 2).toUpperCase();
						System.out.println("Nombre del fabricante: " + nombreFabricante + ", Dos primeros caracteres en mayúsculas: " + dosPrimerosCaracteresEnMayusculas);
					});

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 5. Lista el código de los fabricantes que tienen productos.
	 */
	@Test
	void test5() {

		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();
			List<Fabricante> listFab = fabHome.findAll();

			listFab.stream()
					.filter(fabricante -> fabricante.getProductos().size() > 0)
					.map(Fabricante::getCodigo)
					.forEach(codigoFabricante -> System.out.println("Código del fabricante con productos: " + codigoFabricante));

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
	 */
	@Test
	void test6() {
	
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();
			List<Fabricante> listFab = fabHome.findAll();

			listFab.stream()
					.map(Fabricante::getNombre) // Obtiene los nombres de los fabricantes
					.sorted(Comparator.reverseOrder()) // Ordena los nombres en orden descendente
					.forEach(nombreFabricante -> System.out.println("Nombre del fabricante (descendente): " + nombreFabricante));

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
	 */
	@Test
	void test7() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.sorted(Comparator.comparing(Producto::getNombre) // Ordena por nombre de forma ascendente
							.thenComparing(Comparator.comparing(Producto::getPrecio).reversed())) // Luego por precio de forma descendente
					.forEach(producto -> System.out.println("Nombre del producto: " + producto.getNombre() + ", Precio: " + producto.getPrecio()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 8. Devuelve una lista con los 5 primeros fabricantes.
	 */
	@Test
	void test8() {
	
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();
			List<Fabricante> listFab = fabHome.findAll();

			List<Fabricante> primerosCincoFabricantes = listFab.stream()
					.limit(5)
					.collect(Collectors.toList());

			primerosCincoFabricantes.forEach(fabricante -> System.out.println("Nombre del fabricante: " + fabricante.getNombre()));

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
	 */
	@Test
	void test9() {
	
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();
			List<Fabricante> listFab = fabHome.findAll();

			List<Fabricante> fabricantesAPartirDelCuarto = listFab.stream()
					.skip(3) // Salta los primeros tres fabricantes
					.limit(2)
					.collect(Collectors.toList());

			fabricantesAPartirDelCuarto.forEach(fabricante -> System.out.println("Nombre del fabricante: " + fabricante.getNombre()));

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 10. Lista el nombre y el precio del producto más barato
	 */
	@Test
	void test10() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			Optional<Producto> productoMasBarato = listProd.stream()
					.min(Comparator.comparing(Producto::getPrecio));

			// Comprueba si hay un producto más barato e imprime su nombre y precio
			if (productoMasBarato.isPresent()) {
				Producto producto = productoMasBarato.get();
				System.out.println("Nombre del producto más barato: " + producto.getNombre() + ", Precio: " + producto.getPrecio());
			} else {
				System.out.println("No se encontraron productos.");
			}

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 11. Lista el nombre y el precio del producto más caro
	 */
	@Test
	void test11() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			Optional<Producto> productoMasCaro = listProd.stream()
					.max(Comparator.comparing(Producto::getPrecio));

			if (productoMasCaro.isPresent()) {
				Producto producto = productoMasCaro.get();
				System.out.println("Nombre del producto más caro: " + producto.getNombre() + ", Precio: " + producto.getPrecio());
			} else {
				System.out.println("No se encontraron productos.");
			}

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
	 * 
	 */
	@Test
	void test12() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.filter(producto -> producto.getFabricante().getCodigo() == 2)
					.forEach(producto -> System.out.println("Nombre del producto del fabricante 2: " + producto.getNombre()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
	@Test
	void test13() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.filter(producto -> producto.getPrecio() <= 120)
					.forEach(producto -> System.out.println("Nombre del producto (precio <= 120€): " + producto.getNombre()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 14. Lista los productos que tienen un precio mayor o igual a 400€.
	 */
	@Test
	void test14() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.filter(producto -> producto.getPrecio() >= 400)
					.forEach(producto -> System.out.println("Nombre del producto (precio >= 400€): " + producto.getNombre()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 15. Lista todos los productos que tengan un precio entre 80€ y 300€. 
	 */
	@Test
	void test15() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.filter(producto -> producto.getPrecio() >= 80 && producto.getPrecio() <= 300)
					.forEach(producto -> System.out.println("Nombre del producto (precio entre 80€ y 300€): " + producto.getNombre()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
	 */
	@Test
	void test16() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.filter(producto -> producto.getPrecio() > 200 && producto.getFabricante().getCodigo() == 6)
					.forEach(producto -> System.out.println("Nombre del producto (precio > 200€, código de fabricante 6): " + producto.getNombre()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
	 */
	@Test
	void test17() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			Set<Integer> codigosFabricantesPermitidos = new HashSet<>(Arrays.asList(1, 3, 5));

			listProd.stream()
					.filter(producto -> codigosFabricantesPermitidos.contains(producto.getFabricante().getCodigo()))
					.forEach(producto -> System.out.println("Nombre del producto (códigos de fabricantes 1, 3 o 5): " + producto.getNombre()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 18. Lista el nombre y el precio de los productos en céntimos.
	 */
	@Test
	void test18() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.map(producto -> {
						Producto productoEnCéntimos = new Producto();
						productoEnCéntimos.setNombre(producto.getNombre());
						productoEnCéntimos.setPrecio(producto.getPrecio() * 100); // Conversión a céntimos
						return productoEnCéntimos;
					})
					.forEach(producto -> System.out.println("Nombre del producto: " + producto.getNombre() + ", Precio en céntimos: " + producto.getPrecio()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
	 */
	@Test
	void test19() {

		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();
			List<Fabricante> listFab = fabHome.findAll();

			listFab.stream()
					.filter(fabricante -> fabricante.getNombre().startsWith("S"))
					.forEach(fabricante -> System.out.println("Nombre del fabricante (empieza con 'S'): " + fabricante.getNombre()));

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
	 */
	@Test
	void test20() {
	
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.filter(producto -> producto.getNombre().contains("Portátil"))
					.forEach(producto -> System.out.println("Nombre del producto (contiene 'Portátil'): " + producto.getNombre()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
	 */
	@Test
	void test21() {
	
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.filter(producto -> producto.getNombre().contains("Monitor") && producto.getPrecio() < 215)
					.forEach(producto -> System.out.println("Nombre del producto (contiene 'Monitor' y precio < 215€): " + producto.getNombre()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
	 */
	void test22() {

		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.filter(producto -> producto.getPrecio() >= 180)
					.sorted(Comparator
							.comparing(Producto::getPrecio, Comparator.reverseOrder())
							.thenComparing(Producto::getNombre))
					.forEach(producto -> System.out.println("Nombre del producto: " + producto.getNombre() + ", Precio: " + producto.getPrecio()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. 
	 * Ordene el resultado por el nombre del fabricante, por orden alfabético.
	 */
	@Test
	void test23() {
		
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.map(producto -> producto.getNombre() + " - Precio: " + producto.getPrecio() + " - Fabricante: " + producto.getFabricante().getNombre())
					.sorted(Comparator.comparing(nombrePrecioFabricante -> {
						String[] parts = nombrePrecioFabricante.split(" - Fabricante: ");
						return parts[1];
					}))
					.forEach(resultado -> System.out.println(resultado));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
	 */
	@Test
	void test24() {
		
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			Optional<Producto> productoMasCaro = listProd.stream()
					.max(Comparator.comparing(Producto::getPrecio));

			productoMasCaro.ifPresent(producto -> System.out.println("Nombre del producto más caro: " + producto.getNombre()
					+ " - Precio: " + producto.getPrecio()
					+ " - Fabricante: " + producto.getFabricante().getNombre()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
	 */
	@Test
	void test25() {
		
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			listProd.stream()
					.filter(producto -> producto.getFabricante().getNombre().equals("Crucial") && producto.getPrecio() > 200)
					.forEach(producto -> System.out.println("Nombre del producto: " + producto.getNombre() + " - Precio: " + producto.getPrecio()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
	 */
	@Test
	void test26() {
		
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			List<String> fabricantesPermitidos = Arrays.asList("Asus", "Hewlett-Packard", "Seagate");

			listProd.stream()
					.filter(producto -> fabricantesPermitidos.contains(producto.getFabricante().getNombre()))
					.forEach(producto -> System.out.println("Nombre del producto: " + producto.getNombre()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
	 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
	 * La salida debe quedar tabulada como sigue:

Producto                Precio             Fabricante
-----------------------------------------------------
GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
Portátil Yoga 520      |452.79            |Lenovo
Portátil Ideapd 320    |359.64000000000004|Lenovo
Monitor 27 LED Full HD |199.25190000000003|Asus

	 */		
	@Test
	void test27() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			//TODO STREAMS
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. 
	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados. 
	 * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
	 * La salida debe queda como sigue:
Fabricante: Asus

            	Productos:
            	Monitor 27 LED Full HD
            	Monitor 24 LED Full HD

Fabricante: Lenovo

            	Productos:
            	Portátil Ideapd 320
            	Portátil Yoga 520

Fabricante: Hewlett-Packard

            	Productos:
            	Impresora HP Deskjet 3720
            	Impresora HP Laserjet Pro M26nw

Fabricante: Samsung

            	Productos:
            	Disco SSD 1 TB

Fabricante: Seagate

            	Productos:
            	Disco duro SATA3 1TB

Fabricante: Crucial

            	Productos:
            	GeForce GTX 1080 Xtreme
            	Memoria RAM DDR4 8GB

Fabricante: Gigabyte

            	Productos:
            	GeForce GTX 1050Ti

Fabricante: Huawei

            	Productos:


Fabricante: Xiaomi

            	Productos:

	 */
	@Test
	void test28() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			//TODO STREAMS
								
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
	 */
	@Test
	void test29() {
	
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();
			List<Fabricante> listFab = fabHome.findAll();

			List<Producto> listProd = new ProductoHome().findAll();

			List<Fabricante> fabricantesSinProductos = listFab.stream()
					.filter(fabricante -> listProd.stream()
							.noneMatch(producto -> producto.getFabricante().equals(fabricante)))
					.collect(Collectors.toList());

			fabricantesSinProductos.forEach(fabricante -> System.out.println("Fabricante sin productos asociados: " + fabricante.getNombre()));

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
	 */
	@Test
	void test30() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			long totalProductos = listProd.stream().count();

			System.out.println("Número total de productos: " + totalProductos);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}

	
	/**
	 * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
	 */
	@Test
	void test31() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			long fabricantesConProductos = listProd.stream()
					.map(Producto::getFabricante)
					.distinct()
					.count();

			System.out.println("Número de fabricantes con productos: " + fabricantesConProductos);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 32. Calcula la media del precio de todos los productos
	 */
	@Test
	void test32() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			double mediaPrecios = listProd.stream()
					.mapToDouble(Producto::getPrecio)
					.average()
					.orElse(0.0);

			System.out.println("Media de precios de todos los productos: " + mediaPrecios);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
	 */
	@Test
	void test33() {
	
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			double precioMasBarato = listProd.isEmpty() ? 0.0 : listProd.get(0).getPrecio();

			for (Producto producto : listProd) {
				if (producto.getPrecio() < precioMasBarato) {
					precioMasBarato = producto.getPrecio();
				}
			}

			System.out.println("Precio más barato de todos los productos: " + precioMasBarato);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 34. Calcula la suma de los precios de todos los productos.
	 */
	@Test
	void test34() {
		
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			double sumaPrecios = listProd.stream()
					.mapToDouble(Producto::getPrecio)
					.sum();

			System.out.println("Suma de los precios de todos los productos: " + sumaPrecios);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 35. Calcula el número de productos que tiene el fabricante Asus.
	 */
	@Test
	void test35() {
		
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			long productosAsus = listProd.stream()
					.filter(producto -> producto.getFabricante().getNombre().equals("Asus"))
					.count();

			System.out.println("Número de productos del fabricante Asus: " + productosAsus);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 36. Calcula la media del precio de todos los productos del fabricante Asus.
	 */
	@Test
	void test36() {
		
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			OptionalDouble mediaPreciosAsus = listProd.stream()
					.filter(producto -> producto.getFabricante().getNombre().equals("Asus"))
					.mapToDouble(Producto::getPrecio)
					.average();

			if (mediaPreciosAsus.isPresent()) {
				System.out.println("Media de precios de productos del fabricante Asus: " + mediaPreciosAsus.getAsDouble());
			} else {
				System.out.println("No hay productos del fabricante Asus en la base de datos.");
			}

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. 
	 *  Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 */
	@Test
	void test37() {
		
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
			List<Producto> listProd = prodHome.findAll();

			Double[] resultados = listProd.stream()
					.filter(producto -> producto.getFabricante().getNombre().equals("Crucial"))
					.map(producto -> producto.getPrecio())
					.collect(
							() -> new Double[] { Double.MAX_VALUE, Double.MIN_VALUE, 0.0, 0.0 },
							(acc, precio) -> {
								acc[0] = Math.min(acc[0], precio); // Precio mínimo
								acc[1] = Math.max(acc[1], precio); // Precio máximo
								acc[2] += precio; // Suma de precios
								acc[3] += 1.0; // Número total de productos
							},
							(acc1, acc2) -> {
								acc1[0] = Math.min(acc1[0], acc2[0]);
								acc1[1] = Math.max(acc1[1], acc2[1]);
								acc1[2] += acc2[2];
								acc1[3] += acc2[3];
							}
					);

			Double mediaPrecios = resultados[2] / resultados[3];

			System.out.println("Precio mínimo: " + resultados[0]);
			System.out.println("Precio máximo: " + resultados[1]);
			System.out.println("Precio medio: " + mediaPrecios);
			System.out.println("Número total de productos: " + resultados[3]);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 38. Muestra el número total de productos que tiene cada uno de los fabricantes. 
	 * El listado también debe incluir los fabricantes que no tienen ningún producto. 
	 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. 
	 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
	 * La salida debe queda como sigue:
	 
     Fabricante     #Productos
-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
           Asus              2
         Lenovo              2
Hewlett-Packard              2
        Samsung              1
        Seagate              1
        Crucial              2
       Gigabyte              1
         Huawei              0
         Xiaomi              0

	 */
	@Test
	void test38() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. 
	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 * Deben aparecer los fabricantes que no tienen productos.
	 */
	@Test
	void test39() {
	
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();
			List<Fabricante> listFab = fabHome.findAll();

			Map<Fabricante, Double[]> resultadosPorFabricante = listFab.stream()
					.collect(
							Collectors.toMap(
									fabricante -> fabricante,
									fabricante -> new Double[] { Double.MAX_VALUE, Double.MIN_VALUE, 0.0, 0.0 },
									(acc1, acc2) -> {
										acc1[0] = Math.min(acc1[0], acc2[0]);
										acc1[1] = Math.max(acc1[1], acc2[1]);
										acc1[2] += acc2[2];
										acc1[3] += acc2[3];
										return acc1;
									}
							)
					);

			// Filtrar y mostrar los resultados
			resultadosPorFabricante.forEach((fabricante, resultados) -> {
				Double precioMinimo = resultados[0];
				Double precioMaximo = resultados[1];
				Double sumaPrecios = resultados[2];
				Double numeroProductos = resultados[3];

				// Calcula la media de precios por fabricante
				Double mediaPrecios = sumaPrecios / numeroProductos;

				System.out.println("Fabricante: " + fabricante.getNombre());
				System.out.println("Precio mínimo: " + precioMinimo);
				System.out.println("Precio máximo: " + precioMaximo);
				System.out.println("Precio medio: " + mediaPrecios);
				System.out.println();
			});

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. 
	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
	 */
	@Test
	void test40() {
	
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();
			List<Fabricante> listFab = fabHome.findAll();

			Map<Fabricante, Double[]> resultadosPorFabricante = listFab.stream()
					.collect(
							Collectors.toMap(
									fabricante -> fabricante,
									fabricante -> new Double[] { Double.MAX_VALUE, Double.MIN_VALUE, 0.0, 0.0 },
									(acc1, acc2) -> {
										acc1[0] = Math.min(acc1[0], acc2[0]);
										acc1[1] = Math.max(acc1[1], acc2[1]);
										acc1[2] += acc2[2];
										acc1[3] += acc2[3];
										return acc1;
									}
							)
					);

			resultadosPorFabricante.forEach((fabricante, resultados) -> {
				Double precioMinimo = resultados[0];
				Double precioMaximo = resultados[1];
				Double sumaPrecios = resultados[2];
				Double numeroProductos = resultados[3];

				Double mediaPrecios = sumaPrecios / numeroProductos;

				if (mediaPrecios > 200.0) {
					System.out.println("Código de Fabricante: " + fabricante.getCodigo());
					System.out.println("Precio mínimo: " + precioMinimo);
					System.out.println("Precio máximo: " + precioMaximo);
					System.out.println("Precio medio: " + mediaPrecios);
					System.out.println();
				}
			});

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
	 */
	@Test
	void test41() {
		
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();

			List<Fabricante> listFab = fabHome.findAll();

			List<Fabricante> fabricantesConDosOMasProductos = listFab.stream()
					.filter(fabricante -> fabricante.getProductos().size() >= 2)
					.collect(Collectors.toList());

			List<String> nombresFabricantes = fabricantesConDosOMasProductos.stream()
					.map(Fabricante::getNombre)
					.collect(Collectors.toList());

			nombresFabricantes.forEach(System.out::println);

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €. 
	 * Ordenado de mayor a menor número de productos.
	 */
	@Test
	void test42() {
		
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();

			List<Fabricante> listFab = fabHome.findAll();

			List<Fabricante> fabricantesFiltrados = listFab.stream()
					.filter(fabricante -> fabricante.getProductos().stream()
							.anyMatch(producto -> producto.getPrecio() >= 220.0))
					.collect(Collectors.toList());

			// Ordena los fabricantes por el número de productos en orden descendente
			fabricantesFiltrados.sort(Comparator.comparingInt(fabricante -> fabricante.getProductos().size()));
			Collections.reverse(fabricantesFiltrados);

			// Crea un mapa con los nombres de los fabricantes y el número de productos
			Map<String, Integer> fabricantesConNumProductos = new LinkedHashMap<>();
			fabricantesFiltrados.forEach(fabricante ->
					fabricantesConNumProductos.put(fabricante.getNombre(), fabricante.getProductos().size()));

			// Imprime el listado ordenado
			fabricantesConNumProductos.forEach((nombre, numProductos) ->
					System.out.println(nombre + ": " + numProductos));

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 */
	@Test
	void test43() {
		
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();

			List<Fabricante> listFab = fabHome.findAll();

			List<Fabricante> fabricantesFiltrados = listFab.stream()
					.filter(fabricante -> fabricante.getProductos().stream()
							.mapToDouble(Producto::getPrecio)
							.sum() > 1000.0)
					.collect(Collectors.toList());

			// Imprime el listado de fabricantes que cumplen el criterio
			fabricantesFiltrados.forEach(fabricante ->
					System.out.println(fabricante.getNombre()));

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 * Ordenado de menor a mayor por cuantía de precio de los productos.
	 */
	@Test
	void test44() {
		
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();

			List<Fabricante> listFab = fabHome.findAll();

			// Luego, ordénalos de menor a mayor por cuantía de precio de los productos
			List<Fabricante> fabricantesFiltrados = listFab.stream()
					.filter(fabricante -> fabricante.getProductos().stream()
							.mapToDouble(Producto::getPrecio)
							.sum() > 1000.0)
					.sorted(Comparator.comparingDouble(fabricante ->
							fabricante.getProductos().stream()
									.mapToDouble(Producto::getPrecio)
									.sum()))
					.collect(Collectors.toList());

			// Imprime el listado de fabricantes ordenados
			fabricantesFiltrados.forEach(fabricante ->
					System.out.println(fabricante.getNombre()));

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. 
	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. 
	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
	 */
	@Test
	void test45() {
		
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();

			List<Fabricante> listFab = fabHome.findAll();

			List<Object[]> resultado = listFab.stream()
					.map(fabricante -> {
						Optional<Producto> productoMasCaro = fabricante.getProductos().stream()
								.max(Comparator.comparing(Producto::getPrecio));
						if (productoMasCaro.isPresent()) {
							return new Object[]{
									productoMasCaro.get().getNombre(),
									productoMasCaro.get().getPrecio(),
									fabricante.getNombre()
							};
						} else {
							return null;
						}
					})
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(arr -> (String) arr[2])) // Ordena por el nombre del fabricante
					.collect(Collectors.toList());

			// Imprime el listado ordenado
			resultado.forEach(arr -> {
				System.out.println("Nombre del producto: " + arr[0]);
				System.out.println("Precio: " + arr[1]);
				System.out.println("Nombre del fabricante: " + arr[2]);
				System.out.println();
			});

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
			
	}
	
	/**
	 * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
	 */
	@Test
	void test46() {
		
		FabricanteHome fabHome = new FabricanteHome();

		try {
			fabHome.beginTransaction();

			List<Fabricante> listFab = fabHome.findAll();

			List<Producto> resultado = listFab.stream()
					.flatMap(fabricante -> {
						// Calcula la media de precios de los productos del fabricante
						double mediaPrecios = fabricante.getProductos().stream()
								.mapToDouble(Producto::getPrecio)
								.average()
								.orElse(0.0);

						// Filtra los productos con precio mayor o igual a la media
						return fabricante.getProductos().stream()
								.filter(producto -> producto.getPrecio() >= mediaPrecios)
								.sorted(Comparator.comparing(Producto::getPrecio).reversed());
					})
					.collect(Collectors.toList());

			// Ordena el resultado por fabricante en orden alfabético ascendente
			resultado.sort(Comparator.comparing(p -> p.getFabricante().getNombre()));

			// Imprime el listado de productos
			resultado.forEach(producto -> {
				System.out.println("Nombre del producto: " + producto.getNombre());
				System.out.println("Precio: " + producto.getPrecio());
				System.out.println("Nombre del fabricante: " + producto.getFabricante().getNombre());
				System.out.println();
			});

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
			
	}
	
}

