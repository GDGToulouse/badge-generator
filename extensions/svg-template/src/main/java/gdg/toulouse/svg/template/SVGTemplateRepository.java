package gdg.toulouse.svg.template;

import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.svg.utils.DocumentUtils;
import gdg.toulouse.template.data.TemplateData;
import gdg.toulouse.template.service.TemplateInstance;
import gdg.toulouse.template.service.TemplateRepository;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Function;

import static gdg.toulouse.svg.utils.Constants.NAME;
import static gdg.toulouse.svg.utils.Constants.QRCODE;
import static gdg.toulouse.svg.utils.Constants.SURNAME;
import static gdg.toulouse.svg.utils.Constants.DATA_IMAGE_PNG_BASE64;
import static gdg.toulouse.svg.utils.Constants.XLINK_HREF;
import static gdg.toulouse.svg.utils.DocumentUtils.getElementById;
import static gdg.toulouse.svg.utils.DocumentUtils.parse;
import static gdg.toulouse.svg.utils.DocumentUtils.transform;

public class SVGTemplateRepository implements TemplateRepository {

    private final Document document;

    public SVGTemplateRepository(URL resource) throws IOException, ParserConfigurationException, SAXException {
        try (InputStream stream = resource.openStream()) {
            this.document = parse(stream);
        }
    }

    @Override
    public Function<TemplateData, Try<TemplateInstance>> getGenerator() {
        return this::getInstance;
    }

    //
    // Private behaviors
    //

    private Try<TemplateInstance> getInstance(TemplateData templateData) {
        return performInstantiation(templateData).map(document -> stream -> {
            try {
                transform(document, stream);
                return Try.success(Unit.UNIT);
            } catch (Exception e) {
                return Try.failure(e);
            }
        });
    }

    private Try<Document> performInstantiation(TemplateData templateData) {
        return DocumentUtils.clone(this.document).onSuccess(document -> {
            getElementById(document, SURNAME).
                    ifPresent(node -> DocumentUtils.setContent(node, templateData.getSurname()));
            getElementById(document, NAME).
                    ifPresent(node -> DocumentUtils.setContent(node, templateData.getName()));
            getElementById(document, QRCODE).
                    ifPresent(node -> {
                        final String identity = templateData.getSurname() + " " + templateData.getName();
                        final String mail = templateData.getMail();
                        final String image = DATA_IMAGE_PNG_BASE64 + PNGQRCode.createFrom(identity, mail);
                        DocumentUtils.setAttribute(node, XLINK_HREF, image);
                    });
        });
    }
}