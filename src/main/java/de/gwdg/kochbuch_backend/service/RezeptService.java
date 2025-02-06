package de.gwdg.kochbuch_backend.service;



import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;


import java.io.ByteArrayOutputStream;
import java.util.List;

import de.gwdg.kochbuch_backend.model.dao.RezeptRepository;
import de.gwdg.kochbuch_backend.model.dao.RezeptzutatRepository;
import de.gwdg.kochbuch_backend.model.dto.Rezept;
import de.gwdg.kochbuch_backend.model.dto.Rezeptzutat;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RezeptService {
    private final RezeptRepository rezeptRepository;
    private final RezeptzutatRepository rezeptzutatRepository;

    @Autowired
    public RezeptService(RezeptRepository rezeptRepository, RezeptzutatRepository rezeptzutatRepository) {
        this.rezeptRepository = rezeptRepository;
        this.rezeptzutatRepository = rezeptzutatRepository;
    }

    //Create: nimmt ein Rezept Objekt entgegen und speichert dieses in die Oracle Datenbank
    @Transactional //Datenbank-Operation können nicht mehr gleichzeitig durchgeführt werden
    public Rezept createRezept(Rezept rezept) {
        return rezeptRepository.save(rezept);
    }

    //Read: liest alle Rezept Objekte aus der Datenbank
    public List<Rezept> getAllRezepte() {
        return (List<Rezept>) rezeptRepository.findAll();
    }

    // liest das Rezept mit der spezifischen ID aus
    public Rezept getRezeptById(long id) {
        Rezept rezept = rezeptRepository.findById(id).orElseThrow(() -> new RuntimeException("Rezept nicht gefunden"));

        Rezept rezeptDTO = new Rezept();
        rezeptDTO.setId(rezept.getId());
        rezeptDTO.setTitel(rezept.getTitel());
        rezeptDTO.setBeschreibung(rezept.getBeschreibung());
        rezeptDTO.setZubereitungszeit(rezept.getZubereitungszeit());

        // Rezeptzutaten in DTO umwandeln
        List<Rezeptzutat> rezeptzutatDTOs = rezept.getRezeptzutaten().stream().map(zutat -> {
            Rezeptzutat dto = new Rezeptzutat();
            dto.setId(zutat.getId());
            dto.setZutatName(zutat.getZutatName());
            dto.setGramm(zutat.getGramm());
            dto.setMl(zutat.getMl());
            return dto;
        }).toList();

        rezeptDTO.setRezeptzutaten(rezeptzutatDTOs);

        return rezeptDTO;
    }

    //Update: updated das übergebene Rezept Objekt
    public Rezept updateRezept(Rezept rezept) {
        // Prüfen, ob das Rezept mit der ID existiert
        Rezept existingRezept = rezeptRepository.findById(rezept.getId())
                .orElseThrow(() -> new EntityNotFoundException("Rezept mit ID " + rezept.getId() + " nicht gefunden"));

        // Felder aktualisieren
        existingRezept.setTitel(rezept.getTitel());
        existingRezept.setBeschreibung(rezept.getBeschreibung());
        existingRezept.setZubereitungszeit(rezept.getZubereitungszeit());

        // Speichern der Änderungen
        return (Rezept) rezeptRepository.save(existingRezept);
    }

    //Delete: löscht ein Rezept anhand seiner ID aus der Datenbank
    public void deleteRezept(Long id) throws EntityNotFoundException {

        // Prüfen, ob das Rezept mit der ID existiert
        if (!rezeptRepository.existsById(id)) {
            throw new EntityNotFoundException("Rezept mit ID " + id + " nicht gefunden");
        }
        // Löschen des Rezepts
        rezeptRepository.deleteById(id);
    }

    // Create: mehrere Rezepte auf einmal erstellen
    @Transactional
    public List<Rezept> createRezepte(List<Rezept> rezepte) {
        return (List<Rezept>) rezeptRepository.saveAll(rezepte);
    }

    // Liest alle Rezepte eines Autors aus der Datenbank
    public Optional<Rezept> getRezepteDesAutors(Long autorId) {
        // Rufe die Methode aus dem RezeptRepository auf, um alle Rezepte des Autors zu erhalten
        return rezeptRepository.findById(autorId);
    }


    public byte[] generateRezeptPdf(Long rezeptId) throws EntityNotFoundException {

        // Rezept und Zutaten abrufen
        Rezept rezept = getRezeptById(rezeptId); // Holt das Rezept basierend auf der ID
        List<Rezeptzutat> zutaten = rezeptzutatRepository.findAllByRezepte_Id(rezeptId); // Zutaten für das Rezept

        // PDF-Erstellung
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // PDF Writer erstellen
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);

        // PDF Dokument erstellen
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Layout Dokument erstellen
        Document document = new Document(pdfDoc);

        // **Rezeptdetails hinzufügen**

        // Titel des Rezepts
        String rezeptTitel = "Rezept: " + rezept.getTitel();
        Paragraph titelParagraph = new Paragraph(rezeptTitel).setFontSize(16);
        document.add(titelParagraph);

        // Beschreibung
        String beschreibung = rezept.getBeschreibung();
        Paragraph beschreibungParagraph = new Paragraph("Beschreibung: " + beschreibung).setFontSize(12);
        document.add(beschreibungParagraph);

        // Zubereitungszeit
        String zubereitungszeit = "Zubereitungszeit: " + rezept.getZubereitungszeit() + " Minuten";
        Paragraph zubereitungszeitParagraph = new Paragraph(zubereitungszeit).setFontSize(12);
        document.add(zubereitungszeitParagraph);

        // **Zutaten als Tabelle hinzufügen**
        float[] columnWidths = {300F, 100F, 100F};  // 3 Spalten: Zutat, Gramm, Milliliter
        Table zutatenTable = new Table(columnWidths);

        // Tabellenüberschrift
        zutatenTable.addCell(new Cell().add(new Paragraph("Zutat")));  // Hier verwenden wir den add() Aufruf mit Paragraph
        zutatenTable.addCell(new Cell().add(new Paragraph("Gramm")));
        zutatenTable.addCell(new Cell().add(new Paragraph("Milliliter")));

        // Zutaten einfügen
        for (Rezeptzutat zutat : zutaten) {
            zutatenTable.addCell(new Cell().add(new Paragraph(zutat.getZutatName())));  // Zutat Name
            zutatenTable.addCell(new Cell().add(new Paragraph(zutat.getGramm() > 0 ? String.valueOf(zutat.getGramm()) : "-")));  // Gramm
            zutatenTable.addCell(new Cell().add(new Paragraph(zutat.getMl() > 0 ? String.valueOf(zutat.getMl()) : "-")));  // Milliliter
        }

        // Tabelle zum Dokument hinzufügen
        document.add(zutatenTable);  // Die Tabelle wird dem Dokument hinzugefügt

        // Dokument schließen
        document.close();
        System.out.println("PDF wurde erfolgreich erstellt!");

        // PDF als Byte-Array zurückgeben
        return byteArrayOutputStream.toByteArray();
    }
}
