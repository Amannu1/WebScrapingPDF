package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class App
{
    public static void main( String[] args ) throws IOException {

        String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
        Document doc = Jsoup.connect(url).get();

        Element content = doc.getElementById("content");
        Elements links = content.getElementsByTag("a");

        for (Element link : links){
            String linkHref = link.attr("href");
            String linkText = link.text();

            if(linkHref.contains(".pdf")){

                InputStream input = new URL(linkHref).openStream();
                Files.copy(input, Paths.get(linkText + ".pdf"), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
