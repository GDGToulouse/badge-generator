package gdg.toulouse.svg;

import gdg.toulouse.data.Try;
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

class DocumentUtils {

    private static final String ID = "id";

    static Document parse(File file) throws ParserConfigurationException, IOException, SAXException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return parse(stream);
        }
    }

    static Document parse(InputStream stream) throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
    }

    static Try<Document> clone(Document document) {
        return Try.success(Document.class.cast(document.cloneNode(true)));
    }

    static void transform(Document document, OutputStream stream) throws TransformerException {
        TransformerFactory.newInstance().newTransformer().
                transform(new DOMSource(document), new StreamResult(stream));
    }

    static void removeChilds(Node element) {
        while (element.hasChildNodes()) {
            element.removeChild(element.getFirstChild());
        }
    }

    static Optional<Node> getElementById(Node node, String value) {
        return getElement(node, ID, value);
    }

    //
    // Private behaviors
    //

    private static Optional<Node> getElement(Node node, String name, String value) {
        if (hasTheRequiredId(node, name, value)) {
            return Optional.of(node);
        }

        return getElementByIdFromChilds(node.getChildNodes(), name, value);
    }

    private static Optional<Node> getElementByIdFromChilds(NodeList childNodes, String name, String value) {

        for (int i = 0; i < childNodes.getLength(); i++) {
            final Optional<Node> byId = getElement(childNodes.item(i), name, value);

            if (byId.isPresent()) {
                return byId;
            }
        }

        return Optional.empty();
    }

    private static boolean hasTheRequiredId(Node node, String name, String value) {
        return value.equals(getAttribute(node, name));
    }

    private static String getAttribute(Node node, String name) {
        if (node.getAttributes() == null) {
            return "";
        }

        final Node id = node.getAttributes().getNamedItem(name);

        if (id == null) {
            return "";
        }

        return id.getNodeValue();
    }

}