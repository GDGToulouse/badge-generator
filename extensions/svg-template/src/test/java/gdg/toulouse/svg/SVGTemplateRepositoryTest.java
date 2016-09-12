package gdg.toulouse.svg;

import gdg.toulouse.data.Try;
import gdg.toulouse.template.service.TemplateInstance;
import gdg.toulouse.template.service.TemplateRepository;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class SVGTemplateRepositoryTest {

    @Test
    public void shouldReadSVGFile() throws Exception {
        final TemplateRepository template = new SVGTemplateRepository(givenSVGURL("test"));
        final Try<TemplateInstance> instance = template.getGenerator().apply(givenValues());

        assertThat(instance.isSuccess()).isTrue();
    }

    @Test
    public void shouldWriteSVGFile() throws Exception {
        final TemplateRepository template = new SVGTemplateRepository(givenSVGURL("test"));
        final Try<TemplateInstance> instance = template.getGenerator().apply(givenValues());

        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        instance.success().dump(stream);
        stream.close();

        final ByteArrayOutputStream expect = new ByteArrayOutputStream();
        DocumentUtils.transform(DocumentUtils.parse(givenSVGURL("result").openStream()), expect);
        expect.close();

        assertThat(stream.toString()).isEqualTo(expect.toString());
    }

    //
    // Private behaviors
    //

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