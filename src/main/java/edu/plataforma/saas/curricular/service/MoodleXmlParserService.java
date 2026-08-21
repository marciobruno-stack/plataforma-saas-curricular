package edu.plataforma.saas.curricular.service;

import edu.plataforma.saas.curricular.model.Pergunta;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoodleXmlParserService {

    public List<Pergunta> parseMoodleXml(InputStream inputStream) {
        List<Pergunta> perguntasExtraidas = new ArrayList<>();
        
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // Evitar ataques XXE (XML External Entity)
            dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("question");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String tipoMoodle = eElement.getAttribute("type");
                    
                    // Ignorar categorias (não são perguntas)
                    if ("category".equalsIgnoreCase(tipoMoodle)) {
                        continue;
                    }

                    String enunciado = extrairTextoQuestion(eElement);
                    if (enunciado == null || enunciado.trim().isEmpty()) {
                        continue; // Ignorar perguntas sem enunciado
                    }
                    
                    // Mapeamento simplificado do tipo de pergunta
                    String tipoNosso = mapearTipo(tipoMoodle);
                    
                    Pergunta pergunta = new Pergunta();
                    pergunta.setEnunciado(limparTagsHtml(enunciado));
                    pergunta.setTipo(tipoNosso);
                    
                    perguntasExtraidas.add(pergunta);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao fazer parse do ficheiro XML do Moodle: " + e.getMessage(), e);
        }

        return perguntasExtraidas;
    }

    private String extrairTextoQuestion(Element questionElement) {
        NodeList textNodes = questionElement.getElementsByTagName("questiontext");
        if (textNodes.getLength() > 0) {
            Element questionTextElement = (Element) textNodes.item(0);
            NodeList textChild = questionTextElement.getElementsByTagName("text");
            if (textChild.getLength() > 0) {
                return textChild.item(0).getTextContent();
            }
        }
        return null;
    }

    private String mapearTipo(String tipoMoodle) {
        if (tipoMoodle == null) return "Outro";
        
        return switch (tipoMoodle.toLowerCase()) {
            case "multichoice" -> "Escolha Múltipla";
            case "truefalse" -> "Verdadeiro/Falso";
            case "essay" -> "Desenvolvimento";
            case "shortanswer" -> "Resposta Curta";
            case "numerical" -> "Numérica";
            case "matching" -> "Associação";
            default -> "Outro (" + tipoMoodle + ")";
        };
    }
    
    // Moodle XML question text usually comes wrapped in HTML or CDATA with HTML inside. 
    // We strip basic HTML tags for a cleaner text import.
    private String limparTagsHtml(String html) {
        if (html == null) return null;
        return html.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", " ").trim();
    }
}
