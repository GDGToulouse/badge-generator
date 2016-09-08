package gdg.toulouse.svg;

import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.service.TemplateInstance;
import gdg.toulouse.template.service.TemplateRepository;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;

import static gdg.toulouse.svg.DomUtils.getElementById;
import static gdg.toulouse.svg.DomUtils.parse;
import static gdg.toulouse.svg.DomUtils.removeChilds;
import static gdg.toulouse.svg.DomUtils.transform;

public class SVGTemplateRepository implements TemplateRepository {

    private final String baseURL;
    private final Document document;

    public SVGTemplateRepository(URL resource) throws IOException, ParserConfigurationException, SAXException {
        this.baseURL = resource.toString();
        try (InputStream stream = resource.openStream()) {
            this.document = parse(stream);
        }
    }

    @Override
    public Function<Map<String, String>, Try<TemplateInstance>> getGenerator() {
        return this::getInstance;
    }

    //
    // Private behaviors
    //

    private  Try<TemplateInstance> getInstance(Map<String, String> map) {
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
            getElementById(document, keyValueEntry.getKey()).ifPresent(element -> {
                removeChilds(element);
                element.setTextContent(keyValueEntry.getValue());
            });
        });

        return document;
    }
}
