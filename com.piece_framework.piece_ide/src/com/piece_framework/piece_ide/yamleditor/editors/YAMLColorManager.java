package com.piece_framework.piece_ide.yamleditor.editors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * YAML �J���[�}�l�[�W���[.
 * YAML �G�f�B�^�[�Ŏg�p���� Color �I�u�W�F�N�g���Ǘ�����B
 * 
 * @author Hideharu Matsufuji
 * @version 0.1.0
 * @since 0.1.0
 * 
 */
public final class YAMLColorManager {
    
    // �J���[�e�[�u��
    private Map< RGB, Color > fColorTable = 
                new HashMap< RGB, Color >();
    
    private static YAMLColorManager manager;
    
    /**
     * �R���X�g���N�^.
     */
    private YAMLColorManager() {
    }
    
    /**
     * YAML �J���[�}�l�[�W���[�̃C���X�^���X��Ԃ�.
     * 
     * @return YAML �J���[�}�l�[�W���[
     */
    public static YAMLColorManager getColorManager() {
        if (manager == null) {
            manager = new YAMLColorManager();
        }
        return manager;
    }
    
    /**
     * �w�肳�ꂽ RGB �I�u�W�F�N�g�ɑΉ����� Color �I�u�W�F�N�g��Ԃ�.
     * 
     * @param rgb RGB �I�u�W�F�N�g
     * @return Color �I�u�W�F�N�g
     */
    public Color getColor(RGB rgb) {
        Color color = (Color) fColorTable.get(rgb);
        if (color == null) {
            color = new Color(Display.getCurrent(), rgb);
            fColorTable.put(rgb, color);
        }
        return color;
    }
    
    /**
     * �ێ����Ă��� Color �I�u�W�F�N�g�����ׂĂɑ΂��āA�I���������s��.
     */
    public void dispose() {
        Iterator e = fColorTable.values().iterator();
        while (e.hasNext()) {
            ((Color) e.next()).dispose();
        }
    }
    
}
