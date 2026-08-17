package edu.plataforma.saas.curricular.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import edu.plataforma.saas.curricular.model.Ficha;
import edu.plataforma.saas.curricular.model.Pergunta;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfExportService {

    public byte[] exportarFichaParaPdf(Ficha ficha) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            
            document.open();
            
            // Título Principal
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("Ficha de Trabalho: " + ficha.getTitulo(), fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Cabeçalho / Informações do Aluno
            Font fontCabecalho = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph cabecalho = new Paragraph();
            cabecalho.add(new Chunk("Nome do Aluno: __________________________________________________\n", fontCabecalho));
            cabecalho.add(new Chunk("Data: ___/___/_______   Classificação: ______________\n", fontCabecalho));
            cabecalho.setSpacingAfter(20);
            document.add(cabecalho);
            
            // Descrição da Ficha
            if (ficha.getDescricao() != null && !ficha.getDescricao().isEmpty()) {
                Font fontDesc = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11);
                Paragraph desc = new Paragraph(ficha.getDescricao(), fontDesc);
                desc.setSpacingAfter(20);
                document.add(desc);
            }
            
            document.add(new Chunk(new com.lowagie.text.pdf.draw.LineSeparator()));
            
            // Perguntas
            Font fontPergunta = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            
            int num = 1;
            for (Pergunta p : ficha.getPerguntas()) {
                Paragraph pEnunciado = new Paragraph();
                pEnunciado.setSpacingBefore(15);
                pEnunciado.setSpacingAfter(10);
                pEnunciado.add(new Chunk(num + ". " + p.getEnunciado(), fontPergunta));
                document.add(pEnunciado);
                
                // Espaço para responder
                Paragraph linhasResposta = new Paragraph();
                linhasResposta.setSpacingAfter(15);
                linhasResposta.add(new Chunk("___________________________________________________________________________\n"));
                linhasResposta.add(new Chunk("___________________________________________________________________________\n"));
                linhasResposta.add(new Chunk("___________________________________________________________________________\n"));
                document.add(linhasResposta);
                
                num++;
            }
            
            document.close();
            return baos.toByteArray();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }
    }
}
