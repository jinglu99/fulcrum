package cn.justl.fulcrum.vertx.boot;

import cn.justl.fulcrum.vertx.boot.annotation.VertxScan;
import cn.justl.fulcrum.vertx.boot.context.BootStrapContext;
import cn.justl.fulcrum.vertx.boot.context.DefaultBootStrapContext;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootException;
import cn.justl.fulcrum.vertx.boot.helper.ClassHelper;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import java.util.Arrays;
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

    private static BootStrapHandler handler;

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
                config.setVerticleScanClazz(clazz);
                config.setVerticleScanClazzRequired(true);
                doRun(config);
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
                doRun(config);
            } catch (Throwable e) {
                logger.error("VertX-Boot initializing failed", e);
                promise.fail(e);
            }
        });
    }

    /**
     * Initialize Vertx-Boot with given Verticle classes.
     */
    public static synchronized Future<Void> runWithVerticles(Vertx vertx, Class... verticles) {
        return runWithVerticles(vertx, new InitProps(), verticles);
    }

    public static synchronized Future<Void> runWithVerticles(Vertx vertx, InitProps props, Class... verticles) {
        return Future.future(promise -> {
            try {
                BootStrapConfig config = new BootStrapConfig();
                config.setPromise(promise);
                config.setVertx(vertx);
                config.setProps(props);
                config.setVerticles(verticles);
                config.setVerticlesRequired(true);
                doRun(config);
            } catch (Throwable e) {
                logger.error("VertX-Boot initializing failed", e);
                promise.fail(e);
            }
        });
    }

    public static Future<Void> close() {
        return Future.future(promise -> {
            try {
                logger.info("Closing VertX-Boot...");
                handler.close();
                handler = null;
                logger.info("Closing VertX-Boot successfully.");
                promise.complete();
            } catch (VertxBootException e) {
                logger.error("VertX-Boot closing failed", e);
                promise.fail(e);
            }
        });
    }

    public static BootStrapContext getContext() {
        if (handler == null) {
            return null;
        } else {
            return handler.getContext();
        }
    }

    private static void checkHandler(Promise promise) throws VertxBootException {
        if (handler != null) {
            logger.warn(
                "VertxBootStrap.run() has been called, a new context will be created and the old one will be closed!");
            handler.close();
        }
        handler = new DefaultBootStrapContext(promise);
    }

    private static void scanVerticles(BootStrapConfig config) throws VertxBootException {
        scanByVerticleScanIfNeeded(config);

        scanByGivenPackagesIfNeeded(config);

        scanByGivenVerticles(config);
    }

    private static void scanByVerticleScanIfNeeded(BootStrapConfig config)
        throws VertxBootException {
        Class clazz;
        if ((clazz = config.getVerticleScanClazz()) != null) {
            VertxScan verticleScan = (VertxScan) clazz.getAnnotation(VertxScan.class);
            String[] packages =
                verticleScan.value().length == 0 ? new String[]{clazz.getPackage().getName()}
                    : verticleScan.value();
            handler.scanVerticles(packages);
        } else if (clazz == null && config.isVerticleScanClazzRequired()) {
            logger.error(
                "VerticleScan annotation not found or no package declared in VerticleScan in class "
                    + clazz.getName());
            throw new VertxBootException(
                "VerticleScan annotation not found or no package declared in VerticleScan in class "
                    + clazz.getName());

        }
    }

    private static void scanByGivenPackagesIfNeeded(BootStrapConfig config)
        throws VertxBootException {
        String[] packages;
        if ((packages = config.getPackages()) != null && packages.length != 0) {
            handler.scanVerticles(packages);
        } else if (config.isPackagesRequired()) {
            logger.error("Packages not provided for Vertx-Boot!");
            throw new VertxBootException("packages not provided for Vertx-Boot!");
        }
    }

    private static void scanByGivenVerticles(BootStrapConfig config) throws VertxBootException {
        Class[] classes;
        if ((classes = config.getVerticles()) != null && classes.length > 0) {
            handler.scanVerticles(classes);
        } else if (config.isVerticlesRequired()) {
            logger.error("Verticles not provided for Vertx-Boot!");
            throw new VertxBootException("Verticles not provided for Vertx-Boot!");
        }
    }

    private static void doRun(BootStrapConfig config) throws VertxBootException {
        printLogo();

        checkHandler(config.getPromise());

        handler.loadProperties(config.props.getProperties());

        handler.setVertx(config.getVertx());

        scanVerticles(config);

        handler.instantiateVerticles();

        handler.initializeVerticles();
    }

    private static void printLogo() {
        InputStream in = ClassHelper.getClassLoader().getResourceAsStream(Constants.LOGO_PATH);
        System.out.println(new BufferedReader(new InputStreamReader(in))
            .lines().collect(Collectors.joining(System.lineSeparator())));
    }

    private static class BootStrapConfig {

        private Vertx vertx;
        private InitProps props;

        private Class VerticleScanClazz;
        private boolean VerticleScanClazzRequired;

        private String[] packages;
        private boolean packagesRequired;

        private Class[] verticles;
        private boolean verticlesRequired;

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

        public Class getVerticleScanClazz() {
            return VerticleScanClazz;
        }

        public void setVerticleScanClazz(Class verticleScanClazz) {
            VerticleScanClazz = verticleScanClazz;
        }

        public boolean isVerticleScanClazzRequired() {
            return VerticleScanClazzRequired;
        }

        public void setVerticleScanClazzRequired(boolean verticleScanClazzRequired) {
            VerticleScanClazzRequired = verticleScanClazzRequired;
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

        public Class[] getVerticles() {
            return verticles;
        }

        public void setVerticles(Class[] verticles) {
            this.verticles = verticles;
        }

        public boolean isVerticlesRequired() {
            return verticlesRequired;
        }

        public void setVerticlesRequired(boolean verticlesRequired) {
            this.verticlesRequired = verticlesRequired;
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
                ", VerticleScanClazz=" + VerticleScanClazz +
                ", VerticleScanClazzRequired=" + VerticleScanClazzRequired +
                ", packages=" + Arrays.toString(packages) +
                ", packagesRequired=" + packagesRequired +
                ", verticles=" + Arrays.toString(verticles) +
                ", verticlesRequired=" + verticlesRequired +
                '}';
        }
    }
}
