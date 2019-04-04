package ru.devlegal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Go {
    public static void main(String[] args) throws FileNotFoundException {
        /*SVGExport A = new SVGExport();
        SVGExport B = A.setInput(new FileInputStream("b.svg"));
        SVGExport C = B.setOutput(new FileOutputStream("test12.pdf"));
        SVGExport D = C.setTranscoder(Format.PDF);
        D.transcode().transcode();*/

        new SVGExport().setInput(new FileInputStream("7-uploaded.svg"))
                .setOutput(new FileOutputStream("test33.pdf"))
                .setTranscoder(Format.PDF)
                .transcode();
    }
}
