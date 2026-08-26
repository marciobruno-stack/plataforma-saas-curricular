package edu.plataforma.saas.curricular.service;

import edu.plataforma.saas.curricular.model.Ficha;
import edu.plataforma.saas.curricular.model.Pergunta;
import org.springframework.stereotype.Service;

@Service
public class MoodleXmlExportService {

    public byte[] exportarFichaParaMoodleXml(Ficha ficha) {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<quiz>\n");

        // Adicionar categoria (opcional, mas boa prática no Moodle)
        xml.append("  <question type=\"category\">\n");
        xml.append("    <category>\n");
        xml.append("      <text>$course$/top/").append(escapeXml(ficha.getTitulo())).append("</text>\n");
        xml.append("    </category>\n");
        xml.append("  </question>\n");

        for (Pergunta p : ficha.getPerguntas()) {
            String tipoMoodle = mapearTipoInverso(p.getTipo());
            
            xml.append("  <question type=\"").append(tipoMoodle).append("\">\n");
            xml.append("    <name>\n");
            xml.append("      <text>").append(escapeXml(truncar(p.getEnunciado(), 50))).append("</text>\n");
            xml.append("    </name>\n");
            xml.append("    <questiontext format=\"html\">\n");
            xml.append("      <text><![CDATA[").append(p.getEnunciado()).append("]]></text>\n");
            xml.append("    </questiontext>\n");
            xml.append("    <defaultgrade>1.0000000</defaultgrade>\n");
            xml.append("  </question>\n");
        }

        xml.append("</quiz>");
        
        return xml.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    private String mapearTipoInverso(String tipoNosso) {
        if (tipoNosso == null) return "essay"; // fallback
        return switch (tipoNosso) {
            case "ESCOLHA_MULTIPLA" -> "multichoice";
            case "VERDADEIRO_FALSO" -> "truefalse";
            case "TEXTO_LIVRE" -> "essay";
            default -> "essay";
        };
    }

    private String escapeXml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }

    private String truncar(String text, int limit) {
        if (text == null) return "";
        if (text.length() <= limit) return text;
        return text.substring(0, limit) + "...";
    }
}
