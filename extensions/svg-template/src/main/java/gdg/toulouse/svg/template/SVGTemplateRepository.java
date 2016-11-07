package gdg.toulouse.svg.template;

import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.data.TemplateData;
import gdg.toulouse.template.service.TemplateInstance;
import gdg.toulouse.template.service.TemplateRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
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

import static gdg.toulouse.svg.template.Constants.DATA_IMAGE_PNG_BASE64;
import static gdg.toulouse.svg.template.Constants.NAME;
import static gdg.toulouse.svg.template.Constants.QRCODE;
import static gdg.toulouse.svg.template.Constants.ROLE;
import static gdg.toulouse.svg.template.Constants.ROLECOLOR;
import static gdg.toulouse.svg.template.Constants.SURNAME;
import static gdg.toulouse.svg.template.Constants.XLINK_HREF;
import static gdg.toulouse.svg.utils.DocumentUtils.cloneDocument;
import static gdg.toulouse.svg.utils.DocumentUtils.getAttribute;
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
    public Function<TemplateData[], Try<TemplateInstance>> getGenerator() {
        return this::getInstance;
    }

    //
    // Private behaviors
    //

    private Try<TemplateInstance> getInstance(TemplateData[] data) {
        return performInstantiation(data).map(document -> stream -> {
            try {
                transform(document, stream);
                return Try.success(Unit.UNIT);
            } catch (Exception e) {
                return Try.failure(e);
            }
        });
    }

    private Try<Document> performInstantiation(TemplateData[] data) {
        return cloneDocument(this.document).onSuccess(document -> {
            for (int i = 0; i < data.length; i++) {
                final TemplateData current = data[i];

                getElementById(document, SURNAME + i).ifPresent(node -> setEntry(node, current.getSurname(), 18));
                getElementById(document, NAME + i).ifPresent(node -> setEntry(node, current.getName().toUpperCase(), 18));
                getElementById(document, ROLE + i).ifPresent(node -> setEntry(node, getRole(current.getRole()), 16));
                getElementById(document, ROLECOLOR + i).ifPresent(node -> setAttribute(node, "style", getAttribute(node, "style").replace("$color", getColor(current.getRole()))));
                getElementById(document, QRCODE + i).ifPresent(node -> setAttribute(node, XLINK_HREF, getQRCode(current)));
            }
        });
    }

    // TODO(didier) Find a better approach
    private String getRole(String role) {
        switch (role) {
            case "Bénévole":
                return "Bénévole";
            case "Invitation communauté partenaire":
                return "Communauté Partenaire";
            case "Organisateur":
                return "Organisateur";
            case "Speaker":
                return "Speaker";
            case "Sponsor":
            case "Sponsor Toulouse Métropole":
                return "Sponsor";
            default:
                return "";
        }
    }

    // TODO(didier) Find a better approach
    private String getColor(String role) {
        switch (role) {
            case "Bénévole":
                return "#FF8888";
            case "Invitation communauté partenaire":
                return "#0000FF";
            case "Organisateur":
                return "#FF0000";
            case "Speaker":
                return "#00FF00";
            case "Sponsor":
            case "Sponsor Toulouse Métropole":
                return "#00FFFF";
            default:
                return "#FFFFFF";
        }
    }

    private void setEntry(Node node, String value, int fontInPixel) {
        final double x = Double.parseDouble(getAttribute(node, "x"));
        final double s = pixelToMM(textSizeInPixel(value, fontInPixel));
        setAttribute(node, "x", String.valueOf(x - s / 2.0));
        setContent(node, value);
    }

    private double pixelToMM(double pixel) {
        return pixel * 25.4 / 90; // (didier) BAD SMELL TO BE FIXED ASAP
    }

    private double textSizeInPixel(String s, int fontInPixel) {
        final Font font = new Font(ROBOTO, Font.PLAIN, fontInPixel);

        if (!font.getFamily().equals(ROBOTO)) {
            Logger.getAnonymousLogger().log(Level.WARNING, getMessageWhenFontIsMissing(font));
        }

        final FontMetrics metrics = new FontMetrics(font) {
        };

        return metrics.getStringBounds(s, null).getWidth();
    }

    private String getQRCode(TemplateData data) {
        return DATA_IMAGE_PNG_BASE64 + PNGQRCode.createFrom(data);
    }

    private String getMessageWhenFontIsMissing(Font font) {
        return "Text size estimated using font " +
                font.getFamily() +
                " - (1) Download Roboto or (2) Modify the template";
    }
}
