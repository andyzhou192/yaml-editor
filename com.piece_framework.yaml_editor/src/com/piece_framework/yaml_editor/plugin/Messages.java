// $Id$
package com.piece_framework.yaml_editor.plugin;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 外部文字列アクセサクラス.
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 */
public final class Messages {
    
    private static final String BUNDLE_NAME = //$NON-NLS-1$
      "com.piece_framework.yaml_editor.plugin.messages"; 
    
    private static final ResourceBundle RESOURCE_BUNDLE = 
            ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * コンストラクタ.
     *
     */
    private Messages() {
    }

    /**
     * 指定されたキーに対応する文字列を返す.
     * 
     * @param key キー
     * @return キーに対応する文字列
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}