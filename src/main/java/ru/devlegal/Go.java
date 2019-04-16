package ru.devlegal;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Go {
    public static void main(String[] args) throws IOException {
        /*SVGExport A = new SVGExport();
        SVGExport B = A.setInput(new FileInputStream("b.svg"));
        SVGExport C = B.setOutput(new FileOutputStream("test12.pdf"));
        SVGExport D = C.setTranscoder(Format.PDF);
        D.transcode().transcode();*/

        /*new SVGExport().setInput(new FileInputStream("7-uploaded.svg"))
                .setOutput(new FileOutputStream("test33.pdf"))
                .setTranscoder(Format.PDF)
                .transcode();*/

        PDFMergerUtility ut = new PDFMergerUtility();
        ut.addSource("C:\\Users\\User\\Geek\\Svgtopdf\\b.pdf");
        ut.addSource("C:\\Users\\User\\Geek\\Svgtopdf\\c.pdf");
        ut.setDestinationFileName("C:\\Users\\User\\Geek\\Svgtopdf\\as.pdf");
        ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
    }
}
