package ru.devlegal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Go {
    public static void main(String[] args) throws FileNotFoundException {
        SVGExport A = new SVGExport();
        SVGExport B = A.setInput(new FileInputStream("7-uploaded.svg"));
        SVGExport C = B.setOutput(new FileOutputStream("test12.pdf"));
        SVGExport D = C.setTranscoder(Format.PDF);
        SVGExport E = D.transcode();

        /*new SVGExport().setInput(new FileInputStream("C:\\Users\\vstartsev\\IdeaProjects\\svg\\8-uploaded.svg"))
                .setOutput(new FileOutputStream("C:\\Users\\vstartsev\\IdeaProjects\\svg\\test9.pdf"))
                .setTranscoder(Format.PDF)
                .transcode();*/
    }
}
