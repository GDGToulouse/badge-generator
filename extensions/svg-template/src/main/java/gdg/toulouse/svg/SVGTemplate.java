package gdg.toulouse.svg;

import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.service.TemplateInstance;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import static gdg.toulouse.svg.DomUtils.getElementById;
import static gdg.toulouse.svg.DomUtils.parse;
import static gdg.toulouse.svg.DomUtils.removeChilds;
import static gdg.toulouse.svg.DomUtils.transform;

public class SVGTemplate {

    private final String baseURL;
    private final Document document;

    public SVGTemplate(URL resource) throws IOException, ParserConfigurationException, SAXException {
        this.baseURL = resource.toString();
        try (InputStream stream = resource.openStream()) {
            this.document = parse(stream);
        }
    }

    public Try<TemplateInstance> getInstance(Map<String, String> map) {
        final Document document = performInstantiation(map);

        return Try.success(stream -> {
            try {
                transform(document, stream);
                return Try.success(Unit.UNIT);
            } catch (Exception e) {
                return Try.failure(e);
            }
        });
    }

    private Document performInstantiation(Map<String, String> map) {
        final Document document = this.document;

        map.entrySet().stream().forEach(keyValueEntry -> {
            final Node element = getElementById(document, keyValueEntry.getKey());
            if (element != null) {
                removeChilds(element);
                element.setTextContent(keyValueEntry.getValue());
            }
        });

        return document;
    }

}
