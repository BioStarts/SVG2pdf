package ru.devlegal;


import de.bripkens.svgexport.Format;
import de.bripkens.svgexport.SVGExport;
import org.apache.fop.pdf.PDFLink;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.devlegal.Greeting;

import java.io.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController

public class ApiController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded.svg")));
                stream.write(bytes);
                stream.close();
                return "Вы удачно загрузили " + name + " в " + name + "-uploaded !";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }
    }

    /*new SVGExport().setInput(new FileInputStream(name + "-uploaded.svg"))
            .setOutput(new FileOutputStream(name + ".pdf"))
            .setTranscoder(Format.PDF)
                        .transcode();*/

    @RequestMapping("/pdf")
    public PDF greeting2(@RequestParam(value="name", defaultValue="b") String name) {

        try {
            new SVGExport().setInput(new FileInputStream(name + ".svg"))
                    .setOutput(new FileOutputStream(name + ".pdf"))
                    .setTranscoder(Format.PDF)
                    .transcode();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new PDF(new File(name + ".pdf"));
        /*return new Greeting(counter.incrementAndGet(),
                String.format(template, name));*/
    }



    @RequestMapping("/svg")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
}
