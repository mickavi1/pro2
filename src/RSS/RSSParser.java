package RSS;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class RSSParser {

    InputStream inputStream;

    public RSSParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<RSSitem> parseItems() {
        List<RSSitem> items = new ArrayList<>();
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser parser = saxParserFactory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {

                RSSitem item;       //na metodu startelement
                boolean title, link, description, pubDate;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("item")) {        //nazev titlu v pripade spatnych znaku
                        item = new RSSitem();
                    }
                    if (qName.equalsIgnoreCase("title") && item != null) {
                        title = true;
                    }

                    if (qName.equalsIgnoreCase("link") && item != null) {
                        link = true;
                    }

                    if (qName.equalsIgnoreCase("description") && item != null) {
                        description = true;
                    }

                    if (qName.equalsIgnoreCase("pubdate") && item != null) {
                        pubDate = true;
                    }
                    super.startElement(uri, localName, qName, attributes);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("item")) {
                        items.add(item);
                    }
                    super.endElement(uri, localName, qName);
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (title) {
                        item.setTitle((new String(ch, start, length)));
                        title = false;
                    }

                    if (link) {
                        item.setLink((new String(ch, start, length)));
                        link = false;
                    }

                    if (description) {
                        item.setDescription((new String(ch, start, length)));
                        description = false;
                    }

                    if (pubDate) {
                        item.setPubDate((new String(ch, start, length)));
                        pubDate = false;
                    }
                    super.characters(ch, start, length);
                }
            };

            parser.parse(inputStream, handler);
        } catch (Exception e) {
            //ignored
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return items;
        }
    }
}
