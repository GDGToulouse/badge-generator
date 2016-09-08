package gdg.toulouse.template.service;

import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.design.annotations.ServiceInterface;

import java.io.OutputStream;

@ServiceInterface
public interface TemplateInstance {

    Try<Unit> dump(OutputStream stream);

}
