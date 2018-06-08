package br.com.urbieta.jeferson.exception;

public class ApplicationException extends Exception {

    private String[] params;

    /**
     * Construtor default.
     */
    public ApplicationException() {
        super();
    }

    /**
     * Construtor com uma mensagem e o problema.
     */
    public ApplicationException(String msg, Throwable arg1) {
        super(msg, arg1);
    }

    /**
     * Construtor que recebe um conjunto de paramentros.
     */
    public ApplicationException(String... params) {
        super();
        this.params = params;
    }

    /**
     * Construtor com uma mensagem, o problema e um conjunto de parametros.
     */
    public ApplicationException(String msg, Throwable arg1, String... params) {
        super(msg, arg1);
        this.params = params;
    }

    /**
     * Construtor apenas com uma mensagem.
     */
    public ApplicationException(String msg) {
        super(msg);
    }

    /**
     * Construtor apenas com o problema.
     */
    public ApplicationException(Throwable msg) {
        super(msg);
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String... params) {
        this.params = params;
    }

}

