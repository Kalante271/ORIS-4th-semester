package ru.itis.dis403.lab_01.di.config;

import ru.itis.dis403.lab_01.di.annotation.Controller;
import ru.itis.dis403.lab_01.di.annotation.GetMapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {

    private String scanPath = "ru.itis.dis403.lab_01";

    private Map<Class<?>, Object> components = new HashMap<>();

    private Map<String, Method> methodsMapping = new HashMap<>();

    public Context() {
        scanComponent();
    }

    public Object getComponent(Class clazz) {
        return components.get(clazz);
    }

    public Method getMethodByPath(String path) {
        return methodsMapping.get(path);
    }

    private void scanComponent() {
        List<Class<?>> classes = PathScan.find(scanPath);

        // Создание экземпляров компонент
        // перебираем список классов
        // находим конструктор с аргументами, если такого нет - создаем экземпляр
        // размещаем в components, удаляем из списка
        // если есть конструктор с аргументами (только компоненты)
        // пытаемся получить из components объекты - аргументы
        // если полный набор - создаем экземпляр, иначе пропускаем итерацию

        int countClasses = classes.size();

        while (countClasses > 0) {
            // Перебираем классы компоненты
            objectNotFound:
            for (Class c : classes) {

                if (components.get(c) != null) continue;

                // Берем первый попавшийся конструктор
                Constructor constructor = c.getConstructors()[0];
                // Извлекаем типы аргументов конструктора
                Class[] types = constructor.getParameterTypes();
                // Пытаемся найти готовые компоненты по аргументы
                Object[] args = new Object[types.length];
                for (int i = 0; i < types.length; i++) {
                    args[i] = components.get(types[i]);
                    if (args[i] == null) {
                        continue objectNotFound;
                    }
                }

                try {
                    Object o = constructor.newInstance(args);
                    components.put(c, o);
                    countClasses--;
                    System.out.println(c + " добавлен");
                } catch (Exception e) {
                    System.out.println(e.getMessage());;
                }
            }
        }
        handleControllers();
    }

    private void handleControllers() {
        for (Class<?> clazz : components.keySet()) {

            if(!clazz.isAnnotationPresent(Controller.class)) {
                continue;
            }

            for (Method method : clazz.getDeclaredMethods()) {

                if(method.isAnnotationPresent(GetMapping.class)) {
                    GetMapping annotation = method.getAnnotation(GetMapping.class);
                    String path = annotation.value();

                    methodsMapping.put(path, method);
                }
            }
        }
    }
}
