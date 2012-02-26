package se.digitman.util.resource;

/**
 *
 * @author thomas
 */
public abstract class ClassLoaderResolver {

    private static ClassLoadStrategy s_strategy; // initialized in <clinit>
    private static final int CALL_CONTEXT_OFFSET = 3; // may need to change if this class is redesigned
    private static final CallerResolver CALLER_RESOLVER; // set in <clinit>

    static {
        try {
            // This can fail if the current SecurityManager does not allow
            // RuntimePermission ("createSecurityManager"):

            CALLER_RESOLVER = new CallerResolver();
        } catch (SecurityException se) {
            throw new RuntimeException("ClassLoaderResolver: could not create CallerResolver: " + se);
        }

        s_strategy = new DefaultClassLoadStrategy();
    }

    /**
     * This method selects the best classloader instance to be used for
     * class/resource loading by whoever calls this method. The decision
     * typically involves choosing between the caller's current, thread context,
     * system, and other classloaders in the JVM and is made by the {@link ClassLoadStrategy}
     * instance established by the last call to {@link #setStrategy}.
     * 
     * @return classloader to be used by the caller ['null' indicates the
     * primordial loader]   
     */
    public static synchronized ClassLoader getClassLoader() {
        final Class caller = getCallerClass(0);
        final ClassLoadContext ctx = new ClassLoadContext(caller);

        return s_strategy.getClassLoader(ctx);
    }

    public static synchronized ClassLoadStrategy getStrategy() {
        return s_strategy;
    }

    public static synchronized ClassLoadStrategy setStrategy(final ClassLoadStrategy strategy) {
        final ClassLoadStrategy old = s_strategy;
        s_strategy = strategy;

        return old;
    }

    /**
     * A helper class to get the call context. It subclasses SecurityManager
     * to make getClassContext() accessible. An instance of CallerResolver
     * only needs to be created, not installed as an actual security
     * manager.
     */
    private static final class CallerResolver extends SecurityManager {

        @Override
        protected Class[] getClassContext() {
            return super.getClassContext();
        }
    } // End of nested class 

    /*
     * Indexes into the current method call context with a given
     * offset.
     */
    private static Class getCallerClass(final int callerOffset) {
        return CALLER_RESOLVER.getClassContext()[CALL_CONTEXT_OFFSET
                + callerOffset];
    }
}