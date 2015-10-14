package co.com.importcolex.tejeduria.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    public static final String OPERARIO = "ROLE_OPERARIO";
    
    public static final String COMPRAS = "ROLE_COMPRAS";

    private AuthoritiesConstants() {
    }
}
