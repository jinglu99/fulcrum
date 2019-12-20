package cn.justl.fulcrum.vertx.boot;

import cn.justl.fulcrum.vertx.boot.annotation.VertxScan;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.context.DefaultBootStrapContext;
import cn.justl.fulcrum.vertx.boot.definition.loader.BeanClassLoader;
import cn.justl.fulcrum.vertx.boot.definition.loader.ClassPathBeanClassLoader;
import cn.justl.fulcrum.vertx.boot.definition.loader.DefaultBeanClassLoader;
import cn.justl.fulcrum.vertx.boot.definition.loader.VertxScanBeanClassLoader;
import cn.justl.fulcrum.vertx.boot.excetions.DefinitionLoadException;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootException;
import cn.justl.fulcrum.vertx.boot.helper.ClassHelper;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import java.util.Arrays;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @Date : 2019/11/23
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc : Vertx-Boot start here.
 */
public class VertxBootStrap {

    private static final Logger logger = LoggerFactory.getLogger(VertxBootStrap.class);

    private static Context context;

    /**
     * Initialize Vertx-Boot with given {@link VertxScan} annotated class.
     */
    public static synchronized Future<Void> run(Vertx vertx, Class clazz) {
        return run(vertx, new InitProps(), clazz);
    }

    public static synchronized Future<Void> run(Vertx vertx, InitProps props, Class clazz) {
        return Future.future(promise -> {
            try {
                BootStrapConfig config = new BootStrapConfig();
                config.setPromise(promise);
                config.setVertx(vertx);
                config.setProps(props);
                config.setVertxScanClazz(clazz);
                config.setVertxScanClazzRequired(true);
                doRun(config)
                    .compose(res -> {
                        promise.complete();
                        return Future.succeededFuture();
                    }).otherwise(throwable -> {
                    logger.error("Fail to start Vertx-Boot!", throwable);
                    promise.fail(throwable);
                    return Future.failedFuture(throwable);
                });
            } catch (Throwable e) {
                logger.error("VertX-Boot initializing failed", e);
                promise.fail(e);
            }
        });
    }

    /**
     * Initialize Vertx-Boot with given packages.
     */
    public static synchronized Future<Void> run(Vertx vertx, String packages) {
        return run(vertx, new InitProps(), packages);
    }

    public static synchronized Future<Void> run(Vertx vertx, InitProps props, String packages) {
        return Future.future(promise -> {
            try {
                BootStrapConfig config = new BootStrapConfig();
                config.setPromise(promise);
                config.setVertx(vertx);
                config.setProps(props);
                config.setPackages(packages.split(","));
                config.setPackagesRequired(true);
                doRun(config)
                    .compose(res -> {
                        promise.complete();
                        return Future.succeededFuture();
                    }).otherwise(throwable -> {
                    logger.error("Fail to start Vertx-Boot!", throwable);
                    promise.fail(throwable);
                    return Future.failedFuture(throwable);
                });
            } catch (Throwable e) {
                logger.error("VertX-Boot initializing failed", e);
                promise.fail(e);
            }
        });
    }

    /**
     * Initialize Vertx-Boot with given Verticle classes.
     */
    public static synchronized Future<Void> runWithBeans(Vertx vertx, Class... verticles) {
        return runWithBeans(vertx, new InitProps(), verticles);
    }

    public static synchronized Future<Void> runWithBeans(Vertx vertx, InitProps props,
        Class... verticles) {
        return Future.future(promise -> {
            try {
                BootStrapConfig config = new BootStrapConfig();
                config.setPromise(promise);
                config.setVertx(vertx);
                config.setProps(props);
                config.setBeans(verticles);
                config.setBeansRequired(true);

                doRun(config)
                    .compose(res -> {
                        promise.complete();
                        return Future.succeededFuture();
                    }).otherwise(throwable -> {
                    logger.error("Fail to start Vertx-Boot!", throwable);
                    promise.fail(throwable);
                    return Future.failedFuture(throwable);
                });
            } catch (Throwable e) {
                logger.error("VertX-Boot initializing failed", e);
                promise.fail(e);
            }
        });
    }

    public static synchronized Future<Void> close() {
        return context.close()
            .compose(res -> {
                context = null;
                return Future.succeededFuture();
            });
    }

    public static Context getContext() {
        return context;
    }

