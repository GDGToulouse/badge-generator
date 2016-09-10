package gdg.toulouse.svg;

import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.service.TemplateInstance;
import gdg.toulouse.template.service.TemplateRepository;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class SVGPDFTemplateRepositoryTest {

    @Test
    public void shouldReadSVGFile() throws Exception {
        final TemplateRepository template = new SVGPDFTemplateRepository(givenSVGURL("test"));
        final Try<TemplateInstance> instance = template.getGenerator().apply(givenValues());

        assertThat(instance.isSuccess()).isTrue();
    }

    @Test
    public void shouldWritePDFFile() throws Exception {
        final TemplateRepository template = new SVGPDFTemplateRepository(givenSVGURL("badge"));
        final Try<TemplateInstance> instance = template.getGenerator().apply(givenValues());

        instance.onSuccess(templateInstance -> {
            final File file = new File("/tmp/test.pdf");
            shouldDump(templateInstance, file).onFailure(throwable -> file.delete());
        });
    }

    //
    // Private behaviors
    //

    private Try<Unit> shouldDump(TemplateInstance templateInstance, File file) {
        Try<Unit> dump;
        try (OutputStream outputStream = new FileOutputStream(file)) {
            dump = templateInstance.dump(outputStream);
        } catch (IOException e) {
            dump = Try.failure(e);
        }
        return dump;
    }

    private URL givenSVGURL(String name) {
        return SVGTemplateRepositoryTest.class.getResource(String.format("/%s.svg", name));
    }

    private HashMap<String, String> givenValues() {
        return new HashMap<String, String>() {{
            this.put("$surname", "John");
            this.put("$name", "Doe");
        }};
    }

}