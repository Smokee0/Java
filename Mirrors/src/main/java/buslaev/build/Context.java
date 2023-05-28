package buslaev.build;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.lang.reflect.Field;


public class Context {
    private static final Map<Class<?>, Object> instances = new HashMap<>();

    public static void injectDependencies() throws Exception {
        makeInstances();
        inject();
    }

    private static void makeInstances() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        instances.clear();
        Reflections reflections = new Reflections("buslaev");
        Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> c:components) {
            instances.put(c, c.getDeclaredConstructor().newInstance());
        }
    }

    private static void inject() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("buslaev"))
                .setScanners(new FieldAnnotationsScanner()));
        Set<Field> fields = reflections.getFieldsAnnotatedWith(Autowired.class);
        for (Field field: fields) {
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            try {
                Object object = instances.get(field.getType());
                Class<?> cl = field.getDeclaringClass();
                Object instanceToInject = instances.get(cl);
                field.set(instanceToInject, object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(isAccessible);
        }
    }
}
