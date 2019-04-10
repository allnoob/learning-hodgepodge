### 模拟HttpPatch请求（map中的数据塞入object）
```java
private <T> void fill(T previousObject, Map<String, Object> payload) {
    payload.forEach((key, value) -> {
        if (value instanceof Map) {
            try {
                Field keyField = previousObject.getClass().getDeclaredField(key);
                keyField.setAccessible(true);
                if (keyField.getType().equals(Map.class)) {
                    keyField.set(previousObject, value);
                    return;
                }
                
                Object keyObject = keyField.get(previousObject);
                if (keyObject == null) {
                    keyObject = Class.forName(keyField.getType().getTypeName()).newInstance();
                }

                comparedAndUpdate(keyObject, (Map<String, Object>) value);
                
                keyField.set(previousObject, keyObject);
            } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                // log.warn
                // 跳过当前字段，并报错
                return;
            }
        } else {
            try {
                Field keyField = previousObject.getClass().getDeclaredField(key);
                keyField.setAccessible(true);
                keyField.set(previousObject, getValue(keyField.getType(), value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // log.warn
                // 跳过当前字段，并报错
                return;
            }
        }
    });
}

private Object getValue(Class fieldClazz, Object value) {
    if (fieldClazz.isEnum()) {
        return Enum.valueOf(fieldClazz, value.toString());
    }
    return value;
}
```
