// $Id$
package com.piece_framework.yaml_editor.ui.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import com.piece_framework.yaml_editor.plugin.ConfigurationFactory;
import com.piece_framework.yaml_editor.plugin.IConfiguration;
import com.piece_framework.yaml_editor.plugin.IYAMLEditor;
import com.piece_framework.yaml_editor.plugin.Messages;

/**
 * YAML テキストエディター(スキーマ選択コンボボックス付).
 * YAMLEditor クラスが提供するエディター機能とスキーマ選択
 * コンボボックスを表示・管理する。
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 * @see org.eclipse.ui.part.EditorPart
 * @see YAMLEditor
 * @see org.eclipse.core.resources.IResourceChangeListener
 */
public class YAMLEditorPart extends EditorPart 
                                   implements IResourceChangeListener,
                                               IYAMLEditor {
    
    private static final int SCHEMA_COMBO_WIDTH = 200;
    private static final int SCHEMA_COMBO_HEIGHT = 50;
    
    private YAMLEditor fEditor;
    
    private Combo fSchemaCombo;
    
    private Label fSchemaFolderLabel;
    
    private IConfiguration fConfig;
    
    /**
     * コンストラクタ.
     * YAML Editor の生成及び各初期化を行う。
     *
     */
    public YAMLEditorPart() {
        super();
        fEditor = new YAMLEditor();
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
        
        // TODO: デバッグ
        System.out.println("construct"); //$NON-NLS-1$
    }

    /**
     * テキストを保存するときの処理を行う.
     * YAMLEditor クラスの doSave メソッドを呼出す。
     * 
     * @param monitor プログレスモニタ
     * @see YAMLEditor#doSave(IProgressMonitor)
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        fEditor.setYAMLSchemaFile(getSelectionYAMLSchemaFile());
        YAMLSchemaManager.setSchemaFileForYAML(
                getYAMLFile(), getSelectionYAMLSchemaFile());
        fEditor.doSave(monitor);
    }

    /**
     * ファイル名を指定してテキストを保存するときの処理を行う.
     * YAMLEditor クラスの doSaveAs メソッドを呼出す。
     * 
     * @see YAMLEditor#doSaveAs()
     */
    @Override
    public void doSaveAs() {
        fEditor.setYAMLSchemaFile(getSelectionYAMLSchemaFile());
        YAMLSchemaManager.setSchemaFileForYAML(
                getYAMLFile(), getSelectionYAMLSchemaFile());
        fEditor.doSaveAs();
    }
    
    /**
     * 初期化を行う.
     * 
     * @param site エディターサイト
     * @param input エディターインプット
     * @throws PartInitException 初期化時例外
     */
    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        
        setSite(site);
        setInput(input);
        setPartName(input.getName());
        
        // TODO: デバッグ   
        System.out.println("init"); //$NON-NLS-1$
    }

    /**
     * 終了処理を行う.
     */
    @Override
    public void dispose() {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
        super.dispose();
    }

    /**
     * 最終保存時から変更されたかを返す.
     * YAMLEditor クラスの doSaveAs メソッドを呼出す。
     * 
     * @return 最終保存時から変更されたか
     * @see YAMLEditor#isDirty()()
     */
    @Override
    public boolean isDirty() {
        return fEditor.isDirty();
    }

    /**
     * テキストを保存できるかを返す.
     * 
     * @return テキストを保存できるか
     */
    @Override
    public boolean isSaveAsAllowed() {
        return true;
    }

    /**
     * 各コントロールの生成・配置を行う.
     * コントロールのサイズ修正は ControlListener イベントを利用する。
     * 
     * @param parent 親コントロール
     */
    @Override
    public void createPartControl(Composite parent) {
        // TODO: デバッグ
        System.out.println("createPartControl"); //$NON-NLS-1$
        
        try {

            RowLayout parentLayout = new RowLayout(SWT.VERTICAL);
            parentLayout.marginRight = 0;
            parentLayout.marginLeft = 0;
            parentLayout.marginTop = 0;
            parentLayout.marginBottom = 0;
            parentLayout.marginHeight = 0;
            parentLayout.marginWidth = 0;
            
            final Composite parentComposite = new Composite(parent, SWT.NONE);
            parentComposite.setLayout(parentLayout);
            
            RowLayout schemaLayout = new RowLayout(SWT.HORIZONTAL);
            schemaLayout.marginRight = 0;
            schemaLayout.marginLeft = 0;
            schemaLayout.marginTop = 0;
            schemaLayout.marginBottom = 0;
            schemaLayout.marginHeight = 0;
            schemaLayout.marginWidth = 0;
            schemaLayout.spacing = 5;
            
            Group schemaGroup = new Group(parentComposite, SWT.NONE);
            schemaGroup.setText(
                Messages.getString("YAMLEditorPart.SchemaTitle")); //$NON-NLS-1$
            schemaGroup.setLayout(schemaLayout);
            
            fSchemaCombo = new Combo(schemaGroup, SWT.READ_ONLY);
            fSchemaCombo.setLayoutData(new RowData(SCHEMA_COMBO_WIDTH,
                                                  SCHEMA_COMBO_HEIGHT));
            
            fSchemaFolderLabel = new Label(schemaGroup, SWT.NONE);
            RGB rgb = new RGB(255, 255, 255);
            fSchemaFolderLabel.setBackground(
                    YAMLColorManager.getColorManager().getColor(rgb));
            
            FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            fillLayout.spacing = 0;
            
            final Composite editorComposite = 
                            new Composite(parentComposite, SWT.NONE);
            editorComposite.setLayout(fillLayout);
            
            fEditor.init(getEditorSite(), getEditorInput());
            fEditor.addPropertyListener(new IPropertyListener() {
                public void propertyChanged(Object source, int propertyId) {
                    firePropertyChange(propertyId);
                }
            });
            
            fEditor.createPartControl(editorComposite);
            
            parent.addControlListener(new ControlListener() {

                public void controlMoved(ControlEvent e) {
                }

                public void controlResized(ControlEvent e) {
                    
                    Point parentSize = parentComposite.getParent().getSize();
                    
                    // TODO: コンボボックスのサイズ修正
                    
                    editorComposite.setLayoutData(
                            new RowData(parentSize.x, 
                                        parentSize.y - SCHEMA_COMBO_HEIGHT));
                    
                }
                
            });
            
        } catch (PartInitException e) {
            // TODO: 例外処理
            e.printStackTrace();
        }
        
        // プロジェクト設定を取得
        fConfig = ConfigurationFactory.getConfiguration(getYAMLProject());
        
        // スキーマフォルダーをセット
        fSchemaFolderLabel.setText(
                fConfig.get(IConfiguration.KEY_SCHEMAFOLDER));
        
        //スキーマファイルの一覧をコンボボックスにセット
        setSchemaFileList();
        
        
    }
    
    /**
     * フォーカスを取得したときの処理を行う.
     * 
     */
    @Override
    public void setFocus() {
        fEditor.setFocus();

    }

    /**
     * リソースが変更された場合の処理を行う.
     * 
     * @param event リソース変更イベント
     */
    public void resourceChanged(IResourceChangeEvent event) {
        
        if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
            
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    IFile yamlFile = getYAMLFile();
                    if (yamlFile != null) {
                        if (!yamlFile.exists()) {
                            IWorkbenchPage page = 
                                PlatformUI.getWorkbench().
                                getActiveWorkbenchWindow().getActivePage();
                            page.closeEditor(YAMLEditorPart.this, false);
                            
                        } else if (!getPartName().equals(yamlFile.getName())) {
                            setPartName(yamlFile.getName());
                        }
                    }
                }
            });
        }
        
    }
    
    /**
     * 指定されたアダプターに対してオブジェクトを返す.
     * 
     * @param adapter アダプター
     * @return オブジェクト
     */
    @Override
    public Object getAdapter(Class adapter) {
        return fEditor.getAdapter(adapter);
    }
    
    
    
    public void changeProperty() {
        //スキーマフォルダーをセット
        fSchemaFolderLabel.setText(
                fConfig.get(IConfiguration.KEY_SCHEMAFOLDER));
        
        fSchemaFolderLabel.redraw();
        
        System.out.println("changeProperty!!:" + fConfig.get(IConfiguration.KEY_SCHEMAFOLDER));
        
    }

    /**
     * 編集中の YAML ファイルが所属するプロジェクトを返す.
     *  
     * @return プロジェクト
     */
    private IProject getYAMLProject() {
        IProject project = null;
        
        if (getEditorInput() != null) {
            IFile yamlFile = ((IFileEditorInput) getEditorInput()).getFile();
            project = yamlFile.getProject();
        }
        
        return project;
    }
    
    /**
     * 編集中の YAML ファイルを返す.
     * 
     * @return YAML ファイル
     */
    private IFile getYAMLFile() {
        
        IEditorInput input = fEditor.getEditorInput();
        IFile yamlFile = null;
        
        if (input != null) {
            yamlFile = ((IFileEditorInput) input).getFile();
        }
        
        return yamlFile;
    }


    /**
     * YAML スキーマファイル一覧を取得して、コンボボックスにセットする.
     *
     */
    private void setSchemaFileList() {
        
        if (fSchemaCombo == null) {
            return;
        }
        
        // スキーマファイルの取得
        IFile[] schemaFiles = 
                    YAMLSchemaManager.getSchemaFiles(getYAMLProject());
        
        // YAML ファイルに対応するスキーマファイルを取得
        IFile schemaFileForYAML = 
                    YAMLSchemaManager.getSchemaFileForYAML(getYAMLFile());
        
        // スキーマ選択メッセージ行の追加
        fSchemaCombo.add(Messages.getString(
                "YAMLEditorPart.SelectSchemaMessage")); //$NON-NLS-1$
        fSchemaCombo.select(0);
        
        if (schemaFiles != null) {
            for (int i = 0; i < schemaFiles.length; i++) {
                fSchemaCombo.add(schemaFiles[i].getName());
                
                if (schemaFileForYAML != null) {
                    if (schemaFiles[i].getFullPath().toString().equals(
                            schemaFileForYAML.getFullPath().toString())) {
                        
                        fSchemaCombo.select(i + 1);
                    }
                }
            }
        }
        
    }
    
    /**
     * コンボボックスで選択された YAML スキーマファイルを返す.
     * 
     * @return YAML スキーマファイル
     */
    private IFile getSelectionYAMLSchemaFile() {
        
        IFile schemaFile = null;
        
        // 先頭はスキーマ選択メッセージ行なので、
        // 実際の YAML スキーマファイルは2行目から
        if (fSchemaCombo.getSelectionIndex() > 0) {
            String schemaFileName = fSchemaCombo.getItem(
                    fSchemaCombo.getSelectionIndex());
    
            schemaFile = getYAMLProject().getFile(
                YAMLSchemaManager.SCHEMA_FOLDER + "/"  //$NON-NLS-1$
                + schemaFileName);
            
            if (!schemaFile.exists()) {
                schemaFile = null;
            }
            
        }
        
        return schemaFile;
    }
    
}
