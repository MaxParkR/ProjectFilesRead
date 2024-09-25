package projectinfofiles;

import java.io.*;
import java.util.*;

/**
 * Clase donde se procesa la información a partir de los archivos
 * @author Cristian Camilo Higuera Carrillo
 * 
 * @author Emildra Carolina López Ramírez
 * 
 * @author Mateo Fajardo Henao
 * 
 * @author Yony Arbey Rua Jimenez
 */
public class GenerateInfoFiles {

	// se crea un HashMap para guardar el documento del vendedor como clave y poder
	// referenciarlo y/o utilizar facilmente esta información
	public HashMap<String, String> sellers = new HashMap<>();
	// se crea un HashMap para guardar el id del producto como clave y poder
	// referenciarlo y/o utilizar facilmente esta información
	public HashMap<String, Product> products = new HashMap<>();
	// Lista para almacenar las ventas
	public List<Sale> sales = new ArrayList<>();

	/**
	 * Método que lee la información del archivo SellerInformation, es decir, de los
	 * vendedores y su número de documento
	 * 
	 * @param filePath ruta general de los archivos
	 * @throws IOException Signals that an I/O exception of some sort has occurred. Thisclass is the general class of exceptions produced by failed orinterrupted I/O operations.
	 */
	public void ReadSellers(String filePath) throws IOException {
		String fileSellers = filePath + "SellerInformation.txt";
		BufferedReader reader = new BufferedReader(new FileReader(fileSellers));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] data = line.split(";");
			// guarda el documento del venderdor como clave y el nombre completo como un
			// valor
			sellers.put(data[1], data[2] + " " + data[3]); // Se guarda el documento del vendedor como clave
		}
		System.out.println("Archivo de los vendedores leido correctamente");
	}

	/**
	 * Lee la información de los productos y su precio
	 * 
	 * @param filePath ruta general de los archivos
	 * @throws IOException Signals that an I/O exception of some sort has occurred. Thisclass is the general class of exceptions produced by failed orinterrupted I/O operations.
	 */
	public void ReadProducts(String filePath) throws IOException {
		String fileProducts = filePath + "ProductData.txt";
		BufferedReader reader = new BufferedReader(new FileReader(fileProducts));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] data = line.split(";");
			// guarda el ID de producto como clave y un objeto de tipo producto que tiene el
			// ID, nombre y precio del producto como valores
			products.put(data[0], new Product(data[0], data[1], Double.parseDouble(data[2])));
		}
		System.out.println("Archivo de productos leído correctamente");

	}

	/**
	 * Lee los archivos de las ventas de cada vendedor
	 * @param filePath ruta general de los archivos
	 * @throws IOException Signals that an I/O exception of some sort has occurred. Thisclass is the general class of exceptions produced by failed orinterrupted I/O operations.
	 */
	public void ReadSale(String filePath) throws IOException {
		File salesFiles = new File(filePath);

		File[] files = salesFiles.listFiles((dir, name) -> name.endsWith(".txt")
				&& !name.equals("SellerInformation.txt") && !name.equals("ProductData.txt"));
		if (files != null) {
			// for each que va a iterar sobre cada archivo de los vendedores
			for (File file : files) {
				BufferedReader reader = new BufferedReader(new FileReader(file));

				// Esta primera linea se lee para identificar al trabajador
				String firstLine = reader.readLine();
				String[] sellerData = firstLine.split(";");
				String iDNumberSeller = sellerData[1];

				// Las demas lineas contienen la información restante que pertenece a los
				// productos que vendió
				String line;
				while ((line = reader.readLine()) != null) {
					String[] infoSale = line.split(";");
					String idProduct = infoSale[0];
					int quantitySoldProduct = Integer.parseInt(infoSale[1]);

					// Guardar la venta en la lista
					sales.add(new Sale(iDNumberSeller, idProduct, quantitySoldProduct));
				}
				System.out.println("Archivo del vendedor:  "+ sellers.get(iDNumberSeller) + " leído correctamente.");
			}
		} else {
			System.out.println("No se encontraron archivos de ventas.");
		}

	}

	
	/**
	 * método main de la clase principal
	 * @param args default
	 */
	public static void main(String[] args) {
		
		GenerateInfoFiles generateInfoFiles = new GenerateInfoFiles();
		String rutaArchivos = ".\\src\\projectinfofiles\\";

		try {
			generateInfoFiles.ReadSellers(rutaArchivos);
			generateInfoFiles.ReadProducts(rutaArchivos);
			generateInfoFiles.ReadSale(rutaArchivos);

			System.out.println("\nEjecución finalizada correctamente.");

		} catch (IOException e) {
			System.err.println("Error al leer los archivos: " + e.getMessage());
		}
	}

}

/**
 * Clase producto con sus atributos y metodos 
 */
class Product {

	private String id;
	private String name;
	private double price;

	/**
	 * @param id
	 * @param name
	 * @param price
	 */
	public Product(String id, String name, double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/** 
	 * @return price
	 */
	public double getPrice() {
		return price;
	}
}

/**
 * Clase venta con sus atributos y metodos
 */
class Sale {

	private String documentIdSeller;
	private String productID;
	private int quantitySold;

	/**
	 * @param documentIdSeller id del vendedor
	 * @param productID id del producto
	 * @param quantitySold cantidad vendida
	 */
	public Sale(String documentIdSeller, String productID, int quantitySold) {
		this.documentIdSeller = documentIdSeller;
		this.productID = productID;
		this.quantitySold = quantitySold;
	}

	public String getDocumentIdSeller() {
		return documentIdSeller;
	}

	public String getProductID() {
		return productID;
	}

	public int getQuantitySold() {
		return quantitySold;
	}
}
