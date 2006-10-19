package com.piece_framework.piece_ide.yamleditor.editors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * �J���[�}�l�[�W���[.
 * �^�O���[�����`����B
 * TODO:�^�O���[���̌�����(���������^�O�łȂ��H)
 * 
 * @author Hideharu Matsufuji
 * @version 0.2.0
 * @since 0.2.0
 * 
 */
public class ColorManager {
    
    // �J���[�e�[�u���̏����l
    private static final int INIT_MAP = 10;
    // �J���[�e�[�u��
    private Map< RGB, Color > fColorTable = 
                new HashMap< RGB, Color >(INIT_MAP);
    
    
    /**
     * �I���������s��.
     * �J���[�e�[�u���̂��ׂẴJ���[�̏I���������ďo���B
     */
    public void dispose() {
        Iterator e = fColorTable.values().iterator();
        while (e.hasNext()) {
            ((Color) e.next()).dispose();
        }
    }
    
    /**
     * �J���[�I�u�W�F�N�g���擾����.
     * �w�肳�ꂽRGB�̃J���[�I�u�W�F�N�g���Ȃ��ꍇ�͐V����
     * ��������B
     * 
     * @param rgb �擾����RGB
     * @return �J���[�I�u�W�F�N�g
     */
    public Color getColor(RGB rgb) {
        Color color = (Color) fColorTable.get(rgb);
        if (color == null) {
            color = new Color(Display.getCurrent(), rgb);
            fColorTable.put(rgb, color);
        }
        return color;
    }
}
