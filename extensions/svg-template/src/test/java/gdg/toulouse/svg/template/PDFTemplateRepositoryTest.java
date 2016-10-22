package gdg.toulouse.svg.template;

import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.data.TemplateData;
import gdg.toulouse.template.service.TemplateInstance;
import gdg.toulouse.template.service.TemplateRepository;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class PDFTemplateRepositoryTest {

    @Test
    public void shouldReadSVGFile() throws Exception {
        final TemplateRepository template = new PDFTemplateRepository(givenSVGURL("badge"));
        final Try<TemplateInstance> instance = template.getGenerator().apply(givenTemplateData());

        assertThat(instance.isSuccess()).isTrue();
    }

    @Test
    public void shouldWritePDFFile() throws Exception {
        final TemplateRepository template = new PDFTemplateRepository(givenSVGURL("badge"));
        final OutputStream stream = new FileOutputStream("/tmp/test.pdf");

        template.getGenerator().apply(givenTemplateData()).onSuccess(templateInstance -> {
            shouldDump(templateInstance, stream);
        });

        // TODO -- find the right way to test the generated PDF document
    }

    //
    // Private behaviors
    //

    private Try<Unit> shouldDump(TemplateInstance templateInstance, OutputStream outputStream) {
        return templateInstance.dump(outputStream);
    }

    private URL givenSVGURL(String name) {
        return SVGTemplateRepositoryTest.class.getResource(String.format("/%s.svg", name));
    }

    private TemplateData[] givenTemplateData() {
        return new TemplateData[] { new TemplateData("John", "Doe", "john.doe@acme.com", null,  null) };
    }
}