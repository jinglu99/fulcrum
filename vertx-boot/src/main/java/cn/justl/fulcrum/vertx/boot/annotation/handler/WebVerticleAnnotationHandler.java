//package cn.justl.fulcrum.vertx.boot.annotation.handler;
//
//import cn.justl.fulcrum.vertx.boot.VerticleHolder;
//import cn.justl.fulcrum.vertx.boot.verticle.annotations.HttpRouter;
//import cn.justl.fulcrum.vertx.boot.web.WebVerticle;
//import cn.justl.fulcrum.vertx.boot.context.BootStrapContext;
//import cn.justl.fulcrum.vertx.boot.definition.DefaultBeanDefinition;
//import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
//import cn.justl.fulcrum.vertx.boot.excetions.AnnotationScannerException;
//import cn.justl.fulcrum.vertx.boot.excetions.BeanStartException;
//import cn.justl.fulcrum.vertx.boot.web.WebConstants;
//import io.vertx.core.http.HttpMethod;
//import io.vertx.ext.web.Router;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * @Date : 2019/12/2
// * @Author : Jingl.Wang [jingl.wang123@gmail.com]
// * @Desc : An implementation of {@link AnnotationHandler} to handle {@link WebVerticle} annotation.
// */
//public class WebVerticleAnnotationHandler extends AbstractAnnotationHandler implements VerticleParsable {
//
//    private static final Logger logger = LoggerFactory.getLogger(WebVerticleAnnotationHandler.class);
//
//    @Override
//    public BeanDefinition parseVerticle(Class clazz) throws AnnotationScannerException {
//        WebVerticle verticle = (WebVerticle) clazz.getDeclaredAnnotation(WebVerticle.class);
//        BeanDefinition definition = new DefaultBeanDefinition();
//        String id =
//            verticle.value().equals("") ? clazz.getSimpleName().substring(0, 1).toLowerCase()
//                + clazz.getSimpleName().substring(1) : verticle.value();
//        definition.setId(id);
//        definition.setClazz(clazz);
//        return definition;
//    }
//
//    @Override
//    <T> void doStart(BootStrapContext context, BeanDefinition<T> verticleDefinition,
//        VerticleHolder<T> verticleHolder) throws BeanStartException {
//
//        HttpRouter rootRoute = verticleDefinition.getClazz()
//            .getDeclaredAnnotation(HttpRouter.class);
//
//        Stream.of(verticleDefinition.getClazz()
//            .getDeclaredMethods())
//            .filter(method -> method.getDeclaredAnnotation(HttpRouter.class) != null)
//            .map(handler -> {
//                HttpRouter httpRouter = handler.getDeclaredAnnotation(HttpRouter.class);
//                RouterHandler rh = new RouterHandler();
//                handler.setAccessible(true);
//                rh.setHandler(handler);
//                rh.setPath(httpRouter.value());
//                rh.setMethod(httpRouter.method());
//                rh.setPort(httpRouter.port() > 0 ? httpRouter.port()
//                    : rootRoute != null && rootRoute.port() > 0 ? rootRoute.port() : WebConstants.DEFAULT_HTTP_PORT);
//
//                return rh;
//            }).collect(Collectors.groupingBy(RouterHandler::getPort))
//            .entrySet()
//            .stream()
//            .forEach(routerHandlers -> {
//                List<RouterHandler> handlers = routerHandlers.getValue();
//
//                Router subRouter = Router.router(context.getVertx());
//                handlers.forEach(handler -> {
//                    subRouter.route(handler.path)
//                        .method(handler.method)
//                        .handler(req -> {
//                            try {
//                                handler.handler.invoke(verticleHolder.getVerticle(), req);
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                                throw new RuntimeException(e);
//                            } catch (InvocationTargetException e) {
//                                e.printStackTrace();
//                                throw new RuntimeException(e.getCause());
//                            }
//                        });
//                    String path = handler.path.replace("//", "/");
//                    if (rootRoute != null) {
//                        path = (rootRoute.value() + "/" + handler.path).replace("//", "/");
//                    }
//                    logger.info("http service {} mounted at port {}", path, routerHandlers.getKey());
//                });
//
//                Router router = subRouter;
//                if (rootRoute != null && !rootRoute.value().equals("/")) {
//                    router = Router.router(context.getVertx()).mountSubRouter(rootRoute.value(), subRouter);
//                }
//
//                context.getVertx()
//                    .createHttpServer()
//                    .requestHandler(router)
//                    .listen(routerHandlers.getKey());
//
//            });
//    }
//
//    @Override
//    void doClose(BootStrapContext context, BeanDefinition verticleDefinition,
//        VerticleHolder verticleHolder) {
//
//    }
//
//    @Override
//    public boolean isTargetBean(Class clazz) {
//        return clazz.getDeclaredAnnotation(WebVerticle.class) != null;
//    }
//
//    private static class RouterHandler {
//
//        private HttpMethod method;
//        private String path;
//        private int port;
//        private Method handler;
//
//        public HttpMethod getMethod() {
//            return method;
//        }
//
//        public void setMethod(HttpMethod method) {
//            this.method = method;
//        }
//
//        public String getPath() {
//            return path;
//        }
//
//        public void setPath(String path) {
//            this.path = path;
//        }
//
//        public int getPort() {
//            return port;
//        }
//
//        public void setPort(int port) {
//            this.port = port;
//        }
//
//        public Method getHandler() {
//            return handler;
//        }
//
//        public void setHandler(Method handler) {
//            this.handler = handler;
//        }
//    }
//
//}
