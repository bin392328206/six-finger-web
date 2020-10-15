package com.xiaoliuliu.six.finger.web.common.util;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/14 18:04
 */
public class ObjectUtil {

    public static Object convert(Class<?> targetType, String s) {
        PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
        editor.setAsText(s);
        return editor.getValue();
    }
}
