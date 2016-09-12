package gdg.toulouse.svg;

import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.service.TemplateInstance;
import gdg.toulouse.template.service.TemplateRepository;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;

public class SVGPDFTemplateRepository implements TemplateRepository {

    private final SVGTemplateRepository svgTemplateRepository;

    public SVGPDFTemplateRepository(URL resource) throws ParserConfigurationException, SAXException, IOException {
        this.svgTemplateRepository = new SVGTemplateRepository(resource);
    }

    @Override
    public Function<Map<String, String>, Try<TemplateInstance>> getGenerator() {
        return this::getInstance;
    }

    private Try<TemplateInstance> getInstance(Map<String, String> map) {
        final Try<TemplateInstance> templateInstanceTry = svgTemplateRepository.getGenerator().apply(map);
        return templateInstanceTry.flatmap(templateInstance -> Try.success(stream -> {
            try {
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                templateInstance.dump(outputStream);

                final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                transcode(inputStream, stream);

                return Try.success(Unit.UNIT);
            } catch (Exception e) {
                return Try.failure(e);
            }
        }));
    }

    private void transcode(InputStream inputStream, OutputStream outputStream) throws TranscoderException, FileNotFoundException {
        final Transcoder transcoder = new PDFTranscoder();
        final TranscoderInput transcoderInput = new TranscoderInput(inputStream);
        final TranscoderOutput transcoderOutput = new TranscoderOutput(outputStream);
        transcoder.transcode(transcoderInput, transcoderOutput);
    }

}