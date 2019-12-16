package cn.justl.fulcrum.vertx.boot.context;

import cn.justl.fulcrum.vertx.boot.annotation.BootBean;
import cn.justl.fulcrum.vertx.boot.definition.loader.BeanClassLoader;
import cn.justl.fulcrum.vertx.boot.excetions.DefinitionLoadException;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for DefaultBootStrapContext")
@ExtendWith(VertxExtension.class)
public class DefaultBootStrapContextTest {

    @Test
    public void contextInitTest(Vertx vertx, VertxTestContext testContext) {
        Context context = new DefaultBootStrapContext();
        context.setBeanClassLoader(C1C2C3Loader());
        context.init()
            .compose(res->{
                assertEquals(3, context.listBeanClasses().size());

                assertEquals(1, context.listBeanDefinitions().size());
                assertEquals(C2.class, context.listBeanDefinitions().get(0).getClazz());
                assertNotNull(context.getBeanDefinition(context.listBeanDefinitions().get(0).getId()));

                assertNotNull(context.getBeanHolder(context.listBeanHolders().get(0).getId()));
                assertTrue(context.getBeanHolder(context.listBeanHolders().get(0).getId()).getInstance() instanceof C2);

                testContext.completeNow();
                return Future.succeededFuture();
            }).otherwise(throwable -> {
                testContext.failNow(throwable);
                return Future.failedFuture(throwable);
        });
    }

    public static class C1{};

    @BootBean
    public static class C2{};

    public static class C3{};

    private BeanClassLoader C1C2C3Loader() {
        return new BeanClassLoader() {
            @Override
            public Set<Class> loadBeanClasses() throws DefinitionLoadException {
                return new HashSet() {{
                    add(C1.class);
                    add(C2.class);
                    add(C3.class);
                }};
            }

            @Override
            public BeanClassLoader getParentLoader() {
                return null;
            }

            @Override
            public void setParentLoader(BeanClassLoader loader) {

            }
        };
    }

}
