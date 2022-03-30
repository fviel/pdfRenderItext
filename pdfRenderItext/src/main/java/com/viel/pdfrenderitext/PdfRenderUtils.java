package com.viel.pdfrenderitext;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

/**
 *
 * @author fviel
 */
public class PdfRenderUtils {
    public InputStream getFileFromResourcesAsInputStream(String filename) {
        try{
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream(filename);
        if (Objects.isNull(is)) {
            throw new IllegalArgumentException("Modelo de certificado " + filename + " não encontrado.");
        } else {
            return is;
        }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }            
    }

    public File getFileFromResource(String filename) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(filename);
            if (Objects.isNull(resource)) {
                throw new IllegalArgumentException("File " + filename + " not found.");
            } else {
                //return new File(resource.toURI());
                return new File(resource.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("getFileFromResource - Falha ao ler modelo de certificado.");
            return null;
        }
    }
}
