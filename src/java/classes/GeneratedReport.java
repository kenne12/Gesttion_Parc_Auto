/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author batrapi
 */
public class GeneratedReport {

    private static JasperPrint jasperPrint;

    /**
     *
     * @param <E>
     * @param T
     * @param path
     * @param output
     * @throws net.sf.jasperreport.engine.JRException
     * @throws java.io.IOException
     */
    public static < E> void PDF(List< E> T, String path, String output) throws JRException, IOException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(T, false);

        jasperPrint = JasperFillManager.fillReport("E:\\GPA\\Netbeans\\TestProject\\web\\resources\\report\\" + path + ".jasper", new HashMap(), beanCollectionDataSource);
       // String filename = output+".pdf";
       // String contextPath = getServletContext().getRealPath(File.separator);
        
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.reset();
      //  httpServletResponse.setContentType("application/pdf");
      //  httpServletResponse.setContentLength(contentLength);
        httpServletResponse.addHeader("Content-disposition", "inline; filename=" + output + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

        FacesContext.getCurrentInstance().responseComplete();
        System.out.println("Jasper List: " + T);
    }
    
       public static < E> void PDFGenerator(String path, String output) throws JRException, IOException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(null);

        jasperPrint = JasperFillManager.fillReport("E:\\GPA\\Netbeans\\TestProject\\web\\resources\\report\\" + path + ".jasper", new HashMap(), beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.reset();
        httpServletResponse.addHeader("Content-disposition", "inline; filename=" + output + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

        FacesContext.getCurrentInstance().responseComplete();
    }
    
     public static < E> void DOCX(List< E> T, String path, String output) throws JRException, IOException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(T, false);
        JRDocxExporter docxExporter = new JRDocxExporter();
        jasperPrint = JasperFillManager.fillReport("E:\\Cam Life\\Gesparcau\\TestProject\\web\\resources\\report\\" + path + ".jasper", new HashMap(), beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.reset();
        httpServletResponse.addHeader("Content-disposition", "inline; filename=" + output + ".docx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        System.out.println("Jasper List: " + T);
     }
     
      public static < E> void XLSX(List< E> T, String path, String output) throws JRException, IOException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(T, false);
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();
        jasperPrint = JasperFillManager.fillReport("E:\\Cam Life\\Gesparcau\\TestProject\\web\\resources\\report\\" + path + ".jasper", new HashMap(), beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.reset();
        httpServletResponse.addHeader("Content-disposition", "inline; filename=" + output + ".xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        xlsxExporter.exportReport();
        System.out.println("Jasper List: " + T);
    }
          
    public static < E> void ODT(List< E> T, String path, String output) throws JRException, IOException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(T, false);
        JROdtExporter odtExporter = new JROdtExporter();
        jasperPrint = JasperFillManager.fillReport("E:\\Cam Life\\Gesparcau\\TestProject\\web\\resources\\report\\" + path + ".jasper", new HashMap(), beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.reset();
        httpServletResponse.addHeader("Content-disposition", "inline; filename=" + output + ".xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        odtExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        odtExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        odtExporter.exportReport();
        System.out.println("Jasper List: " + T);
    }
}

