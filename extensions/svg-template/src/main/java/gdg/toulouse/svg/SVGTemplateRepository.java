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

import static gdg.toulouse.svg.DocumentUtils.getElementById;
import static gdg.toulouse.svg.DocumentUtils.parse;
import static gdg.toulouse.svg.DocumentUtils.removeChilds;
import static gdg.toulouse.svg.DocumentUtils.transform;

public class SVGTemplateRepository implements TemplateRepository {

    private final Document document;

    public SVGTemplateRepository(URL resource) throws IOException, ParserConfigurationException, SAXException {
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

    private Try<TemplateInstance> getInstance(Map<String, String> map) {
        return performInstantiation(map).map(document -> stream -> {
            try {
                transform(document, stream);
                return Try.success(Unit.UNIT);
            } catch (Exception e) {
                return Try.failure(e);
            }
        });
    }

    private Try<Document> performInstantiation(Map<String, String> map) {
        return DocumentUtils.clone(this.document).onSuccess(document -> {
            map.entrySet().stream().forEach(keyValueEntry -> {
                getElementById(document, keyValueEntry.getKey()).ifPresent(element -> {
                    removeChilds(element);
                    element.setTextContent(keyValueEntry.getValue());
                });
            });
        });
    }
}
