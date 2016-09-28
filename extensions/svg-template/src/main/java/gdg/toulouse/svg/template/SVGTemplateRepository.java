package gdg.toulouse.svg.template;

import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.data.TemplateData;
import gdg.toulouse.template.service.TemplateInstance;
import gdg.toulouse.template.service.TemplateRepository;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import static gdg.toulouse.svg.utils.Constants.DATA_IMAGE_PNG_BASE64;
import static gdg.toulouse.svg.utils.Constants.DPI_X;
import static gdg.toulouse.svg.utils.Constants.INCH_IN_CM;
import static gdg.toulouse.svg.utils.Constants.NAME;
import static gdg.toulouse.svg.utils.Constants.QRCODE;
import static gdg.toulouse.svg.utils.Constants.SURNAME;
import static gdg.toulouse.svg.utils.Constants.WIDTH_CM;
import static gdg.toulouse.svg.utils.Constants.XLINK_HREF;
import static gdg.toulouse.svg.utils.DocumentUtils.cloneDocument;
import static gdg.toulouse.svg.utils.DocumentUtils.getElementById;
import static gdg.toulouse.svg.utils.DocumentUtils.parse;
import static gdg.toulouse.svg.utils.DocumentUtils.setAttribute;
import static gdg.toulouse.svg.utils.DocumentUtils.setContent;
import static gdg.toulouse.svg.utils.DocumentUtils.transform;

public class SVGTemplateRepository implements TemplateRepository {

    private static final String ROBOTO = "Roboto";
    
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

    private Try<TemplateInstance> getInstance(TemplateData data) {
        return performInstantiation(data).map(document -> stream -> {
            try {
                transform(document, stream);
                return Try.success(Unit.UNIT);
            } catch (Exception e) {
                return Try.failure(e);
            }
        });
    }

    private Try<Document> performInstantiation(TemplateData data) {
        return cloneDocument(this.document).onSuccess(document -> {
            getElementById(document, SURNAME).ifPresent(node -> {
                setContent(node, data.getSurname());
                setAttribute(node, "x", String.valueOf(getOffset(data.getSurname(), 30)));
            });
            getElementById(document, NAME).ifPresent(node -> {
                setContent(node, data.getName());
                setAttribute(node, "x", String.valueOf(getOffset(data.getName(), 28)));
            });
            getElementById(document, QRCODE).ifPresent(node -> setAttribute(node, XLINK_HREF, getQRCode(data)));
        });
    }

    private double getOffset(String s, double fontInPixel) {
        final double textInPixel = textSizeInPixel(s, fontInPixel);
        final double pageInpixel = pageSizeInPixel();

        return pageInpixel/4 - textInPixel/2;
    }

    private double pageSizeInPixel() {
        return WIDTH_CM * DPI_X / INCH_IN_CM;
    }

    private double textSizeInPixel(String s, double fontInPixel) {
        final Font font = new Font(ROBOTO, Font.PLAIN, (int) fontInPixel);
        if (!font.getFamily().equals(ROBOTO)) {
            Logger.getAnonymousLogger().log(Level.WARNING,
                    "Text size estimated using font " +
                    font.getFamily() +
                    " - (1) Download Roboto or (2) Modify the template"
            );
        }
        final FontMetrics metrics = new FontMetrics(font) {};
        return metrics.getStringBounds(s, null).getWidth();
    }

    private String getQRCode(TemplateData data) {
        final String identity = data.getSurname() + " " + data.getName();
        final String mail = data.getMail();

        return DATA_IMAGE_PNG_BASE64 + PNGQRCode.createFrom(identity, mail);
    }
}
