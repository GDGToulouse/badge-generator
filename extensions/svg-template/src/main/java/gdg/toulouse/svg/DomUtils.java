package gdg.toulouse.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public class DomUtils {

    private static final String ID = "id";

    public static Document parse(File file) throws ParserConfigurationException, IOException, SAXException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return parse(stream);
        }
    }

    public static Document parse(InputStream stream) throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
    }

    public static void transform(Document document, OutputStream stream) throws TransformerException {
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(stream));
    }

    public static void removeChilds(Node element) {
        while (element.hasChildNodes()) {
            element.removeChild(element.getFirstChild());
        }
    }

    public static Optional<Node> getElementById(Node node, String value) {
        if (hasTheRequiredId(node, value)) {
            return Optional.of(node);
        }

        return getElementByIdFromChild(value, node.getChildNodes());
    }

    //
    // Private behaviors
    //

    private static Optional<Node> getElementByIdFromChild(String value, NodeList childNodes) {

        for (int i = 0; i < childNodes.getLength(); i++) {
            final Optional<Node> byId = getElementById(childNodes.item(i), value);

            if (byId.isPresent()) {
                return byId;
            }
        }

        return Optional.empty();
    }

    private static boolean hasTheRequiredId(Node node, String value) {
        return value.equals(getAttributeId(node));
    }

    private static String getAttributeId(Node node) {
        if (node.getAttributes() == null) {
            return "";
        }

        final Node id = node.getAttributes().getNamedItem(ID);

        if (id == null) {
            return "";
        }

        return id.getNodeValue();
    }

}
