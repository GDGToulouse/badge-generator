package gdg.toulouse.svg.command;

import gdg.toulouse.command.command.TemplateCommand;
import gdg.toulouse.data.Try;
import gdg.toulouse.svg.template.PDFTemplateRepository;
import gdg.toulouse.template.service.TemplateRepository;

import java.net.URI;

public class PDFSVGTemplateCommand implements TemplateCommand {

    @Override
    public Try<TemplateRepository> create(String parameter) {
        try {
            return Try.success(new PDFTemplateRepository(new URI(parameter).toURL()));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

}
