package br.com.urbieta.jeferson.model.enumeration;

public enum EnumCommands {

    HELP(0, "help", "Mostra os comandos implemetados no aplicativo"),

    EMIT_MESSAGE(1, "emissor", "Emite uma messagem com os dados informados"),

    CREATE_ROUTER(2, "roteador", "Cria um roteador com os dados informados"),

    ROUTER_LIST(3, "list", "Lista todos os Roteadores ativos na maquina"),

    ROUTER_DETAIL(4, "detail", "Mostra as informações do roteador como a tabela de roteamento"),

    STOP_ROUTER(5, "stop", "Para a execução de um roteador");

    private Integer index;
    private String nome;
    private String descricao;

    EnumCommands(Integer index, String nome, String descricao) {
        this.index = index;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getIndex() {
        return index;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
