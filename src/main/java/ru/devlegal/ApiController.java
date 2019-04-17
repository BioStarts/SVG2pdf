package ru.devlegal;

import de.bripkens.svgexport.Format;
import de.bripkens.svgexport.SVGExport;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity greeting2(@RequestParam(value="name", defaultValue="7") String name) throws IOException {

        try {
            new SVGExport().setInput(new FileInputStream(name + ".svg"))
                    .setOutput(new FileOutputStream(name + ".pdf"))
                    .setTranscoder(Format.PDF)
                    .transcode();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Resource file = new UrlResource(name + ".pdf");

        ResponseEntity respEntity = null;

        byte[] reportBytes = null;
        File result = new File(name + ".pdf");

        if(result.exists()){
            InputStream inputStream = new FileInputStream(name + ".pdf");
            String type=result.toURL().openConnection().guessContentTypeFromName(name + ".pdf");

            byte[]out=org.apache.commons.io.IOUtils.toByteArray(inputStream);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "attachment; filename=" + name + ".pdf");
            responseHeaders.add("Content-Type",type);

            respEntity = new ResponseEntity(out, responseHeaders, HttpStatus.OK);
        }else{
            respEntity = new ResponseEntity ("File Not Found", HttpStatus.OK);
        }
        return respEntity;


        //return ResponseEntity.ok().body(file);
        //return new PDF(new File(name + ".pdf"));
        /*return new Greeting(counter.incrementAndGet(),
                String.format(template, name));*/
    }





    @RequestMapping("/svg")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }


    @RequestMapping(value="/merge", method= RequestMethod.POST)
    public @ResponseBody
    String Merge(@RequestParam("name") String[] name,
                 @RequestParam(value = "new")String newPdf) throws IOException {
        //model.addAttribute("name",name);
        PDFMergerUtility ut = new PDFMergerUtility();
        for (int i = 0; i < name.length; i++) {
            ut.addSource("C:\\Users\\vstartsev\\IdeaProjects\\svg\\" + name[i]);
        }
        ut.setDestinationFileName("C:\\Users\\vstartsev\\IdeaProjects\\svg\\" + newPdf);
        ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        return String.valueOf(name.length);

    }
}
