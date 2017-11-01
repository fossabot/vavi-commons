/*
 * Copyright (c) 2016 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.net.www.content;

import java.net.ContentHandler;
import java.util.ServiceLoader;


/**
 * ContentHandlerUtil. 
 * <p>
 * Prepare to be loaded handlers specified in <code>META-INF/services/java.net.ContentHandler</code>.
 * </p>
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2016/03/13 umjammer initial version <br>
 */
public class ContentHandlerUtil {

    private ContentHandlerUtil() {
    }

    /**
     * @see "https://docs.oracle.com/javase/8/docs/api/java/net/ContentHandler.html"
     */
    public static void loadService() {
        ServiceLoader<ContentHandler> loader = ServiceLoader.load(ContentHandler.class);
loader.forEach(System.err::println);
        StringBuilder packages = new StringBuilder(System.getProperty("java.content.handler.pkgs", ""));
System.err.println("java.content.handler.pkgs: before: " + packages);
        for (ContentHandler handler : loader) {
            String packageName = handler.getClass().getPackage().getName();
            String superPackageName = packageName.substring(0, packageName.lastIndexOf('.'));
            if (packages.indexOf(superPackageName) < 0) {
                if (packages.length() != 0) {
                    packages.append('|');
                }
                packages.append(superPackageName);
            }
        }
System.err.println("java.content.handler.pkgs: after: " + packages);
        System.setProperty("java.content.handler.pkgs", packages.toString());
    }
}

/* */
