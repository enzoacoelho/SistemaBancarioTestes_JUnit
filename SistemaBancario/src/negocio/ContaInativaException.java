package negocio;

public class ContaInativaException extends Exception {

    public static final String MSG_CONTA_INATIVA = 
        "A transferência não pode ser realizada pois uma das contas está inativa.";

    public ContaInativaException(String msg) {
        super(msg);
    }
}