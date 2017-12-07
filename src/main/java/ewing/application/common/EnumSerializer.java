package ewing.application.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 序列化枚举工具类，可通过传入Function实现国际化转换。
 */
public class EnumSerializer {

    private EnumSerializer() {
    }

    public static <E extends Enum<E>> String serialize(E source) {
        return serialize(source, null);
    }

    public static <E extends Enum<E>> String serialize(
            E source, Function<Object, Object> fieldValueMapper) {
        if (source == null) return null;
        Class type = source.getClass();
        try {
            Field[] fields = type.getFields();
            Map<String, Object> map = new HashMap<>(fields.length);
            for (Field field : fields) {
                if (field.getType() != type) {
                    Object value = field.get(source);
                    map.put(field.getName(), fieldValueMapper == null ?
                            value : fieldValueMapper.apply(value));
                }
            }
            map.put("value", source.toString());
            return JsonConverter.toJson(map);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E extends Enum<E>> E deserialize(Class<E> type, String source) {
        if (source == null) return null;
        String string = source.trim();
        if (string.length() == 0) return null;
        char first = string.charAt(0);
        char last = string.charAt(string.length() - 1);
        if (first == '{' && last == '}') {
            Map map = JsonConverter.toObject(string, Map.class);
            string = (String) map.get("value");
        } else if (string.length() > 1 && first == '"' && last == '"') {
            string = string.substring(1, string.length() - 1);
        }
        return E.valueOf(type, string);
    }

}