    private static Future<Void> checkHandler(Void obj) {
        return Future.future(promise -> {
            try {
                if (context != null) {
                    logger.warn(
                        "VertxBootStrap.run() has been called, a new context will be created and the old one will be closed!");
                    context.close();
                }
                context = new DefaultBootStrapContext();
                logger.info("Vertx-Boot Context created!");
                promise.complete();
            } catch (Exception e) {
                promise.fail(new VertxBootException("Failed to create new Vertx Boot context", e));
            }
        });

    }


    private static Future<Void> doRun(BootStrapConfig config) {
        return printLogo()
            .compose(VertxBootStrap::checkHandler)
            .compose(aVoid -> prepareContext(config))
            .compose(res -> context.init());
    }

    private static Future<Void> prepareContext(BootStrapConfig config) {
        return Future.future(promise->{
            try {
                context.setVertx(config.getVertx());
                context.setBeanClassLoader(createBeanClassLoader(config));
                promise.complete();
            } catch (Exception e) {
                promise.fail(e);
            }
        });
    }

    private static Future<Void> printLogo() {
        return Future.future(promise -> {
            try {
                InputStream in = ClassHelper.getClassLoader()
                    .getResourceAsStream(Constants.LOGO_PATH);
                System.out.println(new BufferedReader(new InputStreamReader(in))
                    .lines().collect(Collectors.joining(System.lineSeparator())));
                logger.info("Start to initialize Vertx-Boot Context...");

                promise.complete();
            } catch (Exception e) {
                logger.error("Failed to initialize Vertx-Boot Context!", e);
                promise.fail(new VertxBootException("Failed to initialize Vertx-Boot Context!", e));
            }
        });
    }

    private static BeanClassLoader createBeanClassLoader(BootStrapConfig config)
        throws DefinitionLoadException {
        BeanClassLoader loader = new DefaultBeanClassLoader(config.getBeans());

        if (config.getVertxScanClazz() != null) {
            loader = new VertxScanBeanClassLoader(config.getVertxScanClazz());
        }

        if (config.getPackages() != null && config.getPackages().length > 0) {
            loader = new ClassPathBeanClassLoader(Arrays.asList(config.getPackages()), loader);
        }
        return loader;
    }

    private static class BootStrapConfig {

        private Vertx vertx;
        private InitProps props;

        private Class vertxScanClazz;
        private boolean vertxScanClazzRequired;

        private String[] packages;
        private boolean packagesRequired;

        private Class[] beans;
        private boolean beansRequired;

        private Promise promise;


        public Vertx getVertx() {
            return vertx;
        }

        public void setVertx(Vertx vertx) {
            this.vertx = vertx;
        }

        public InitProps getProps() {
            return props;
        }

        public void setProps(InitProps props) {
            this.props = props;
        }

        public Class getVertxScanClazz() {
            return vertxScanClazz;
        }

        public void setVertxScanClazz(Class verticleScanClazz) {
            vertxScanClazz = verticleScanClazz;
        }

        public boolean isVertxScanClazzRequired() {
            return vertxScanClazzRequired;
        }

        public String[] getPackages() {
            return packages;
        }

        public void setPackages(String[] packages) {
            this.packages = packages;
        }

        public boolean isPackagesRequired() {
            return packagesRequired;
        }

        public void setPackagesRequired(boolean packagesRequired) {
            this.packagesRequired = packagesRequired;
        }

        public void setVertxScanClazzRequired(boolean vertxScanClazzRequired) {
            this.vertxScanClazzRequired = vertxScanClazzRequired;
        }

        public Class[] getBeans() {
            return beans;
        }

        public void setBeans(Class[] beans) {
            this.beans = beans;
        }

        public boolean isBeansRequired() {
            return beansRequired;
        }

        public void setBeansRequired(boolean beansRequired) {
            this.beansRequired = beansRequired;
        }

        public Promise getPromise() {
            return promise;
        }

        public void setPromise(Promise promise) {
            this.promise = promise;
        }

        @Override
        public String toString() {
            return "BootStrapConfig{" +
                "vertx=" + vertx +
                ", props=" + props +
                ", vertxScanClazz=" + vertxScanClazz +
                ", vertxScanClazzRequired=" + vertxScanClazzRequired +
                ", packages=" + Arrays.toString(packages) +
                ", packagesRequired=" + packagesRequired +
                ", beans=" + Arrays.toString(beans) +
                ", beansRequired=" + beansRequired +
                ", promise=" + promise +
                '}';
        }
    }
}
