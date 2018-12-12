package config;

import java.util.Optional;

public enum EnvironmentType {

    DEV("dev", "dev", "MobileWeb-JWT", "https", null, "mobile-${locale}.ebs.ecomp.com", null, "devci-mobile-stats-${region}.ebs.ecomp.com"),
    STATIC_2("st2", "st2", "mobile", "https", null, "ngsp-st2-${locale}.rs-online.com", null, "st2-mobile-stats-${region}.rs-online.com"),
    STATIC_1("st1", "st1", "mobile", "https", null, "st1-${locale}.rs-online.com", "st1rsonline.${locale}", "st1-mobile-stats-${region}.rs-online.com"),
    PRODUCTION("", "prod", "mobile", "https", null, "${locale}.rs-online.com", "rsonline.${locale}", "mobile-stats-${region}.rs-online.com");

    private final String label;
    private final String propertyFolder;
    private final String context;
    private final String scheme;
    private final String port;
    private final String domain;
    private final String domainAlt;
    private final String mobileStatsDomain;

    EnvironmentType(final String label, final String propertyFolder, final String context, final String scheme,
                    final String port, final String domain, final String domainAlt, final String mobileStatsDomain) {
        this.label = label;
        this.propertyFolder = propertyFolder;
        this.context = context;
        this.scheme = scheme;
        this.port = port;
        this.domain = domain;
        this.domainAlt = domainAlt;
        this.mobileStatsDomain = mobileStatsDomain;
    }






//    public static EnvironmentType getType(final String env) {
//        for(EnvironmentType type : EnvironmentType.values()) {
//            if(type.getLabel().equals(env)) {
//                return type;
//            }
//        }
//        return null;
//    }


}