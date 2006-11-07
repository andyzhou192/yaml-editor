package com.piece_framework.piece_ide.yamleditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.piece_framework.piece_ide.yamleditor.editors.YAMLColorManager;

/**
 * �v���O�C���̃��C�t�T�C�N�����Ǘ�����.
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 * 
 */
public class Activator extends AbstractUIPlugin {

    /** �v���O�C��ID. */
    public static final String PLUGIN_ID = 
                "com.piece_framework.piece_ide.yamleditor";

    // �C���X�^���X
    private static Activator plugin;
    
    /**
     * �R���X�g���N�^.
     */
    public Activator() {
        plugin = this;
    }
    
    /**
     * �v���O�C�����J�n����.
     * 
     * @param context �R���e�L�X�g
     * @exception Exception ��ʓI�ȗ�O
     * @see org.eclipse.ui.plugin.AbstractUIPlugin
     *          #start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }
    
    /**
     * �v���O�C�����~����.
     * �J���[�}�l�[�W���[�̏I���������s��.
     * 
     * @param context �R���e�L�X�g
     * @throws Exception ��ʓI�ȗ�O
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
     * ���L�C���X�^���X���擾����.
     *
     * @return ���L�C���X�^���X
     */
    public static Activator getDefault() {
        return plugin;
    }
    
    /**
     * �w�肳�ꂽ�C���[�W�t�@�C���̃C���[�W�f�B�X�N���v�^�擾����.
     * 
     * @param path �C���[�W�t�@�C���p�X
     * @return �C���[�W�f�B�X�N���v�^
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }
}
