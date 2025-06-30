package controller;

import model.Product;
import service.ProductService;
import view.ProductForm;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.LocalDate;

public class ProductController {

    private ProductForm view;
    private ProductService productService;
    private SimpleDateFormat viewSdf = new SimpleDateFormat("dd-MM-yyyy");

    public ProductController(ProductForm view) {
        this.view = view;
        this.productService = new ProductService();
        this.view.setController(this); // Esto ya es correcto
        loadDataIntoTable();
    }

    public void registerProductInDatabase() {
        String name = view.getTxtNombreProducto().getText().trim();
        String type = (String) view.getCmbTipoProducto().getSelectedItem();
        String brand = view.getTxtMarca().getText().trim();
        String priceStr = view.getTxtPrecio().getText().trim();
        boolean hasStock = view.getChkSiStock().isSelected();
        int stock = (int) view.getSpnStock().getValue();
        String registrationDateStr = view.getTxtFechaRegistro();

        // --- Validaciones ---
        if (name.isEmpty() || brand.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (stock < 0) {
            JOptionPane.showMessageDialog(view, "El stock no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp registrationDate = null;
        if (registrationDateStr != null && !registrationDateStr.isEmpty()) {
            try {
                Date parsedDate = viewSdf.parse(registrationDateStr);
                registrationDate = new Timestamp(parsedDate.getTime());
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(view, "El formato de la fecha de registro no es válido (DD-MM-YYYY).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            LocalDate now = LocalDate.now();
            registrationDate = Timestamp.valueOf(now.atStartOfDay());
        }

        Product product = new Product(0, name, type, brand, price, hasStock, stock, registrationDate);
        try {
            productService.saveProduct(product);
            JOptionPane.showMessageDialog(view, "Producto registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            view.clearFields(); // CAMBIO: De clearForm() a clearFields()
            loadDataIntoTable();
        } catch (SQLException e) {
            System.err.println("Error al registrar el producto: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error al registrar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateProductInDatabase() {
        int selectedRow = view.getTablaProductos().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Seleccione un producto para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int productId = (int) view.getTablaProductos().getValueAt(selectedRow, 0);
        String name = view.getTxtNombreProducto().getText().trim();
        String type = (String) view.getCmbTipoProducto().getSelectedItem();
        String brand = view.getTxtMarca().getText().trim();
        String priceStr = view.getTxtPrecio().getText().trim();
        boolean hasStock = view.getChkSiStock().isSelected();
        int stock = (int) view.getSpnStock().getValue();
        String registrationDateStr = view.getTxtFechaRegistro();

        // --- Validaciones (similar a registerProductInDatabase) ---
        if (name.isEmpty() || brand.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (stock < 0) {
            JOptionPane.showMessageDialog(view, "El stock no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp registrationDate = null;
        if (registrationDateStr != null && !registrationDateStr.isEmpty()) {
            try {
                Date parsedDate = viewSdf.parse(registrationDateStr);
                registrationDate = new Timestamp(parsedDate.getTime());
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(view, "El formato de la fecha de registro no es válido (DD-MM-YYYY).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            try {
                String existingDateTableStr = (String) view.getTablaProductos().getValueAt(selectedRow, 7);
                if (existingDateTableStr != null && !existingDateTableStr.isEmpty()) {
                    Date parsedDate = viewSdf.parse(existingDateTableStr);
                    registrationDate = new Timestamp(parsedDate.getTime());
                }
            } catch (ParseException e) {
                System.err.println("Error al obtener o parsear la fecha existente de la tabla: " + e.getMessage());
                JOptionPane.showMessageDialog(view, "Error al obtener o parsear la fecha existente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                JOptionPane.showMessageDialog(view, "No se pudo recuperar la fecha existente del producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }

        Product product = new Product(productId, name, type, brand, price, hasStock, stock, registrationDate);
        try {
            productService.updateProduct(product);
            JOptionPane.showMessageDialog(view, "Producto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            view.clearFields(); // CAMBIO: De clearForm() a clearFields()
            loadDataIntoTable();
        } catch (SQLException e) {
            System.err.println("Error al actualizar el producto: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error al actualizar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // CAMBIO: Renombrado deleteProduct() a deleteProductFromDatabase() para consistencia
    public void deleteProductFromDatabase() {
        int selectedRow = view.getTablaProductos().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Seleccione un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int productId = (int) view.getTablaProductos().getValueAt(selectedRow, 0);

        try {
            productService.deleteProduct(productId);
            JOptionPane.showMessageDialog(view, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            view.clearFields(); // CAMBIO: De clearForm() a clearFields()
            loadDataIntoTable();
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error al eliminar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadDataIntoTable() {
        DefaultTableModel tableModel = view.getTableModel();
        if (tableModel != null) {
            tableModel.setRowCount(0); // Limpiar datos existentes

            try {
                List<Product> productList = productService.getAllProducts();
                if (productList != null) {
                    for (Product product : productList) {
                        String registrationDateStr = "";
                        if (product.getRegistrationDate() != null) {
                            try {
                                Date dateFromDb = new Date(product.getRegistrationDate().getTime());
                                registrationDateStr = viewSdf.format(dateFromDb);
                            } catch (Exception e) {
                                System.err.println("Error al formatear la fecha para la tabla: " + e.getMessage());
                                registrationDateStr = product.getRegistrationDate().toString().split(" ")[0];
                            }
                        }
                        tableModel.addRow(new Object[]{
                                product.getId(),
                                product.getName(),
                                product.getType(),
                                product.getBrand(),
                                product.getPrice(),
                                product.isHasStock() ? "Sí" : "No",
                                product.getStock(),
                                registrationDateStr
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "No se pudieron obtener los productos de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                System.err.println("Error al cargar los productos: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view, "Error: El modelo de la tabla es nulo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // NUEVO MÉTODO: Para que el controlador cargue los datos al formulario desde la tabla
    public void loadSelectedProductToForm() {
        int selectedRow = view.getTablaProductos().getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String name = (String) view.getTablaProductos().getValueAt(selectedRow, 1);
                String type = (String) view.getTablaProductos().getValueAt(selectedRow, 2);
                String brand = (String) view.getTablaProductos().getValueAt(selectedRow, 3);
                double price = Double.parseDouble(view.getTablaProductos().getValueAt(selectedRow, 4).toString());
                String hasStockStr = (String) view.getTablaProductos().getValueAt(selectedRow, 5);
                boolean hasStock = hasStockStr.equals("Sí");
                int stock = (int) view.getTablaProductos().getValueAt(selectedRow, 6);
                String registrationDateStr = (String) view.getTablaProductos().getValueAt(selectedRow, 7);

                Date registrationDate = null;
                if (registrationDateStr != null && !registrationDateStr.isEmpty()) {
                    try {
                        registrationDate = viewSdf.parse(registrationDateStr);
                    } catch (ParseException e) {
                        System.err.println("Error al parsear la fecha de la tabla al formulario: " + e.getMessage());
                    }
                }
                view.loadProductToForm(name, type, brand, price, hasStock, stock, registrationDate);
            } catch (ClassCastException | NumberFormatException | IndexOutOfBoundsException e) {
                System.err.println("Error al cargar datos desde la tabla al formulario: " + e.getMessage());
                JOptionPane.showMessageDialog(view, "Error al leer los datos de la fila seleccionada. Verifique el formato.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
