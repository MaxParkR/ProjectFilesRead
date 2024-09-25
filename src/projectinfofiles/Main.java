/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectinfofiles;
        

import java.util.*;

/**
 *
 * @author Mateo
 */
public class Main {
    
    public static void main(String[] args) {
        //se crea un objeto de tipo GenerateInfoFiles para poder utilizar todo lo implementado en la otra clase
        GenerateInfoFiles generateInfoFiles = new GenerateInfoFiles();
        String rutaArchivos = ".\\src\\projectinfofiles\\";

        try {
            // Esta sección utiliza los métodos de la clase GenerateInfoFiles para leer todos los archivos e imprimir en esta clase Main si fueron leidos correctamente
            generateInfoFiles.ReadSellers(rutaArchivos);
            generateInfoFiles.ReadProducts(rutaArchivos);
            generateInfoFiles.ReadSale(rutaArchivos);

            // Llama a los métodos que imprimen lo recaudado por vendedor y la cantidad de productos vendidos
            sellerCash(generateInfoFiles);
            productQuantity(generateInfoFiles);

            //aquí se manejan las excepciones que pueden generarse más que todo al ejecutar los métodos ReadSellers, ReadProducts y ReadSale
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Este método nos imprime los vendedores, falta hacer que se impriman ordenados, se le pasa como parámetro una instancia de la otra clase generateInfoFiles
    public static void sellerCash(GenerateInfoFiles generateInfoFiles) {
        HashMap<String, Double> ventaTotalVendedor = new HashMap<>();

        // Este bucle for each toma la información de la lista sales y utiliza los métodos de tipo getter de la clase sale para obtener la información de los productos y cálcula la cantidad
        // luego crea un objeto de tipo product del cual se extrae el precio de cada producto y se multiplica por la cantidad de producto que se vendió. luego en el HashMap ventaTotalVendedor
        //guarda la información obtenida
        for (Sale sale : generateInfoFiles.sales) {
            String sellerId = sale.getDocumentIdSeller();
            String productId = sale.getProductID();
            int cantidadVendida = sale.getQuantitySold();
            Product producto = generateInfoFiles.products.get(productId);

            if (producto != null) {
                double totalVenta = producto.getPrice() * cantidadVendida;
                ventaTotalVendedor.put(sellerId,ventaTotalVendedor.getOrDefault(sellerId, 0.0) + totalVenta);
            }
        }

        // Crear la lista de vendedores con sus nombres y la cantidad de dinero que recaudó
        List<Map.Entry<String, Double>> listaRecaudacion = new ArrayList<>(ventaTotalVendedor.entrySet());


        // este for each recorre la lista e imprime el nombre del vendedor y separado por un punto y coma, nos muestra el total recaudado por cada uno
        for (Map.Entry<String, Double> entry : listaRecaudacion) {
            String sellerId = entry.getKey();
            double totalRecaudado = entry.getValue();
            String nombreVendedor = generateInfoFiles.sellers.get(sellerId);

            System.out.println(nombreVendedor + "; " + totalRecaudado);
        }
    }

    // Este método Imprime los productos vendidos
    public static void productQuantity(GenerateInfoFiles generateInfoFiles) {
        HashMap<String, Integer> cantidadPorProducto = new HashMap<>();

        // este for each Calcula la cantidad total vendida de cada producto
        for (Sale sale : generateInfoFiles.sales) {
            String productId = sale.getProductID();
            int cantidadVendida = sale.getQuantitySold();

            cantidadPorProducto.put(productId,
                cantidadPorProducto.getOrDefault(productId, 0) + cantidadVendida);
        }

        // Se crea una lista de productos con sus nombres y cantidades vendidas
        List<Map.Entry<String, Integer>> listaProductos = new ArrayList<>(cantidadPorProducto.entrySet());

        // Este for each Imprime el nombre del producto, el precio y la cantidad vendida
        for (Map.Entry<String, Integer> entry : listaProductos) {
            String productId = entry.getKey();
            int cantidadVendida = entry.getValue();
            Product producto = generateInfoFiles.products.get(productId);

            if (producto != null) {
                System.out.println(producto.getName() + "; " + producto.getPrice() + "; " + cantidadVendida);
            }
        }
    }
}

