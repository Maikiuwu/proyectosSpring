package com.proyecto_prod.proyecto3.Service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.proyecto_prod.proyecto3.Model.Entities.Detalles;
import com.proyecto_prod.proyecto3.Model.Entities.Encabezado;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneratorService {

    public void generateInvoicePDF(Encabezado encabezado, HttpServletResponse response) throws IOException {
        // Crear el documento
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        
        // Añadir título
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        titleFont.setSize(18);
        titleFont.setColor(Color.BLACK);

        Paragraph title = new Paragraph("FACTURA", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        
        document.add(new Paragraph(" ")); // Espacio

        // Información del cliente
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        subtitleFont.setSize(14);
        
        document.add(new Paragraph("Datos del Cliente:", subtitleFont));
        document.add(new Paragraph("ID: " + encabezado.getCliente().getId()));
        document.add(new Paragraph("Nombre: " + encabezado.getCliente().getNombre()));
        document.add(new Paragraph("Email: " + encabezado.getCliente().getEmail()));
        
        document.add(new Paragraph(" ")); // Espacio
        
        // Información de la factura
        document.add(new Paragraph("Datos de la Factura:", subtitleFont));
        document.add(new Paragraph("Número de Factura: " + encabezado.getId()));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        document.add(new Paragraph("Fecha: " + encabezado.getFecha().format(formatter)));
        
        document.add(new Paragraph(" ")); // Espacio
        
        // Tabla de productos
        document.add(new Paragraph("Detalle de la Compra:", subtitleFont));
        
        PdfPTable table = new PdfPTable(5); // 5 columnas
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        
        // Establecer anchos relativos de columnas
        float[] columnWidths = {1f, 2.5f, 1f, 1.5f, 1.5f};
        table.setWidths(columnWidths);
        
        // Añadir encabezados de la tabla
        addTableHeader(table);
        
        // Añadir filas con datos
        for(Detalles detalle : encabezado.getDetalles()) {
            addTableRow(table, detalle);
        }
        
        document.add(table);
        
        // Añadir total
        Paragraph totalParagraph = new Paragraph("Total: $" + String.format("%.2f", encabezado.getTotal()), subtitleFont);
        totalParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(totalParagraph);
        
        document.close();
    }
    
    private void addTableHeader(PdfPTable table) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        headerFont.setColor(Color.WHITE);
        
        PdfPCell headerCell;
        
        // Crear celdas de encabezado con fondo gris oscuro
        headerCell = new PdfPCell(new Phrase("ID", headerFont));
        headerCell.setBackgroundColor(new Color(51, 51, 51));
        headerCell.setPadding(5);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerCell);
        
        headerCell = new PdfPCell(new Phrase("Producto", headerFont));
        headerCell.setBackgroundColor(new Color(51, 51, 51));
        headerCell.setPadding(5);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerCell);
        
        headerCell = new PdfPCell(new Phrase("Cantidad", headerFont));
        headerCell.setBackgroundColor(new Color(51, 51, 51));
        headerCell.setPadding(5);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerCell);
        
        headerCell = new PdfPCell(new Phrase("Precio Unitario", headerFont));
        headerCell.setBackgroundColor(new Color(51, 51, 51));
        headerCell.setPadding(5);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerCell);
        
        headerCell = new PdfPCell(new Phrase("Subtotal", headerFont));
        headerCell.setBackgroundColor(new Color(51, 51, 51));
        headerCell.setPadding(5);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerCell);
    }
    
    private void addTableRow(PdfPTable table, Detalles detalle) {
        // Alternancia de color para filas
        PdfPCell cell;
        
        // ID del producto
        cell = new PdfPCell(new Phrase(detalle.getProducto().getId().toString()));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        // Nombre del producto
        cell = new PdfPCell(new Phrase(detalle.getProducto().getNombre()));
        cell.setPadding(5);
        table.addCell(cell);
        
        // Cantidad
        cell = new PdfPCell(new Phrase(detalle.getCantidad().toString()));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        // Precio unitario
        cell = new PdfPCell(new Phrase("$" + String.format("%.2f", detalle.getPrecioUnitario())));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        
        // Subtotal
        cell = new PdfPCell(new Phrase("$" + String.format("%.2f", detalle.getSubtotal())));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
    }
}