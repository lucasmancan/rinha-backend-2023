package app;

import io.jooby.Jooby;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

public class PessoaRouter extends Jooby {
    {
        PessoaService service = new PessoaServiceJooqImpl(require(DataSource.class));

        get("/contagem-pessoas", (ctx) -> new ContagemPessoasResponse(service.contarPessoas()));

        path("/pessoas", () -> {
            get("/{id}", (ctx -> {

                var optionalPessoa = service.buscarPorId(UUID.fromString(ctx.path("id").value()));

                if (optionalPessoa.isEmpty()) {
                    ctx.setResponseCode(404);
                    return "";
                }

                return optionalPessoa.get();
            }));

            get("/", (ctx -> {

                var termo = ctx.query("t").value();

                if (termo == null || termo.isBlank()) {
                    ctx.setResponseCode(400);
                    return "";
                }

                return service.buscarPorTermo(termo);
            }));

            post("/", (ctx -> {

                var pessoaRequest = ctx.body(Pessoa.class);

                if (pessoaRequest.apelido() == null || pessoaRequest.apelido().isBlank() || pessoaRequest.apelido().length() > 32
                        || pessoaRequest.nome() == null || pessoaRequest.nome().isBlank() || pessoaRequest.nome().length() > 100 || invalidStack(pessoaRequest.stack())) {
                    ctx.setResponseCode(400);
                    return "";
                }


                var pessoa = service.salvar(pessoaRequest);

                ctx.setResponseCode(201);
                return pessoa;
            }));
        });

    }

    private boolean invalidStack(List<String> stack) {
        if (stack == null) {
            return false;
        }

        for (String s : stack) {
            if (s.length() > 32) {
                return true;
            }
        }

        return false;
    }
}
