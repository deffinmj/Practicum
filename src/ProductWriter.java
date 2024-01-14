import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductWriter {
    public static class Product {
        String id;
        String name;
        String description;
        double cost;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        ArrayList<Product> products = new ArrayList<Product>();

        while(true) {
            Product newProduct = new Product();
            newProduct.id = SafeInput.getRegExString(in, "Please Enter a product ID", "[0-9]+");
            newProduct.name = SafeInput.getRegExString(in, "Please Enter a product name", "[a-zA-Z]+");
            newProduct.description = SafeInput.getRegExString(in, "Please Enter a product description", "[a-zA-Z ’]+");
            newProduct.cost = SafeInput.getDouble(in, "Please Enter the product cost");
            products.add((newProduct));
            System.out.println("Finished collecting input for " + newProduct.name);
            if(!SafeInput.getYNConfirm(in, "Would you like to add another product?")) {
                break;
            }
        }

        File workingDir = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDir.getPath() + "ProductTestData.txt");

        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, StandardOpenOption.CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for(int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                String entry = product.id + "," + product.name + "," + product.description + "," + Double.toString(product.cost);
                writer.write(entry, 0, entry.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("Data file written!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
