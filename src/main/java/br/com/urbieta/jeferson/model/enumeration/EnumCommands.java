package br.com.urbieta.jeferson.model.enumeration;

public enum EnumCommands {

    HELP(0, "Mostra os comandos implemetados no aplicativo"),

    CREATE_ROUTER(1, "Cria um roteador com os dados informados"),

    EMIT_MESSAGE(2, "Emite uma messagem com os dados informados"),

    ROUTER_LIST(3, "Lista todos os Roteadores ativos na maquina"),

    ROUTER_DETAIL(4, "Mostra as informações do roteador como a tabela de roteamento"),

    STOP_ROUTER(5, "Para a execução de um roteador");


    private Integer index;
    private String descricao;

    EnumCommands(Integer index, String descricao) {
        this.index = index;
        this.descricao = descricao;
    }

    public Integer getIndex() {
        return index;
    }

    public String getDescricao() {
        return descricao;
    }
}
