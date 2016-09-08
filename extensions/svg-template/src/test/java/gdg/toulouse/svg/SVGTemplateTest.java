package gdg.toulouse.svg;

import gdg.toulouse.data.Try;
import gdg.toulouse.template.service.TemplateInstance;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class SVGTemplateTest {

    @Test
    public void shouldReadSVGFile() throws Exception {
        final SVGTemplate template = new SVGTemplate(givenSVGURL("test"));
        final Try<TemplateInstance> instance = template.getInstance(givenValues());

        assertThat(instance.isSuccess()).isTrue();
    }

    @Test
    public void shouldWriteSVGFile() throws Exception {
        final SVGTemplate template = new SVGTemplate(givenSVGURL("test"));
        final Try<TemplateInstance> instance = template.getInstance(givenValues());

        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        instance.success().dump(stream);
        stream.close();

        final ByteArrayOutputStream expect = new ByteArrayOutputStream();
        DomUtils.transform(DomUtils.parse(givenSVGURL("result").openStream()), expect);
        expect.close();

        assertThat(stream.toString()).isEqualTo(expect.toString());
    }

    //
    // Private behaviors
    //

    private URL givenSVGURL(String name) {
        return SVGTemplateTest.class.getResource(String.format("/%s.svg", name));
    }

    private HashMap<String, String> givenValues() {
        return new HashMap<String, String>() {{
            this.put("$surname", "John");
            this.put("$name", "Doe");
        }};
    }


}