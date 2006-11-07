package com.piece_framework.piece_ide.yamleditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.piece_framework.piece_ide.yamleditor.editors.YAMLColorManager;

/**
 * プラグインのライフサイクルを管理する.
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 * 
 */
public class Activator extends AbstractUIPlugin {

    /** プラグインID. */
    public static final String PLUGIN_ID = 
                "com.piece_framework.piece_ide.yamleditor";

    // インスタンス
    private static Activator plugin;
    
    /**
     * コンストラクタ.
     */
    public Activator() {
        plugin = this;
    }
    
    /**
     * プラグインを開始する.
     * 
     * @param context コンテキスト
     * @exception Exception 一般的な例外
     * @see org.eclipse.ui.plugin.AbstractUIPlugin
     *          #start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }
    
    /**
     * プラグインを停止する.
     * カラーマネージャーの終了処理を行う.
     * 
     * @param context コンテキスト
     * @throws Exception 一般的な例外
     * @see org.eclipse.ui.plugin.AbstractUIPlugin
     *          #stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        YAMLColorManager.getColorManager().dispose();
        System.out.println("stop()");
        plugin = null;
        super.stop(context);
    }
    
    /**
     * 共有インスタンスを取得する.
     *
     * @return 共有インスタンス
     */
    public static Activator getDefault() {
        return plugin;
    }
    
    /**
     * 指定されたイメージファイルのイメージディスクリプタ取得する.
     * 
     * @param path イメージファイルパス
     * @return イメージディスクリプタ
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }
}
