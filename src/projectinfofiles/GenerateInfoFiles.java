package projectinfofiles;

import java.io.*;
import java.util.*;

/**
 *
 * @author Mateo
 */
public class GenerateInfoFiles {

    /**
     * @param args the command line arguments
     */
    
    //se crea un HashMap para guardar el documento del vendedor como clave y poder referenciarlo y/o utilizar facilmente esta información
    private HashMap<String, String> sellers = new HashMap<>();
    ////se crea un HashMap para guardar el id del producto como clave y poder referenciarlo y/o utilizar facilmente esta información
    private HashMap<String, Product> products = new HashMap<>();
    //Lista para almacenar las ventas
    private List<Sale> sales = new ArrayList<>();

    //Método que la información del archivo SellerInformation, es decir, de los vendedores y su número de documento
    public void ReadSellers(String filePath) throws IOException {
        String fileSellers = filePath + "SellerInformation.txt";
        BufferedReader reader = new BufferedReader(new FileReader(fileSellers));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");
            //guarda el documento del venderdor como clave y el nombre completo como un valor
            sellers.put(data[1], data[2] + " " + data[3]); // Se guarda el documento del vendedor como clave
        }
        System.out.println("Archivo de los vendedores leido correctamente");
    }

    // Lee la información de los productos y su precio
    public void ReadProducts(String filePath) throws IOException {
        String fileProducts = filePath + "ProductData.txt";
        BufferedReader reader = new BufferedReader(new FileReader(fileProducts));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");
            //guarda el ID de producto como clave y un objeto de tipo producto que tiene el ID, nombre y precio del producto como valores
            products.put(data[0], new Product(data[0], data[1], Double.parseDouble(data[2])));
        }
        System.out.println("Archivo de productos leído correctamente");

    }

    //lee los archivos de las ventas de cada vendedor 
    public void ReadSale(String filePath) throws IOException {
        File salesFiles = new File(filePath);

        File[] files = salesFiles.listFiles((dir, name) -> name.endsWith(".txt") && !name.equals("SellerInformation.txt") && !name.equals("ProductData.txt"));
        if (files != null) {
            //for each que va a iterar sobre cada archivo de los vendedores
            for (File file : files) {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                //Esta primera linea se lee para identificar al trabajador
                String firstLine = reader.readLine();
                String[] sellerData = firstLine.split(";");
                String iDNumberSeller = sellerData[1];

                    //Las demas lineas contienen la información restante que pertenece a los productos que vendió
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] infoSale = line.split(";");
                    String idProduct = infoSale[0];
                    int quantitySoldProduct = Integer.parseInt(infoSale[1]);

                    //Guardar la venta en la lista
                    sales.add(new Sale(iDNumberSeller, idProduct, quantitySoldProduct));
                }
                System.out.println("Archivos de ventas leidos exitosamente");
            }
        } else {
            System.out.println("No se encontraron archivos de ventas.");
        }

    }

    

    public static void main(String[] args) {
        GenerateInfoFiles generateInfoFiles = new GenerateInfoFiles();
        String rutaArchivos = "C:\\Users\\Mateo\\Documents\\NetBeansProjects\\ProjectInfoFiles\\src\\projectinfofiles\\";

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

class Product {

    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Sale {

    private String documentIdSeller;
    private String productID;
    private int quantitySold;

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
