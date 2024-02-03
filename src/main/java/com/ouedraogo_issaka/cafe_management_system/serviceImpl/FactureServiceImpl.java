package com.ouedraogo_issaka.cafe_management_system.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ouedraogo_issaka.cafe_management_system.JWT.JwtFilter;
import com.ouedraogo_issaka.cafe_management_system.constants.CafeConstant;
import com.ouedraogo_issaka.cafe_management_system.modeles.Facture;
import com.ouedraogo_issaka.cafe_management_system.repository.FactureRepository;
import com.ouedraogo_issaka.cafe_management_system.service.FactureService;
import com.ouedraogo_issaka.cafe_management_system.utilaires.CafeUtilitaires;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FactureServiceImpl implements FactureService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    FactureRepository factureRepository;

    @Override
    public ResponseEntity<String> generedRapport(Map<String, Object> requestMap) {
        log.info("À l'intérieur du rapport {}", requestMap);
        try {
            String nomFichier;

            if (validateRequestMap(requestMap)) {
                if (requestMap.containsKey("isGenerer") && !(Boolean) requestMap.get("isGenerer")) {
                    nomFichier = (String) requestMap.get("uuid");
                } else {
                    nomFichier = CafeUtilitaires.getUUID();
                    requestMap.put("uuid", nomFichier);

                    insererFacture(requestMap);
                }

                String donnee = "Name: " + requestMap.get("nom") + "\nNumero de Telephone: "
                        + requestMap.get("telephone") +
                        "\nEmail: " + requestMap.get("email") + "\nMode de Payement: " + requestMap.get("modePayement");

                Document document = new Document();
                PdfWriter.getInstance(document,
                        new FileOutputStream(CafeConstant.STORE_LOCATION + "/" + nomFichier + ".pdf"));

                document.open();
                setRectangleInPdf(document);

                Paragraph troncon = new Paragraph("Cafe Magenement System", getFont("Header"));
                troncon.setAlignment(Element.ALIGN_CENTER);
                document.add(troncon);

                Paragraph paragraph = new Paragraph(donnee + "\n\n", getFont("Donnee"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = CafeUtilitaires.getJsonArrayFromString((String) requestMap.get("detailProduit"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    addLigne(table, CafeUtilitaires.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total : " + requestMap.get("montantTotal") + "\n" +
                        "Merci pour votre visite. S'il vous plaît visitez à nouveau", getFont("Donnee"));
                document.add(footer);

                document.close();

                return new ResponseEntity<>("{\"uuid\":\"" + nomFichier + "\"}", HttpStatus.OK);
            }
            return CafeUtilitaires.getResponseEntity("Données requises introuvables", HttpStatus.BAD_REQUEST);

        } catch (Exception exception) {
            exception.fillInStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void addLigne(PdfPTable table, Map<String, Object> donnee) {
        log.info("\nÀ l'intérieur du addLigne\n");
        table.addCell((String) donnee.get("nom"));
        table.addCell((String) donnee.get("categorie"));
        table.addCell((String) donnee.get("quantite"));
        table.addCell(Double.toString((Double) donnee.get("prix")));
        table.addCell(Double.toString((Double) donnee.get("total")));

    }

    private void addTableHeader(PdfPTable table) {
        log.info("\nÀ l'intérieur du addTableHeader\n");
        Stream.of("Nom", "Categorie", "Quantité", "Prix", "Somme Total")
                .forEach((titreColonne) -> {
                    PdfPCell headerCell = new PdfPCell();
                    headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    headerCell.setBorderWidth(2);
                    headerCell.setPhrase(new Phrase(titreColonne));
                    headerCell.setBackgroundColor(BaseColor.YELLOW);
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    headerCell.setVerticalAlignment(Element.ALIGN_CENTER);

                    table.addCell(headerCell);
                });
    }

    private Font getFont(String type) {
        log.info("\nÀ l'intérieur du getFont\n");
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Donnee":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;

            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("\nÀ l'intérieur du setRectangleInPdf\n");
        Rectangle rectangle = new Rectangle(577, 825, 18, 15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);
        document.add(rectangle);
    }

    private void insererFacture(Map<String, Object> requestMap) {
        try {
            Facture facture = new Facture();

            facture.setUuid((String) requestMap.get("uuid"));
            facture.setNom((String) requestMap.get("nom"));
            facture.setEmail((String) requestMap.get("email"));
            facture.setTelephone((String) requestMap.get("telephone"));
            facture.setModePayement((String) requestMap.get("modePayement"));
            facture.setDetailProduit((String) requestMap.get("detailProduit"));
            facture.setTotal(Integer.parseInt((String) requestMap.get("montantTotal")));

            facture.setCreePar(jwtFilter.getCurrentUser());

            factureRepository.save(facture);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("nom") &&
                requestMap.containsKey("telephone") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("modePayement") &&
                requestMap.containsKey("detailProduit") &&
                requestMap.containsKey("montantTotal");
    }

    @Override
    public ResponseEntity<byte[]> addPdf(Map<String, Object> requestMap) {
        log.info("À l'intérieur du addPdf : requestMap {}", requestMap);

        try {
            byte[] byteArray = new byte[0];

            if (!requestMap.containsKey("uuid") && validateRequestMap(requestMap)) {
                return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);
            }

            String fichierPath = CafeConstant.STORE_LOCATION + "/" + (String) requestMap.get("uuid") + ".pdf";

            if (CafeUtilitaires.isFichierExiste(fichierPath)) {
                byteArray = getByteArray(fichierPath);

                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            } else {
                requestMap.put("isGenerer", false);
                generedRapport(requestMap);
                byteArray = getByteArray(fichierPath);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getByteArray(String fichierPath) throws IOException {

        File fichierInitial = new File(fichierPath);
        InputStream ciblStream = new FileInputStream(fichierInitial);

        byte[] byteArray = IOUtils.toByteArray(ciblStream);
        ciblStream.close();
        return byteArray;
    }

    @Override
    public ResponseEntity<String> deleteFacture(Integer id) {
        try {
            Optional<Facture> facture = factureRepository.findById(id);

            if (!facture.isEmpty()) {
                factureRepository.deleteById(id);
                return CafeUtilitaires.getResponseEntity("La Facture a été supprimer avec succès", HttpStatus.OK);
            }
            return CafeUtilitaires.getResponseEntity("Facture id n'existe pas", HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtilitaires.getResponseEntity(CafeConstant.QUELQUE_CHOSE_S_EST_PASSE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
