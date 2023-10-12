package app;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jooby.Jooby;
import io.jooby.hikari.HikariModule;
import io.jooby.jackson.JacksonModule;
import io.jooby.netty.NettyServer;
import io.jooby.redis.RedisModule;


public class App extends Jooby {

    {

        install(new JacksonModule());
        install(new NettyServer());
        install(new HikariModule("db"));
        install(new RedisModule());
        install(PessoaRouter::new);
    }

    public static void main(final String[] args) {
        System.setProperty("org.jooq.no-logo", "true");
        System.setProperty("org.jooq.no-tips", "true");

        runApp(args, App::new);
    }
}
