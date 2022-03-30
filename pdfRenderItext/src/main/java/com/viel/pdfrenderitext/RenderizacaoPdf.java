package com.viel.pdfrenderitext;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fviel
 */
public class RenderizacaoPdf {

    public static final String PATH_TEMPLATE = "/home/fviel/renderizacaoPdf/template.pdf";
    public static final String PATH_OUTPUT = "/home/fviel/renderizacaoPdf/";

    public static void main(String[] args) {
        relatorioDto crtDtoTeste = preencherDto();
        try {
            renderPdfFromFile(crtDtoTeste);
            renderPdfFromInputStream(crtDtoTeste);
        } catch (IOException ex) {
            System.err.println("Falha IO");
        } catch (URISyntaxException ex) {
            System.err.println("Falha URI");
        }
    }

    public static relatorioDto preencherDto() {
        relatorioDto relatorioDto = new relatorioDto();
        relatorioDto.setTitulo("01/01/2000");        
        relatorioDto.setDescricao("fooooooooooooooooo");
        return relatorioDto;
    }

    public static String renderPdfFromFile(relatorioDto crtDto) throws IOException, URISyntaxException {
        File fModelo = new File(PATH_TEMPLATE);
        PdfReader readerSrcTemplate = new PdfReader(fModelo);
        return renderizarPdf(crtDto,readerSrcTemplate,"file.pdf");
    }

    public static String renderPdfFromInputStream(relatorioDto crtDto) throws IOException, URISyntaxException {   
        PdfRenderUtils pru = new PdfRenderUtils();
        InputStream isModeloCertificado =  pru.getFileFromResourcesAsInputStream("templateCRE.pdf");            
        PdfReader readerSrcTemplate = new PdfReader(isModeloCertificado);
        return renderizarPdf(crtDto, readerSrcTemplate ,"inputStream.pdf");
    }

    public static String renderizarPdf(relatorioDto crtDto, PdfReader readerSrcTemplate, String outputName) throws IOException, URISyntaxException {
        String dest =  PATH_OUTPUT+outputName;
        PdfDocument pdfDoc = null;
        try {
            PdfWriter writer = new PdfWriter(dest);
            pdfDoc = new PdfDocument(readerSrcTemplate, writer);

            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            Map<String, PdfFormField> fields = new HashMap<String, PdfFormField>();
            fields = form.getFormFields();
             

            String camposNaoEncontrados = "";

            String chave = "TITULO";
            if (fields.containsKey(chave)) {
                PdfFormField campoTitulo = fields.get(chave).setValue(crtDto.getTitulo());
            } else {
                camposNaoEncontrados += "TITULO_COORDENACAO_CTRC -";
            }

            chave = "DESCRICAO";
            if (fields.containsKey(chave)) {
                PdfFormField campoDescricao = fields.get(chave).setValue(crtDto.getDescricao());
            } else {
                camposNaoEncontrados += "ENDERECO_CTRC -";
            }
            
            pdfDoc.close();

            if (!camposNaoEncontrados.isEmpty()) {
                System.err.println("Campos não encontrados: " + camposNaoEncontrados);
            }
            return dest;

        } catch (IOException ioe) {
            System.err.println("Falha ao abrir modelo");
            return null;
        }
    }    
}
