// $Id$
package com.piece_framework.yaml_editor.plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * プラグインのライフサイクルを管理する.
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 * 
 */
public class YAMLEditorPlugin extends AbstractUIPlugin {

    /** プラグインID. */
    public static final String PLUGIN_ID = 
                "com.piece_framework.yaml_editor"; //$NON-NLS-1$

    // インスタンス
    private static YAMLEditorPlugin fPlugin;
    
    // プラグインレベルで管理するオブジェクト
    private Map<Object, IPluginCycle> fPluginCycleObjects;
    
    /**
     * コンストラクタ.
     */
    public YAMLEditorPlugin() {
        fPlugin = this;
        fPluginCycleObjects = new HashMap<Object, IPluginCycle>();
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
        
        Collection<IPluginCycle> c = fPluginCycleObjects.values();
        Iterator<IPluginCycle> ite = c.iterator();
        while (ite.hasNext()) {
            IPluginCycle cycleObject = ite.next();
            cycleObject.dispose();
        }
        
        fPlugin = null;
        super.stop(context);
    }
    
    /**
     * 共有インスタンスを取得する.
     *
     * @return 共有インスタンス
     */
    public static YAMLEditorPlugin getDefault() {
        return fPlugin;
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
    
    /**
     * プラグインサイクルオブジェクトを追加する.
     * 
     * @param key キー
     * @param object プラグインサイクルオブジェクト
     */
    public void addPluginCycleObject(Object key, IPluginCycle object) {
        fPluginCycleObjects.put(key, object);
    }
    
    /**
     * プラグインサイクルオブジェクトを取得する.
     * 
     * @param key キー
     * @return プラグインサイクルオブジェクト
     */
    public IPluginCycle getPluginCycleObject(Object key) {
        return fPluginCycleObjects.get(key);
    }
    
    /**
     * プラグインサイクルオブジェクトを削除する.
     * 
     * @param key キー
     */
    public void removePluginCycleObject(Object key) {
        fPluginCycleObjects.remove(key);
    }
    
    /**
     * 設定の変更をすべてのエディターに通知する.
     *
     */
    public void notifyPropertyChanged() {
        
        IWorkbenchPage page = 
            getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IEditorReference[] editors = page.getEditorReferences();
        
        for (int i = 0; i < editors.length; i++) {
            IEditorPart editor = editors[i].getEditor(true);
            
            if (editor != null) {
                if (editor instanceof IYAMLEditor) {
                    ((IYAMLEditor) editor).changeProperty();
                }
            }
        }
    }
    
    /**
     * エラーダイアログ表示.
     * エラーメッセージダイアログを表示させる。
     * 
     * @param errorLevel エラーレベル
     * @param exception 例外
     */
    public static void showDialog(int errorLevel,
                                    Throwable exception) {
        showDialog(new Status(errorLevel,
                              YAMLEditorPlugin.PLUGIN_ID,
                              1,
                              Messages.getString(
                                "ErrorMessageDialog.MessageException"),
                              exception));
    }
    
    /**
     * エラーダイアログ表示.
     * エラーメッセージダイアログを表示させる。
     * 
     * @param status エラーステータス
     */
    public static void showDialog(IStatus status) {
        ErrorDialog.openError(
               Display.getCurrent().getActiveShell(),
               Messages.getString("ErrorMessageDialog.WindowTitle"),
               Messages.getString("ErrorMessageDialog.MessageTitle"),
               status);
    }
}
