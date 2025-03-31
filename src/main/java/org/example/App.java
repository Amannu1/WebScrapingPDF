package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class App
{
    public static void main( String[] args ) throws IOException {

        String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
        Document doc = Jsoup.connect(url).get();

        Element content = doc.getElementById("content");
        Elements links = content.getElementsByTag("a");

        Map<String, InputStream> map = new HashMap<>();

        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();

            if (linkHref.contains(".pdf")) {

                InputStream input = new URL(linkHref).openStream();
                map.put(linkText + ".pdf", input);
                    }
                }
                String zipFileName = "anexos.zip";
                OutputStream out = Files.newOutputStream(Paths.get(zipFileName));
                ZipOutputStream zs = new ZipOutputStream(out);

                for(Map.Entry<String, InputStream> pair : map.entrySet()){

                    ZipEntry entry = new ZipEntry(pair.getKey());
                    zs.putNextEntry(entry);

                    InputStream inputStream = pair.getValue();

                    int len;
                    byte[] buffer = new byte[1024];

                    while((len = inputStream.read(buffer)) > 0){
                        zs.write(buffer, 0, len);
                    }
                    inputStream.close();
                }
                zs.closeEntry();
                zs.close();
            }
        }
