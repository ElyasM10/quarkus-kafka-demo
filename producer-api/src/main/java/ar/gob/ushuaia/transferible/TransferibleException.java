package ar.gob.ushuaia.transferible;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "TransferibleException",
        description = "implementamos estandar 'problem details' RFC7807",
        externalDocs = @ExternalDocumentation(
                url = "https://www.rfc-editor.org/rfc/rfc7807.html"
        )
)
public class TransferibleException {
    @Schema(description = "codigo estado http")
    private int status;
    @Schema(description = "url a pagina donde se explicaria una solucion al problema, por defecto about:blank")
    private String type;
    @Schema(description = "Mensaje para el usuario")
    private String title;
    @Schema(description = "Mensaje para nosotros")
    private String detail;
    @Schema(description = "url que generó el error")
    private String instance;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }
}
